package frc.robot;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.config.Constants;
import frc.robot.utilities.Utils;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpiutil.math.MathUtil;

@SuppressWarnings("all")
public class DriveTrain implements PIDOutput {
    private static WPI_TalonFX rightMotorFront, rightMotorBack, leftMotorFront, leftMotorBack;
    private static AHRS gyro;
    private static PIDController gyropid;
    public static DriveTrain instance = null;

    private static double m_quickStopAccumulator;

    //Gets instance and creates it if it is Null
    public static DriveTrain getInstance() {
        if (instance == null) {
            instance = new DriveTrain();
            return instance;
        }

        return instance;
    }

    //Constructor that is run once
    public DriveTrain() {

        // Drive train motors
        rightMotorFront = new WPI_TalonFX(Constants.DT_TALON_RIGHTFRONT);
        rightMotorBack = new WPI_TalonFX(Constants.DT_TALON_RIGHTBACK);

        leftMotorFront = new WPI_TalonFX(Constants.DT_TALON_LEFTFRONT);
        leftMotorBack = new WPI_TalonFX(Constants.DT_TALON_LEFTBACK);

        gyro = new AHRS(SPI.Port.kMXP);
        gyropid = new PIDController(1, 0, 0, gyro, this);
        gyropid.setInputRange(-180f, 180f);
        gyropid.setOutputRange(-1.0, 1.0);

        gyropid.setAbsoluteTolerance(2d);
        gyropid.setContinuous(true);
        
        rightMotorBack.follow(rightMotorFront);
        leftMotorBack.follow(leftMotorFront);

    }

    /**
     * 
     * @return gyro angle
     */
    public static double getAngle() {
        return gyro.getAngle();
    }

    /**
     * @return Resets gyro Yaw and Angle
     */
    public static void resetGyro() {
        gyro.reset();
    }

    public static void disablePID() {
        gyropid.disable();
    }

    //Tank Drive used for limelight lineup
    public static void drive(double powerLeft, double powerRight) {
        leftMotorFront.set(ControlMode.PercentOutput, powerLeft);
        rightMotorFront.set(ControlMode.PercentOutput, powerRight);
    }

    /**
     * @return gyro is connected
     */
    public static boolean isConnected() {
        return gyro.isConnected();
    }

    //Drive straight method used for auton
    public static void driveStraight(double power) {
        if (power > 0) {
            if (getAngle() > Constants.DRIVE_STRAIGHT_CONSTANT)
                drive(power * .80, -power * 1.15);
            else if (getAngle() < -Constants.DRIVE_STRAIGHT_CONSTANT)
                drive(power * 1.15, -power * .85);
            else
                drive(power, -power);
        } else {
            if (getAngle() > Constants.DRIVE_STRAIGHT_CONSTANT)
                drive(power * 1.15, -power * .85);
            else if (getAngle() < -Constants.DRIVE_STRAIGHT_CONSTANT)
                drive(power * .8, -power * 1.15);
            else
                drive(power, -power);
        }
    }

    /**
     *
     * @param xSpeed      The robot's speed along the X axis [-1.0..1.0]. Forward is
     *                    positive.
     * @param zRotation   The robot's rotation rate around the Z axis [-1.0..1.0].
     *                    Clockwise is positive.
     * @param isQuickTurn If set, overrides constant-curvature turning for
     *                    turn-in-place maneuvers.
     */
    //Curvature Code that you can copy and paste for next year
    @SuppressWarnings({ "ParameterName", "PMD.CyclomaticComplexity" })
    public static void curvatureDrive(double xSpeed, double zRotation, boolean isQuickTurn) {

        xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);

        zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);

        double angularPower;
        boolean overPower;

        if (isQuickTurn) {
            if (Math.abs(xSpeed) < Constants.kDefaultQuickStopThreshold) {
                m_quickStopAccumulator = (1 - Constants.kDefaultQuickStopAlpha) * m_quickStopAccumulator
                        + Constants.kDefaultQuickStopAlpha * MathUtil.clamp(zRotation, -1.0, 1.0) * 2;
            }
            overPower = true;
            angularPower = zRotation * 0.7;
        } else {
            overPower = false;
            angularPower = Math.abs(xSpeed) * zRotation - m_quickStopAccumulator;

            if (m_quickStopAccumulator > 1) {
                m_quickStopAccumulator -= 1;
            } else if (m_quickStopAccumulator < -1) {
                m_quickStopAccumulator += 1;
            } else {
                m_quickStopAccumulator = 0.0;
            }
        }

        double leftMotorOutput = xSpeed + angularPower;
        double rightMotorOutput = xSpeed - angularPower;

        // If rotation is overpowered, reduce both outputs to within acceptable range
        if (overPower) {
            if (leftMotorOutput > 1.0) {
                rightMotorOutput -= leftMotorOutput - 1.0;
                leftMotorOutput = 1.0;
            } else if (rightMotorOutput > 1.0) {
                leftMotorOutput -= rightMotorOutput - 1.0;
                rightMotorOutput = 1.0;
            } else if (leftMotorOutput < -1.0) {
                rightMotorOutput -= leftMotorOutput + 1.0;
                leftMotorOutput = -1.0;
            } else if (rightMotorOutput < -1.0) {
                leftMotorOutput -= rightMotorOutput + 1.0;
                rightMotorOutput = -1.0;
            }
        }

        // Normalize the wheel speeds
        double maxMagnitude = Math.max(Math.abs(leftMotorOutput), Math.abs(rightMotorOutput));
        if (maxMagnitude > 1.0) {
            leftMotorOutput /= maxMagnitude;
            rightMotorOutput /= maxMagnitude;
        }

        leftMotorFront.set(ControlMode.PercentOutput, leftMotorOutput * 1);
        rightMotorFront.set(ControlMode.PercentOutput, rightMotorOutput * 1 * -1);
    }

    public static double getAHRS() {
        return gyro.getAngle();
    }

    public static double getEncoderDistanceRight() {
        return rightMotorFront.getSelectedSensorPosition();
    }

    public static double getEncoderDistanceLeft() {
        return leftMotorFront.getSelectedSensorPosition();
    }

    public static double getVelocityRight() {
        return rightMotorFront.getSelectedSensorVelocity();
    }

    public static double getVelocityLeft() {
        return leftMotorFront.getSelectedSensorVelocity();
    }

    public static double getAvgVelocity() {
        // System.out.println("LEFT: " + encoderLeftFront.getVelocity());
        return (leftMotorFront.getSelectedSensorVelocity());
    }

    public static double getEncoderAverage() {
        if (Math.abs(getEncoderDistanceLeft()) > Math.abs(getEncoderDistanceRight())) {
            return Math.abs(getEncoderDistanceRight());
        } else
            return Math.abs(getEncoderDistanceRight());
    }

    public static void turnToAngle(double angle) {
        gyropid.setSetpoint(angle);
        if (!gyropid.isEnabled()) {
            System.out.println("PID Enabled");
            gyropid.reset();
            gyropid.enable();
        }
    }

    public static void stop() {
        rightMotorFront.set(ControlMode.PercentOutput, 0);
        rightMotorBack.set(ControlMode.PercentOutput, 0);
        leftMotorBack.set(ControlMode.PercentOutput, 0);
        leftMotorFront.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void pidWrite(double output) {
        if (Math.abs(gyropid.getError()) < 5d) {
            gyropid.setPID(gyropid.getP(), .001, 0);
        } else {
            // I Zone
            gyropid.setPID(gyropid.getP(), 0, 0);
        }
    }

    public static void pidDisable() {
        System.out.println("PID Disabled");
        gyropid.disable();
    }

    public static void pidEnable() {
        gyropid.enable();
    }

    public static boolean ispidEnabled() {
        return gyropid.isEnabled();
    }

    public static void resetEncoder() {
        leftMotorFront.setSelectedSensorPosition(0, 0, 10);
        rightMotorFront.setSelectedSensorPosition(0, 0, 10);

    }

    // Diagnostics Used in the diagnostic subsystem
    public static double getRightMotorFrontTemp() {
        return rightMotorFront.getTemperature();
    }

    public static double getRightMotorBackTemp() {
        return rightMotorBack.getTemperature();
    }

    public static double getLeftMotorFrontTemp() {
        return leftMotorFront.getTemperature();
    }

    public static double getLeftMotorBackTemp() {
        return leftMotorBack.getTemperature();
    }

    public static double getRightMotorFrontCurrent() {
        return rightMotorFront.getOutputCurrent();
    }

    public static double getRightMotorBackCurrent() {
        return rightMotorBack.getOutputCurrent();
    }

    public static double getLeftMotorFrontCurrent() {
        return leftMotorFront.getOutputCurrent();
    }

    public static double getLeftMotorBackCurrent() {
        return leftMotorBack.getOutputCurrent();
    }
}