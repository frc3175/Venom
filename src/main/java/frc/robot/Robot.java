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
import frc.robot.subsystems.Diagnostics;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.LEDs;

public class Robot extends TimedRobot{

  AutonMode autonCommand;
	SendableChooser<AutonMode> autoChooser;

  @Override
  public void robotInit() {

    DriveTrain.getInstance();
    TeleOp.getInstance();
    Intake.getInstance();
    Shooter.getInstance();
    Diagnostics.getInstance();
    LEDs.getInstance();

    autoChooser = new SendableChooser<AutonMode>();
    autoChooser.setDefaultOption("Shoot and Pass Line and turn n stuff", new DriveStraight());

    SmartDashboard.putData("Auto mode", autoChooser);
    SmartDashboard.putNumber("Match Time:", DriverStation.getInstance().getMatchTime());
  }

  @Override
  public void autonomousInit() {
		autoChooser.getSelected().start();
  }


  @Override
  public void autonomousPeriodic() {
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
    TeleOp.run();
  }

  @Override
  public void disabledPeriodic(){
    LEDs.setNormal();
  }
}
