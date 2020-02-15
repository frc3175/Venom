package frc.robot;

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
    private static Timer clock, agitator;

    

    //Creates an instance
    public static TeleOp getInstance() {
        if (instance == null)
            instance = new TeleOp();
        return instance;
    }

    //Constructor (Run's Once)
    private TeleOp() {
        driver = new XBoxController(Constants.XBOX_DRIVER);
        manip = new XBoxController(Constants.XBOX_MANIP);
    }

    //Init function (Run Once as well (In RobotInit()))
    public static void init() {

        clock = new Timer();
        agitator = new Timer();

        Thread thread1 = new Thread(() -> {
            while (!Thread.interrupted()) {

                //Diagnostics.pushClimberDiagnostics();
                Diagnostics.pushIntakeDiagnostics();
                Diagnostics.pushDriveTrainDiagnostics();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                    return;
                }
            }
        });


        thread1.setPriority(1);
        // thread2.setPriority(1);

        thread1.start();
        // thread2.start();

        //LEDS
        LEDs.sendAllianceOutput();
        LEDs.setShooterLEDsNormal();

        // Start Agitator Clock
        agitator.start();

    }

    // Run Method (Looped every 20 milliseconds)
    public static void run() {
        /**
         * ===============================================================================
         *                               Driver Controls
         * ===============================================================================
         */
        long startTime = System.currentTimeMillis();

        Limelight.changePipeline(0);
        // System.out.println("HAS VALID TARGETS: " + Limelight.hasValidTargets());

        double linearSpeed = Utils.deadband(driver.getRawAxis(1), Constants.driveDeadband);
        double curveSpeed = Utils.deadband(-driver.getRawAxis(4), Constants.driveDeadband);

        

        if (driver.getLeftBumper()) { // If the left bumper is pressed
            Limelight.changePipeline(1); // changes Limelight vision pipeline to 1

            if (Limelight.hasValidTargets()) { // If limelight sees a target
                if (driver.getLeftBumper()) {
                    driver.setLeftRumble(0.8); //Rumbles left side
                    driver.setRightRumble(0.8); //Rumbles right Side

                    //If Limelight X cross hair is set X distance away
                    if (Limelight.getX() <= 6d && Limelight.getX() >= -6d) {
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

        if (manip.getRightBumper()) { //if right bumper is pressed
            DriveTrain.safeTurnLeft(); //turn left
        }

        // Intake with agitator
        /**
         * ======================
         *     Intake Control
         * ======================
         */
        if (manip.getYButton()) { // if Y button is pressed
            Intake.intakeCell(Constants.INTAKE_SPEED); //Move intake
            if (agitator.get() < 1) { // if Agitate clock is less than 1 second
                Shooter.hopperAgitationCommand(1); //Move forward direction
            } else if (agitator.get() < 3) { // if agitator clock is less than 3 seconds
                Shooter.hopperAgitationCommand(2); // move in reverse direction
            } else {
                agitator.reset(); //Reset clock to 0 seconds
            }
        } else {
            Intake.intakeCell(0); //Set intake speed to 0
            Shooter.hopperAgitationCommand(0); //Turn off hopper agitator
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
        *        Shooter
        * =====================
        */
        if (manip.getBButton()) {
            LEDs.setShooterLEDsFast(); // Make leds go fast
            Shooter.shoot(Constants.TOP_MOTOR_SPEED_TRENCH, Constants.BOTTOM_MOTOR_SPEED, Constants.HOPPER_SPEED); //Shoot balls w/ hopper
        } else {
            LEDs.setShooterLEDsNormal(); // Leds normal
            Shooter.shoot(0, 0, 0); // Shooter off hopper off
        }
        
        /**
        * =====================
        *        Climber
        * =====================
        */
        if(manip.getStartButton()) {
            Climber.fold(3000); // fold to setpoint
        }
        if(manip.getBackButton()) {
            Climber.climb(0.6); // Climber Speed
        }
    }
}
