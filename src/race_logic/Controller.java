package race_logic;

import java.util.ArrayList;

public class Controller {
	ArrayList<Car> totalCars = new ArrayList<Car>();
	
	ArrayList<Car> lane1 = new ArrayList<Car>(); // will hold all odd indexed cars
	ArrayList<Car> lane2 = new ArrayList<Car>(); // will hold all even indexed cars
	
	public boolean startRacingNow(){
		System.out.println("======================================INITIALISING======================================================");
		
		initialiseController(); // initialise controller
		
		System.out.println("======================================STARTING RACE=====================================================");
		startRace(); // start and complete race
		
		System.out.println("======================================RACE FINISHED=====================================================");
		// Print winner and car stats
	    printRaceResults(totalCars);

		
		return true;
	}
	
	public boolean initialiseController(){
		// This will alternate for the lanes and help assign the correct alternate car index between the two lanes
		boolean isFirstLane = true;
		
		// This will be a temporary value that holds the reference to what lane we have switched to (i.e Lane 1 or Lane 2)
		// we will the pass this  to the car for each id and thus assign it's lane
		ArrayList<Car> currentLaneReference = null;
		
		// loop till all cars are assigned a lane
		for(int i = 0; i < Constants.TOTAL_CARS; i++){
			// when condition for isFirstlane is true, assign it to lane1 else lane2
			// this assignment is held by our reference
			currentLaneReference = (isFirstLane) ? lane1 : lane2;
			
			isFirstLane  = !isFirstLane; // toggle the flag.
			
			double startPosition = 0 - (i * Constants.STARTING_DISTANCE_BETWEEN_CARS); // cars are positioned behind each other and start line
			
			// this creates a new Car, assigns it a track and also adds it to the array
			totalCars.add(new Car(i+1, currentLaneReference, startPosition)); // i+1 because car index starts at 1
		}
		return true;

	}
	
	/*
	 *  this method initiates a race, 
	 *  repeatedly updates the speed and distance traveled by cars in two lanes, 
	 *  checks if the race is finished at each interval, and continues until the race is finished.
	 */
	public boolean startRace(){
		Car.AtRaceStart(System.currentTimeMillis());
		
		boolean isRaceFinished = false;
		
		// race simulation continues until explicitly broken out of i.e. when isRaceFinished = true
		while(!isRaceFinished){
			Sleep.sleepInterval(Constants.CHECK_EVERY_N_SECONDS * Constants.SECONDS_TO_MILLISECONDS);
			
			// Debug prints
	        //System.out.println("Lane 1 size: " + lane1.size());
	        //System.out.println("Lane 2 size: " + lane2.size());
			
			// checks if both lane1 and lane2 have no cars left, indicating that the race is finished
			isRaceFinished = ( (lane1.size() == 0) && (lane2.size() == 0) );
			// Debug prints
			//System.out.println("isRaceFinished: " + isRaceFinished);
			
			long evaluationTime = System.currentTimeMillis();
			
			// for lane 1
			calculateSpeedDistanceTravelled(lane1, evaluationTime);
			// for lane 2
			calculateSpeedDistanceTravelled(lane2, evaluationTime);
			
		}
		// Debug prints
	    //System.out.println("RACE FINISHED");

		return true;
	}
	
	// iterates through the car lane and for each car in lane it will call the calculateTimeBased_SpeedDistanceTravelled method
	public boolean calculateSpeedDistanceTravelled(ArrayList<Car> carLane, long evaluationTime){
		int carLaneSize = carLane.size();
		Car car = null;
		
		for(int i = 0; i < carLaneSize; i++){
			car = carLane.get(i);
			car.calculateTimeBased_SpeedDistanceTravelled(evaluationTime);
		}
		return true;
	}
	
	// Print the winner
	public void printWinner(ArrayList<Car> totalCars) {
	    System.out.println("Race complete! Winner: Car " + totalCars.get(0).carID);
	}

	// Print the car stats at the end of the race
	public void printCarStats(ArrayList<Car> totalCars) {
	    for (int i = 0; i < totalCars.size(); i++) {
	        Car car = totalCars.get(i);
	        System.out.println("\nCAR ID " + car.carID);
	        System.out.print("DISTANCE LEFT: " + (int) (Constants.RACE_LENGTH_METRES - car.currentDistTravelled));
	        System.out.println(" | NITROUS USED: " + car.isNitroUsed);
	        System.out.println("ACCELERATION: " + car.acceleration);
	        System.out.println("TOP SPEED: " + car.topSpeed);
	        System.out.println("CURRENT SPEED: " + car.currentSpeed);
	    }
	}

	// Print the winner and car stats
	public void printRaceResults(ArrayList<Car> totalCars) {
	    printWinner(totalCars);
	    printCarStats(totalCars);
	}
}
