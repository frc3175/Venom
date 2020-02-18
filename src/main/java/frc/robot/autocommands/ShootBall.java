package frc.robot.autocommands;

import frc.robot.config.Constants;
import frc.robot.subsystems.Shooter;


public class ShootBall extends AutoCommandBase {
	
	private double topMotorPower;
	
	
	public ShootBall(double timeOut, double topMotorPower) {
		super(timeOut);
		this.topMotorPower = topMotorPower;
	}

	public void init() {
	}

	@Override
	protected void run() {
        Shooter.shoot(topMotorPower, 1);

    }

	@Override
	public void end() {
        Shooter.shoot(0, 0);
        
	}

	@Override
	protected String getCommandName() {
		return "Shoot Ball";
	}

}