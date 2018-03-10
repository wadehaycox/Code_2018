package Auton;

import Subsystem.Loop;

public class Auto {
	private Loop autoLoop;
	public String gameData;
	boolean firstTime = true;
	public Auto(Loop l)
	{
		autoLoop = l;
	}
	public void run(String gameData)
	{
		if(firstTime)
		{
			autoLoop.onStart();
			firstTime = false;
			this.gameData = gameData;
		}
		else
		{
			autoLoop.onloop();
		}
	}	
}

