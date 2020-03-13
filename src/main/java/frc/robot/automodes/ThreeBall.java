
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
         *  
         */ 
        //limelightAlign(2);
        hopperSpin(1, Constants.HOPPER_AGITATION_FORWARD);
        shootBall(6, Constants.HOPPER_SPEED);
        driveDistance(2, .6, 80000); // Drives 2 seconds at .6 power for 80000 encoder rotations
    }
}
