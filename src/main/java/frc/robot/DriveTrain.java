package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import frc.robot.config.Constants;
import frc.robot.utilities.Utils;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;

@SuppressWarnings("all")
public class DriveTrain implements PIDOutput{
    private static WPI_TalonFX rightMotorFront, rightMotorBack, leftMotorFront, leftMotorBack;
    private static AHRS hyro;
    private static Solenoid shifter;
    private static PIDController hyropid;
    public static DriveTrain instance = null;
    
    public static DriveTrain getInstance(){
        if(instance == null){
            instance = new DriveTrain();
            return instance;
        }

        return instance;
    }

    public DriveTrain() {

        //Right SPARK Motors
        rightMotorFront = new WPI_TalonFX(Constants.DT_TALON_RIGHTFRONT);
        rightMotorBack = new WPI_TalonFX(Constants.DT_TALON_RIGHTBACK);


        leftMotorFront = new WPI_TalonFX(Constants.DT_TALON_LEFTFRONT);
        leftMotorBack = new WPI_TalonFX(Constants.DT_TALON_LEFTBACK);

        hyro = new AHRS(SPI.Port.kMXP);
        hyropid = new PIDController(1, 0, 0, hyro, this);
        hyropid.setInputRange(-180d, 180d);
        hyropid.setOutputRange(-1.0, 1.0);

        hyropid.setAbsoluteTolerance(2d);
        hyropid.setContinuous(true);


        rightMotorFront.config_kP(0, Constants.kP, 50);
        rightMotorFront.config_kI(0, Constants.kI, 50);
        rightMotorFront.config_kD(0, Constants.kD, 50);

        leftMotorFront.config_kP(0, Constants.kP);
        leftMotorFront.config_kI(0, Constants.kI);
        leftMotorFront.config_kD(0, Constants.kD);



        rightMotorBack.follow(rightMotorFront);
        leftMotorBack.follow(leftMotorFront);


    }

    public static void drive(double powerLeft, double powerRight){
        leftMotorFront.set(powerRight);
        rightMotorFront.set(powerLeft);
    }

    public static void drivePower(double powerLeft, double powerRight){
        rightMotorFront.set(powerRight);
        leftMotorFront.set(powerLeft);
    }

    public static void arcadeDrive(double fwd, double tur) {
        //Arcade Drive      
		drive(Utils.ensureRange(fwd + tur, -1d, 1d), Utils.ensureRange(fwd - tur, -1d, 1d));
    }

    public static double getAHRS(){
        return hyro.getAngle();
    }

    public static void shiftUp(){
        shifter.set(true);
    }

    public static void shiftDown(){
        shifter.set(false);
    }

    public static boolean getShifted(){
        return shifter.get();
    }

    public static double getEncoderRight(){
        return rightMotorFront.getSelectedSensorPosition();
    }

    public static double getEncoderLeft(){
        return leftMotorFront.getSelectedSensorPosition();
    }

    public static double getVelocityRight(){
        return rightMotorFront.getSelectedSensorVelocity();
    }

    public static double getVelocityLeft(){
        return leftMotorFront.getSelectedSensorVelocity();
    }

    public static double getAvgVelocity(){
        //System.out.println("LEFT: " + encoderLeftFront.getVelocity());
        return (leftMotorFront.getSelectedSensorVelocity());
    }

    public static void turnToAngle(double angle){
		hyropid.setSetpoint(angle);
		if(!hyropid.isEnabled()){
			System.out.println("PID Enabled");
			hyropid.reset();
			hyropid.enable();
		}	
    }
    
	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		if (Math.abs(hyropid.getError()) < 5d) {
			hyropid.setPID(hyropid.getP(), .001, 0);
		} else {
			// I Zone
			hyropid.setPID(hyropid.getP(), 0, 0);
        }

        
        // if(output != 0){
        DriveTrain.arcadeDrive(output, 0);
        // }
	}

    public static void pidDisable(){
        System.out.println("PID Disabled");
        hyropid.disable();
    }

    public static void pidEnable(){
        hyropid.enable();
    }
    public static boolean ispidEnabled(){
        return hyropid.isEnabled();
    }

    //Diagnostics
    public static double getRightMotorFrontTemp(){
        return rightMotorFront.getTemperature();
    }

    public static double getRightMotorBackTemp(){
        return rightMotorBack.getTemperature();
    }

    public static double getLeftMotorFrontTemp(){
        return leftMotorFront.getTemperature();
    }

    public static double getLeftMotorBackTemp(){
        return leftMotorBack.getTemperature();
    }
    public static double getRightMotorFrontCurrent(){
        return rightMotorFront.getOutputCurrent();
    }

    public static double getRightMotorBackCurrent(){
        return rightMotorBack.getOutputCurrent();
    }

    public static double getLeftMotorFrontCurrent(){
        return leftMotorFront.getOutputCurrent();
    }

    public static double getLeftMotorBackCurrent(){
        return leftMotorBack.getOutputCurrent();
    }
}