package Auton.Autos.Deprecated;

import Auton.Autos.Auto;
import Subsystem.Drivetrain;
import Subsystem.Drivetrain.driveCoords;
import Subsystem.Drivetrain.driveType;
import Subsystem.Intake;
import Subsystem.Loop;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class middleSwitchTimeBased extends Auto {
	public static middleSwitchTimeBased main = new middleSwitchTimeBased();
	private static double driveTime = 1.5;
	private static double driveTime2 = 1.0;
	private static double scoringTime = 2.0;
	private static double startTime;
	public middleSwitchTimeBased() {
		super.registerLoop(new Loop()
		{
			@Override
			public void onStart() {
				startTime = Timer.getFPGATimestamp();
			}

			@Override
			public void onloop() {
				SmartDashboard.putNumber("timer", Timer.getFPGATimestamp());
				
				if(Timer.getFPGATimestamp()-startTime<driveTime)
				{
					SmartDashboard.putBoolean("in drive", true);
					Intake.getInstance().setPosition(0.0);
					if(gameData.substring(0,1).equals("R"))
					{
						Drivetrain.getInstance().swerve(-.3, 0.27,0.0, driveCoords.FIELDCENTRIC, driveType.PERCENTPOWER);
					}
					else
					{
						Drivetrain.getInstance().swerve(-.3, -0.28,0.0, driveCoords.FIELDCENTRIC, driveType.PERCENTPOWER);
					}
				}
				else if(Timer.getFPGATimestamp()-startTime<driveTime+driveTime2)
				{
					Drivetrain.getInstance().swerve(-.3, 0.0,0.0, driveCoords.FIELDCENTRIC, driveType.PERCENTPOWER);
				}
				else if(Timer.getFPGATimestamp()-startTime<driveTime+scoringTime+driveTime2)
				{
					SmartDashboard.putBoolean("in drive", false);
					Intake.getInstance().setWantedState(Subsystem.Intake.systemStates.Scoring);
					Drivetrain.getInstance().swerve(0.0, 0.0, 0.0, driveCoords.FIELDCENTRIC, driveType.PERCENTPOWER);
				}
				else
				{
					Intake.getInstance().setWantedState(Subsystem.Intake.systemStates.Neutral);
				}
				
			}

			@Override
			public void stop() {
				// TODO Auto-generated method stub
				
			}
	
		});
	}
	

}
