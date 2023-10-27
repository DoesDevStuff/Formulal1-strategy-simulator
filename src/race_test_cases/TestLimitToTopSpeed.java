package race_test_cases;

import java.util.ArrayList;

import race_logic.Car;
import race_logic.Constants;

public class TestLimitToTopSpeed {
	
	boolean result;
	
	String testLimitToTopSpeedErrors[] = {"Wrong top speed calculated", "Did not limit speed", "incorrectly changed speed"};

	String getErrorMessage(String[] errorMessageArray, int errorID) {
		String errorMessage = errorMessageArray[errorID];
		return errorMessage;
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
		if ( (result == true) && (car.currentSpeed != expectedTopSpeed) )
				return -2;

		if ( (result == false) && (car.currentSpeed != currentSpeedBefore) )
				return -3;
		
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
			int returnCode = testLimitToTopSpeed(carIDs[i], speeds[i]);
			
			if (returnCode < 0)
				errorsOccured = true;
			
			if (returnCode == -1)
				++countOfFailedConstuctorTopSpeedCalculationFailure;
			if (returnCode == -2)
				++countOfFailedNotSettingTopSpeed;
			if (returnCode == -3)
				++countOfFailedSettingTopSpeedWrongly;
		}

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
