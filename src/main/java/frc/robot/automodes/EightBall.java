
package frc.robot.automodes;

import frc.robot.config.Constants;

public class EightBall extends AutonMode {

    public EightBall() {
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
        shootBall(2, Constants.HOPPER_SPEED);
        driveDistance(2,-.6, 800);
    }
}
