package Auton.Paths;

import Auton.Trajectory;
import Auton.Waypoint;

public class middleToLeftSwitch extends Path {
	public static middleToLeftSwitch main = new middleToLeftSwitch();
	private Trajectory t = new Trajectory();
	private middleToLeftSwitch() {
		t.addWaypoint(new Waypoint(0.0, 0.0, 0.0));
		t.addWaypoint(new Waypoint(-4.85,-5.0,0.0));
		t.addWaypoint(new Waypoint(-4.85, -10.0, 0.0));
		super.regesterTrajectory(t);
	}
}
