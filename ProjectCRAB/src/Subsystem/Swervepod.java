package Subsystem;
import com.ctre.phoenix.CTREJNIWrapper;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Swervepod extends Subsystem {
	
	private TalonSRX driveMotor;
	private TalonSRX steerMotor;
	private int id;
	private int direction;
	private double currAngle;
	private double angleError;
	
	private double PI = Math.PI;
	
	Swervepod(int id,TalonSRX driveMotor,TalonSRX steerMotor) {
		this.id = id;
		this.driveMotor = driveMotor;
		this.steerMotor = steerMotor;
		direction = 1; 
		this.driveMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		this.steerMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,0,0);
	}
	
	public void setPod(double Speed, double Angle){
		//convert Angle from -pi to pi into 0 to 2pi
		if(Angle<0)
		{
			Angle = Angle + 2 * Math.PI;
		}
		
		double steerPosition = findSteerPosition(Angle) % 4096;
		
		steerMotor.set(ControlMode.Position, steerPosition);
		driveMotor.set(ControlMode.Velocity, (Speed * direction));	
	}
	
	private double findSteerPosition(double wantedAngle){
		currAngle = ((steerMotor.getSelectedSensorPosition(0)/4096) * (2*PI));
		angleError = Math.abs((wantedAngle - currAngle));
		if(angleError < (PI/2) || angleError > (3*PI/2)) {
			direction = 1;
			return ((wantedAngle/(2*PI))*4096);
		}
		else {
			direction = -1; 
			return (((wantedAngle+PI)/(2*PI))*4096);
		}
	}
	
	@Override
	public void zeroAllSensors() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkSystem() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void registerLoop() {
		// N/A
	}

}
