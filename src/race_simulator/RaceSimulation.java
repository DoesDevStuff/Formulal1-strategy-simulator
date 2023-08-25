package race_simulator;

import java.util.ArrayList;

import race_logic.Race;
import race_logic.Car;
import race_logic.Constants;

public class RaceSimulation {
  public static void main(String[] args) throws InterruptedException {
    // Initialize an ArrayList to store car objects
    ArrayList < Car > cars = new ArrayList < Car > ();
    // Create a Race instance to handle race logic
    Race raceSim = new Race();

    // Initialize race cars and add them to the cars ArrayList
    initRaceCars(cars, raceSim);

    // Initialize time and print initial positions of cars
    int time = 0;
    printCars(time, cars, raceSim);

    // loop continues till race is finished
    while (checkWinner(cars, raceSim) == null) {
      time += 2;
      assessRaceCars(cars, raceSim);
      printCars(time, cars, raceSim);

      // Pause to simulate the display interval
      Thread.sleep(Constants.DISPLAY_THRESHOLD_TIME);
    }

    sortCars(cars);
    System.out.println("Race complete! Won by : " + cars.get(0).getTeamNumber());
    for (int i = 0; i < cars.size(); i++) {
      Car car = cars.get(i);
      System.out.println("\nTEAM NUMBER " + car.getTeamNumber());
      System.out.print("DISTANCE LEFT " + (int)(Constants.RACE_LENGTH - car.getCurrentDistanceCovered()));
      System.out.println(" NITROUS USED " + " " + car.isNitroUsed());
    }
  }

  static void initRaceCars(ArrayList < Car > cars, Race raceSim) {
    for (int i = 0; i < Constants.TOTAL_CARS; i++) {
      Car car = new Car(Constants.TOTAL_CARS, i + 1);
      cars.add(car);
      raceSim.printCarStats(car);
    }
  }

  static void assessRaceCars(ArrayList < Car > cars, Race raceSim) {
    raceSim.calculateDistances(cars);
    raceSim.calculateSpeeds(cars);
  }

  static Car checkWinner(ArrayList < Car > cars, Race raceSim) {
    for (int i = 0; i < cars.size(); i++) {
      Car car = cars.get(i);
      if (car.getCurrentDistanceCovered() >= Constants.RACE_LENGTH) {
        return car;
      }
    }
    return null;
  }

  static void printCars(int time, ArrayList < Car > cars, Race raceSim) {
    System.out.println("At " + time + " : teamNumber | currentSpeed | currentDistanceCovered");
    for (int i = 0; i < cars.size(); i++) {
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

  //bubble sort
  // Sort the cars in descending order based on their current distances
  static void sortCars(ArrayList < Car > cars) {
    for (int i = 0; i < cars.size() - 1; i++) {
      for (int j = 0; j < cars.size() - i - 1; j++) {
        Car car1 = cars.get(j);
        Car car2 = cars.get(j + 1);
        if (car1.getCurrentDistanceCovered() < car2.getCurrentDistanceCovered()) {
          Car temp = car1;
          cars.set(j, car2);
          cars.set(j + 1, temp);
        }
      }
    }
  }
}