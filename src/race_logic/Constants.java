package race_logic;

/*
 * Description:
 * This class contains the global constants that are predefined in the problem statement
 */

public class Constants {
	public static final int TOTAL_CARS = 3;
	public static final int CHECK_EVERY_N_SECONDS = 2; //SECONDS GIVEN IN QUESTION
	public static final int SECONDS_TO_MILLISECONDS = 1000;
	public static final int RACE_LENGTH_METRES = 5000;
	

	// The threshold values, control how often the race progress is updated and how quickly the simulation runs in terms of real-time.
	public static final int DISPLAY_THRESHOLD_LENGTH = 100;
	public static final int DISPLAY_THRESHOLD_TIME = 100;

	//SPEED STATS
	public static final int BASE_SPEED = 150; //km/hr
	public static final int BASE_ACCELERATION = 20; // in m/s^2
	public static final int SPEED_DIFF = 10; //difference of speed between each car
	public static final int COLLISION_RANGE = 10; // Distance before collision between car behind and one in front
	public static final int ACCELERATION_DIFF = 2; // difference of acceleration between each car

	//CONDITIONAL FACTORS
	public static final double REDUCE_SPEED_FACTOR = 0.8; // GIVEN IN QUESTION
	public static final double STARTING_DISTANCE_BETWEEN_CARS = 200; //every car is at x distance, from each other in a line
}
