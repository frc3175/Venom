package frc.robot.autocommands;

import frc.robot.subsystems.Intake;


public class IntakeUp extends AutoCommandBase {
	
	
	public IntakeUp(double timeOut) {
		super(timeOut);
	}

	public void init() {
	}

	@Override
	protected void run() {
		Intake.IntakeUp();
    }

	@Override
	public void end() {
	}

	@Override
	protected String getCommandName() {
		return "Intake Up";
	}

}