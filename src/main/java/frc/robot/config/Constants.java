package frc.robot.config;

import frc.robot.Gains;

public class Constants {

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

    
    public static final double kDefaultQuickStopThreshold = 0.2;
    public static final double kDefaultQuickStopAlpha = 0.1;
    public static final double driveDeadband = 0.2;
    public static final double turnDeadband = 0.1;

    /**
     * =====================
     *     Limelight
     * =====================
     */
    public static final int LIMELIGHT_VISION_TARGET = 1;


    //values
    public static final double CAMERA_ANGLE = 22.4; //degrees
    public static final double CAMERA_HEIGHT = 23; //inches
    public static final double LIMELIGHT_LINE_DISTANCE = 120d; //inches

    public static final double LINEUP_FULL_SPEED = 1.0;
    public static final double LINEUP_HALF_SPEED = 0.5;
    public static final double DRIVE_STRAIGHT_CONSTANT = 1;

    /**
     * =====================
     *       Intake
     * =====================
     */
    public static final int INTAKE_TALON = 19;
    public static final int INTAKE_FOLLOWER = 1;
    public static final double INTAKE_SPEED = -1;

    //intake DoubleSolenoid
    public static final int INTAKE_SOLENOID_F = 0;
    public static final int INTAKE_SOLENOID_R = 1;

    /**
     * =====================
     *        Shooter
     * =====================
     */

    //Shooter and Hopper
    public static final int TOP_SHOOTER_TALON = 14;
    public static final int BOTTOM_SHOOTER_TALON = 15;
    public static final int HOPPER_TALON = 16;
    public static final double HOPPER_SPEED = -0.8; //85
    public static final double HOPPER_AGITATION_FORWARD = 1;
    public static final double HOPPER_AGITATION_REVERSE = -0.6;
    public static final double TOP_MOTOR_SPEED_LINE = 0.76; //percent
    public static final double BOTTOM_MOTOR_SPEED = 1; //percent
    public static final double TOP_MOTOR_SPEED_TRENCH = 0.65; // percent

    /**
     * =====================
     * Game piece measurements
     * =====================
     */

     public static final int POWERPORT_HEIGHT = 88; //inches

    
    /**
     * =====================
     *        Climber
     * =====================
     */

    public static final int LEFT_CLIMBER_TALON = 5;
    public static final int RIGHT_CLIMBER_TALON = 6;
    public static final int LEFT_NEO = 17;
    public static final int RIGHT_NEO = 18;
    public static final double CLIMBER_SPEED = 1;

    /**
     * =====================
     *       TeleOp
     * =====================
     */
    // TeleOp
    public static final int XB_POS_DRIVER = 0;
    public static final int XB_POS_MANIP = 1;


    public static final double MAX_RPM_FIRST_GEAR = 4700d;

    public static final int XBOX_DRIVER = 0;
    public static final int XBOX_MANIP = 1;

    public static final int LED_CHANNEL_1 = 1; // > 30
    public static final int LED_CHANNEL_2 = 2; // > 32
    public static final int LED_CHANNEL_3 = 3; // > 34
    
    public static final double LIMELIGHT_P = 0.001;
    public static final double LIMELIGHT_I = 0;
    public static final double LIMELIGHT_D = 0;

    /**
     * =========================
     *       PID Constants
     * =========================
     */

    public static final int kSlotIdx = 0;
    public static final int kPIDLoopIdx = 0;
    public static final int kTimeoutMs = 30;
    public final static Gains kGains_Velocit = new Gains( 0.25, 0.001, 20, 1023.0/7200.0,  300,  1.00);

}
