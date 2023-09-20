package race_logic;

import java.util.ArrayList;

public class SingularCar {
    private int teamNumber;
    private int acceleration;
    
    private double topSpeed;
    private double handlingFactor;
    private double currentDistanceCovered;
    private double currentSpeed;
    private double currentTime;
    
    private boolean reduceSpeed;
    private boolean nitroUsed = false;
    
    private ArrayList<SingularCar> cars;

    public SingularCar(ArrayList<SingularCar> cars, int id) {
        this.cars = cars;
        teamNumber = id;
        
        topSpeed = (5.0 * (Constants.BASE_SPEED + (Constants.THRESHOLD_SPEED * id)) ) / 18.0; // changed it to have 5.0 and 18.0 to have accurate speed values
        acceleration = Constants.BASE_ACCELERATION - Constants.THRESHOLD_ACCELERATION * id;
        nitroUsed = false;
        handlingFactor = Constants.HANDLING_FACTOR;
        
        //currentDistanceCovered = (cars.size() - id) * Constants.STARTING_DISTANCE;
        
        // time value
        currentTime = 0;
        currentSpeed = 0;
        
        currentDistanceCovered = -((id -1) * Constants.STARTING_DISTANCE);
        reduceSpeed = false;
        
        //cars.add((id-1), this);
        cars.add(0, this); // to do this for one car only
    }

    public void calculateDistance() {
    	currentDistanceCovered =  (acceleration * Constants.ASSESSING_INTERVAL * Constants.ASSESSING_INTERVAL) / 2;
    }

    public void calculateSpeedDistanceAndVelocity(){
    	currentTime += Constants.ASSESSING_INTERVAL;
    	
    	double newSpeed = currentSpeed + (acceleration * currentTime);
    	double oldSpeed  = currentSpeed;
    	currentSpeed = newSpeed; 
    
    	if(currentSpeed > topSpeed){
    		currentSpeed = topSpeed; // limiting the speed of car to the pre-decided top speed based on the car's position\
    	}
    	
    	// s = ut + 1/2 at^2  and here Ut will always be 0 because initial speed at start is always 0
    	double newDistanceCovered = (0 + ( (acceleration * currentTime * currentTime) / 2 ) );
    	currentDistanceCovered += newDistanceCovered;
    }
    
    /*
    public void calculateSpeed() {
        if (currentSpeed <= topSpeed) {
            double newSpeed = currentSpeed + acceleration * Constants.ASSESSING_INTERVAL;
            currentSpeed = newSpeed <= topSpeed ? newSpeed : topSpeed;
        }

        for (int i = 0; i < cars.size(); i++) {
        	SingularCar carI = cars.get(i);
            if (carI != this) {
                double distanceDifference = Math.abs(currentDistanceCovered - carI.getCurrentDistanceCovered());
                if (distanceDifference <= 10) {
                    reduceSpeed = true;
                    carI.setReduceSpeed(false);// Reset reduceSpeed flag for other cars
                }
                else {
                    carI.setReduceSpeed(true); 
                }
            }
        }

        if (reduceSpeed) {
            currentSpeed *= handlingFactor;
            reduceSpeed = false;
        }

        SingularCar lastCar = cars.get(0);
        for (int i = 0; i < cars.size(); i++) {
        	SingularCar car = cars.get(i);
            if (car.getCurrentDistanceCovered() < lastCar.getCurrentDistanceCovered()) {
                lastCar = car;
            }
        }

        if (!lastCar.isNitroUsed() && lastCar == this) {
            lastCar.nitroUsed = true;
            double newSpeed = lastCar.getCurrentSpeed() * 2;
            lastCar.setCurrentSpeed(newSpeed <= lastCar.getTopSpeed() ? newSpeed : lastCar.getTopSpeed());
        }
    }

	*/

    public int getTeamNumber() {
        return teamNumber;
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    public double getCurrentDistanceCovered() {
        return currentDistanceCovered;
    }

    public int getAcceleration() {
        return acceleration;
      }

    public double getHandlingFactor() {
        return handlingFactor;
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

    public boolean isNitroUsed() {
        return nitroUsed;
    }
    
}
