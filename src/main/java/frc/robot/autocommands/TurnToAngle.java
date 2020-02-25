package frc.robot.autocommands;

import frc.robot.DriveTrain;


public class TurnToAngle extends AutoCommandBase {
	
	private double angle = 0;
	private double power;
	
	
	public TurnToAngle(double timeOut, double angle, double power) {
		super(timeOut);
		this.angle = angle;
		this.power = power;
	}

	
	public void init() {
		DriveTrain.resetGyro();
	}

	@Override
	protected void run() {
		if (DriveTrain.getAngle() < angle && (Math.abs(DriveTrain.getAngle() - angle) > 2)) { // 2 is the range 
			DriveTrain.drive(power, power); // should turn left
		} else if (DriveTrain.getAngle() > angle && (Math.abs(DriveTrain.getAngle() - angle) > 2)) { // range
			DriveTrain.drive(-power, -power); // should turn right
		} else {
			DriveTrain.drive(0, 0);
		}
	}

	@Override
	public void end() {
		DriveTrain.disablePID();
		System.out.println(DriveTrain.getAngle());
		DriveTrain.resetGyro();
		
		//DriveTrain.resetEncoder();
		//SmartDashboard.putNumber("AUTO GYROOO", DriveTrain.getAngle());
	}

	@Override
	protected String getCommandName() {
		return "turn to angle";
	}

}