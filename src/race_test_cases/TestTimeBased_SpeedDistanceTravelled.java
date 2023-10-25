package race_test_cases;

import race_logic.Constants;

import race_logic.Car;

import java.util.ArrayList;

public class TestTimeBased_SpeedDistanceTravelled {

    public boolean runTestCases() {
        boolean allPassed = true;

        if (!testCalculateTimeBased_SpeedDistanceTravelled(1, 10)) {
            allPassed = false;
        }
        return allPassed;
    }

    boolean testCalculateTimeBased_SpeedDistanceTravelled(int carID, double currentSpeed) {
        double expectedAcceleration = findAccelerationFromID(carID);
        double expectedStartPosition = findStartPositionFromID(carID);

        ArrayList<Car> totalCars = new ArrayList<Car>();
        ArrayList<Car> testTrack = new ArrayList<Car>();

        Car car = new Car(carID, testTrack, totalCars, expectedStartPosition);

        if (car.acceleration != expectedAcceleration) {
            System.out.println("Acceleration not calculated correctly for car " + carID);
            return false;
        }

        if (car.currentDistTravelled != expectedStartPosition) {
            System.out.println("Initial position not set correctly for car " + carID);
            return false;
        }

        // Variables for time and distance
        double totalTime = 16.0;  // Total time in seconds
        int steps = 4;  // Number of steps
        double stepTime = totalTime / steps;  // Time for each step


        for (int i = 0; i < steps; i++) {
            // Call calculateTimeBased_SpeedDistanceTravelled for each step
            boolean result = car.calculateTimeBased_SpeedDistanceTravelled((long) (stepTime * 1000));

            if (!result) {
                System.out.println("calculateTimeBased_SpeedDistanceTravelled failed for car " + carID + " at step " + i);
                return false;
            }

            // Print the state of the car after each step
            System.out.println("\n" + "Step " + (i + 1) + ": Current Speed = " + car.currentSpeed +
                    ", Current Distance = " + car.currentDistTravelled);
        }

		System.out.println("All test conditions passed for this test case.");
        return true;
    }

    double findAccelerationFromID(int carID) {
        return (Constants.BASE_ACCELERATION * (Math.exp(-Constants.ACCELERATION_DIFF * carID)));
    }

    double findStartPositionFromID(int carID) {
        return (0 - (carID * Constants.STARTING_DISTANCE_BETWEEN_CARS));
    }

}
