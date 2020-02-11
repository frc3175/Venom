package frc.robot.autocommands;

import frc.robot.DriveTrain;

public class DriveDistance extends AutoCommandBase{
	private double power, distance;
	
	public DriveDistance(double timeOut, double power, double distance) {
		super(timeOut);
		this.power = power;
		this.distance = distance;
	}
	
	public void init(){
		DriveTrain.resetEncoder();
		DriveTrain.resetGyro();
	}

	@Override
	protected void run() {
		if(Math.abs(DriveTrain.getEncoderAverage()) < Math.abs(distance)){
			DriveTrain.driveStraight(power);
		}
		else
			DriveTrain.stop();
		
	}

	@Override
	public void end() {
		DriveTrain.stop();
		DriveTrain.resetGyro();
		DriveTrain.resetEncoder();
	}

	@Override
	protected String getCommandName() {
		return "Drive Distance";
	}
	
}