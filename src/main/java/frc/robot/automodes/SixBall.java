
package frc.robot.automodes;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.DriveTrain;
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
        shootBall(3, Constants.TOP_MOTOR_SPEED_LINE);
        // DriveTrain.isConnected() is used to determine if the gyro is connected
        if (DriveTrain.isConnected()) {
            turnToAngle(1, 120, 0, 0.5);
            driveWithIntake(3, 0.7, 3000);
            turnToAngle(1, 180, 0, 0.5);
            shootBall(3, Constants.TOP_MOTOR_SPEED_TRENCH);
        } else { 
            //timed based turn instead of gyro turn
            doubleSidePower(1, 0.5, -0.5);
            driveWithIntake(3, 0.7, 3000);
            doubleSidePower(2, 0.8, -0.8);
            shootBall(3, Constants.TOP_MOTOR_SPEED_TRENCH);
        }
    }
}
