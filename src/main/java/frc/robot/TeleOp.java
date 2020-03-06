package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.config.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Diagnostics;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.LEDs;
import frc.robot.utilities.Utils;

@SuppressWarnings("unused")
public class TeleOp {

    // Creates objects
    private static XBoxController manip;
    private static XBoxController driver;
    private static XBoxController climber;
    private static XBoxController reset;
    private static TeleOp instance;
    private static Timer agitator, shooterDelay;
    private static Compressor compressor;
    private static boolean state = true;
    private static boolean climberControllerState = false;


    // Creates an instance
    public static TeleOp getInstance() {
        if (instance == null)
            instance = new TeleOp();
        return instance;
    }

    // Constructor (Run's Once) initializes Xbox Controller
    private TeleOp() {
        driver = new XBoxController(Constants.XBOX_DRIVER);
        manip = new XBoxController(Constants.XBOX_MANIP);
        climber = new XBoxController(Constants.XBOX_CLIMB);
        reset = new XBoxController(Constants.XBOX_RESET);
        compressor = new Compressor(0);
    }

    // Init function (Run Once (In RobotInit()))
    public static void init() {

        SmartDashboard.putNumber("kP", 0);
        SmartDashboard.putNumber("kI", 0);
        SmartDashboard.putNumber("kD", 0);
        
        Climber.resetEncoders();

        agitator = new Timer();
        shooterDelay = new Timer();

        Thread thread1 = new Thread(() -> {
            while (!Thread.interrupted()) {

                Diagnostics.pushClimberDiagnostics(); // Climber
                Diagnostics.pushIntakeDiagnostics(); // Intake
                Diagnostics.pushDriveTrainDiagnostics(); // DriveTrain
                Diagnostics.pushShooterDiagnostics(); // Shooter
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                    return;
                }
            }
        });

        thread1.setPriority(1);
        thread1.start();

        // LEDS
        LEDs.sendAllianceOutput();
        LEDs.setShooterLEDsNormal();

        // Start Agitator Clock
        agitator.start();
        shooterDelay.start();

