package race_simulator;

import race_logic.Controller;
import race_test_cases.AllTestCases;
//import race_message_bundles.*;

public class Main {
	public static void main(String[] args) {
		// to run the test cases set the boolean to true
		boolean runTestCases = false;

		//program wide initialization of message bundle
		//boolean useEnglishmessageBundle = false;

		//MessagebundleBase.MessagebundleToUse = 
			//((useEnglishmessageBundle) ? new MessageBundleEnglish() : new MessageBundleSpanish());

		//String WelcomeMessage = MessagebundleBase.MessagebundleToUse.programGreeting;
		//System.out.println(WelcomeMessage);
		
		if (runTestCases) {
			AllTestCases allTestCases = new AllTestCases();
			
			allTestCases.runAllTestCases();
			
			
			return;
		}
		
		Controller racecontroller = new Controller();
		racecontroller.startRacingNow();
	}
}
