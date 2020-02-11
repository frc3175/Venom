package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.config.Constants;

/**
 * <summary>
 * Code meant for the Shooter and launcher balls with the hopper
 * </summary>
 */
public class Shooter{
    private static Shooter instance;
    private static TalonSRX topShooterTalon, bottomShooterTalon, hopperTalon;

    

    public static Shooter getInstance(){
        if(instance == null){
            instance = new Shooter();
        }
        return instance;
    }

    public Shooter(){
        topShooterTalon = new TalonSRX(Constants.TOP_SHOOTER_TALON); // 2 
        bottomShooterTalon = new TalonSRX(Constants.BOTTOM_SHOOTER_TALON); //3
        hopperTalon = new TalonSRX(Constants.HOPPER_TALON); // ID 4

    }

    public static void shoot(double topMotorPower, double bottomMotorPower){
        topShooterTalon.set(ControlMode.PercentOutput, topMotorPower);
        bottomShooterTalon.set(ControlMode.PercentOutput, bottomMotorPower);
        hopperTalon.set(ControlMode.PercentOutput, Constants.HOPPER_SPEED);

    }

    /**
     * 
     * @param direction 1 == Forward 2 == Reverse
     */
    public static void hopperAgitationCommand(int direction) {
        //Forward
        if(direction == 1) {
            hopperTalon.set(ControlMode.PercentOutput, Constants.HOPPER_AGITATION_FORWARD);
        //Reverse
        } else if (direction == 2) {
            hopperTalon.set(ControlMode.PercentOutput, Constants.HOPPER_AGITATION_REVERSE);
        } else {
            //Shut off
            hopperTalon.set(ControlMode.PercentOutput, 0);
        }
    }

    public static void stop() {
        topShooterTalon.set(ControlMode.PercentOutput, 0);
        bottomShooterTalon.set(ControlMode.PercentOutput, 0);
        hopperTalon.set(ControlMode.PercentOutput, 0);
    }

    //Diagnostic Information
    public static boolean isTopShooterAlive(){
        return (topShooterTalon.getBusVoltage() != 0.0);
    }

    public static boolean isBottomShooterAlive() {
        return(bottomShooterTalon.getBusVoltage() != 0.0);
    }

    public static boolean isHopperAlive() {
        return(hopperTalon.getBusVoltage() != 0.0);
    }
    
    public static double getTempTopTalon(){
        return topShooterTalon.getTemperature();
    }

    public static double getTempBottomTalon() {
        return bottomShooterTalon.getTemperature();
    }

    public static double getTempHopperTalon() {
        return hopperTalon.getTemperature();
    }
}