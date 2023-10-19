# Formula 1 Race Simulator

## Introduction
This was started as a practice project to test my Java programming and was largely influenced by the [Google Formula 1 coding problem](https://www.geeksforgeeks.org/google-interview-question-for-java-position/). <br> Heavy modifications were made to the problem statement after the first implementation to clarify areas that seemed vague to me. <br> Furthermore, I have added two implementations of this problem in this repository: 
1. [Non-threaded attempt](https://github.com/DoesDevStuff/Formulal1-strategy-simulator/tree/main/Non%20Threaded%20versions/Improved%20non%20threaded%20version)
2. [multithreading](https://github.com/DoesDevStuff/Formulal1-strategy-simulator/tree/main/src) <br>

I have also added test cases to this to test the multi-threaded version of this code and added features to this that would make either testing or expansion of this current implementation easier.
<br>
The idea was to use as many concepts as I could that would be used in industry-standard code and written in a way that prioritised clean code that is easy to debug and test.

## Problem Statement: 

In a Formula-1 challenge, there are n teams numbered 1 to n. Each team has a car and a driver. Car’s specification are as follows:
* Top speed: (150 + 10 * i) km per hour
* Acceleration: (2 * i) meter per second square.
* Handling factor (hf) = 0.8
* Nitro : Increases the speed to double or top speed, whichever is less. Can be used only once.

Here i is the team number.
The cars line up for the race. The start line for (i + 1)th car is 200 * i meters behind the ith car.

All of them start at the same time and try to attain their top speed. A re-assessment of the positions is done every 2 seconds(So even if the car has crossed the finish line in between, you’ll get to know after 2 seconds). During this assessment, each driver checks if there is any car within 10 meters of his car, his speed reduces to: hf * (speed at that moment). Also, if the driver notices that he is the last one on the race, he uses ‘nitro’.

Taking the number of teams and length of track as the input, Calculate the final speeds and the corresponding completion times.
