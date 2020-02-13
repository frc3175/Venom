package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    private static CANPIDController pid;
    private static CANEncoder encoder;

    

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

        leftNeo.restoreFactoryDefaults(true);
        rightNeo.restoreFactoryDefaults(true);

        leftNeo.follow(rightNeo);

        pid = rightNeo.getPIDController();
        encoder = rightNeo.getEncoder();


            // PID coefficients
        double kP = 6e-5;
        double kI = 0;
        double kD = 0; 
        double kIz = 0; 
        double kFF = 0.000015; 
        double kMaxOutput = 1; 
        double kMinOutput = -1;

        pid.setP(kP);
        pid.setI(kI);
        pid.setD(kD);
        pid.setIZone(kIz);
        pid.setFF(kFF);
        pid.setOutputRange(kMinOutput, kMaxOutput);

        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);

    }

    /**
     * 
     * @param climbSpeed Speed at which the TalonFXs rotate
     */
    public static void climb(double climbSpeed){
        leftClimbTalon.set(ControlMode.PercentOutput, climbSpeed);
        rightClimbTalon.set(ControlMode.PercentOutput, climbSpeed);
    }

    public static void fold(double setPoint){
        pid.setReference(setPoint, ControlType.kVelocity);
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