package frc.robot.autocommands;

import frc.robot.Limelight;
import frc.robot.config.Constants;
import frc.robot.subsystems.Shooter;


public class ShootWithAlign extends AutoCommandBase {
	
	private double hopperPower;
	
	
	public ShootWithAlign(double timeOut, double hopperPower) {
		super(timeOut);
		this.hopperPower = hopperPower;
	}

	public void init() {
	}

	@Override
	protected void run() {
        Limelight.dumbLineup();
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