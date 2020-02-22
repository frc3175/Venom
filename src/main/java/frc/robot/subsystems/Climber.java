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
 * <summary> Code meant for the Climber </summary>
 */
@SuppressWarnings("unused")
public class Climber {
    private static Climber instance;
    private static TalonFX leftClimbTalon, rightClimbTalon;
    private static CANSparkMax leftNeo, rightNeo;

    private static CANPIDController pid;
    private static CANEncoder encoder;

    /**
     * 
     * @return Climber instance
     */
    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

    // initializes climber motors
    public Climber() {
        leftClimbTalon = new TalonFX(Constants.LEFT_CLIMBER_TALON);
        rightClimbTalon = new TalonFX(Constants.RIGHT_CLIMBER_TALON);
        leftNeo = new CANSparkMax(Constants.LEFT_NEO, MotorType.kBrushless);
        rightNeo = new CANSparkMax(Constants.RIGHT_NEO, MotorType.kBrushless);

        leftNeo.restoreFactoryDefaults(true);
        rightNeo.restoreFactoryDefaults(true);

    }

    /**
     * 
     * @param climbSpeed Speed at which the TalonFXs rotate
     */
    public static void climb(double climbSpeed) {
        leftClimbTalon.set(ControlMode.PercentOutput, climbSpeed);
        rightClimbTalon.set(ControlMode.PercentOutput, -climbSpeed);
    }

    /**
     * 
     * @param speed Folding speed
     */
    public static void foldSet(double speed) {
        rightNeo.set(Constants.CLIMBER_SPEED);
        leftNeo.set(Constants.CLIMBER_SPEED);
    }

    // Diagnostic Information pushed to diagnositic subsystem
    public static boolean isLeftClimberTalonAlive() {
        return (leftClimbTalon.getBusVoltage() != 0.0);
    }

    public static boolean isRightClimberTalonAlive() {
        return (rightClimbTalon.getBusVoltage() != 0.0);
    }

    public static boolean isLeftNeoAlive() {
        return (leftNeo.getBusVoltage() != 0.0);
    }

    public static boolean isRightNeoAlive() {
        return (rightNeo.getBusVoltage() != 0.0);
    }

    public static double getTempLeftTalon() {
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