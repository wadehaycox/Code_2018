
/**
 * @author 222002121
 *
 */
public class Main {
	public static void main(String arg[])
	{
		Trajectory t = new Trajectory();
		t.addWaypoint(new Waypoint(0.0, 0.0, 0.0,0.0));
		t.addWaypoint(new Waypoint(10.0,10.0,0.0,0.0));
		t.addWaypoint(new Waypoint(15.0,10.0,0.0));
		t.calculateTrajectory();
		//t.print();
		for(double i = 0.0; i<1.0; i=i+.1)
		{
		System.out.println(t.getSpeed(i));
		}
		//t.print();
		
	}
}