package race_logic;

import java.util.ArrayList;

public class Controller {
	public int iteration = 0;
	
	ArrayList<Car> totalCars = new ArrayList<Car>();
	
	volatile ArrayList<Car> lane1 = new ArrayList<Car>(); // will hold all odd indexed cars
	volatile ArrayList<Car> lane2 = new ArrayList<Car>(); // will hold all even indexed cars
	
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
			Car myCar = new Car(i + 1, currentLaneReference, totalCars, startPosition); // i+1 because car index starts at 1
			
			// also adds this to total cars array
			totalCars.add(myCar);
			
			myCar.start(); // start the thread
		}
		
		try {
		System.out.println("Controller: Threads created...");
		Thread.sleep(1000);
		System.out.println("Controller: Starting business logic all threads...");
		Thread.sleep(1000);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return true;

	}
	
	/*
	 *  this method initiates a race, 
	 *  repeatedly updates the speed and distance traveled by cars in two lanes, 
	 *  checks if the race is finished at each interval, and continues until the race is finished.
	 */
	public boolean startRace() {
		//Car.AtRaceStart(System.currentTimeMillis());
		Car.startAllThreads();
		System.out.println("Race Started");
		
		boolean isRaceFinished = false;
		
		// race simulation continues until explicitly broken out of i.e. when isRaceFinished = true
		while(!isRaceFinished) {
			Sleep.sleepInterval(Constants.CHECK_EVERY_N_SECONDS * Constants.SECONDS_TO_MILLISECONDS);
			
		    visualiseRaceProgress(lane1, lane2);

		    clearConsole();
		    // Debug prints
	        //System.out.println("Lane 1 size: " + lane1.size());
	        //System.out.println("Lane 2 size: " + lane2.size());
			
			// checks if both lane1 and lane2 have no cars left, indicating that the race is finished
			isRaceFinished = ( (lane1.size() == 0) && (lane2.size() == 0) );
			
			if (isRaceFinished) {
				System.out.println("isRaceFinished: " + isRaceFinished);
				break;
			}
			
		}
		// Debug prints
		Sleep.sleepInterval(2500);
	    System.out.println("exiting startRace()...");

	    
		return true;
	}

 private void clearConsole() {
 	// Clear the console (print newline characters)
     for (int i = 0; i < 15; i++) {
         System.out.println();
     }
	}

	// Print the car stats at the end of the race
    public void printCarStats(ArrayList<Car> totalCars) {

    	double minTime = -1;
    	int minCarId = -1;
    	double carTime = -1;
        StringBuffer stringBuffer = new StringBuffer();
    	
    	int totalCarCount = totalCars.size();
    	
    	
    	for (int i = 0; i < totalCarCount; i++) {
            Car car = totalCars.get(i);

            carTime = car.totalTimeTakenSeconds;
            if (minTime < 0)
            	minTime = carTime; 
            
            if (carTime < minTime ) {
            	minTime = carTime;
            	minCarId = car.carID;
            }
            
            if (i == (totalCarCount - 1))
            	System.out.println("Race complete! MyWinner: Car " + minCarId);
/*
            stringBuffer.append("\n");
            stringBuffer.append("CAR ID " + car.carID + "\n");
            stringBuffer.append("Distance covered: " + (int) car.currentDistTravelled + "\n");
            stringBuffer.append("NITROUS USED: " + car.isNitroUsed + "\n");
            stringBuffer.append("ACCELERATION: " + car.acceleration + "\n");
            stringBuffer.append("TOP SPEED: " + car.topSpeed + "\n");
            stringBuffer.append("CURRENT SPEED: " + car.currentSpeed + "\n");
            stringBuffer.append("Car " + car.carID + " Finish Time: " + car.totalTimeTakenSeconds + " seconds" + "\n");
*/            
        }

    	System.out.println(stringBuffer.toString());
    }

 // Print the car stats sorted by current finish time without using external libraries
    public void printSortedCarStats(ArrayList<Car> totalCars) {
    	StringBuffer stringBuffer = new StringBuffer();
    	
    	int totalCarCount = totalCars.size();
    	
        for (int i = 0; i < totalCarCount - 1; i++) {
            for (int j = 0; j < totalCars.size() - i - 1; j++) {
                Car car1 = totalCars.get(j);
                Car car2 = totalCars.get(j + 1);

                if (car1.totalTimeTakenSeconds > car2.totalTimeTakenSeconds) {
                    // Swap cars if they are out of order
                    totalCars.set(j, car2);
                    totalCars.set(j + 1, car1);
                }
            }
        }

        System.out.println("===============================================================================");
        System.out.println("Sorted Results based on Finish Time:");
        
        for (int i = 0; i < totalCarCount; i++) {
        	Car car = totalCars.get(i);
            stringBuffer.append("\n");
            stringBuffer.append("CAR ID " + car.carID + "\n");
            stringBuffer.append("Distance covered: " + (int) car.currentDistTravelled + "\n");
            stringBuffer.append("NITROUS USED: " + car.isNitroUsed + "\n");
            stringBuffer.append("ACCELERATION: " + car.acceleration + "\n");
            stringBuffer.append("TOP SPEED: " + car.topSpeed + "\n");
            stringBuffer.append("CURRENT SPEED: " + car.currentSpeed + "\n");
            stringBuffer.append("Car " + car.carID + " Finish Time: " + car.totalTimeTakenSeconds + " seconds" + "\n");
        }
        System.out.println(stringBuffer.toString());
    }

    // Print the winner and car stats
    public void printRaceResults(ArrayList<Car> totalCars) {
        printCarStats(totalCars);
        printSortedCarStats(totalCars);
    }

    public void visualiseRaceProgress(ArrayList<Car> lane1, ArrayList<Car> lane2) {
    	++iteration;
    	System.out.println();
    	System.out.println("==============================================================================================================================");
    	System.out.println("Visualising Race Progress" + " At interval: " + iteration);
    	System.out.println("==============================================================================================================================");
        
        // Print each row of the race
        for (int i = 0; i < Math.max(lane1.size(), lane2.size()); i++) {
            printCarRow(lane1, i);
            //System.out.print("\t\t\t"); // Add some spacing between lanes
            printCarRow(lane2, i);
            System.out.println(); // Move to the next line
        }
    }

    private static void printCarRow(ArrayList<Car> lane, int rowIndex) {
        System.out.print((rowIndex < lane.size()) ? printCarDetails(lane.get(rowIndex)) : " ");

        // Move the cursor back to the beginning of the line
        System.out.print("\r");
    }

    private static String printCarDetails(Car car) {
        // Print the car details for the current row
        String carDetails = String.format("Car %d | %.2f m travelled\t", car.carID, car.currentDistTravelled);
        carDetails += printRaceTrack(car);

        // Calculate the percentage distance completed
        double percentageCompleted = (car.currentDistTravelled / Constants.RACE_LENGTH_METRES) * 100.0;
        // Append percentage completed with square brackets
        carDetails += String.format(" [%d%% of Race Length Covered]", (int) percentageCompleted);

        return carDetails;
    }

    private static String printRaceTrack(Car car) {
        int raceLength = Constants.RACE_LENGTH_METRES / Constants.DISPLAY_THRESHOLD_LENGTH;
        int positionIndex = (int) (car.currentDistTravelled / Constants.DISPLAY_THRESHOLD_LENGTH);

        // Print the race track with cars ordered by ID
        StringBuilder raceTrack = new StringBuilder();
        for (int j = 0; j < raceLength; j++) {
            raceTrack.append((j == positionIndex) ? "|" + car.carID + "|" : "-");
        }

        return raceTrack.toString();
    }
}