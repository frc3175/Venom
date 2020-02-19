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

    

    /**
     * 
     * @return Shooter instance
     */
    public static Shooter getInstance(){
        if(instance == null){
            instance = new Shooter();
        }
        return instance;
    }

    //initializes the motors
    public Shooter(){
        topShooterTalon = new TalonSRX(Constants.TOP_SHOOTER_TALON); 
        bottomShooterTalon = new TalonSRX(Constants.BOTTOM_SHOOTER_TALON); 
        hopperTalon = new TalonSRX(Constants.HOPPER_TALON); 
    }

    /**
     * 
     * @param topMotorPower Top motor power
     * @param bottomMotorPower Bottom motor power
     * @param hopperSpeed hopper speed
     */
    public static void shoot(double topMotorPower, double bottomMotorPower){
        topShooterTalon.set(ControlMode.PercentOutput, topMotorPower);
        bottomShooterTalon.set(ControlMode.PercentOutput, bottomMotorPower);

    }

    public static void hopperPower(double power) {
        hopperTalon.set(ControlMode.PercentOutput, power);
    }


    //Stops everything
    public static void stop() {
        topShooterTalon.set(ControlMode.PercentOutput, 0);
        bottomShooterTalon.set(ControlMode.PercentOutput, 0);
    }

    //Diagnostic Information used in diagnostics subsystem
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