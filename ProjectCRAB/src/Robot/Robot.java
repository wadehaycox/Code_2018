/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package Robot;

import Subsystem.*;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	private Loop_Manager myLoops = Loop_Manager.getInstance();
	private Drivetrain drive = Drivetrain.getInstance(); 
	private PixyCam camera = PixyCam.getInstance();
	private Joystick j;
	private Joystick j2;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		camera.registerLoop();
		drive.registerLoop();
		myLoops.startLoops();
		j = new Joystick(0);
		j2 = new Joystick(1);
		
	}
	@Override
	public void autonomousInit() {
		
		
	}
	
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		myLoops.runLoops();
		
	}
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		myLoops.runLoops();
		if(j.getRawButton(1)) {
			camera.setSystemState(PixyCam.systemStates.TRACKING_CUBE);
			drive.setSystemState(Drivetrain.systemStates.VISION_TRACK_TANK);
		}
		drive.swerve(j.getY(), j.getX(), j.getX(), Drivetrain.driveCoords.FIELDCENTRIC, Drivetrain.driveType.PERCENTPOWER);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	
}