package frc.robot.autocommands;

import frc.robot.DriveTrain;
import frc.robot.config.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class DriveWithIntake extends AutoCommandBase{
	private double power, distance;
	
	public DriveWithIntake(double timeOut, double power, double distance) {
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
		Intake.intakePowerCell(Constants.INTAKE_SPEED);
		Shooter.hopperPower(Constants.HOPPER_AGITATION_REVERSE);
		if(Math.abs(DriveTrain.getEncoderAverage()) < Math.abs(distance)){
			if(Math.abs(DriveTrain.getEncoderAverage()) < Math.abs(distance)){
				DriveTrain.drive(power, -power);
			} else {
				DriveTrain.drive(0, 0);
			}
		}
		else
            //DriveTrain.stop();
            Intake.intakePowerCell(0);
		}

	@Override
	public void end() {
		//DriveTrain.stop();
		DriveTrain.resetGyro();
        DriveTrain.resetEncoder();
	}

	@Override
	protected String getCommandName() {
		return "Drive With Intake";
	}
	
}