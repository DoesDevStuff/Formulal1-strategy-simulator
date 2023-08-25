package race_logic;

import java.util.ArrayList;

public class Race {
  public void initRaceCars(ArrayList < Car > cars) {
    for (int i = 0; i < Constants.TOTAL_CARS; i++) {
      Car car = new Car(Constants.TOTAL_CARS, i + 1);
      cars.add(car);
      printCarStats(car);
    }
  }

  public void assessRaceCars(ArrayList < Car > cars) {
    calculateDistances(cars);
    calculateSpeeds(cars);
  }

  public void calculateDistances(ArrayList < Car > cars) {
    for (int i = 0; i < cars.size(); i++) {
      Car car = cars.get(i);
      double distanceIncrement = car.getCurrentSpeed() * Constants.ASSESSING_INTERVAL +
        (car.getAcceleration() * Constants.ASSESSING_INTERVAL * Constants.ASSESSING_INTERVAL) / 2;
      car.setCurrentDistanceCovered(car.getCurrentDistanceCovered() + distanceIncrement);
    }
  }

  public void calculateSpeeds(ArrayList < Car > cars) {
    for (int i = 0; i < cars.size(); i++) {
      Car car = cars.get(i);
      if (car.getCurrentSpeed() <= car.getTopSpeed()) {
        double newSpeed = car.getCurrentSpeed() + car.getAcceleration() * Constants.ASSESSING_INTERVAL;
        car.setCurrentSpeed(newSpeed <= car.getTopSpeed() ? newSpeed : car.getTopSpeed());
      }
    }

    /*
     * HANDLING FACTOR Check if there is any car in the proximity of 10
     * meters. If yes, decrease the currentSpeed by 0.8. But that has to be
     * done after calculating speed. Checking in calculateSpeeds, as this is
     * being called after calculateDistances
     */

    for (int i = 0; i < cars.size() - 1; i++) {
      for (int j = i + 1; j < cars.size(); j++) {
        Car carI = cars.get(i);
        Car carJ = cars.get(j);
        double distanceDifference = Math.abs(carI.getCurrentDistanceCovered() - carJ.getCurrentDistanceCovered());
        if (distanceDifference <= 10) {
          carI.setReduceSpeed(true);
          carJ.setReduceSpeed(true);
        }
      }
    }

    for (int i = 0; i < cars.size(); i++) {
      Car car = cars.get(i);
      if (car.isReduceSpeed()) {
        car.setCurrentSpeed(car.getCurrentSpeed() * car.getHandlingFactor());
        car.setReduceSpeed(false);
        System.out.println("REDUCED SPEED! " + car.getTeamNumber());
      }
    }

    /*
     * NITROUS check which team is last, and use their Nitro
     */

    Car lastCar = cars.get(0);
    for (int i = 0; i < cars.size(); i++) {
      Car car = cars.get(i);
      if (car.getCurrentDistanceCovered() < lastCar.getCurrentDistanceCovered()) {
        lastCar = car;
      }
    }
    System.out.println("LAST CAR IS = " + lastCar.getTeamNumber());
    if (!lastCar.isNitroUsed()) {
      System.out.println("" + lastCar.getTeamNumber() + " USED NITRO!");
      lastCar.setNitroUsed(true);
      double newSpeed = lastCar.getCurrentSpeed() * 2;

      /*
       * set lastCars speed to twice its current speed if that less than
       * its topSpeed, else set its speed to topSpeed
       */

      lastCar.setCurrentSpeed(newSpeed <= lastCar.getTopSpeed() ? newSpeed : lastCar.getTopSpeed());
    }
  }

  public Car checkWinner(ArrayList < Car > cars) {
    for (int i = 0; i < cars.size(); i++) {
      Car car = cars.get(i);
      if (car.getCurrentDistanceCovered() >= Constants.RACE_LENGTH) {
        return car;
      }
    }
    return null;
  }

  public void printCars(int time, ArrayList < Car > cars) {
    System.out.println("At " + time + " : teamNumber | currentSpeed | currentDistanceCovered");
    for (int i = 0; i < cars.size(); i++) {
      /*
       * Dividing total distance into 100 ' = ' denoting the racetrack
       * ' x ' will denote the car at that position/ part of track
       */
      Car car = cars.get(i);
      int distanceIndex = (int)(car.getCurrentDistanceCovered() / Constants.DISPLAY_THRESHOLD_LENGTH);
      for (int j = 0; j < Constants.RACE_LENGTH / Constants.DISPLAY_THRESHOLD_LENGTH; j++) {
        System.out.print(j == distanceIndex ? car.getTeamNumber() : "=");
      }
      System.out.println();
    }
    for (int i = 0; i < 20; ++i)
      System.out.println();
  }

  public void printCarStats(Car car) {
    System.out.println("Team = " + car.getTeamNumber() + " | Top Speed = " + car.getTopSpeed() +
      " | Accn = " + car.getAcceleration() + " | Head Start = " + car.getCurrentDistanceCovered());
  }
}