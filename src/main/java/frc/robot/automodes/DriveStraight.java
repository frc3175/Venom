
package frc.robot.automodes;

import frc.robot.DriveTrain;

public class DriveStraight extends AutonMode {

    public DriveStraight() {
        super();
    }

    @Override
    protected void run() {

        /**
         * ==================== Tune This ====================
         * 
         * Drive Distance Params (Seconds, Power, Distance)
         */
        driveDistance(.5, -.2, (800));
        driveDistance(1.2, -.6, (7510));

        //This is all random
        if (DriveTrain.isConnected()) {
            // Some random commands
            turnToAngle(1, 60, 30, .5);
            driveDistance(2, -.7, (20057 - (260 * 10)));
            turnToAngle(1., -35, 25, .5);
            driveDistance(1, -.5, (260 * 28));
            driveDistance(2, .5, (260 * 12));
        } else {
            // easy commands
            turnToAngle(.4, -90, 13, .5);
            driveDistance(1, -.5, (260 * 20));
        }
    }
}
