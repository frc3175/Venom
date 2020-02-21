package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.config.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Diagnostics;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.LEDs;
import frc.robot.utilities.Utils;

@SuppressWarnings("unused")
public class TeleOp {

    //Creates objects
    private static XBoxController manip;
    private static XBoxController driver;
    private static TeleOp instance;
    private static Timer agitator, shooterDelay;
    private static Compressor compressor;

    

    //Creates an instance
    public static TeleOp getInstance() {
        if (instance == null)
            instance = new TeleOp();
        return instance;
    }

    //Constructor (Run's Once) initializes Xbox Controller
    private TeleOp() {
        driver = new XBoxController(Constants.XBOX_DRIVER);
        manip = new XBoxController(Constants.XBOX_MANIP);
        compressor = new Compressor(0);
    }

    //Init function (Run Once (In RobotInit()))
    public static void init() {

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

        //LEDS
        LEDs.sendAllianceOutput();
        LEDs.setShooterLEDsNormal();

        // Start Agitator Clock
        agitator.start();
        shooterDelay.start();

    }

    // Run Method (Looped every 20 milliseconds)
    public static void run() {

        compressor.setClosedLoopControl(false);

        /**
         * ===============================================================================
         *                               Driver Controls
         * ===============================================================================
         */
        long startTime = System.currentTimeMillis();

        Limelight.changePipeline(0);
        // System.out.println("HAS VALID TARGETS: " + Limelight.hasValidTargets());

        double linearSpeed = Utils.deadband(driver.getRawAxis(1), Constants.driveDeadband);
        double curveSpeed = Utils.deadband(-driver.getRawAxis(4), Constants.turnDeadband);

        

        if (driver.getLeftBumper()) { // If the left bumper is pressed
            Limelight.changePipeline(2); // changes Limelight vision pipeline to 2

            if (Limelight.hasValidTargets()) { // If limelight sees a target
                if (driver.getLeftBumper()) {

                    //If Limelight X cross hair is set X distance away
                    if (Limelight.getX() <= 3d && Limelight.getX() >= -3d) {
                        DriveTrain.arcadeDrive(0, 0.2);
                    } else {
                        Limelight.dumbLineup(120); //Limelight lineup
                    }
                } else {
                    if (DriveTrain.ispidEnabled()) { 
                        DriveTrain.pidDisable(); // Turn off pid
                    }
                    DriveTrain.curvatureDrive(linearSpeed, curveSpeed, driver.getRightBumper()); // Drive Curvature
                }

            } else {
                driver.setLeftRumble(0.0); //Turn off rumbles
                driver.setRightRumble(0.0);
            }
        } else {
            DriveTrain.curvatureDrive(linearSpeed, curveSpeed, driver.getRightBumper());
            //DriveTrain.arcadeDrive(Utils.expoDeadzone(-driver.getLeftStickXAxis(), 0.1, 2), Utils.expoDeadzone(-driver.getRightStickYAxis(), 0.1, 1.2));
        }

        /**
         * ===============================================================================
         *                               MANIP CONTROLS BELOW
         * ===============================================================================
         */

        // Intake with agitator
        /**
         * ======================
         *     Intake Control
         * ======================
         */
        if(!manip.getBButton()) {
            if (manip.getYButton()) { // if Y button is pressed
                Intake.intakePowerCell(Constants.INTAKE_SPEED); //Move intake
                if (agitator.get() < 1) { // For 1 second...
                    Shooter.hopperPower(Constants.HOPPER_AGITATION_REVERSE); //Move forward direction
                } else if (agitator.get() < 3) { // For 3 seconds...
                    Shooter.hopperPower(Constants.HOPPER_AGITATION_FORWARD); // move in reverse direction
                } else {
                    agitator.reset(); //Reset timer to 0 seconds
                }
            } else {
                Intake.intakePowerCell(0); //Set intake speed to 0
                Shooter.hopperPower(0); //Turn off hopper agitator
            }
        }

        /**
        * =====================
        *        Shooter
        * =====================
        */

        if(!manip.getYButton()) {
            if (manip.getBButton()) {
                Shooter.shoot(Constants.TOP_MOTOR_SPEED_TRENCH, Constants.BOTTOM_MOTOR_SPEED); //Shoot balls
                if(shooterDelay.get() < 1) {
                    
                } else if (shooterDelay.get() > 1) {
                    Shooter.hopperPower(Constants.HOPPER_SPEED);
                    LEDs.setShooterLEDsFast(); // Make leds go fast
                }
            } else {
                LEDs.setShooterLEDsNormal(); // Leds normal
                Shooter.shoot(0, 0); // Shooter off
                Shooter.hopperPower(0d);
                shooterDelay.reset();
            }
        }

        /**
        * =====================
        *     Intake Piston
        * =====================
        */
        if (manip.getXButton()) {
            Intake.IntakeUp(); // Intake up
        } else if (manip.getAButton()) {
            Intake.IntakeDown(); // Intake down
        }
        
        /**
        * =====================
        *        Climber
        * =====================
        */
        if(manip.getRightBumper()) {
            Climber.fold(3000); // fold to setpoint
        }

        /**
        * =====================
        *         LEDs
        * =====================
        */
        if(manip.getAButton()) {
            LEDs.setShooterLEDsFast();
        }


        
    
        if (manip.getLeftBumper()){
            Climber.climb(0.5); // Climber Speed
        } else {
            Climber.climb(0); // turn it off
        }
    }
}
