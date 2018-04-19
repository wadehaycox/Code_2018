package Auton.Commands;

import Subsystem.Intake;
import Subsystem.Intake.systemStates;
import Subsystem.Loop;

public class Intaking extends Command {

	public Intaking() {
		super(commandType.triggerBased);
		super.setLoop(new Loop() {
			@Override
			public void onStart() {
				Intake.getInstance().setWantedState(systemStates.Intaking);
			}
			@Override
			public void onloop() {
				Intake.getInstance().setWantedState(systemStates.Neutral);
				setTrigger(Intake.getInstance().isCubeIn());
			}
			@Override
			public void stop() {

			}
		});
	}
	public void setTrigger(boolean t)
	{
		super.setTrigger(t);
	}
}
