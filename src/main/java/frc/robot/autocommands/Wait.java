package frc.robot.autocommands;

import frc.robot.DriveTrain;

public class Wait extends AutoCommandBase {

	
	public Wait(double timeOut) {
		super(timeOut);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void run() {
		// TODO Auto-generated method stub
		DriveTrain.stop();
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getCommandName() {
		return null;
	}

}