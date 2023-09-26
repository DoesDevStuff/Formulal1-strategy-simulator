package race_simulator;

import race_logic.Controller;

public class Main {
	public static void main(String[] args) {
		Controller racecontroller = new Controller();
		racecontroller.initialiseLanes();
	}
}
