package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.config.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.Utils;

@SuppressWarnings("unused")
public class Limelight {
	public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	public static DriverStation ds = DriverStation.getInstance();

	private static boolean m_LimelightHasValidTarget = false;
	private static double m_LimelightDriveCommand = 0.0;
	private static double m_LimelightSteerCommand = 0.0;
    private static NetworkTableEntry ty = table.getEntry("ty");

	private static double limelightY = ty.getDouble(0.0);

	//TODO: Tune these
	private static double[] distances = {120, 180}; // should be in Inches???
    public static double[] RPMs = {3200, 3500};

	public static void testFeed() {
		double x = table.getEntry("tx").getDouble(0.0);
		double y = table.getEntry("ty").getDouble(0.0);
	}

	public static void forceLEDsOn() {
		table.getEntry("ledMode").forceSetValue(3);
	}

	public static void forceLEDsOff() {
		table.getEntry("ledMode").forceSetValue(1);
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

	public static void dumbLineup() {
		Limelight.testFeed();
		double x = Math.abs(Limelight.getX()); 
		double power = x * 0.03;
		if (Limelight.getX() > 0d) {
			DriveTrain.drive(-power, 0);
		} 
		if (Limelight.getX() < -0d) {
			DriveTrain.drive(0, power);
		}
	}

	public static void goToDistance() {
		double distance = distanceCalulator(Limelight.getY());
		
		if (distance <= findClosestDistance() - 5) { // 5 acts as a range
			DriveTrain.drive(0.5, -0.5); // should go back if distance is short
		} else if (distance >= findClosestDistance() + 5) { // 5 acts as a range
			DriveTrain.drive(-0.5, 0.5); // should drive forward
		} else {
			DriveTrain.drive(0, 0);
		}
	}

	public static double getPipeline() {
		NetworkTableEntry pipeline = table.getEntry("pipeline");
		return pipeline.getDouble(-1);
	}
	
	/**
	 * 
	 * @param ty Ty value of the limelight
	 * @return distance from the target
	 */
	public static double distanceCalulator(double limelightTY) {

        limelightY = ty.getDouble(0.0);
        double targetAngle = limelightY;
        SmartDashboard.putNumber("Limelight/Target Angle", targetAngle);
        double limelightHeight = Constants.CAMERA_HEIGHT;
        double targetHeight = Constants.POWERPORT_HEIGHT;

        return ((targetHeight-limelightHeight)/(Math.tan((Constants.CAMERA_ANGLE + targetAngle) * Math.PI/180)));
	}

    public static void pushPeriodic() {
		SmartDashboard.putNumber("Limelight Distance", distanceCalulator(Limelight.getY()));
        SmartDashboard.putNumber("RPM Setpoint", findRPM());
        Shooter.publishRPM();
	}
	
	public static double findRPM() {
        double myNumber = distanceCalulator(Limelight.getY());
        double distance = Math.abs(distances[0] - myNumber);
        int idx = 0;
        for(int c = 1; c < distances.length; c++){
            double cdistance = Math.abs(distances[c] - myNumber);
            if(cdistance < distance){
                idx = c;
                distance = cdistance;
            }
        }

        if(idx < distances.length && idx < RPMs.length){
            return RPMs[idx];
        } else {
            return 0;
        }
	}

	public static double findClosestDistance() {
        double myNumber = distanceCalulator(Limelight.getY());
        double distance = Math.abs(distances[0] - myNumber);
        int idx = 0;
        for(int c = 1; c < distances.length; c++){
            double cdistance = Math.abs(distances[c] - myNumber);
            if(cdistance < distance){
                idx = c;
                distance = cdistance;
            }
		}
		return distances[idx];
	}
}
