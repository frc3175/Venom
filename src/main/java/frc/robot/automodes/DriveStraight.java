
package frc.robot.automodes;

import frc.robot.DriveTrain;
import edu.wpi.first.wpilibj.DriverStation;

public class DriveStraight extends AutonMode {

    public DriveStraight() {
        super();
    }

    @Override
    protected void run() {

        String gameData = "";
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData == null) {
            for (int i = 0; i < 20; i++) {
                gameData = DriverStation.getInstance().getGameSpecificMessage();
                if (gameData != null)
                    break;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        /**
         * ==================== Tune This ====================
         * 
         * Drive Distance Params (Seconds, Power, Distance)
         */
        driveDistance(.5, -.2, (800));
        driveDistance(1.2, -.6, (7510));
        if (gameData != null && gameData.charAt(0) == 'L') {
            if (DriveTrain.isConnected()) {
                // test turn -45 Degrees
                turnToAngle(1, -45, 30, .5);
                // drive forward a bit
                driveDistance(1, .2, 800);
            } else {
                // Else turn 90 Degrees
                turnToAngle(.4, -90, 13, .5);
                // Drive back a bit
                driveDistance(1, -.5, (260 * 20));
            }
        } else {
            if (DriveTrain.isConnected()) {
                //Some random commands
                turnToAngle(1, 60, 30, .5);
                driveDistance(2, -.7, (20057 - (260 * 10)));
                turnToAngle(1., -35, 25, .5);
                driveDistance(1, -.5, (260 * 28));
                driveDistance(2, .5, (260 * 12));
            } else {
                //easy commands
                turnToAngle(.4, -90, 13, .5);
                driveDistance(1, -.5, (260 * 20));
            }
        }
    }
}
