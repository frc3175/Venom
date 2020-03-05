
package frc.robot.automodes;


public class TurnToAngleTest extends AutonMode {

    public TurnToAngleTest() {
        super();
    }

    @Override
    protected void run() {

        /**
         * ==================== Tune This ====================
         * 
         * Drive Distance Params (Seconds, Power, Distance)
         */
        turnToAngle(2, 90, 0.4);
    }
}
