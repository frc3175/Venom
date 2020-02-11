package frc.robot.config;

public class Constants {
    /**
     * Use This File Only For Emergencies (like JSONConstants cannot find the config
     * file)
     */

    /**
     * =====================
     *     DRIVE TRAIN
     * =====================
     */
    // Left Motor IDs
    public static final int DT_TALON_LEFTFRONT = 10; // CAN
    public static final int DT_TALON_LEFTBACK = 11; // CAN

    // right motor IDs
    public static final int DT_TALON_RIGHTFRONT = 12; // CAN
    public static final int DT_TALON_RIGHTBACK = 13; // CAN

    // Limelight
    public static final int LIMELIGHT_VISION_TARGET = 1;
    public static final int LIMELIGHT_LINEUP_LINE_FRONT = 2;
    public static final int LIMELIGHT_LINEUP_LINE_MIDDLE = 3;
    public static final int LIMELIGHT_LINEUP_LINE_BACK = 4;

    public static final double CAMERA_ANGLE = 15.0;
    public static final double CAMERA_HEIGHT = 15.0;

    public static final double LINEUP_FULL_SPEED = 1.0;
    public static final double LINEUP_HALF_SPEED = 0.5;
    public static final double DRIVE_STRAIGHT_CONSTANT = 1;

    /**
     * =====================
     *       Intake
     * =====================
     */
    public static final int INTAKE_TALON = 1;
    public static final double INTAKE_SPEED = 0.8;

    //intake DoubleSolenoid
    public static final int INTAKE_SOLENOID_F = 0;
    public static final int INTAKE_SOLENOID_R = 1;

    /**
     * =====================
     *        Shooter
     * =====================
     */

    //Shooter and Hopper
    public static final int TOP_SHOOTER_TALON = 2;
    public static final int BOTTOM_SHOOTER_TALON = 3;
    public static final int HOPPER_TALON = 4;
    public static final int HOPPER_SPEED = 1;
    public static final double HOPPER_AGITATION_FORWARD = 1;
    public static final double HOPPER_AGITATION_REVERSE = -0.49;
    public static final double TOP_MOTOR_SPEED = 0.7;
    public static final double BOTTOM_MOTOR_SPEED = 1;

    
    /**
     * =====================
     *        Climber
     * =====================
     */

    public static final int LEFT_CLIMBER_TALON = 5;
    public static final int RIGHT_CLIMBER_TALON = 6;
    public static final int LEFT_NEO = 17;
    public static final int RIGHT_NEO = 18;

    /**
     * =====================
     *       TeleOp
     * =====================
     */
    // TeleOp
    public static final int XB_POS_DRIVER = 0;
    public static final int XB_POS_MANIP = 1;

    public static final double kP = 0.1;
    public static final double kI = 1e-4;
    public static final double kD = 1;
    public static final double kFF = 0;
    public static final double kMaxOutput = 1.0;
    public static final double kMinOutput = -1.0;




    public static final double MAX_RPM_FIRST_GEAR = 4700d;

    public static final int XBOX_DRIVER = 0;
    public static final int XBOX_MANIP = 1;

    public static final int LED_CHANNEL = 1;
    public static final double LIMELIGHT_P = 0.001;
    public static final double LIMELIGHT_I = 0;
    public static final double LIMELIGHT_D = 0;
}
