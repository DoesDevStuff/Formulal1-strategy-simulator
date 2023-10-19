package race_simulator;

import race_logic.Controller;
import race_test_cases.TestLimitToTopSpeed;
import race_test_cases.TestNitroUsed;

public class Main {
	public static void main(String[] args) {
		boolean runTestCases = true;
		
		if (runTestCases) {
			//TestLimitToTopSpeed testRace = new TestLimitToTopSpeed();
			TestNitroUsed testRace = new TestNitroUsed();
			testRace.runTestCases();
			return;
		}
		
		Controller racecontroller = new Controller();
		racecontroller.startRacingNow();
	}
}
