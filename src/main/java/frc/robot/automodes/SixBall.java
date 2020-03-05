
package frc.robot.automodes;

import frc.robot.config.Constants;

public class SixBall extends AutonMode {

    public SixBall() {
        super();
    }

    @Override
    protected void run() {
        shootWithAlign(4, Constants.HOPPER_SPEED);
        turnToAngle(1.5, 155, 0.4); //TODO: finish turn
        intakeDown(0.1);
        driveWithIntake(2, 0.5, 80000); //TODO: Drive straight with Intake
        intakeUp(0.1);
        turnToAngle(1.5, 180, -0.5); //TODO: 180
        shootWithLimelight(5, Constants.HOPPER_SPEED);
    }
}
