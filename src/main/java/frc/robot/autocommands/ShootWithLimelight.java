package frc.robot.autocommands;

import frc.robot.Limelight;
import frc.robot.config.Constants;
import frc.robot.subsystems.Shooter;


public class ShootWithLimelight extends AutoCommandBase {
	
	private double hopperPower;
	
	
	public ShootWithLimelight(double timeOut, double hopperPower) {
		super(timeOut);
		this.hopperPower = hopperPower;
	}

	public void init() {
	}

	@Override
	protected void run() {
        Limelight.dumbLineup();
        Limelight.goToDistance(true);
        Shooter.shoot(true);
        if(timeOut <= 1) {
		    Shooter.hopperPower(Constants.HOPPER_AGITATION_FORWARD);
        } else if(timeOut > 1) {
            Shooter.hopperPower(hopperPower);
        }
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