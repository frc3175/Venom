package frc.robot.autocommands;

import frc.robot.DriveTrain;

public class Wait extends AutoCommandBase {

	
	public Wait(double timeOut) {
		super(timeOut);
	}

	@Override
	public void init() {
		
	}

	@Override
	protected void run() {
		DriveTrain.stop();
	}

	@Override
	public void end() {
		
	}

	@Override
	protected String getCommandName() {
		return null;
	}

}