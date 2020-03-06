
package frc.robot.automodes;

import frc.robot.Limelight;
import frc.robot.config.Constants;

public class SixBall extends AutonMode {

    public SixBall() {
        super();
    }

    @Override
    protected void run() {
        Limelight.forceLEDsOn();
        shootWithAlign(4.5, Constants.HOPPER_SPEED);
        rightSideTurn(5, 0.7, 0.7, 15000);; //TODO: finish turn
        // intakeDown(0.1);
        // driveWithIntake(2, -0.5, 30000); //TODO: Drive straight with Intake
        // intakeUp(0.1);
        // leftsideTurn(1, 0.7, -0.7, 15000);; //TODO: 180
        // shootWithLimelight(5, Constants.HOPPER_SPEED);
    }
}
