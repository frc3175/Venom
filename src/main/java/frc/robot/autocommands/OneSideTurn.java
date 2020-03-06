package frc.robot.autocommands;

import frc.robot.DriveTrain;

public class OneSideTurn extends AutoCommandBase {
	private double right, left, distance;
	
	public OneSideTurn(double timeOut, double left, double right, double distance) {
		super(timeOut);
		this.right = right;
		this.left = left;
		this.distance = distance;
	}

	@Override
	public void init() {
		
	}

	@Override
	protected void run() {
		if(Math.abs(DriveTrain.getEncoderDistanceRight()) < Math.abs(distance)){
			DriveTrain.drive(left, right);
		} else {
			DriveTrain.drive(0, 0);
		}
	}


	@Override
	public void end() {
	}

	@Override
	protected String getCommandName() {
		return null;
	}

}