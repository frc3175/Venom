
package frc.robot.automodes;

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
            turnToAngle(1, 120, 0, 0.5); // turns 120 degrees for 1 second at 0.5 power
            driveWithIntake(3, 0.7, 3000); //Drives with the intake on for 3000 rotations
            turnToAngle(1, 180, 0, 0.5); // turns 180 degrees at half power
            //TODO: add Limelight step
            shootBall(3, Constants.TOP_MOTOR_SPEED_TRENCH); //Shoots ball at trench speed
        } else { 
            //timed based turn instead of gyro turn
            // Dumb stuff
            doubleSidePower(1, 0.5, -0.5);
            driveWithIntake(3, 0.7, 3000);
            doubleSidePower(2, 0.8, -0.8);
            shootBall(3, Constants.TOP_MOTOR_SPEED_TRENCH);
        }
    }
}
