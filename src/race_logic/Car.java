package race_logic;

public class Car {
  private int teamNumber;
  private double topSpeed;
  private int acceleration;
  private boolean nitroUsed;
  private double handlingFactor;
  private double currentDistanceCovered;
  private double currentSpeed;
  private boolean reduceSpeed;

  public int getTeamNumber() {
    return teamNumber;
  }

  public void setTeamNumber(int teamNumber) {
    this.teamNumber = teamNumber;
  }

  public double getTopSpeed() {
    return topSpeed;
  }

  public void setTopSpeed(double topSpeed) {
    this.topSpeed = topSpeed;
  }

  public int getAcceleration() {
    return acceleration;
  }

  public void setAcceleration(int acceleration) {
    this.acceleration = acceleration;
  }

  public boolean isNitroUsed() {
    return nitroUsed;
  }

  public void setNitroUsed(boolean nitroUsed) {
    this.nitroUsed = nitroUsed;
  }

  public double getHandlingFactor() {
    return handlingFactor;
  }

  public void setHandlingFactor(double handlingFactor) {
    this.handlingFactor = handlingFactor;
  }

  public double getCurrentDistanceCovered() {
    return currentDistanceCovered;
  }

  public void setCurrentDistanceCovered(double currentDistanceCovered) {
    this.currentDistanceCovered = currentDistanceCovered;
  }

  public double getCurrentSpeed() {
    return currentSpeed;
  }

  public void setCurrentSpeed(double currentSpeed) {
    this.currentSpeed = currentSpeed;
  }

  public boolean isReduceSpeed() {
    return reduceSpeed;
  }

  public void setReduceSpeed(boolean reduceSpeed) {
    this.reduceSpeed = reduceSpeed;
  }

  public Car(int totalCars, int i) {
    teamNumber = i;
    topSpeed = 5 * (Constants.BASE_SPEED + (Constants.THRESHOLD_SPEED * i)) / 18; //Top speed: (150 + 10 * i) km per hour, and we are converting to m/s
    acceleration = Constants.BASE_ACCELERATION - Constants.THRESHOLD_ACCELERATION * i; //Acceleration: (2 * i) meter per second square.

    nitroUsed = false;
    handlingFactor = Constants.HANDLING_FACTOR;
    currentDistanceCovered = (totalCars - i) * Constants.STARTING_DISTANCE;
    currentSpeed = 0;
    reduceSpeed = false;
  }
}