package race_logic;

import java.util.ArrayList;

/*
 * Description:
 * This class holds all of the car information.
 * Includes - 
 * 1. Car value definitions
 * 2. Individual calculations of distance and speed by each car 
 * 
 */

public class Car {
	// these are static variable so every instance will hold the exact same value when in use
	// these will be set by the start race method. This means that every car will have the same start time	
	public static long raceTimeAtStart = 0;
	public static boolean isRaceStarted = false;
	
	int carID; // every car will have a unique id starting from 1
	
	ArrayList<Car> carLane; // cars start in two lanes. This will tell us which id is on what track.
	
	public boolean isNitroUsed = false;
	
	// final variables; these are set only once
	// since we are not initialising him here we must do so in the constructor
	public final double topSpeed;
	public final double acceleration;
	
	// given that we are checking every 2 seconds we would also want to have each car keep track of time
	// this is because our calculation is going to be discrete and we would want more granularity within our car
	// this will use the system time for now. to calculate next time and current time
	public double prevElapsedTime_eval = 0;
	public double elapsedTime = 0;
	public double currentDistTravelled;
	public double currentSpeed;
	
	// Constructor for Car
	public Car(int carID, ArrayList<Car> carLane, double startPosition){
		this.carID = carID;
		this.carLane = carLane;
		
		topSpeed = (5.0 * (Constants.BASE_SPEED + (Constants.SPEED_DIFF * carID)) ) / 18.0;;
		acceleration = Constants.ACCELERATION_DIFF * carID; // the problem statement tells us that it is based on car id
		
		// the start position for each car will be in negatives because each car should start behind the start line
		currentDistTravelled = startPosition;
		
		this.carLane.add(this); // adding value of each lane reference to the carLane here. (from controller, line 25)		
	}	
	
	// this will start the race for all of the car instances
	// it will take in an argument of time - system time
	public static boolean AtRaceStart(long startRaceTime){
		// if the race has started then return false
		if(isRaceStarted){ 
			return false;
		}
		
		Car.raceTimeAtStart = startRaceTime; // race time at the start will be set here and will be the same for every instance
		isRaceStarted = true;
		return true;
	}
	
	
	// this method does it's calculation based on time since we are doing a discrete calculation.
	// we will have 2 evaluations for time. One for the controller and the other for the main program interval
	// the controller will calculate the time intervals passed for the car (we have been asked to check every 2 seconds)
	// whereas for the main program interval so that will be threaded, this  could be in fractions of a second for each thread
	public boolean calculateTimeBased_SpeedDistanceTravelled(long evalTimeInMilliSeconds){
		if(!isRaceStarted){	// race has not begun yet
			return false;
		}
		/*
		 * Since this is a discrete calculation with time we also have tdi = discrete time between intervals
		 * and velocity changes based on our distance to next car (proximity)
		 * u = initial velocity, v = final velocity, s = displacement, t = time, a = acceleration  
		 * 
		 * 
		 * now distance = speed x time; v = u + at 
		 * 
		 * At t0 , u0 = 0, v0 = 0, s0 = 0
		 * 
		 * t1, u1 = v0, v1 = u1 + a * tdi, s = s0 + v1 * tdi   // here distance is previous distance + next distance based on discrete time interval
		 * t2, u2 = v1, v2 = u2 + a * tdi, s = s1 + v2 * tdi
		 * and so on....
		 * */
		
		// check if it's the beginning of the race and if so set the  evaluation time to the race start time (t0)
		if(prevElapsedTime_eval == 0){
			prevElapsedTime_eval = Car.raceTimeAtStart;
		}
		
		elapsedTime = (evalTimeInMilliSeconds - prevElapsedTime_eval) / Constants.SECONDS_TO_MILLISECONDS; // elapsed time in seconds
		prevElapsedTime_eval = evalTimeInMilliSeconds; // time at previous moves to time at current
		
		currentSpeed = currentSpeed + (acceleration * elapsedTime); // v1 = u1 + a * tdi
		currentDistTravelled = currentDistTravelled + (currentSpeed * elapsedTime); //  s = s0 + v1 * tdi
		
		useNitro();
		reduceSpeed();
		
		// if Car has crossed the finish line i.e travelled the race length then remove it from the track
		if(currentDistTravelled >= Constants.RACE_LENGTH_METRES) {
			carLane.remove(this);
		}
		
		return true;
	}
	
