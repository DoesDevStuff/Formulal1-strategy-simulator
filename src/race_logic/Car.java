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

public class Car extends Thread {
// These are class members {
	//the keyword "volatile" tells the compiler that the value of a variable must never be "cached"
	//as its value may change outside of the scope of the program
	//read more on "volatile" in the context of multi-threading + multiprocessor
	private static volatile boolean startFlag = false;
	private static volatile boolean terminateFlag = false;

	//MainClass.terminateThreadsAfterSeconds tells how many seconds a thread will run
	private static int runGranularityMs = 2000; //loop wait, see the while loop inside run(); 2 secs

	
	// these are static variable so every instance will hold the exact same value when in use
	// these will be set by the start race method. This means that every car will have the same start time	
	public long raceTimeAtStart = 0;  //this thread started business logic at ... (milliseconds)
	public static boolean isRaceStarted = false;
	
	//starts all threads. note the static
	public static void startAllThreads() {
		startFlag = true;
	}

	//terminates all threads. note the static
	public static void terminateAllThreads() {
		terminateFlag = true;
	}
// These are class members }
	
	// instance members start here:
	int carID; // every car will have a unique id starting from 1
	ArrayList<Car> totalCars = null; //reference to total cars array in controller	
	ArrayList<Car> carLane = null;   //reference to the lane this car is in
	
	public int iterationNumner = 0;
	public boolean isNitroUsed = false;
	
	// final variables; these are set only once
	// since we are not initialising him here we must do so in the constructor
	public final double topSpeed;
	public final double acceleration;
	
	// given that we are checking every 2 seconds we would also want to have each car keep track of time
	// this is because our calculation is going to be discrete and we would want more granularity within our car
	// this will use the system time for now. to calculate next time and current time
	public double prevElapsedTime = 0;
	public double elapsedTime = 0;
	public double currentDistTravelled;
	public double currentSpeed;
    public double finishTime = 0;
	
	// Constructor for Car
	public Car(int carID, ArrayList<Car> carLane, ArrayList<Car> totalCars, double startPosition){
		this.carID = carID;
		this.totalCars = totalCars;
		this.carLane = carLane;
		
		topSpeed = (5.0 * (Constants.BASE_SPEED + (Constants.SPEED_DIFF * carID)) ) / 18.0;;
		acceleration = Constants.ACCELERATION_DIFF * carID; // the problem statement tells us that it is based on car id
		
		// the start position for each car will be in negatives because each car should start behind the start line
		currentDistTravelled = startPosition;
		
		this.carLane.add(this); // adding value of each lane reference to the carLane here. (from controller, line 25)		
	}	

