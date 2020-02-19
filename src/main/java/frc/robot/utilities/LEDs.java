package frc.robot.utilities;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.config.Constants;

public class LEDs {
    private static DigitalOutput shooterLEDs, flashbangMode, allianceMode;
    private static LEDs instance;

    public static LEDs getInstance(){
        if(shooterLEDs== null){
            instance = new LEDs();
        }

        return instance;
    }

    private LEDs(){
        shooterLEDs = new DigitalOutput(Constants.LED_CHANNEL_1); // > 30
        flashbangMode = new DigitalOutput(Constants.LED_CHANNEL_2); // > 32
        allianceMode = new DigitalOutput(Constants.LED_CHANNEL_3); // > 34
    }

    public static DriverStation.Alliance getAlliance() {
        return DriverStation.getInstance().getAlliance();
    }

    public static void sendAllianceOutput() {
        if(getAlliance() == Alliance.Red) {
            allianceMode.set(true);
        } else if (getAlliance() == Alliance.Blue) {
            allianceMode.set(false);
        }
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