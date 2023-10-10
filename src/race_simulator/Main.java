package race_simulator;

import race_logic.Controller;
import testrace.TestRace;

public class Main {
	public static void main(String[] args) {
		boolean runTestCases = true;
		
		if (runTestCases) {
			TestRace testRace = new TestRace();
			testRace.runTestCases();
			return;
		}
		
		Controller racecontroller = new Controller();
		racecontroller.startRacingNow();
	}
}
