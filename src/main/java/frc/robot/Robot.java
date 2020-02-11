/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.Diagnostics;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.LEDs;

public class Robot extends TimedRobot{

  @Override
  public void robotInit() {

    DriveTrain.getInstance();
    TeleOp.getInstance();
    Intake.getInstance();
    Shooter.getInstance();
    Diagnostics.getInstance();
    LEDs.getInstance();

    
  }

  @Override
  public void autonomousInit() {
    TeleOp.init();
  }


  @Override
  public void autonomousPeriodic() {
    TeleOp.run();
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
