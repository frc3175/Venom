
package frc.robot.automodes;

import frc.robot.DriveTrain;
import frc.robot.autocommands.ShootBall;

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
        if (DriveTrain.isConnected()) {
            //Actual commands
            ShootBall(.4);
   
        } else {
            //easy commands
            turnToAngle(.4, -90, 13, .5);
            driveDistance(1, -.5, (260 * 20));
        }
    }
}
