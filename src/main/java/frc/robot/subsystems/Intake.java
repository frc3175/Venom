package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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
    private static DoubleSolenoid intake;

    public static Intake getInstance(){
        if(instance == null){
            instance = new Intake();
        }
        return instance;
    }

    public Intake(){
        cellIntakeTalon = new TalonSRX(Constants.INTAKE_TALON);
        intake = new DoubleSolenoid(Constants.INTAKE_SOLENOID_F, Constants.INTAKE_SOLENOID_R);
    }

    public static void intakeCell(double power){
        cellIntakeTalon.set(ControlMode.PercentOutput, power);
    }
    
    public static void IntakeDown(){
        intake.set(Value.kForward);
    }

    public static void IntakeUp(){
        intake.set(Value.kReverse);
    }

    public static DoubleSolenoid.Value isIntakeUp(){
        return intake.get();
    }

    //Diagnostic Information
    public static boolean isCellIntakeAlive(){
        return (cellIntakeTalon.getBusVoltage() != 0.0);
    }
    
    public static double getTempCellIntake(){
        return cellIntakeTalon.getTemperature();
    }
}