package race_simulator;

import race_logic.Controller;
import race_test_cases.TestLimitToTopSpeed;

public class Main {
	public static void main(String[] args) {
		boolean runTestCases = false;
		
		if (runTestCases) {
			TestLimitToTopSpeed testRace = new TestLimitToTopSpeed();
			testRace.runTestCases();
			return;
		}
		
		Controller racecontroller = new Controller();
		racecontroller.startRacingNow();
	}
}
