# SYSC3303B_Project Group 9:
Sam Whitty              (GitHub samwhitty)      (Student ID: 101002258)

Everett	Soldaat         (GitHub everettsoldaat) (Student ID: 101038071)

Michael Evans           (GitHub MikeEvans6)     (Student ID: 101067157)

Said Omar               (GitHub saidsheikhomar) (Student ID: 101004648)

# Iteration 3:

Due: 03/07/2020
GitHub : https://github.com/samwhitty/SYSC3303B_Project

# To Run

1. With Eclipse
  i. Import into eclipse
  ii. Right-click scheduler, click run-as -> java application
  iii. Right-click elevatorSubsystem, click run-as-> java application
  iv. Rick-click floorSubsystem, click run-as-> java application
2. Through separate console
  Run the files in the following order:
  1. Scheduler
  2. ElevatorSubsystem
  3. FloorSubsystem


# Relevant Files:

## /src/iteration3

### FloorStateMachine:

- Defines the state that a floor is in, hiding which functions are available and what functions are unavailable. FloorStateMachine extends the FloorSubsystem and changes state through functions defined within FloorSubsystem. 

### SchedulerStateMachine:

- Defines the state that the scheduler is in, hiding which functions are available and what functions are unavailable. SchedulerStateMachine extends the SchedulerStateMachine and changes state through functions defined within SchedulerSubsystem.

### ElevatorSubsystem: 

- Recieves information from the scheduler and sends it back to the scheduler. Controls flow of data using DatagramPacket and DatagramSocket objects, and relevant functions.

### FloorSubsystem: 

- Reads from an input file and sends instructions to the schedular by using a queue. Changes state based on the data provided in input file. Controls flow of data using DatagramPacket and DatagramSocket objects, and relevant functions.

### Scheduler:

- Takes information from the Floor queue and moves it to the Elevator queue to be 
proccessed by the elevator. Controls flow of data using DatagramPacket and DatagramSocket objects, and relevant functions.

## /src/util

### TimeData:
- A data structure used to transmit the times that a request is sent, used by the state machines and subsystems for uniform data transfer. Also provides utility functions to ensure accurate times.

### All other classes ( found in /src and /src/util folders) are for the next iteration or previous iteration.

## Tasks:

### Everett
- State machine changes
- Subsystem changes

### Michael
- State machine changes
- Subsystem changes
- Tests

### Sam
- Scheduler state machine diagram
- Updated class diagram
- README.md, .txt
- TimeData updates

### Said
- Elevator State Machine diagram
- Tests and Test diagram
- 

## Deliverables:
- This README
- Above breakdown of tasks
- Above reflection
- All Relevent Java Files
- UML Class diagram in /diagrams/iter3
- UML Sequence diagram: /diagrams/iter3
- SchedulerSubsystem State Machine diagram: /diagrams/iter3
- ElevatorSubsystem State Machine diagram:  	/diagrams/iter3








# Iteration 2:

Due: 02/15/2020
GitHub : https://github.com/samwhitty/SYSC3303B_Project

# To Run

Import into Eclipse, and run as a Java Project.

# Relevant Files:

## /src/iteration1

### ElevatorSubsystem: 

- Recieves information from the scheduler provided by the FloorSubsystem and sends it back to the scheduler.

### FloorSubsystem: 

- Reads from an input file and sends instructions to the schedular by using a queue. Changes state based on the

### Scheduler:

- Takes information from the Floor queue and moves it to the Elevator queue to be 
proccessed by the elevator. 

## /src/iteration2

### FloorStateMachine:

- Defines the state that a floor is in, hiding which functions are available and what functions are unavailable. FloorStateMachine extends the FloorSubsystem and changes state through functions defined within FloorSubsystem.

### SchedulerStateMachine:

- Defines the state that the scheduler is in, hiding which functions are available and what functions are unavailable. SchedulerStateMachine extends the SchedulerStateMachine and changes state through functions defined within SchedulerSubsystem.

## /src/util

### TimeData:
- A data structure used to transmit the times that a request is sent, used by the state machines and subsystems for uniform data transfer. Also provides utility functions to ensure accurate times.

### All other classes ( found in /src and /src/util folders) are for the next iteration or previous iteration.

## Tasks:

### Everett
- SchedulerStateMachine
- Scheduler changes
- Floor Subsystem changes

### Michael
- ElevatorStateMachine
- ElevatorSubsystem changes
- Floor Subsystem changes
- Tests

### Sam
- Scheduler state machine diagram
- README.md
- TimeData

### Said
- Elevator state machine diagram
- Class diagram
- Sequence diagram

## Deliverables:
- All Relevent Java Files
- UML Class diagram in /diagrams/iter2
- UML Sequence diagram: /diagrams/iter2
- SchedulerSubsystem State Machine diagram: /diagrams/iter2
- ElevatorSubsystem State Machine diagram:  	/diagrams/iter2
