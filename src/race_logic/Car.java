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
	private volatile boolean terminateFlag = false;

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
	public void terminateAllThreads() {
		terminateFlag = true;
	}
// These are class members }
	
	// instance members start here:
	int carID; // every car will have a unique id starting from 1
	volatile ArrayList<Car> totalCars = null; //reference to total cars array in controller	
	volatile ArrayList<Car> carLane = null;   //reference to the lane this car is in
	
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

	double elapsedTimeSeconds;
	public double currentDistTravelled;
	public double currentSpeed;
    public double finishTime = 0;
    public double totalTimeTakenSeconds = 0;
    
	
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
			Sleep.sleepInterval(2500);
			
			while(!startFlag) {} // wait for start flag
			
			System.out.println("Car: Car " + carID + " started running business logic");
			
			raceTimeAtStart = System.currentTimeMillis();
			isRaceStarted = true;			
			
			while(!terminateFlag) {
				// Calls calculateTimeBased_SpeedDistanceTravelled and passes the evaluation period (runGranularityMs)
				calculateTimeBased_SpeedDistanceTravelled(runGranularityMs);
				
				Sleep.sleepInterval(2500);
			}
			
			System.out.println("Mythread: Thread " + carID + " terminated with terminate flag");
			Sleep.sleepInterval(2500);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		finally {
			System.out.println("exiting run() method");
		}
	}

	// the string block will be good in the event I have more messages to be printed in which case they can be called with a messageCode
	// Also really useful when printing translated messages, thus providing a little more language accessibility
	public String MessageBlock(int messageCode) {
	    String message = ""; // Initialise an empty string

	    switch (messageCode) {
	        case 1:
	            message = " speed limited to top speed";
	            break;
	        case 2:
	            message = " reduced speed due to proximity with the car in front to: ";
	            break;
	        case 3:
	            message = " used Nitro!";
	            break;
	        default:
	            break;
	    }

	    return message;
	}
	
	public boolean calculateTimeBased_SpeedDistanceTravelled(long evalTimeInMilliSeconds) {
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
		StringBuffer stringBuffer = new StringBuffer();
		++iterationNumner;
		stringBuffer.append("---- before --------\n");
		stringBuffer.append("iterationNumner: " + iterationNumner + "\n");
		stringBuffer.append("CAR ID " + carID + "\n");
		stringBuffer.append("Elapsed time: " + elapsedTimeSeconds + "\n");
		stringBuffer.append("DISTANCE COVERED: " + currentDistTravelled + "\n");
		stringBuffer.append("NITROUS USED: " + isNitroUsed + "\n");
		stringBuffer.append("ACCELERATION: " + acceleration + "\n");
		stringBuffer.append("TOP SPEED: " + topSpeed + "\n");
		stringBuffer.append("CURRENT SPEED: " + currentSpeed + "\n");
		stringBuffer.append("---- before --------\n");
		System.out.println(stringBuffer. toString());
		//		
		
		//elapsedTime is the granularity milliseconds
		elapsedTime = evalTimeInMilliSeconds; 

		//convert to elapsed time in seconds
		elapsedTimeSeconds = elapsedTime / Constants.SECONDS_TO_MILLISECONDS;		

		currentSpeed = currentSpeed + (acceleration * elapsedTimeSeconds); // v1 = u1 + a * tdi
		
		useNitro();
		limitToTopSpeed();
		reduceSpeedIfPossibleCollision();


		currentDistTravelled = currentDistTravelled + (currentSpeed * elapsedTimeSeconds); //  s = s0 + v1 * tdi
		
		//
		stringBuffer = new StringBuffer();
		stringBuffer.append("---- after --------\n");
		stringBuffer.append("iterationNumner: " + iterationNumner + "\n");
		stringBuffer.append("CAR ID " + carID + "\n");
		stringBuffer.append("Elapsed time: " + elapsedTimeSeconds + "\n");
		stringBuffer.append("DISTANCE COVERED: " + currentDistTravelled + "\n");
		stringBuffer.append("NITROUS USED: " + isNitroUsed + "\n");
		stringBuffer.append("ACCELERATION: " + acceleration + "\n");
		stringBuffer.append("TOP SPEED: " + topSpeed + "\n");
		stringBuffer.append("CURRENT SPEED: " + currentSpeed + "\n");
		stringBuffer.append("---- after --------\n");
		System.out.println(stringBuffer. toString());
		//		

		// if Car has crossed the finish line i.e travelled the race length then remove it from the track
		if(currentDistTravelled >= Constants.RACE_LENGTH_METRES) {
			carLane.remove(this);
	        finishTime = System.currentTimeMillis(); // Record the finish time
	        totalTimeTakenSeconds = (finishTime - raceTimeAtStart) / Constants.SECONDS_TO_MILLISECONDS;
	        terminateFlag = true;
		}
		
		return true;
	}
	
	public boolean limitToTopSpeed() {
	    if (this.currentSpeed > this.topSpeed) {
	        this.currentSpeed = this.topSpeed;
	        System.out.println("Car " + this.carID + MessageBlock(1));
	        return true;
	    }

        return false;
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
	            System.out.println("Car " + this.carID + MessageBlock(2) + this.currentSpeed);

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
	    System.out.println("Car " + this.carID + MessageBlock(3));
	    return true;
	}

	// Helper method to check if this car is the last in both lanes
	private boolean isLastCarInBothLanes() {
	    int currentLaneIndex = totalCars.indexOf(this);
	    int laneSize = totalCars.size();

	    return currentLaneIndex >= (laneSize - 1);
	}
   
}
