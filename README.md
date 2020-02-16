Group 9:
Sam Whitty		(GitHub samwhitty)	(Student ID: 101002258)
Everett	Soldatt		(GitHub everettsoldaat)	(Student ID: )
Michael Evans		(GitHub MikeEvans6)	(Student ID: 101067157)
Said Omar		(GitHub saidsheikhomar)	(Student ID: 101004648)

Iteration 2:

02/15/2020

Relevant Files:

  /src/Iteration1

  ElevatorSubsystem: 
  
  - Recieves information from the scheduler provided by the FloorSubsystem
  and sends it back to the scheduler.

  FloorSubsystem: 
  
  - Reads from an input file and sends instructions to the schedular by using a queue. Changes state based on the

  Scheduler:
  
  - Takes information from the Floor queue and moves it to the Elevator queue to be 
  proccessed by the elevator. 

  /src/Iteration2

  FloorStateMachine:
  
  - Defines the state that a floor is in, hiding which functions are available and what functions are unavailable. FloorStateMachine extends the FloorSubsystem and changes state through functions defined within FloorSubsystem.

  SchedulerStateMachine:
  
  - Defines the state that the scheduler is in, hiding which functions are available and what functions are unavailable. SchedulerStateMachine extends the SchedulerStateMachine and changes state through functions defined within SchedulerSubsystem.

  TimeData:


All other classes ( found in /src and /src/util folders) are for the next iteration or previous iteration.

	Member Tasks:
  
	Everett
		- Scheduler state machine

	Michael
		- Elevator state machine

	Sam
		- Scheduler state machine diagram
		- README
		- TimeData changes

	Said
		- Elevator state machine diagram

Deliverables:
- All Relevent Java Files
- UML Class diagram: Iteration2_ClassDiagram.png
- UML Sequence diagram: iteration2_sequence.png
- SchedulerSubsystem State Machine diagram: SchedulerStateMachineDiagram.png
- FloorSubsystem State Machine diagram: FloorStateMachineDiagram.png