        Intake.IntakeUp();

    }

    // Run Method (Looped every 20 milliseconds)
    public static void run() {

        //Periodic stuff
        Limelight.pushPeriodic();
        Shooter.publishRPM();
        Climber.pushClimberEncoderValue();

        /**
         * ===============================================================================
         *                              Driver Controls
         * ===============================================================================
         */
        compressor.setClosedLoopControl(true);



        
        // System.out.println("HAS VALID TARGETS: " + Limelight.hasValidTargets());

        double linearSpeed = Utils.deadband(driver.getRawAxis(1), Constants.driveDeadband);
        double curveSpeed = Utils.deadband(-driver.getRawAxis(4), Constants.turnDeadband);

        /*
        * =====================================================
        *            Limelight Controlled by Driver
        * =====================================================
        */

        if (driver.getLeftBumper()) { // If the left bumper is pressed
            Limelight.changePipeline(1);
            Limelight.forceLEDsOn();
            manip.setDoubleRumble(0.5);
            if (Limelight.hasValidTargets()) { // If limelight sees a target
                if (driver.getLeftBumper()) {
                        Limelight.dumbLineup();
                } else {
                    if (DriveTrain.ispidEnabled()) {
                        DriveTrain.pidDisable(); // Turn off pid
                    }
                    DriveTrain.curvatureDrive(linearSpeed, curveSpeed, driver.getRightBumper()); // Drive Curvature
                }
            } else {
            }
        } else if (driver.getAButton()) {
            Limelight.changePipeline(1);
            Limelight.forceLEDsOn();
            manip.setDoubleRumble(0.5);
            if (Limelight.hasValidTargets()) { // If limelight sees a target
                if (driver.getAButton()) {
                        Limelight.goToDistance(true);
                } else {
                    Limelight.goToDistance(false);
                    if (DriveTrain.ispidEnabled()) {
                        DriveTrain.pidDisable(); // Turn off pid
                    }
                    DriveTrain.curvatureDrive(linearSpeed, curveSpeed, driver.getRightBumper()); // Drive Curvature
                }
            } else {
            }
        } else {
            DriveTrain.curvatureDrive(linearSpeed, curveSpeed, driver.getRightBumper()); // Drive Curvature
            Limelight.forceLEDsOff();
            manip.setDoubleRumble(0);
        }

        //Go straight go reverse
        if(driver.getLeftTriggerAxis() > .5 || driver.getRightTriggerAxis() > 0.5) {
            DriveTrain.drive(driver.getLeftTriggerAxis() - 0.5, driver.getRightTriggerAxis() - 0.5);
        }

        /**
         * ===============================================================================
         *                              MANIP CONTROLS BELOW
         * ===============================================================================
         */

        // Intake with agitator

        /**
         * ============================= 
         *    Enable Climber Controller 
         * =============================
         */
        if(manip.getStartButton()) {
            climberControllerState = true;
        }

        /**
         * ====================== 
         *    Intake Control 
         * ======================
         */

        if (!state) {
            if (!manip.getBButton()) {
                if (manip.getYButton()) { // if Y button is pressed
                    Intake.intakePowerCell(Constants.INTAKE_SPEED); // Move intake
                    if (agitator.get() < 1) { // For 1 second...
                        Shooter.hopperPower(Constants.HOPPER_AGITATION_REVERSE); // Move forward direction
                    } else if (agitator.get() < 3) { // For 3 seconds...
                        Shooter.hopperPower(Constants.HOPPER_AGITATION_FORWARD); // move in reverse direction
                    } else {
                        agitator.reset(); // Reset timer to 0 seconds
                    }
                } else if (manip.getBackButton()) {
                    Intake.intakePowerCell(-Constants.INTAKE_SPEED);
                } else {
                    Intake.intakePowerCell(0); // Set intake speed to 0
                    Shooter.hopperPower(0); // Turn off hopper agitator
                }
            }
        }

        if(state) {
            if(manip.getYButton()) {
                if (agitator.get() < 1) { // For 1 second...
                    Shooter.hopperPower(Constants.HOPPER_AGITATION_REVERSE); // Move forward direction
                } else if (agitator.get() < 3) { // For 3 seconds...
                    Shooter.hopperPower(Constants.HOPPER_AGITATION_FORWARD); // move in reverse direction
                } else {
                    agitator.reset(); // Reset timer to 0 seconds
                }
            }
        }

        /**
         * ===================== 
         *       Shooter 
         * =====================
         */

        if (!manip.getYButton()) {
            if (manip.getBButton()) {
                Limelight.forceLEDsOn();
                compressor.setClosedLoopControl(false);
                Shooter.shoot(true); // Shoot balls
                driver.setDoubleRumble(0.6);
                    if (shooterDelay.get() < 2) {
                } else if (shooterDelay.get() > 2) {
                    Shooter.hopperPower(Constants.HOPPER_SPEED);
                } else if (shooterDelay.get() > 2.5) {
                    Shooter.hopperPower(0);
                } else if (shooterDelay.get() > 4) {
                    Shooter.hopperPower(Constants.HOPPER_SPEED);
                } 
            } else {
                driver.setDoubleRumble(0);
                Shooter.shoot(false); // Shooter off
                Shooter.hopperPower(0d);
                shooterDelay.reset();
            }
        }

        /**
         * ===================== 
         *    Intake Piston
         * =====================
         */
        if (manip.getLeftBumper()) {
            Intake.IntakeUp(); // Intake up
            state = true;
        } else if (manip.getRightBumper()) {
            Intake.IntakeDown(); // Intake down
            state = false;
        }

        /**
         * ===================== 
         *       Climber 
         * =====================
         */

        if (climberControllerState) {
            // run based on button pressed
            if (climber.getLeftBumper()) {
                Climber.runUpDownToPosition(Constants.UP_CLIMB_POSITION, Constants.CLIMBER_SPEED);
            } else if (climber.getRightBumper()) {
                Climber.runUpDownToPosition(Constants.DOWN_CLIMB_POSITION, Constants.CLIMBER_SPEED);
            } else if (reset.getAButton()) {
                Climber.reset(Constants.CLIMBER_SPEED / 4);
            } else if (climber.getRightTriggerButton()) {
                Climber.climb(Constants.CLIMBER_SPEED / 2);
            } else {
                Climber.climb(0.0);
            }

            // Folding - left climb
            if (!climber.getAButton()) {
                if (climber.getLeftStickYAxis() > 0.2) {
                    Climber.leftFoldSet(Constants.FOLDER_SPEED);
                } else if (climber.getLeftStickYAxis() < -0.2) {
                    Climber.leftFoldSet(-Constants.FOLDER_SPEED);
                } else {
                    Climber.leftFoldSet(0.0);
                }
            }
            // Folding - right climb
            if (!climber.getAButton()) {
                if (climber.getRightStickYAxis() > 0.2) {
                    Climber.rightFoldSet(Constants.FOLDER_SPEED);
                } else if (climber.getRightStickYAxis() < -0.2) {
                    Climber.rightFoldSet(-Constants.FOLDER_SPEED);
                } else {
                    Climber.rightFoldSet(0.0);
                }
            }

            // Single button fold
            if (climber.getAButton()) {
                Climber.foldSet(Constants.CLIMBER_SPEED);
            }
        }
    }
}
