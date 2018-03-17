

import java.awt.geom.Arc2D;
import java.util.ArrayList;


public class Trajectory {
	private double kMaxVelocity = 4.0;
	private double kMaxAcceleration=5.0; 
	private double timeStep = .01;
	private double simTime = 0.0;
	private ArrayList<Waypoint>  points;
	private ArrayList<Double> speed;
	private ArrayList<Double> angle;
	public Trajectory() {
		points = new ArrayList<Waypoint>();
		speed = new ArrayList<Double>();
		angle = new ArrayList<Double>();
	}
	public void addWaypoint(Waypoint w)
	{
		points.add(w);
	}
	public void calculateTrajectory()
	{
		double currSpeed = 0.0;
		double currX = points.get(0).getX();
		double currY = points.get(0).getY();
		double endSpeed = points.get(1).getSpeed();
		double currAngle = 0.0;
		int waypointIdx = 1;
		boolean isSlowing = false;
		while(simTime < 8.0)
		{
			
			double stoppingDistance = (Math.pow(endSpeed, 2)-Math.pow(currSpeed,2))/(-2.0*kMaxAcceleration);
			double distanceToWaypoint = Math.sqrt(Math.pow(currX-points.get(waypointIdx).getX(), 2)+ Math.pow(currY - points.get(waypointIdx).getY(),2));
			currAngle = Math.atan2(points.get(waypointIdx).getY()-currY, points.get(waypointIdx).getX()-currX);
			angle.add(currAngle);
			if(stoppingDistance>distanceToWaypoint)
			{
				isSlowing = true;
			}
			if(Math.abs(currSpeed) <= kMaxVelocity && !isSlowing)
			{
				if((kMaxVelocity - Math.abs(currSpeed)) < kMaxAcceleration*timeStep)
				{
					currSpeed = kMaxVelocity;
				}
				else
				{
					currSpeed = currSpeed + kMaxAcceleration * timeStep;
				}
			}
			else
			{
				currSpeed = currSpeed - kMaxAcceleration * timeStep;
				if(currSpeed<endSpeed)
				{
					currSpeed = endSpeed;
					if(waypointIdx < points.size()-1)
					{
						waypointIdx++;
						isSlowing = false;
						endSpeed = points.get(waypointIdx).getSpeed();
					}
				}		
			}
			speed.add(currSpeed);
			
			currX = currX + currSpeed * Math.cos(currAngle) * timeStep;
			currY = currY + currSpeed * Math.sin(currAngle) * timeStep;
			//System.out.println("X: " + currX);
			//System.out.println(currY);
			//System.out.println("stop: " + stoppingDistance);
			//System.out.println("dtw " + distanceToWaypoint);
			//System.out.println(currSpeed);
			//System.out.println("x: "+  currX  + " y: " +currY + " sp: " + currSpeed +" angle " + (currAngle * 180.0/Math.PI) +  " currway " + waypointIdx );
			simTime+= timeStep;
		}
	}
	public double getSpeed(double Time)
	{
		return speed.get((int)(Time/timeStep));
	}
	public double getWheelAngle(double Time)
	{
		return angle.get((int)(Time/timeStep));
	}
	public void print()
	{
	    for (int i = 0; i<speed.size(); i++)
	    {
	    	System.out.println("index: "+ i + " speed: " + speed.get(i) + " time: " + i*timeStep);
	    }
	}
	public void setWantedPos() {
		
	}
}

