package race_test_cases;

import java.util.ArrayList;

import race_logic.Car;

public class TestNitroUsed {
	boolean result;
	
	public boolean testNitroUsed() {
		boolean allpassed = true;

		if (testNitroUsedSpeedChangedInCarObject(2, 50) != 0)
			allpassed = false;
		
		return allpassed;
	}
	
	int testNitroUsedSpeedChangedInCarObject(int carID, double currentSpeed) {
		ArrayList<Car> totalCars = new ArrayList<Car>();
		ArrayList<Car> testTrack1 = new ArrayList<Car>();
		ArrayList<Car> testTrack2 = new ArrayList<Car>();
		
		Car dummyCar1 = new Car(carID + 1, testTrack1, totalCars, 100); 	totalCars.add(dummyCar1);
		Car dummyCar2 = new Car(carID + 2, testTrack2, totalCars, 200);		totalCars.add(dummyCar2);
		Car dummyCar3 = new Car(carID + 3, testTrack1, totalCars, 400);		totalCars.add(dummyCar3);
		Car dummyCar4 = new Car(carID + 4, testTrack2, totalCars, 600);		totalCars.add(dummyCar4);
		
		// we are writing the condition so that it always uses nitro
		Car car = new Car(carID, testTrack1, totalCars, 50); // so this is my test car it is in track 1 and in last place
		totalCars.add(car);

		double expectedNitroSpeed = currentSpeed * 2;
		
		car.currentSpeed = currentSpeed;
		double currentSpeedBefore = currentSpeed;

		boolean nitroUsedStateBefore = car.isNitroUsed;
		result = car.useNitro();
		boolean nitroUsedStateAfter = car.isNitroUsed;

		boolean xyz = (car.currentSpeed != currentSpeedBefore);
		if (xyz == true);
		
		int returnCode = 0;
		
		// pass condition -- FFF (Fail Fast First)
		if ( (result == true) && (nitroUsedStateBefore == false) && (nitroUsedStateAfter == true) &&
			(currentSpeedBefore != car.currentSpeed)  && ( (car.currentSpeed) == expectedNitroSpeed) ) 
		{
			System.out.println("All test conditions passed for this test case.");
			returnCode = 0;
		}

		// fail conditions: 
		
		// The method UseNitro() failed to return true
		if (result != true) {
			System.out.println("The method useNitro() failed to return true");
			returnCode = -1;
		}
		// before calling this function isNitroUsed should have been false
		if (nitroUsedStateBefore != false) {
			System.out.println("isNitroUsed incorrectly set before method call");
			returnCode = -2;
		}
		// the function UseNitro returned true but failed to set the value of variable nitro used to true
		if (nitroUsedStateAfter != true) {
			System.out.println("Function useNitro() is called but isNitroUsed is not set");
			returnCode = -3;
		}

		// the call the function UseNitro did not change the current speed of the car
		if (car.currentSpeed == currentSpeedBefore) {
			System.out.println("the call the function UseNitro did not change the current speed of the car");
			returnCode = -4;
		}

		// the current speed after nitro is used is not twice its previous current speed
		if ( (currentSpeedBefore * 2) != expectedNitroSpeed) {
			System.out.println("Nitro speed is not set to twice it's current speed");
			returnCode = -5;
		}

		return returnCode; // this means it hasn't checked for enough fail conditions, this is a test case error
	}

}
