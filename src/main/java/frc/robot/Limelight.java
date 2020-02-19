package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.config.Constants;
import frc.robot.utilities.Utils;

@SuppressWarnings("unused")
public class Limelight {
	public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	public static DriverStation ds = DriverStation.getInstance();

	private static boolean m_LimelightHasValidTarget = false;
	private static double m_LimelightDriveCommand = 0.0;
	private static double m_LimelightSteerCommand = 0.0;

	public static void testFeed() {
		double x = table.getEntry("tx").getDouble(0.0);
		double y = table.getEntry("ty").getDouble(0.0);
	}

	public static double getX() {
		return table.getEntry("tx").getDouble(0.0);
	}

	public static double getY() {
		return table.getEntry("ty").getDouble(0.0);
	}

	public static double getA() {
		return table.getEntry("ta").getDouble(0.0);
	}

	public static boolean hasValidTargets() {
		if (table.getEntry("tv").getDouble(0.0) == 1) {
			return true;
		}
		return false;
	}

	public static void changePipeline(int pipeline_num) {
		NetworkTableEntry pipeline = table.getEntry("pipeline");
		pipeline.setValue(pipeline_num);
	}

	public static double getContourArea() {
		return table.getEntry("ta0").getDouble(0.0);
	}

	public static double getContourX() {
		return table.getEntry("ty1").getDouble(0.0);
	}

	public static void lineUp() {
		Limelight.testFeed();
		double target = DriveTrain.getAHRS() + Limelight.getX();
		DriveTrain.turnToAngle(target);
		System.out.println("TARGET: " + target);
		System.out.println("AHRS: " + DriveTrain.getAHRS());
		if (!ds.isEnabled() || (Limelight.getX() >= 5d || Limelight.getX() <= -5d)) {
			DriveTrain.pidDisable();
		}
	}

	public static void dumbLineup(double distance) {
		Limelight.testFeed();
		double x = Math.abs(Limelight.getX()) - 5; // this is like the "error" term
		double power = x * 0.03;
		if (Limelight.getX() >= 5d || Limelight.getX() <= -5d) {
			if (Limelight.getX() > 5) {
				// System.out.println("Should Be Moving Right");
				DriveTrain.arcadeDrive(-power, 0);
				if (Limelight.getX() < 5) {
					goToDistance(distance); // go to distance
				}
			}
		} else if (Limelight.getX() < -5) {
			// System.out.println("Should Be Moving Left");
			DriveTrain.arcadeDrive(power, 0);
			if (Limelight.getX() > -5) {
				goToDistance(distance); // go to distance
			}
		}
	}

	public static void goToDistance(double setDistanceInInches) {
		double distance = Utils.distanceCalulator(Limelight.getY());
		System.out.println(distance);

		if (distance <= setDistanceInInches - 10) { // 10 acts as a range
			DriveTrain.drive(-0.3, -0.3); // should go back if distance is short
		} else if (distance >= setDistanceInInches + 10) { // 10 acts as a range
			DriveTrain.drive(0.3, 0.3); // should drive forward
		} else {
			DriveTrain.drive(0, 0);
		}
	}

	public static double getPipeline() {
		NetworkTableEntry pipeline = table.getEntry("pipeline");
		return pipeline.getDouble(-1);
	}

	public static void dock() {
		double distance = Utils.distFrom(Utils.degToRad(Limelight.getX()), Utils.degToRad(Limelight.getY()));
		System.out.println(distance);
		Limelight.dumbLineup(120);
		DriveTrain.arcadeDrive(0.3, 0);
		// Limelight.lineUp();
		if (distance >= 34000) {
			Limelight.changePipeline(1);
		}

		if (distance <= 33000) {
			Limelight.changePipeline(1);
			DriveTrain.drive(Constants.LINEUP_FULL_SPEED,
					Constants.LINEUP_HALF_SPEED * Constants.DRIVE_STRAIGHT_CONSTANT);
		} else {
			DriveTrain.drive(Constants.LINEUP_FULL_SPEED,
					Constants.LINEUP_FULL_SPEED * Constants.DRIVE_STRAIGHT_CONSTANT);
		}
	}

}
