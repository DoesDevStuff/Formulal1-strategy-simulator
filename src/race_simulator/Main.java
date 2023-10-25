package race_simulator;

import race_logic.Controller;

import race_test_cases.TestLimitToTopSpeed;
import race_test_cases.TestNitroUsed;
import race_test_cases.TestTimeBased_SpeedDistanceTravelled;
import race_message_bundles.*;

public class Main {
	public static void main(String[] args) {
		// to run the test cases set the boolean to true
		boolean runTestCases = true;
		
		//MessagebundleBase.MessagebundleToUse = new MessageBundleSpanish();
		
		//String message = MessagebundleBase.MessagebundleToUse.incorrectValue;
		
		if (runTestCases) {
			TestTimeBased_SpeedDistanceTravelled testRace = new TestTimeBased_SpeedDistanceTravelled();
			//TestLimitToTopSpeed testRace = new TestLimitToTopSpeed();
			//TestNitroUsed testRace = new TestNitroUsed();
			testRace.runTestCases();
			return;
		}
		
		Controller racecontroller = new Controller();
		racecontroller.startRacingNow();
	}
}
