package race_simulator;

import java.util.ArrayList;

import race_logic.Constants;
import race_logic.SingularCar;

public class SimulateRaceMain {
	public static void main(String[] args) throws InterruptedException {
        // Initialize an ArrayList to store car objects
        ArrayList<SingularCar> cars = new ArrayList<>();

        // Initialize time and print initial positions of cars
        int time = 0;
        initRaceCars(cars);

        while (checkWinner(cars) == null) {
            time += 2;
            assessRaceCars(cars);
            printCars(time, cars);

            // Pause to simulate the display interval
            Thread.sleep(Constants.DISPLAY_THRESHOLD_TIME);
        }

        sortCars(cars);
        System.out.println("Race complete! Won by: " + cars.get(0).getTeamNumber());
        for (int i = 0; i < cars.size(); i++) {
        	SingularCar car = cars.get(i);
            System.out.println("\nTEAM NUMBER " + car.getTeamNumber());
            System.out.print("DISTANCE LEFT " + (int) (Constants.RACE_LENGTH - car.getCurrentDistanceCovered()));
            System.out.println(" NITROUS USED " + " " + car.isNitroUsed());
        }
    }

    static void initRaceCars(ArrayList<SingularCar> cars) {
        for (int i = 0; i < Constants.TOTAL_CARS; i++) {
        	SingularCar car = new SingularCar(cars, i + 1);
            cars.add(car);
            printCarStats(car);
        }
    }

    static void assessRaceCars(ArrayList<SingularCar> cars) {
        for (int i = 0; i < cars.size(); i++) {
        	SingularCar car = cars.get(i);
            car.calculateDistance();
        }
        for (int i = 0; i < cars.size(); i++) {
        	SingularCar car = cars.get(i);
            car.calculateSpeed();
        }

        for (int i = 0; i < cars.size() - 1; i++) {
        	SingularCar carI = cars.get(i);
            for (int j = i + 1; j < cars.size(); j++) {
            	SingularCar carJ = cars.get(j);
                double distanceDifference = Math.abs(carI.getCurrentDistanceCovered() - carJ.getCurrentDistanceCovered());
                if (distanceDifference <= 10) {
                    carI.setReduceSpeed(true);
                    carJ.setReduceSpeed(true);
                }
            }
        }

        for (int i = 0; i < cars.size(); i++) {
        	SingularCar car = cars.get(i);
            if (car.isReduceSpeed()) {
                car.setCurrentSpeed(car.getCurrentSpeed() * car.getHandlingFactor());
                car.setReduceSpeed(false);
                System.out.println("REDUCED SPEED! " + car.getTeamNumber());
            }
        }

    }

    static SingularCar checkWinner(ArrayList<SingularCar> cars) {
        for (int i = 0; i < cars.size(); i++) {
        	SingularCar car = cars.get(i);
            if (car.getCurrentDistanceCovered() >= Constants.RACE_LENGTH) {
                return car;
            }
        }
        return null;
    }

    static void printCars(int time, ArrayList<SingularCar> cars) {
        System.out.println("At " + time + " : teamNumber | currentSpeed | currentDistanceCovered");
        for (int i = 0; i < cars.size(); i++) {
        	SingularCar car = cars.get(i);
            int distanceIndex = (int) (car.getCurrentDistanceCovered() / Constants.DISPLAY_THRESHOLD_LENGTH);
            for (int j = 0; j < Constants.RACE_LENGTH / Constants.DISPLAY_THRESHOLD_LENGTH; j++) {
                System.out.print(j == distanceIndex ? car.getTeamNumber() : "=");
            }
            System.out.println();
        }
        for (int i = 0; i < 20; ++i)
            System.out.println();
    }

    // bubble sort
    // Sort the cars in descending order based on their current distances
    static void sortCars(ArrayList<SingularCar> cars) {
        for (int i = 0; i < cars.size() - 1; i++) {
            for (int j = 0; j < cars.size() - i - 1; j++) {
            	SingularCar car1 = cars.get(j);
            	SingularCar car2 = cars.get(j + 1);
                if (car1.getCurrentDistanceCovered() < car2.getCurrentDistanceCovered()) {
                	SingularCar temp = car1;
                    cars.set(j, car2);
                    cars.set(j + 1, temp);
                }
            }
        }
    }

    static void printCarStats(SingularCar car) {
        System.out.println("Team = " + car.getTeamNumber() + " | Top Speed = " + car.getTopSpeed() +
                " | Accn = " + car.getAcceleration() + " | Head Start = " + car.getCurrentDistanceCovered());
    }
}

