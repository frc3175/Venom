package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Limelight;
import frc.robot.config.Constants;

/**
 * <summary>
 * Code meant for the Shooter and launcher balls with the hopper
 * </summary>
 */
public class Shooter{
    private static Shooter instance;
    private static TalonSRX masterShooterTalon, followerTalon, hopperTalon;


    
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
        masterShooterTalon = new TalonSRX(Constants.TOP_SHOOTER_TALON); 
        followerTalon = new TalonSRX(Constants.BOTTOM_SHOOTER_TALON); 
        hopperTalon = new TalonSRX(Constants.HOPPER_TALON); 
        
        masterShooterTalon.configFactoryDefault();
        masterShooterTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
        masterShooterTalon.setSensorPhase(true);
        masterShooterTalon.configNominalOutputForward(0, Constants.kTimeoutMs);
        masterShooterTalon.configNominalOutputReverse(0, Constants.kTimeoutMs);
        masterShooterTalon.configPeakOutputForward(1, Constants.kTimeoutMs);
        masterShooterTalon.configPeakOutputReverse(1, Constants.kTimeoutMs);

        //Gains
        masterShooterTalon.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
        masterShooterTalon.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
        masterShooterTalon.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
        masterShooterTalon.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);

        masterShooterTalon.enableCurrentLimit(true);
        masterShooterTalon.configPeakCurrentLimit(40);
        masterShooterTalon.configPeakCurrentDuration(0);
        masterShooterTalon.configContinuousCurrentLimit(30);

        followerTalon.enableCurrentLimit(true);
        followerTalon.configPeakCurrentLimit(40);
        followerTalon.configPeakCurrentDuration(0);
        followerTalon.configContinuousCurrentLimit(30);

        followerTalon.follow(masterShooterTalon);

    }

    public static void shoot() {
        double targetVelocity_UnitsPer100ms = Limelight.findRPM();
        masterShooterTalon.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
        }
    

    public static boolean reachedRPM() {
        for(int i = 0; i < Limelight.RPMs.length; i++) {
            if (masterShooterTalon.getSelectedSensorVelocity() >= Limelight.RPMs[i] - 20 && masterShooterTalon.getSelectedSensorVelocity() <= Limelight.RPMs[i] + 20) {
                return true;
            }
        } return false;
    }

    public static void hopperPower(double power) {
        hopperTalon.set(ControlMode.PercentOutput, power);
    }

    public static double publishRPM() {
        SmartDashboard.putNumber("Shooter/bottomMotor RPM", masterShooterTalon.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Shooter/BottomMotor RPM", followerTalon.getSelectedSensorVelocity());
        return masterShooterTalon.getSelectedSensorVelocity();
    }


    //Diagnostic Information used in diagnostics subsystem
    public static boolean isTopShooterAlive(){
        return (masterShooterTalon.getBusVoltage() != 0.0);
    }

    public static boolean isBottomShooterAlive() {
        return(followerTalon.getBusVoltage() != 0.0);
    }

    public static boolean isHopperAlive() {
        return(hopperTalon.getBusVoltage() != 0.0);
    }
    
    public static double getTempTopTalon(){
        return masterShooterTalon.getTemperature();
    }

    public static double getTempBottomTalon() {
        return followerTalon.getTemperature();
    }

    public static double getTempHopperTalon() {
        return hopperTalon.getTemperature();
    }
}