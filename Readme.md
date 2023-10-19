# Formula 1 Race Simulator

## Introduction
This was started as a practice project to test my Java programming and was largely influenced by the [Google Formula 1 coding problem](https://www.geeksforgeeks.org/google-interview-question-for-java-position/). <br> Heavy modifications were made to the problem statement after the first implementation to clarify areas that seemed vague to me. <br> Furthermore, I have added two implementations of this problem in this repository: 
1. [Non-threaded attempt](https://github.com/DoesDevStuff/Formulal1-strategy-simulator/tree/main/Non%20Threaded%20versions/Improved%20non%20threaded%20version)
2. [multithreading](https://github.com/DoesDevStuff/Formulal1-strategy-simulator/tree/main/src) <br>

I have also added test cases to this to test the multi-threaded version of this code and added features to this that would make either testing or expansion of this current implementation easier.
<br>
The idea was to use as many concepts as I could that would be used in industry-standard code and written in a way that prioritised clean code that is easy to debug and test.

## Problem Statement: 
<details>
<summary>Original Problem Statement</summary>
<br>
In a Formula-1 challenge, there are n teams numbered 1 to n. Each team has a car and a driver. Car’s specification are as follows:
* Top speed: (150 + 10 * i) km per hour
* Acceleration: (2 * i) meter per second square.
* Handling factor (hf) = 0.8
* Nitro : Increases the speed to double or top speed, whichever is less. Can be used only once.

Here i is the team number.
The cars line up for the race. The start line for (i + 1)th car is 200 * i meters behind the ith car.

All of them start at the same time and try to attain their top speed. A re-assessment of the positions is done every 2 seconds (So even if the car has crossed the finish line in between, you’ll get to know after 2 seconds). During this assessment, each driver checks if there is any car within 10 meters of his car, his speed reduces to: hf * (speed at that moment). Also, if the driver notices that he is the last one on the race, he uses ‘nitro’.

Taking the number of teams and length of track as the input, Calculate the final speeds and the corresponding completion times.
</details>

<b> Modified Statement </b> <br>
In a Formula-1 challenge, there are n teams numbered 1 to n. Each team has a car and a driver. The car’s specification are as follows:
1. Top speed: (150 + 10 * i) km per hour
2. Acceleration: (2 * i) meters per second square.
3. Handling factor (hf) = 0.8
4. Nitro: Increases the speed to double or top speed, whichever is less. Can be used only once.

Here is the team number.
The cars line up for the race. The start line for (i + 1)th car is 200 * i meters behind the ith car.

All of them start at the same time and try to attain their top speed.
A re-assessment of the positions is done every 2 seconds (So even if the car has crossed the finish line in between, you’ll get to know after 2 seconds).
During this assessment, each driver checks if there is any car within 10 meters of his car, and his speed reduces to hf * (speed at that moment).
Also, if the driver notices that he is the last one in the race, he uses ‘nitro’.

Taking the number of teams and length of the track as the input, Calculate the final speeds and the corresponding completion times.

Question: Assuming track length = finish line
Question: "If the driver notices that he is the last one in the race, he uses nitro" --- Means the last car will use nitro in the first assessment only
<br>

<b> They look nearly identical! What's the difference? </b> <br>
While the main specifications have stayed the same the difference is in clarifying the points that were noticed when I started my first implementation.
1. When does nitro get used? At the start of the race? Towards the end? after an unspecified amount of time? These weren't explicitly mentioned in the main statement.
2. Not only that some values are assumed by the programmer, eg: The total distance of the race, and when the race is considered "finished" is it when all cars cross the line or the first car that passes the line.
3. Time calculations. Due to the mentioned checking of conditions every two seconds one needed to clarify if the time was to be seen as continous or discrete. This distinction turned out to be especially important in the physics calculations of speed and other dependent variables of distance.

<ins>Thus came the changes in place which have been written down and are follows:</ins> <br>
1. Track length is assumed to be the finish line. Race ends when all cars cross the finish line aka the track length
2. The driver using nitro in the first assessment means they will use it early in the race when realizing they are the last, not waiting for subsequent assessments.
3. Time was taken as discrete so all of are calculations are done discretely. The decision for this was taken not just because of the problem statement conditions but also because we are told that acceleration is constant and thus we know that changes will change between discrete time intervals.

### High-level design 
----------------------------------------------------------------------------------------------------
Program
----------------------------------------------------------------------------------------------------
- Object Car
  - For each car i:
  - Constructor:
      1. arguments:
         1. array of car objects
         2. its own index.
  1. It should construct and insert in the array at its index
  2. Top speed = ( ( (150 + (10 * i) ) * 5) / 18 ) m/s
  3. Acceleration = (2 * i) m/s^2
  4. Others specific to each car

Run time:
Each car should have 2 functions that will be called every n seconds (2 seconds here)
1) Calculate current speed and distance teavelled --- CalculateSpeedAndDistanceTravelled()
2) Reduce speed in case of possible collision with other car --- ReduceSpeedForPossibleCollision()
3) Increase speed by Nitro if it is the last car IncreaseSpeedWithNitro()

----------------------------------------------------------------------------------------------------
2) Object Race:
   - Create instances of cars
   - Call the 3 functions at regular intervals (2 Sec)

Run time:
	- Create and add Car objects, passing each of them an array object, and index
	- In a while loop call the 3 functions
	- Determine a win

----------------------------------------------------------------------------------------------------
When multithreading, the car objects themselves can calculate the (3 functions), The Object Race
just has to monitor every 2 seconds
