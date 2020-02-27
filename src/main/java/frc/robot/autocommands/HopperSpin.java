package frc.robot.autocommands;

import frc.robot.subsystems.Shooter;


public class HopperSpin extends AutoCommandBase {
	
	private double hopperPower;
	
	
	public HopperSpin(double timeOut, double hopperPower) {
		super(timeOut);
		this.hopperPower = hopperPower;
	}

	public void init() {
	}

	@Override
	protected void run() {
		Shooter.hopperPower(hopperPower);

    }

	@Override
	public void end() {
        Shooter.hopperPower(0);
	}

	@Override
	protected String getCommandName() {
		return "Hopper Spin";
	}

}