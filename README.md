# CS-4310-Race-Conditions

## Configuring Your System to Run the Code
* Setting up Your System
  * Download the JDK from https://www.oracle.com/java/technologies/downloads/#jdk17-windows so you can compile the source code
  * Download the JRE from https://www.java.com/en/download/manual.jsp so you can run the compiled java code
  * Edit your system variables to include `C:\Program Files\Java\jre1.8.0_311\bin` in your path
* Compiling the Code
  * In your terminal, change the directory to where the .java files are located
  * Use the command `javac RaceConditions.java` to compile the program without semamphores
  * Use the command `javac RaceConditionsPart2.java` to compile the program with semaphores
* Running the Code
  * The .class files should be in the same directory where the source files were compiled
  * Use the command `java RaceConditions` to run the program without semaphores
  * Use the command `java RaceConditionsPart2` to run the program with semaphores
## Race Condition
To reproduce the race condition, run `RaceConditions.class` in a terminal or load `RaceConditions.java` into an IDE and run it that way.
## Project Description
![image](https://user-images.githubusercontent.com/43283288/145481657-855c1fd3-9db0-45ef-b67d-1754133caae3.png)
