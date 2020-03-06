package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.automodes.AutonMode;
import frc.robot.automodes.SixBall;
import frc.robot.automodes.ThreeBall;
import frc.robot.automodes.TurnToAngleTest;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Initializations {

    private static Initializations instance;
    
    AutonMode autonCommand;
    private static SendableChooser<AutonMode> autoChooser;
    private static UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();

    // Creates an instance
    public static Initializations getInstance() {
        if (instance == null)
            instance = new Initializations();
        return instance;
    }

    public static void init() {

        // Auton Choosers
        autoChooser = new SendableChooser<AutonMode>();
        autoChooser.setDefaultOption("Three Ball Auto", new ThreeBall());
        autoChooser.addOption("Six Ball Auto", new SixBall());
        autoChooser.addOption("Turn to Angle Test", new TurnToAngleTest());
        SmartDashboard.putData("Auto mode", autoChooser);
        SmartDashboard.putNumber("Match Time:", DriverStation.getInstance().getMatchTime());

        //Limelight and Camera
        Limelight.changePipeline(1);
        camera.setResolution(200, 200);
    }

    public static void autonInit() {
        //Selects the Auton Mode
        autoChooser.getSelected().start(); // Auton init
    }
}