	/*
	 * Here we first check if the car is in the same lane as the car we are checking against
	 * also since we are alternating the cars in lanes we must check if  otherCar is following the same alternating pattern i.e frontCar.carID == this.carID + 1
	 * This ensures that each car is checking for the distance to the car immediately in front of it in the same lane
	 */
	public boolean reduceSpeed(){
		if(!isRaceStarted){	// race has not begun yet
			return false;
		}
		// logic to reduce the speed if we detect possible collision
		Car frontCar = findCarInFront();

	    if (frontCar != null) {
	        double proximity = this.currentDistTravelled - frontCar.currentDistTravelled;

	        if (proximity > 0 && proximity <= Constants.COLLISION_RANGE) {
	            this.currentSpeed *= Constants.REDUCE_SPEED_FACTOR;
	            System.out.println("Car " + this.carID + " reduced speed due to proximity with the car in front");
	        }
	    }

	    if (this.currentSpeed > this.topSpeed) {
	        this.currentSpeed = this.topSpeed;
	        System.out.println("Car " + this.carID + " speed limited to top speed");
	    }

	    return true;		
	}

	private Car findCarInFront() {
	    int currentIndex = carLane.indexOf(this);
	    int laneNumber = (currentIndex % 2 == 0) ? 1 : 2; // Determine the lane number based on even or odd index

	    for (int i = currentIndex - 1; i >= 0; i--) {
	        Car frontCar = carLane.get(i);
	        // Check if the other car is in the same lane
	        if (frontCar.carLane == this.carLane && (i % 2 == laneNumber - 1)) {
	            return frontCar;
	        }
	    }

	    return null; // No car in front
	}
	
	/*
	 * If the current car is the last in both lanes and not in the same position as another car, Nitro is used.
	 * Nitro is used ONLY ONCE
	 * Speed is boosted to double the current speed or the top speed, whichever is less.
	 * 
	 * If the other car is in the same position, the loop breaks, and the current car does not use Nitro. 
	 * This ensures that only one car uses Nitro when they are in the same position in different lanes.
	 */
	public boolean useNitro() {
	    if (!isRaceStarted) {    // race has not begun yet
	        return false;
	    }

	    // Logic to add nitro if the car is lagging behind

	    if (isNitroUsed || !isLastCarInBothLanes()) { // checks if nitro has been used or if the car is not the last in both lanes
	        return false;
	    }

	    // Use Nitro only once
	    isNitroUsed = true;

	    // Boost the speed to double the current speed or top speed, whichever is less
	    double nitroBoost = Math.min(this.currentSpeed * 2, this.topSpeed);
	    this.currentSpeed = nitroBoost;
	    System.out.println("Car " + this.carID + " used Nitro!");
	    return true;
	}

	// Helper method to check if this car is the last in both lanes
	private boolean isLastCarInBothLanes() {
	    int currentLaneIndex = carLane.indexOf(this);
	    int laneSize = carLane.size();

	    // Check if this car is the last in its lane
	    if (currentLaneIndex == laneSize - 1) {
	        // Check if this car is the last in the other lane
	        for (int i = 0; i < laneSize; i++) {
	            Car otherCar = carLane.get(i);
	            if (otherCar != this && otherCar.currentDistTravelled <= this.currentDistTravelled) {
	                return false;
	            }
	        }
	        return true;
	    }

	    return false;
	}
   
}