package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveTrain;

@SuppressWarnings("unused")
public class Diagnostics{
    private static Diagnostics instance;
    private static NetworkTableInstance inst;
    private static NetworkTable diagnosticTable;
    private static SmartDashboard tuningDashboard;
    private static PowerDistributionPanel pdp;

    /**
     * 
     * @return Diagnostic instance
     */
    public static Diagnostics getInstance(){
        if(instance == null){
            instance = new Diagnostics();
        }

        return instance;
    }

    // creates a diagnostic table
    public Diagnostics(){
        inst = NetworkTableInstance.getDefault();
        diagnosticTable = inst.getTable("datatable");
        pdp=new PowerDistributionPanel();
        inst.setUpdateRate(0.01);

    }

    /**
     * Drive train Diagnostics are pushed to SmartDashboard
     */
    public static void pushDriveTrainDiagnostics(){
        pushDouble("dtRightFrontTemp", DriveTrain.getRightMotorFrontTemp());
        pushDouble("dtRightBackTemp", DriveTrain.getRightMotorBackTemp());

        pushDouble("dtLeftFrontTemp", DriveTrain.getLeftMotorFrontTemp());
        pushDouble("dtLeftBackTemp", DriveTrain.getLeftMotorBackTemp());
        
        pushDouble("dtRightFrontCurrent",DriveTrain.getRightMotorFrontCurrent());
        pushDouble("dtRightBackCurrent",DriveTrain.getRightMotorBackCurrent());

        pushDouble("dtLeftFrontCurrent",DriveTrain.getLeftMotorFrontCurrent());
        pushDouble("dtLeftBackCurrent",DriveTrain.getLeftMotorBackCurrent());
        pushDouble("dtVelocity", DriveTrain.getAvgVelocity());
    }

    public static void pushIntakeDiagnostics(){
        diagnosticTable.getEntry("PowerCellAlive").setBoolean(Intake.isCellIntakeAlive());

        pushDouble("CellIntakeTemp", Intake.getTempCellIntake());
    }

    public static void pushShooterDiagnostics() {
        diagnosticTable.getEntry("TopMotorAlive").setBoolean(Shooter.isTopShooterAlive());
        diagnosticTable.getEntry("BottomMotorAlive").setBoolean(Shooter.isBottomShooterAlive());
        diagnosticTable.getEntry("HopperAlive").setBoolean(Shooter.isHopperAlive());

        pushDouble("topMotorTemp", Shooter.getTempTopTalon());
        pushDouble("bottomMotorTemp", Shooter.getTempBottomTalon());
        pushDouble("HopperTemp", Shooter.getTempHopperTalon());
    }

    public static void pushClimberDiagnostics() {
        diagnosticTable.getEntry("LeftClimber").setBoolean(Climber.isLeftClimberTalonAlive());
        diagnosticTable.getEntry("RightClimber").setBoolean(Climber.isRightClimberTalonAlive());

        pushDouble("LeftClimberTemp", Climber.getTempLeftTalon());
        pushDouble("RightClimberTemp", Climber.getTempRightTalon());
    }

    public static void pushDouble(String name, double value){
        diagnosticTable.getEntry(name).setDouble(value);

    }

}