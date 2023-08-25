package race_logic;

public class Constants {
	public static int TOTAL_CARS = 6;
	public static int ASSESSING_INTERVAL = 2; //SECONDS GIVEN IN QUESTION
	public static int RACE_LENGTH = 10000;
	public static int DISPLAY_THRESHOLD_LENGTH = 100;
	public static int DISPLAY_THRESHOLD_TIME = 100;
	
	//SPEED STATS
	public static int BASE_SPEED = 200; //km/hr
	public static int BASE_ACCELERATION = 20; // in m/s^2
	public static int THRESHOLD_SPEED = -4; //difference of speed between each car
	public static int THRESHOLD_ACCELERATION = -2; // difference of acceleration between each car
	
	//CONDITIONAL FACTORS
	public static double HANDLING_FACTOR = 0.8; // GIVEN IN QUESTION
	public static double STARTING_DISTANCE = 250; //every car is at x distance, from each other in a line
	
	
}
