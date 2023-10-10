package testrace;

import java.util.ArrayList;

import race_logic.Car;
import race_logic.Constants;

public class TestRace {
	
	boolean result;
	
	String testLimitToTopSpeedErrors[] = {"Wrong top speed calculated", "Did not limit speed", "incorrectly changed speed"};

	String getErrorMessage(String[] errorMessageArray, int errorID) {
		String errorMessage = errorMessageArray[errorID];
		return errorMessage;
	}

	public boolean runTestCases() {
		boolean allpassed = true;

		if (!testLimitTopSpeedInCarObject())
			allpassed = false;
		
		return allpassed;
	}
	
	int testLimitToTopSpeed(int carID, double currentSpeed) {
		ArrayList<Car> totalCars = new ArrayList<Car>();
		ArrayList<Car> testTrack = new ArrayList<Car>();
		Car car = new Car(carID, testTrack, totalCars, -10000);
		totalCars.add(car);

		double expectedTopSpeed = findTopSpeedFromID(carID);

		//by convention return values for failure ID is -ve, no failure is 0
		if (car.topSpeed != expectedTopSpeed) //whether constructor is calculating correct top speed
			return -1;   

		car.currentSpeed = currentSpeed;
		double currentSpeedBefore = currentSpeed;
		
		result = car.limitToTopSpeed();
		if (result == true) {
			if (car.currentSpeed != expectedTopSpeed)
				return -2;
		}

		if (result == false) {
			if (car.currentSpeed != currentSpeedBefore)
				return -3;
		}
		
		return 0;
	} 

	double findTopSpeedFromID(int carID) {
		return (5.0 * (Constants.BASE_SPEED + (Constants.SPEED_DIFF * carID)) ) / 18.0;
	}
	
	boolean testLimitTopSpeedInCarObject() {
		int countOfFailedConstuctorTopSpeedCalculationFailure = 0;
		int countOfFailedNotSettingTopSpeed = 0;
		int countOfFailedSettingTopSpeedWrongly = 0;
		boolean errorsOccured = true;
		
		int carIDs[]    = {1, 45, 9, 56, 5};
		double speeds[] = {10, 20, 30, 40, 50};
		int count = carIDs.length;
		
		for (int i = 0; i < count; i++) {
			if (testLimitToTopSpeed(carIDs[i], speeds[i]) == -1)
				++countOfFailedConstuctorTopSpeedCalculationFailure;

			if (testLimitToTopSpeed(carIDs[i], speeds[i]) == -2)
				++countOfFailedNotSettingTopSpeed;

			if (testLimitToTopSpeed(carIDs[i], speeds[i]) == -3)
				++countOfFailedSettingTopSpeedWrongly;
		}

		errorsOccured = ( (countOfFailedConstuctorTopSpeedCalculationFailure > 0 ) ||
							 (countOfFailedNotSettingTopSpeed > 0 ) ||
							 (countOfFailedSettingTopSpeedWrongly > 0 )
						);
		

		if (!errorsOccured) {
			System.out.println("No errors occured");
			return errorsOccured;
		}
		
		System.out.println("occurrences of " + getErrorMessage(testLimitToTopSpeedErrors, 0) + ": " + countOfFailedConstuctorTopSpeedCalculationFailure);
		System.out.println("occurrences of " + getErrorMessage(testLimitToTopSpeedErrors, 1) + ": " + countOfFailedNotSettingTopSpeed);
		System.out.println("occurrences of " + getErrorMessage(testLimitToTopSpeedErrors, 2) + ": " + countOfFailedSettingTopSpeedWrongly);

		return errorsOccured;
	}
}
