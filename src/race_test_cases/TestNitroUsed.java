package race_test_cases;

import java.util.ArrayList;

import race_logic.Car;

public class TestNitroUsed {
	boolean result;
	
	String testLimitNitroUsedErrors[] = {"Nitro set more than once", "Did not set Nitro Used", "incorrectly changed nitro speed"};

	String getErrorMessage(String[] errorMessageArray, int errorID) {
		String errorMessage = errorMessageArray[errorID];
		return errorMessage;
	}
	
	public boolean runTestCases() {
		boolean allpassed = true;

		if (!testNitroUsedSpeedChangedInCarObject())
			allpassed = false;
		
		return allpassed;
	}
	
	int testNitroUsedAndSpeedChange(int carID, double currentSpeed) {
		ArrayList<Car> totalCars = new ArrayList<Car>();
		ArrayList<Car> testTrack = new ArrayList<Car>();
		Car car = new Car(carID, testTrack, totalCars, -10000);
		totalCars.add(car);
		
		car.isNitroUsed = true; // if I set this to false I get nitro used more than once
		
		double expectedNitroSpeed = currentSpeed *2;
		
		car.currentSpeed = currentSpeed;
		double currentSpeedBefore = currentSpeed;
		
		result = car.useNitro();

		if (result == true) {
			
			// Check if nitro is used only once
	        if (car.isNitroUsed) {
	            return -1;  // Nitro used more than once
	        }

			
			if ( (car.currentSpeed * 2) != expectedNitroSpeed)
				return -2;
			
			//car.isNitroUsed = true;  // Set isNitroUsed to true

		}

		if (result == false) {
			if (car.currentSpeed != currentSpeedBefore)
				return -3;
		}
		
		return 0;
	}

	
	boolean testNitroUsedSpeedChangedInCarObject() {
		int countOfFailedNotSettingNitroUsedOnlyOnce = 0;
		int countOfFailedNotSettingNitroUsed = 0;
		int countOfFailedSettingNitroSpeedWrongly = 0;
		boolean errorsOccured = true;
		
		int carIDs[]    = {1, 45, 9, 56, 5};
		double speeds[] = {10, 20, 30, 40, 50};
		int count = carIDs.length;
		
		for (int i = 0; i < count; i++) {
			if (testNitroUsedAndSpeedChange(carIDs[i], speeds[i]) == -1)
				++countOfFailedNotSettingNitroUsedOnlyOnce;
			
			if (testNitroUsedAndSpeedChange(carIDs[i], speeds[i]) == -2)
				++countOfFailedNotSettingNitroUsed;

			if (testNitroUsedAndSpeedChange(carIDs[i], speeds[i]) == -3)
				++countOfFailedSettingNitroSpeedWrongly;
		}

		errorsOccured = ( (countOfFailedNotSettingNitroUsedOnlyOnce > 0) || 
							(countOfFailedNotSettingNitroUsed > 0 ) || 
							(countOfFailedSettingNitroSpeedWrongly > 0 ) 
						);
		
		if (!errorsOccured) {
			System.out.println("No errors occured");
			return errorsOccured;
		}
		System.out.println("occurrences of " + getErrorMessage(testLimitNitroUsedErrors, 0) + ": " + countOfFailedNotSettingNitroUsedOnlyOnce);
		System.out.println("occurrences of " + getErrorMessage(testLimitNitroUsedErrors, 1) + ": " + countOfFailedNotSettingNitroUsed);
		System.out.println("occurrences of " + getErrorMessage(testLimitNitroUsedErrors, 2) + ": " + countOfFailedSettingNitroSpeedWrongly);

		return errorsOccured;
	}
}
