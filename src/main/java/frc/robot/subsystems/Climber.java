package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.config.Constants;

/**
 * <summary>
 * Code meant for the Climber
 * </summary>
 */
public class Climber{
    private static Climber instance;
    private static TalonFX leftClimbTalon, rightClimbTalon;
    private static CANSparkMax leftNeo, rightNeo;

    

    public static Climber getInstance(){
        if(instance == null){
            instance = new Climber();
        }
        return instance;
    }

    public Climber(){
        leftClimbTalon = new TalonFX(Constants.LEFT_CLIMBER_TALON);
        rightClimbTalon = new TalonFX(Constants.RIGHT_CLIMBER_TALON);
        leftNeo = new CANSparkMax(Constants.LEFT_NEO, MotorType.kBrushless);
        rightNeo = new CANSparkMax(Constants.RIGHT_NEO, MotorType.kBrushless);

    }

    /**
     * 
     * @param climbSpeed Speed at which the TalonFXs rotate
     */
    public static void climb(double climbSpeed){
        leftClimbTalon.set(ControlMode.PercentOutput, climbSpeed);
        rightClimbTalon.set(ControlMode.PercentOutput, climbSpeed);
    }

    public static void fold(double foldSpeed){
        //TODO:WRite Pid for folding
    }

    //Diagnostic Information
    public static boolean isLeftClimberTalonAlive(){
        return (leftClimbTalon.getBusVoltage() != 0.0);
    }

    public static boolean isRightClimberTalonAlive() {
        return(rightClimbTalon.getBusVoltage() != 0.0);
    }

    public static boolean isLeftNeoAlive() {
        return(leftNeo.getBusVoltage() != 0.0);
    }
    
    public static boolean isRightNeoAlive() {
        return(rightNeo.getBusVoltage() != 0.0);
    }
    
    public static double getTempLeftTalon(){
        return leftClimbTalon.getTemperature();
    }

    public static double getTempRightTalon() {
        return rightClimbTalon.getTemperature();
    }

    public static double getTempLeftNeo() {
        return leftNeo.getMotorTemperature();
    }

    public static double getTempRightNeo() {
        return rightNeo.getMotorTemperature();
    }
}