package frc.robot.autocommands;

import frc.robot.Limelight;
import frc.robot.config.Constants;
import frc.robot.subsystems.Shooter;


public class ShootBall extends AutoCommandBase {
	
	private double hopperPower;
	private int RPMarray;
	
	
	public ShootBall(double timeOut, int RPMarray, double hopperPower) {
		super(timeOut);
		this.RPMarray = RPMarray;
		this.hopperPower = hopperPower;
	}

	public void init() {
	}

	@Override
	protected void run() {
		//Shooter.shoot(Limelight.RPMs[RPMarray]);
		Shooter.hopperPower(hopperPower);

    }

	@Override
	public void end() {
        //Shooter.shoot(0);
        Shooter.hopperPower(0);
	}

	@Override
	protected String getCommandName() {
		return "Shoot Ball";
	}

}