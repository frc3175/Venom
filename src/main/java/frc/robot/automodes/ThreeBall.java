
package frc.robot.automodes;

import frc.robot.DriveTrain;
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

         //Shoot Ball then drive back
        shootBall(3, Constants.TOP_MOTOR_SPEED_LINE);
        // DriveTrain.isConnected() is used to determine if the gyro is connected
        if (DriveTrain.isConnected()) {
            turnToAngle(1, 180, 0, 0.5); // Turns 180 degrees for 1 second at 0.5 power
            driveDistance(2, .6, 800); // Drives 2 seconds at .6 power for 800 encoder rotations
        } else { 
            //timed based turn instead of gyro turn
            doubleSidePower(1, 0.5, -0.5); // Dumb spin
        }
    }
}
