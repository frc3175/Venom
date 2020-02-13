package frc.robot.autocommands;

import frc.robot.DriveTrain;


public class TurnToAngle extends AutoCommandBase {
	
	private double angle = 0;
	private double power;
	private double angleOffSet;
	
	
	public TurnToAngle(double timeOut, double angle, double angleOffSet, double power) {
		super(timeOut);
		this.angle = angle;
		this.power = power;
		this.angleOffSet = angleOffSet;
	}

	
	public void init() {
		DriveTrain.resetGyro();
	}

	@Override
	protected void run() {
		if(DriveTrain.getAngle() > 0){
			if(DriveTrain.getAngle() < (angle - angleOffSet))
				DriveTrain.arcadeDrive(0, power);
			else if(DriveTrain.getAngle() < angle && DriveTrain.getAngle() > angle - angleOffSet)
				DriveTrain.arcadeDrive(0, .15);
		}
		else{
			if(DriveTrain.getAngle() > (angle + angleOffSet))
				DriveTrain.arcadeDrive(0, -power);
			else if(DriveTrain.getAngle() > angle && DriveTrain.getAngle() < angle + angleOffSet)
				DriveTrain.arcadeDrive(0, -.15);
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