package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.config.Constants;
import frc.robot.subsystems.Diagnostics;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.LEDs;
import frc.robot.utilities.Utils;

public class TeleOp {
    private static XBoxController manip;
    private static XBoxController driver;
    private static TeleOp instance;
    private static Timer clock, agitator;

    public static TeleOp getInstance() {
        if (instance == null)
            instance = new TeleOp();
        return instance;
    }

    private TeleOp() {
        driver = new XBoxController(Constants.XBOX_DRIVER);
        manip = new XBoxController(Constants.XBOX_MANIP);
    }

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
        LEDs.setNormal();

        // Start Agitator Clock
        agitator.start();

    }

    public static void run() {
        /**
         * ============================ 
         *    DRIVER CONTROLS BELOW
         * ============================
         */
        long startTime = System.currentTimeMillis();

        Limelight.changePipeline(0);
        // System.out.println("HAS VALID TARGETS: " + Limelight.hasValidTargets());

        if (driver.getLeftBumper()) {
            Limelight.changePipeline(1);

            if (Limelight.hasValidTargets()) {
                if (driver.getLeftBumper()) {

                    if (Limelight.getX() <= 6d && Limelight.getX() >= -6d) {
                        // Elevator.flipClawUp();
                        DriveTrain.arcadeDrive(0, 0.2);
                    } else {
                        Limelight.dumbLineup();
                    }
                    // DriveTrain.arcadeDrive(0, 0.3);
                } else {
                    if (DriveTrain.ispidEnabled()) {
                        DriveTrain.pidDisable();
                    }
                    // Elevator.flipClawDown();
                    DriveTrain.arcadeDrive(Utils.expoDeadzone(-driver.getLeftStickXAxis(), 0.1, 2),
                            Utils.expoDeadzone(-driver.getRightStickYAxis(), 0.1, 1.2));
                }

            } else {
                // DriveTrain.pidDisable();
                // Limelight.changePipeline(1);
                driver.setLeftRumble(0.0);
                driver.setRightRumble(0.0);
                if (driver.getLeftBumper()) {
                    // DriveTrain.arcadeDrive(0, 0.);
                } else {

                }
            }
        } else {
            DriveTrain.arcadeDrive(Utils.expoDeadzone(-driver.getLeftStickXAxis(), 0.1, 2),
                    Utils.expoDeadzone(-driver.getRightStickYAxis(), 0.1, 1.2));
        }

        /**
         * ============================ 
         *     MANIP CONTROLS BELOW
         * ============================
         */

        // Intake with agitator
        if (manip.getYButton()) {
            Intake.intakeCell(Constants.INTAKE_SPEED);
            if (agitator.get() < 1) {
                Shooter.hopperAgitationCommand(1);
            } else if (agitator.get() < 3) {
                Shooter.hopperAgitationCommand(2);
            } else {
                agitator.reset();
            }
        } else {
            Intake.intakeCell(0);
        }

        // Intake up and down
        if (manip.getXButton()) {
            Intake.IntakeUp();
        } else if (manip.getAButton()) {
            Intake.IntakeDown();
        }

        // Shooter
        if (manip.getBButton()) {
            LEDs.setFast();
            Shooter.shoot(Constants.TOP_MOTOR_SPEED_TRENCH, Constants.BOTTOM_MOTOR_SPEED);
        } else {
            LEDs.setNormal();
            Shooter.shoot(0, 0);
        }
    }
}
