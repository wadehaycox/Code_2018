package Auton.Commands;

import Subsystem.Intake;
import Subsystem.Intake.systemStates;
import Subsystem.Loop;
import Subsystem.Superstructure;

public class Scoring extends Command {

	public Scoring(double scoreTime) {
		super.setLoop(new Loop() {
			@Override
			public void onStart() {
				Intake.getInstance().setWantedState(systemStates.Scoring);
			}
			@Override
			public void onloop() {

			}
			@Override
			public void stop() {
        Intake.getInstance.setWantedState(systemStates.Neutral);
			}
		});
    super(commandType.timeBased,scoreTime);
	}
	public setTrigger(boolean t)
	{
		super.setTrigger(t);
	}
}
