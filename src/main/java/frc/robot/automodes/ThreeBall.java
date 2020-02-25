
package frc.robot.automodes;

import frc.robot.config.Constants;

public class ThreeBall extends AutonMode {

    public ThreeBall() {
        super();
    }

    @Override
    protected void run() {

        /**
         * ==================== Tune This ====================
         * 
         * Drive Distance Params (Seconds, Power, Distance)
         */

        shootBall(2, 0, Constants.HOPPER_SPEED);
        driveDistance(2, .6, 80000); // Drives 2 seconds at .6 power for 800 encoder rotations
    }
}
