
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
        hopperSpin(1, Constants.HOPPER_AGITATION_FORWARD);
        shootBall(7, Constants.HOPPER_SPEED);
        driveDistance(2, .6, 80000); // Drives 2 seconds at .6 power for 800 encoder rotations
        leftsideTurn(1.5, 0.5, 0, 12000);
        intakeDown(0.5);
        driveWithIntake(1.5, 0.3, 15000);
        doubleSidePower(1, 0, 0.5, 12000);
        limelightAlign(1.5);
        shootBall(3, Constants.HOPPER_SPEED);

    }
}
