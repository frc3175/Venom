package frc.robot.autocommands;

import frc.robot.Limelight;

public class LimelightDistance extends AutoCommandBase {
	
	
	
	public LimelightDistance(double timeOut) {
		super(timeOut);
	}

	public void init() {
	}

	@Override
	protected void run() {
		Limelight.forceLEDsOn();
        Limelight.goToDistance(true);
    }

	@Override
	public void end() {
	}

	@Override
	protected String getCommandName() {
		return "Shoot Ball";
	}

}