package race_test_cases;

import race_logic.Constants;

import race_logic.Car;

import java.util.ArrayList;
import java.util.Collections;

public class TestTimeBased_SpeedDistanceTravelled {
	ArrayList<Integer> resultCodes = new ArrayList<Integer>();
	ArrayList<String> resultMessages = new ArrayList<String>();
	
	public boolean testTimeBased_SpeedDistanceTravelled() {
		Collections.addAll(resultCodes, 0, -1, -2, -3);
		Collections.addAll(resultMessages,
				"All test conditions passed for this test case.",
				"Acceleration not calculated correctly for car",
				"Initial position not set correctly for car",
				"calculateTimeBased_SpeedDistanceTravelled failed for car" );
		
		int resultCode = (testCalculateTimeBased_SpeedDistanceTravelled(1, 10));
		
		if (resultCode > 0 || resultCode < -3) {
			System.out.println("Wrong Error Code Returned: " + resultCode);
			return false;
		}
		
		int index = resultCodes.indexOf(resultCode);
		System.out.println(resultMessages.get(index));
		
		boolean returnFlag = ( (resultCode == 0 ) ? true : false );
		return returnFlag;
	}


    int testCalculateTimeBased_SpeedDistanceTravelled(int carID, double currentSpeed) {
        double expectedAcceleration = findAccelerationFromID(carID);
        double expectedStartPosition = findStartPositionFromID(carID);

        ArrayList<Car> totalCars = new ArrayList<Car>();
        ArrayList<Car> testTrack = new ArrayList<Car>();

        Car car = new Car(carID, testTrack, totalCars, expectedStartPosition);

        if (car.acceleration != expectedAcceleration)
            return -1;

        if (car.currentDistTravelled != expectedStartPosition)
            return -2;

        // Variables for time and distance
        double totalTime = 16.0;  // Total time in seconds
        int steps = 4;  // Number of steps
        double stepTime = totalTime / steps;  // Time for each step


        for (int i = 0; i < steps; i++) {
            // Call calculateTimeBased_SpeedDistanceTravelled for each step
            boolean result = car.calculateTimeBased_SpeedDistanceTravelled((long) (stepTime * 1000));

            if (!result)
                return -3;

            // Print the state of the car after each step
            System.out.println("\n" + "Step " + (i + 1) + ": Current Speed = " + car.currentSpeed +
                    ", Current Distance = " + car.currentDistTravelled);
        }
        
        return 0;
    }

    double findAccelerationFromID(int carID) {
        return (Constants.BASE_ACCELERATION * (Math.exp(-Constants.ACCELERATION_DIFF * carID)));
    }

    double findStartPositionFromID(int carID) {
        return (0 - (carID * Constants.STARTING_DISTANCE_BETWEEN_CARS));
    }

}
