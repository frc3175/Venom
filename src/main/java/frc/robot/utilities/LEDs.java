package frc.robot.utilities;

import edu.wpi.first.wpilibj.DigitalOutput;
import frc.robot.config.Constants;

public class LEDs {
    private static DigitalOutput shooterLEDs, flashbangMode;
    private static LEDs instance;

    public static LEDs getInstance(){
        if(shooterLEDs== null){
            instance = new LEDs();
        }

        return instance;
    }

    private LEDs(){
        shooterLEDs = new DigitalOutput(Constants.LED_CHANNEL_1);
        flashbangMode = new DigitalOutput(Constants.LED_CHANNEL_2);

    }

    public static void setShooterLEDsFast(){
        shooterLEDs.set(true);
    }
    public static void setShooterLEDsNormal() {
        shooterLEDs.set(false);
    }

    public static void flashbangOff() {
        flashbangMode.set(false);
    }
}