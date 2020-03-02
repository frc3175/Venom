package frc.robot.autocommands;

import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;


public class IntakeDown extends AutoCommandBase {
	
	
	public IntakeDown(double timeOut) {
		super(timeOut);
	}

	public void init() {
	}

	@Override
	protected void run() {
		Intake.IntakeDown();
    }

	@Override
	public void end() {
	}

	@Override
	protected String getCommandName() {
		return "Intake Down";
	}

}