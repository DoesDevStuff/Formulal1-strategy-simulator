package race_logic;

import java.util.ArrayList;

/*
 * Description:
 * This class holds all of the car information.
 * Includes - 
 * 1. Car value definitions
 * 2. Individual calculations of distance and speed by each car 
 * 
 */

public class Car {
	private int teamNumber;
	private int acceleration;

	private double topSpeed;
	private double currentDistanceCovered;

	
	int carID; // every car will have a unique id starting from 1
	
	ArrayList<Car> carLane; // cars start in two lanes. This will tell us which id is on what track.
	ArrayList<Car> cars;
	
	
	// Constructor for Car
	public Car(int carID, ArrayList<Car> carLane){
		this.carID = carID;
		this.carLane = carLane;
		
		this.carLane.add(this); // adding value of each lane reference to the carLane here. (from controller, line 25)		
		
	}
	/*
	// overloading
	public Car(ArrayList<Car> cars, int carID){
		this.cars = cars;
		teamNumber = carID;
		
		topSpeed = (5.0 * (Constants.BASE_SPEED + (Constants.SPEED_DIFF * carID)) ) / 18.0; // changed it to have 5.0 and 18.0 to have accurate speed values
		acceleration = Constants.BASE_ACCELERATION - Constants.ACCELERATION_DIFF * carID;
		
		currentDistanceCovered = -((carID -1) * Constants.STARTING_DISTANCE_BETWEEN_CARS);
		
		cars.add((carID-1), this);
	}
	
	
	public void calculateDistance() {
    	currentDistanceCovered =  (acceleration * Constants.CHECK_EVERY_TWO_SECONDS * Constants.CHECK_EVERY_TWO_SECONDS) / 2;
    }
	
	// GETTERS
	
	public int getTeamNumber() {
		return teamNumber;
	}
	
	public int getAcceleration() {
		return acceleration;
	}
	
	public double getTopSpeed() {
		return topSpeed;
	}

	public double getCurrentDistanceCovered() {
		return currentDistanceCovered;
	}
*/
}
