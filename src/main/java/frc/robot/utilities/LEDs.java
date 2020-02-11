package frc.robot.utilities;

import edu.wpi.first.wpilibj.DigitalOutput;
import frc.robot.config.Constants;

public class LEDs {
    private static DigitalOutput leds;
    private static LEDs instance;

    public static LEDs getInstance(){
        if(leds == null){
            instance = new LEDs();
        }

        return instance;
    }

    private LEDs(){
        leds = new DigitalOutput(Constants.LED_CHANNEL);
    }

    public static void setFast(){
        leds.set(true);
    }
    public static void setNormal() {
        leds.set(false);
    }
}