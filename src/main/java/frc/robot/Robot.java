/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.automodes.AutonMode;
import frc.robot.automodes.DriveStraight;
import frc.robot.automodes.SixBall;
import frc.robot.automodes.ThreeBall;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Diagnostics;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.LEDs;

public class Robot extends TimedRobot{

  AutonMode autonCommand;
	SendableChooser<AutonMode> autoChooser;

  @Override
  public void robotInit() {

    //gets the instance of the subsystems
    DriveTrain.getInstance();
    TeleOp.getInstance();
    Climber.getInstance();
    Intake.getInstance();
    Shooter.getInstance();
    Diagnostics.getInstance();
    LEDs.getInstance();

    //Auton Choosers
    autoChooser = new SendableChooser<AutonMode>();
    autoChooser.setDefaultOption("Three Ball Auto", new ThreeBall());
    autoChooser.addOption("Test Auton", new DriveStraight());
    autoChooser.addOption("Six Ball Auto", new SixBall());

    SmartDashboard.putData("Auto mode", autoChooser);
    SmartDashboard.putNumber("Match Time:", DriverStation.getInstance().getMatchTime());
  }

  @Override
  public void autonomousInit() {
		autoChooser.getSelected().start(); // Auton init
  }


  @Override
  public void autonomousPeriodic() {
    //Periodic encoders
    SmartDashboard.putNumber("Drive Train", DriveTrain.getEncoderAverage());
		SmartDashboard.putNumber("AUTO GYRO", DriveTrain.getAngle());
  }
  
  @Override
	public void teleopInit(){
    //Starts Teleop 
    TeleOp.init();
	}

  @Override
  public void teleopPeriodic() {
    //Continues teleop
    TeleOp.run();
  }

  @Override
  public void disabledPeriodic(){
    //Leds normal
    LEDs.setShooterLEDsNormal();
  }
}
