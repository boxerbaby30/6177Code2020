/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import ControlUtil.MasterControl;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Hopper.State;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  MasterControl control;
  RobotMap bMap;
  boolean shoot = true;
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    bMap = new RobotMap();
    control = new MasterControl(bMap);
    bMap.hopper.start();
  }

  @Override
  public void robotPeriodic() {
    if(!bMap.hopper.isAlive()){bMap.hopper.start();}
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    if(shoot){
      double rpm = 3500;
      double aimerr = 0;
      this.bMap.shooter.set(rpm);
      this.bMap.vision.lightOn(true);
          //this.bMap.shooter.setspd(spd);
      aimerr = 0.045 * this.bMap.vision.getAngle();
      if(this.bMap.shooter.ready(rpm)){
          if(Math.abs(this.bMap.vision.getAngle()) < 0.5){
              SmartDashboard.putBoolean("On Point", true);
              this.bMap.hopper.setState(State.Shooting);
          } else {
              System.out.println("\nShould see this!!!!!\n");
          }
      } else {
          SmartDashboard.putBoolean("On Point", false);
          this.bMap.hopper.setState(State.Idle);
      }
      this.bMap.intake.up();
      this.bMap.intake.stop();
      this.bMap.Drive.TeleopDrive(0, aimerr);
    }
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    control.Teleop();
    bMap.hopper.printTelemtry();
    bMap.shooter.printTelemetry();
    bMap.vision.printTelemetry();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
