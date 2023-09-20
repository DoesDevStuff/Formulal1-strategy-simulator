package race_logic;

public class Constants {
  public static final int TOTAL_CARS = 7;
  public static final int ASSESSING_INTERVAL = 2; //SECONDS GIVEN IN QUESTION
  public static final int RACE_LENGTH = 12000;
  
  // The threshold values, control how often the race progress is updated and how quickly the simulation runs in terms of real-time.
  public static final int DISPLAY_THRESHOLD_LENGTH = 100;
  public static final int DISPLAY_THRESHOLD_TIME = 100;

  //SPEED STATS
  public static final int BASE_SPEED = 150; //km/hr
  public static final int BASE_ACCELERATION = 20; // in m/s^2
  public static final int THRESHOLD_SPEED = 10; //difference of speed between each car
  public static final int THRESHOLD_ACCELERATION = 2; // difference of acceleration between each car

  //CONDITIONAL FACTORS
  public static final double HANDLING_FACTOR = 0.8; // GIVEN IN QUESTION
  public static final double STARTING_DISTANCE = 300; //every car is at x distance, from each other in a line

}