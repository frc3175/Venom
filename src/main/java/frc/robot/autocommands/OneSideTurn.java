package frc.robot.autocommands;

import frc.robot.DriveTrain;

public class OneSideTurn extends AutoCommandBase {
	private double right, left;
	
	public OneSideTurn(double timeOut, double left, double right) {
		super(timeOut);
		this.right = right;
		this.left = left;
	}

	@Override
	public void init() {
		
	}

	@Override
	protected void run() {
		DriveTrain.arcadeDrive(left, right);
	}

	@Override
	public void end() {
		DriveTrain.stop();
	}

	@Override
	protected String getCommandName() {
		return null;
	}

}