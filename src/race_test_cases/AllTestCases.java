
package race_test_cases;

public class AllTestCases {

	public boolean runAllTestCases() {
		int testcaseCount = 3;
		int howmanyPassed = 0;

		boolean result = false;
		
		//test case
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------");
		System.out.println("Running TestNitroUsed");
		System.out.println("------------------------------------------------------------------------------------------------------");
		TestNitroUsed testNitroUsed = new TestNitroUsed();
		result = testNitroUsed.testNitroUsed();
		if (result) ++howmanyPassed;

		//test case
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------");
		System.out.println("Running TestLimitToTopSpeed");
		System.out.println("------------------------------------------------------------------------------------------------------");
		TestLimitToTopSpeed testLimitToTopSpeed = new TestLimitToTopSpeed();
		result = testLimitToTopSpeed.testLimitTopSpeedInCarObject();
		if (result) ++howmanyPassed;
		
		//test case
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------");
		System.out.println("Running TestTimeBased_SpeedDistanceTravelled");
		System.out.println("------------------------------------------------------------------------------------------------------");
		TestTimeBased_SpeedDistanceTravelled testSpeedDistance = new TestTimeBased_SpeedDistanceTravelled();
		result = testSpeedDistance.testTimeBased_SpeedDistanceTravelled();
		if (result) ++howmanyPassed;

		boolean allpassed = (testcaseCount == howmanyPassed);
		
		System.out.println();
		System.out.println();
		System.out.println("======================================================================================================");
		System.out.println("Results");
		System.out.println("======================================================================================================");

		if (allpassed)
			System.out.println("All tests passed");
		else
			System.out.println(howmanyPassed + " of " + testcaseCount + " testcases passed");

		
		return allpassed;
	}

}
