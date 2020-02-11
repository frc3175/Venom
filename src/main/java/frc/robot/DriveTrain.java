package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.config.Constants;
import frc.robot.utilities.Utils;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;

@SuppressWarnings("all")
public class DriveTrain implements PIDOutput {
    private static WPI_TalonFX rightMotorFront, rightMotorBack, leftMotorFront, leftMotorBack;
    private static AHRS gyro;
    private static Solenoid shifter;
    private static PIDController gyropid;
    public static DriveTrain instance = null;

    public static DriveTrain getInstance() {
        if (instance == null) {
            instance = new DriveTrain();
            return instance;
        }

        return instance;
    }

    public DriveTrain() {

        // Right SPARK Motors
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

    }

    public static double getAngle() {
        return gyro.getAngle();
    }

    public static void resetGyro(){
        gyro.reset();
    }

	public static void disablePID() {
		gyropid.disable();
	}
    public static void drive(double powerLeft, double powerRight) {
        leftMotorFront.set(powerLeft);
        leftMotorBack.set(powerRight);
        rightMotorFront.set(powerRight);
        rightMotorBack.set(powerRight);
    }
	public static boolean isConnected(){
		return gyro.isConnected();
	}
    public static void arcadeDrive(double fwd, double tur) {
        // Arcade Drive
        drive(Utils.ensureRange(fwd + tur, -1d, 1d), Utils.ensureRange(fwd - tur, -1d, 1d));
    }

    public static void driveStraight(double power) {
        if (power > 0) {
            if (getAngle() > Constants.DRIVE_STRAIGHT_CONSTANT)
                drive(power * .80, power * 1.15);
            else if (getAngle() < -Constants.DRIVE_STRAIGHT_CONSTANT)
                drive(power * 1.15, power * .85);
            else
                drive(power, power);
        } else {
            if (getAngle() > Constants.DRIVE_STRAIGHT_CONSTANT)
                drive(power * 1.15, power * .85);
            else if (getAngle() < -Constants.DRIVE_STRAIGHT_CONSTANT)
                drive(power * .8, power * 1.15);
            else
                drive(power, power);
        }

    }

    public static double getAHRS() {
        return gyro.getAngle();
    }

    public static void shiftUp() {
        shifter.set(true);
    }

    public static void shiftDown() {
        shifter.set(false);
    }

    public static boolean getShifted() {
        return shifter.get();
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
        if (getEncoderDistanceLeft() > getEncoderDistanceRight()) {
            return getEncoderDistanceRight();
        } else
            return getEncoderDistanceRight();
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
        // TODO Auto-generated method stub
        if (Math.abs(gyropid.getError()) < 5d) {
            gyropid.setPID(gyropid.getP(), .001, 0);
        } else {
            // I Zone
            gyropid.setPID(gyropid.getP(), 0, 0);
        }

        // if(output != 0){
        DriveTrain.arcadeDrive(output, 0);
        // }
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

    public static void resetEncoder(){
		leftMotorFront.setSelectedSensorPosition(0, 0, 10);
		rightMotorFront.setSelectedSensorPosition(0, 0, 10);
		
	}

    // Diagnostics
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