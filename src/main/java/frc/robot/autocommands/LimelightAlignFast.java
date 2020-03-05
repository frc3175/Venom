package frc.robot.autocommands;

import frc.robot.Limelight;

public class LimelightAlignFast extends AutoCommandBase {
	
	
	
	public LimelightAlignFast(double timeOut) {
		super(timeOut);
	}

	public void init() {
	}

	@Override
	protected void run() {
		Limelight.forceLEDsOn();
        Limelight.autonAlign();
    }

	@Override
	public void end() {
	}

	@Override
	protected String getCommandName() {
		return "Limelight Align Fast";
	}

}