	public void run() {
		try {
			System.out.println("Car: Car " + carID + " in Run method, waiting to start business logic");
			Thread.sleep(runGranularityMs);
			
			while(!startFlag) {} // wait for start flag
			
			System.out.println("Car: Car " + carID + " started running business logic");
			
			raceTimeAtStart = System.currentTimeMillis();
			isRaceStarted = true;			
			
			while(!terminateFlag) {
				// Calls calculateTimeBased_SpeedDistanceTravelled and passes the evaluation period (runGranularityMs)
				calculateTimeBased_SpeedDistanceTravelled(runGranularityMs);
				
				Thread.sleep(runGranularityMs);
			}
			
			System.out.println("Mythread: Thread " + carID + " terminated with terminate flag");
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/*
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
	*/
	
	// this method does it's calculation based on time since we are doing a discrete calculation.
	// we will have 2 evaluations for time. One for the controller and the other for the main program interval
	// the controller will calculate the time intervals passed for the car (we have been asked to check every 2 seconds)
	// whereas for the main program interval so that will be threaded, this  could be in fractions of a second for each thread
	public boolean calculateTimeBased_SpeedDistanceTravelled(long evalTimeInMilliSeconds) {
/*
		if (!isRaceStarted) {	// race has not begun yet
			return false;
		}
*/
		
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
		
		//
		++iterationNumner;
		System.out.println("---- before --------");
		System.out.println("iterationNumner: " + iterationNumner);
		System.out.println("CAR ID " + carID);
		System.out.println("Elapsed time: " + elapsedTime);
		System.out.println("DISTANCE COVERED: " + currentDistTravelled);
		System.out.println("NITROUS USED: " + isNitroUsed);
		System.out.println("ACCELERATION: " + acceleration);
		System.out.println("TOP SPEED: " + topSpeed);
		System.out.println("CURRENT SPEED: " + currentSpeed);
		System.out.println("---- before --------");
		System.out.println();
		//		
		
		// check if it's the beginning of the race and if so set the  evaluation time to the race start time (t0)
		if (prevElapsedTime == 0) {
			prevElapsedTime = raceTimeAtStart;
		}
		
		elapsedTime = (evalTimeInMilliSeconds - prevElapsedTime) / Constants.SECONDS_TO_MILLISECONDS; // elapsed time in seconds
		prevElapsedTime = evalTimeInMilliSeconds; // time at previous moves to time at current

		currentSpeed = currentSpeed + (acceleration * elapsedTime); // v1 = u1 + a * tdi
		
		useNitro();
		limitToTopSpeed();
		reduceSpeedIfPossibleCollision();

		currentDistTravelled = currentDistTravelled + (currentSpeed * elapsedTime); //  s = s0 + v1 * tdi
		
		//
		System.out.println("---- after --------");
		System.out.println("iterationNumner: " + iterationNumner);
		System.out.println("CAR ID " + carID);
		System.out.println("Elapsed time: " + elapsedTime);
		System.out.println("DISTANCE COVERED: " + currentDistTravelled);
		System.out.println("NITROUS USED: " + isNitroUsed);
		System.out.println("ACCELERATION: " + acceleration);
		System.out.println("TOP SPEED: " + topSpeed);
		System.out.println("CURRENT SPEED: " + currentSpeed);
		System.out.println("---- after --------");
		System.out.println();
		//		

		// if Car has crossed the finish line i.e travelled the race length then remove it from the track
		if(currentDistTravelled >= Constants.RACE_LENGTH_METRES) {
			carLane.remove(this);
	        finishTime = evalTimeInMilliSeconds; // Record the finish time
		}
		
		return true;
	}
	

	public boolean limitToTopSpeed() {
	    if (this.currentSpeed > this.topSpeed) {
	        this.currentSpeed = this.topSpeed;
	        System.out.println("Car " + this.carID + " speed limited to top speed");
	    }

        return true;
	}

	 /*
	 * Here we first check if the car is in the same lane as the car we are checking against
	 * also since we are alternating the cars in lanes we must check if  otherCar is following the same alternating pattern i.e frontCar.carID == this.carID + 1
	 * This ensures that each car is checking for the distance to the car immediately in front of it in the same lane
	 */
	public boolean reduceSpeedIfPossibleCollision() {
		// logic to reduce the speed if we detect possible collision
		Car frontCar = findCarInFront();
		
		if (frontCar == null)
			return false;

	        double proximityWithFrontCar = frontCar.currentDistTravelled - (this.currentDistTravelled);

	        if (proximityWithFrontCar <= Constants.COLLISION_RANGE && proximityWithFrontCar >= 0) {
	            this.currentSpeed *= Constants.REDUCE_SPEED_FACTOR;
	            System.out.println("Car " + this.carID + " reduced speed due to proximity with the car in front to: " + this.currentSpeed);
	    }

	    return true;		
	}
	
	

	private Car findCarInFront() {
	    int myIndex = carLane.indexOf(this);
	    //int laneNumber = (currentIndex % 2 == 0) ? 1 : 2; // Determine the lane number based on even or odd index

	    return (myIndex <= 0) ? null : carLane.get(myIndex - 1);
/*
	    for (int i = currentIndex - 1; i >= 0; i--) {
	        Car frontCar = carLane.get(i);
	        // Check if the other car is in the same lane
	        if (frontCar.carLane == this.carLane && (i % 2 == laneNumber - 1)) {
	            return frontCar;
	        }
	    }
*/

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
		// return if nitro has already been used or if the car is not the last in both lanes
	    if ( (isNitroUsed) || (!isLastCarInBothLanes()) ) { 
	        return false;
	    }

	    // Use Nitro only once
	    isNitroUsed = true;

	    // Boost the speed to double the current speed or top speed, whichever is less
	    //double nitroBoost = Math.min(this.currentSpeed * 2, this.topSpeed);
	    this.currentSpeed = this.currentSpeed * 2;
	    System.out.println("Car " + this.carID + " used Nitro!");

	    return true;
	}

	// Helper method to check if this car is the last in both lanes
	private boolean isLastCarInBothLanes() {
	    int currentLaneIndex = totalCars.indexOf(this);
	    int laneSize = totalCars.size();

	    return currentLaneIndex >= (laneSize - 1);
	}
   
}