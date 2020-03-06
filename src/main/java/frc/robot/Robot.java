/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Diagnostics;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.utilities.LEDs;

public class Robot extends TimedRobot{


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
    Initializations.getInstance();
    Initializations.init();

  }

  @Override
  public void autonomousInit() {
    Initializations.autonInit();
    DriveTrain.resetGyro();
  }


  @Override
  public void autonomousPeriodic() {

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
