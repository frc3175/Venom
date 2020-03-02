package frc.robot.autocommands;

import frc.robot.Limelight;

public class LimelightAlign extends AutoCommandBase {
	
	
	
	public LimelightAlign(double timeOut) {
		super(timeOut);
	}

	public void init() {
	}

	@Override
	protected void run() {
        Limelight.dumbLineup();
    }

	@Override
	public void end() {
	}

	@Override
	protected String getCommandName() {
		return "Shoot Ball";
	}

}