package frc.robot.autocommands;

import frc.robot.subsystems.Shooter;


public class ShootBall extends AutoCommandBase {
	
	private double hopperPower;
	
	
	public ShootBall(double timeOut, double hopperPower) {
		super(timeOut);
		this.hopperPower = hopperPower;
	}

	public void init() {
	}

	@Override
	protected void run() {
		Shooter.shoot(true);
		Shooter.hopperPower(hopperPower);

    }

	@Override
	public void end() {
        Shooter.shoot(false);
        Shooter.hopperPower(0);
	}

	@Override
	protected String getCommandName() {
		return "Shoot Ball";
	}

}