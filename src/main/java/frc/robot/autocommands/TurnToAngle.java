package frc.robot.autocommands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveTrain;


public class TurnToAngle extends AutoCommandBase {
	
	private double angle = 0;
	private double power;
    private static AHRS autonGyro = new AHRS(Port.kMXP);
	
	
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
		SmartDashboard.putNumber("Auton Gyro", autonGyro.getAngle());
		if (autonGyro.getAngle() < angle && (Math.abs(autonGyro.getAngle() - angle) > 2)) { // 2 is the range 
			DriveTrain.drive(power, power); // should turn left
		} else if (autonGyro.getAngle() > angle && (Math.abs(autonGyro.getAngle() - angle) > 2)) { // range
			DriveTrain.drive(-power, -power); // should turn right
		} else {
			DriveTrain.drive(0, 0);
		}
	}

	@Override
	public void end() {
		System.out.println(autonGyro.getAngle());
		DriveTrain.resetGyro();
		
	}

	@Override
	protected String getCommandName() {
		return "turn to angle";
	}

}