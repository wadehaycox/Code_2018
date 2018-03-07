package Subsystem;

import org.usfirst.frc.team6713.robot.Constants;

import Subsystem.Intake.systemStates;

public class Superstructure {
	private static Superstructure instance = new Superstructure();
	private Intake intake;
	private Elevator elevator;
	private systemStates currState;
 	private systemStates lastState;
 	private wantedStates wantedState;
 	private Loop_Manager loopMan = Loop_Manager.getInstance();
 	private double elevatorCommandedHeight = 0.0; 
 	public enum systemStates{
 		Intaking,
 		UnJamming,
 		MovingToPosition,
 		Neutral, 
 		Holding,
 		Scoring
 	};
 	public enum wantedStates{
 		Intaking,
 		Switch,
 		ScaleLow,
 		ScaleMid,
 		ScaleHigh,
 		Score,
 		Down,
 		Unjamming,
 		Neutral
 	}
	private Superstructure()
	{
		intake = Intake.getInstance();
		elevator = Elevator.getInstance();
	}
	public static Superstructure getInstance()
	{
		return instance;
	}
	
	public void registerLoop() {
 		loopMan.addLoop(new Loop() {
 			
			@Override
			public void onStart() {
				currState = systemStates.Neutral;
			 	lastState = systemStates.Neutral;
			 	wantedState = wantedStates.Neutral;
			}
			
			@Override
			public void onloop() {
				switch(currState)
				{
				case Intaking:
					elevator.setWantedState(Elevator.systemStates.POSITION_FOLLOW);
					if(elevator.getHeight() > .1)
					{
						elevator.setThrottleValue(0.0);
					}
					else
					{
						intake.setWantedState(Intake.systemStates.Intaking);
					}
					lastState = systemStates.Intaking;
					checkState();
					break;
				case MovingToPosition:
					intake.setWantedState(Intake.systemStates.Stowing);
					elevator.setWantedState(Elevator.systemStates.POSITION_FOLLOW);
					if(Math.abs(elevator.getHeight()-elevatorCommandedHeight)<Constants.ELEVATORACCEPTEDERROR)
					{
						elevator.setThrottleValue(elevatorCommandedHeight);
						checkState();
					}
					else
					{
						currState = systemStates.Holding;
					}
					lastState = systemStates.MovingToPosition;
					break;
				case Holding:
					checkState();
					lastState = systemStates.Holding;
					break;
				case Neutral:
					break;
				case UnJamming:
					intake.setWantedState(Intake.systemStates.UnJamming);
					checkState();
					lastState = systemStates.UnJamming;
					break;
				case Scoring:
					intake.setWantedState(Intake.systemStates.Scoring);
					break;
			
				
				}
				
			}

			@Override
			public void stop() {
				// TODO Auto-generated method stub
				
			}
		});

 		}
	private void checkState()
	{
		switch(wantedState)
		{
		case Intaking:
			currState = systemStates.Intaking;
			break;
		case Neutral:
			break;
		case ScaleHigh:
			elevatorCommandedHeight = Constants.SCALEHIGHHEIGHT;
			break;
		case ScaleLow:
			elevatorCommandedHeight = Constants.SCALELOWHEIGHT;
			break;
		case ScaleMid:
			elevatorCommandedHeight = Constants.SCALEMIDHEIGHT;
			break;
		case Score:
			currState = systemStates.Scoring;
			break;
		case Switch:
			elevatorCommandedHeight = Constants.SWITCHHEIGHT;
			break;
		case Unjamming:
			currState = systemStates.UnJamming;
			break;
		case Down:
			elevatorCommandedHeight = 0.0;
			break;
		}
		if(Math.abs(elevator.getHeight()-elevatorCommandedHeight)<Constants.ELEVATORACCEPTEDERROR)
		{
			currState = systemStates.MovingToPosition;
		}
	}
}