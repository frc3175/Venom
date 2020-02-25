
package frc.robot.automodes;

import frc.robot.DriveTrain;
import frc.robot.Limelight;
import frc.robot.config.Constants;

public class SixBall extends AutonMode {

    public SixBall() {
        super();
    }

    @Override
    protected void run() {

        /**
         * ==================== Tune This ====================
         * 
         * Drive Distance Params (Seconds, Power, Distance)
         */

         //Shoot Ball then drive back
        shootBall(2, 0, Constants.HOPPER_SPEED);
        turnToAngle(1, 270, 0, 0.5);
        driveDistance(2, 0.6, 40000);
        turnToAngle(1, 180, 0, 0.5);
        driveWithIntake(2.5, 0.3, 30000);
        turnToAngle(1, 0, 0, 0.5);
        shootBall(3, 1, Constants.HOPPER_SPEED);

    }
}
