package race_logic;

import java.util.ArrayList;

public class Controller {
	ArrayList<Car> totalCars = new ArrayList<Car>();
	
	ArrayList<Car> lane1 = new ArrayList<Car>(); // will hold all odd indexed cars
	ArrayList<Car> lane2 = new ArrayList<Car>(); // will hold all even indexed cars
	
	public boolean initialiseLanes(){
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
	
	
}
