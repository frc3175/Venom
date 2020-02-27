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
    private static TeleOp instance;
    private static Timer agitator, shooterDelay;
    private static Compressor compressor;
    private static boolean state = true;

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
        compressor = new Compressor(0);
    }

    // Init function (Run Once (In RobotInit()))
    public static void init() {
        
        SmartDashboard.putNumber("kP", 0);
        SmartDashboard.putNumber("kI", 0);
        SmartDashboard.putNumber("kD", 0);
    

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

        /**
         * ===============================================================================
         *                              Driver Controls
         * ===============================================================================
         */
        compressor.setClosedLoopControl(true);
        long startTime = System.currentTimeMillis();



        Limelight.changePipeline(1);
        
        // System.out.println("HAS VALID TARGETS: " + Limelight.hasValidTargets());

        double linearSpeed = Utils.deadband(driver.getRawAxis(1), Constants.driveDeadband);
        double curveSpeed = Utils.deadband(-driver.getRawAxis(4), Constants.turnDeadband);

//342
        /*
        * =====================================================
        *    Limelight Controlled by Driver Rumbles Operator
        * =====================================================
        */
        Limelight.forceLEDsOn();

        if (driver.getLeftBumper()) { // If the left bumper is pressed
            if (Limelight.hasValidTargets()) { // If limelight sees a target
                if (driver.getLeftBumper()) {
						Limelight.dumbLineup();
                } else {
                    if (DriveTrain.ispidEnabled()) {
                        DriveTrain.pidDisable(); // Turn off pid
                    }
                    DriveTrain.curvatureDrive(linearSpeed, curveSpeed, driver.getRightBumper()); // Drive Curvature
                }
            } else if (driver.getAButton()) {
                if (Limelight.hasValidTargets()) { // If limelight sees a target
                    if (driver.getAButton()) {
                            Limelight.goToDistance();
                    } else {
                        if (DriveTrain.ispidEnabled()) {
                            DriveTrain.pidDisable(); // Turn off pid
                        }
                        DriveTrain.curvatureDrive(linearSpeed, curveSpeed, driver.getRightBumper()); // Drive Curvature
                    }
                } else {
                }
            } else {
            }
        } else {
            DriveTrain.curvatureDrive(linearSpeed, curveSpeed, driver.getRightBumper()); // Drive Curvature
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

        // if (!manip.getYButton()) {
        //     if (manip.getBButton()) {
        //         compressor.setClosedLoopControl(false);
        //         Shooter.shoot(Constants.TOP_MOTOR_SPEED_TRENCH, Constants.BOTTOM_MOTOR_SPEED); // Shoot balls
        //         if (shooterDelay.get() < 1.5) {

        //         } else if (shooterDelay.get() > 1.5) {
        //             Shooter.hopperPower(Constants.HOPPER_SPEED);
        //         } else if (shooterDelay.get() > 2) {
        //             Shooter.hopperPower(0);
        //         } else if (shooterDelay.get() > 4) {
        //             Shooter.hopperPower(Constants.HOPPER_SPEED);
        //         } 
        //     } else {
        //         Shooter.shoot(0, 0); // Shooter off
        //         Shooter.hopperPower(0d);
        //         shooterDelay.reset();
        //     }
        // }


        if (!manip.getYButton()) {
            if (manip.getBButton()) {
                compressor.setClosedLoopControl(false);
                Shooter.shoot(true); // Shoot balls
                    if (shooterDelay.get() < 1.5) {
                } else if (shooterDelay.get() > 1.5) {
                    Shooter.hopperPower(Constants.HOPPER_SPEED);
                } else if (shooterDelay.get() > 2) {
                    Shooter.hopperPower(0);
                } else if (shooterDelay.get() > 4) {
                    Shooter.hopperPower(Constants.HOPPER_SPEED);
                } 
                // if(Shooter.reachedRPM()) {
                //     Shooter.hopperPower(Constants.HOPPER_SPEED);
                // }
            } else {
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
        if (manip.getRightTriggerButton()) {
            Climber.foldSet(Constants.CLIMBER_SPEED); // fold
        } else {
            Climber.foldSet(0);
        }

        if (manip.getLeftTriggerButton()) {
            Climber.climb(1); // Climber Speed
        } else {
            Climber.climb(0); // turn it off
        }
    }
}
