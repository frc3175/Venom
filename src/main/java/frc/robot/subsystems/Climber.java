package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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
    private static VictorSPX leftFolder, rightFolder;


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
        leftFolder = new VictorSPX(Constants.LEFT_FOLDER);
        rightFolder = new VictorSPX(Constants.RIGHT_FOLDER);
        


    }

    /**
     * 
     * @param climbSpeed Speed at which the TalonFXs rotate
     */
    public static void climb(double climbSpeed) {
        leftClimbTalon.set(ControlMode.PercentOutput, climbSpeed);
        rightClimbTalon.set(ControlMode.PercentOutput, -climbSpeed);
    }

    public static void runUpDownToPosition(double position, double climbSpeed) {
        if (position - leftClimbTalon.getSelectedSensorPosition() > 0) {
            //TODO: add current climber encoder position to SmartDashboard
            leftClimbTalon.set(ControlMode.PercentOutput, climbSpeed);
            rightClimbTalon.set(ControlMode.PercentOutput, -climbSpeed);
        } else {
            leftClimbTalon.set(ControlMode.PercentOutput, 0.0);
            rightClimbTalon.set(ControlMode.PercentOutput, 0.0);
        }
    }

    /**
     * 
     * @param speed Folding speed
     */
    public static void foldSet(double speed) {
        leftFolder.set(ControlMode.PercentOutput, speed);
        rightFolder.set(ControlMode.PercentOutput,-speed);
    }

    public static void leftFoldSet(double speed) {
        leftFolder.set(ControlMode.PercentOutput, speed);
    }

    public static void rightFoldSet(double speed) {
        rightFolder.set(ControlMode.PercentOutput,-speed);
    }

    // Diagnostic Information pushed to diagnositic subsystem
    public static boolean isLeftClimberTalonAlive() {
        return (leftClimbTalon.getBusVoltage() != 0.0);
    }

    public static boolean isRightClimberTalonAlive() {
        return (rightClimbTalon.getBusVoltage() != 0.0);
    }


    public static double getTempLeftTalon() {
        return leftClimbTalon.getTemperature();
    }

    public static double getTempRightTalon() {
        return rightClimbTalon.getTemperature();
    }

    public static void reset(double climbSpeed) {
        leftClimbTalon.set(ControlMode.PercentOutput, -climbSpeed);
        rightClimbTalon.set(ControlMode.PercentOutput, climbSpeed);
    }

    public static void pushClimberEncoderValue() {
        SmartDashboard.putNumber("leftClimbTalon", leftClimbTalon.getSelectedSensorPosition());
    }

}