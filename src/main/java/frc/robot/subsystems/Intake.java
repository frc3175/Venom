package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.config.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * <summary>
 * Code meant for the Cargo Intake/Cargo Delivery System
 * </summary>
 */
public class Intake{
    private static Intake instance;
    private static TalonSRX cellIntakeTalon;
    private static VictorSPX intakeFollower;
    private static DoubleSolenoid intake;

    //returns Intake instance
    public static Intake getInstance(){
        if(instance == null){
            instance = new Intake();
        }
        return instance;
    }

    //initializes motors
    public Intake(){
        cellIntakeTalon = new TalonSRX(Constants.INTAKE_TALON);
        intakeFollower = new VictorSPX(Constants.INTAKE_FOLLOWER);
        intake = new DoubleSolenoid(Constants.INTAKE_SOLENOID_F, Constants.INTAKE_SOLENOID_R);
    }

    //Sucks balls in
    public static void intakePowerCell(double power){
        cellIntakeTalon.set(ControlMode.PercentOutput, -power);
        intakeFollower.set(ControlMode.PercentOutput, -power);
    }
    
    //Moves intake down
    public static void IntakeUp(){
        intake.set(Value.kForward);
    }

    //Moves intake up
    public static void IntakeDown(){
        intake.set(Value.kReverse);
    }

    /**
     * 
     * @return intake state
     */
    public static DoubleSolenoid.Value isIntakeUp(){
        return intake.get();
    }

    //Diagnostic Information used in diagnostic subsystem
    public static boolean isCellIntakeAlive(){
        return (cellIntakeTalon.getBusVoltage() != 0.0);
    }
    
    public static double getTempCellIntake(){
        return cellIntakeTalon.getTemperature();
    }

}