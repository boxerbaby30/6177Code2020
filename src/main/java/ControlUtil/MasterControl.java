/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ControlUtil;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.RobotMap;
import frc.robot.subsystems.Hopper.State;

/**
 * Add your docs here.
 */
public class MasterControl {
   private RobotMap bMap;
   public Thread hopperControl;
   
   public MasterControl(RobotMap map){
       this.bMap = map;
   }
   
   public void Teleop() {
       double rpm = 3500;
       double aimerr = 0;
       //double spd = 0.5;
       double xspeed = this.bMap.Xstick.getRawAxis(1);
       double zrot = this.bMap.Xstick.getRawAxis(4);
       //this.bMap.intake.up();
       //this.bMap.intake.stop();
       if(this.bMap.Xstick.getRawButton(5)){
           //intaking
           this.bMap.vision.lightOn(false);
           this.bMap.hopper.setState(State.Intake);
           this.bMap.intake.down();
           this.bMap.intake.in();
           this.bMap.shooter.set(0);
       }
       else if(this.bMap.Xstick.getRawButton(6)){
           //outtaking
           this.bMap.vision.lightOn(false);
           this.bMap.shooter.set(0);
           this.bMap.hopper.setState(State.Outtake);
           this.bMap.intake.down();
           this.bMap.intake.out();
       }
       else if(this.bMap.Xstick.getRawAxis(3) > 0.5){
           //shooting
           this.bMap.shooter.set(rpm);
           this.bMap.vision.lightOn(true);
           //this.bMap.shooter.setspd(spd);
           //aimerr = 0.0485 * this.bMap.vision.getAngle();
           if(this.bMap.shooter.ready(rpm)){
               SmartDashboard.putBoolean("On Point", true);
               this.bMap.hopper.setState(State.Shooting);
               /*if(Math.abs(this.bMap.vision.getAngle()) < 0.5){
                   SmartDashboard.putBoolean("On Point", true);
                   this.bMap.hopper.setState(State.Shooting);
               } else {
                   System.out.println("\nShould see this!!!!!\n");
               }*/
           } else {
               SmartDashboard.putBoolean("On Point", false);
               this.bMap.hopper.setState(State.Idle);
           }
           this.bMap.intake.up();
           this.bMap.intake.stop();
       } else{
           this.bMap.vision.lightOn(false);
           this.bMap.shooter.set(0);
           this.bMap.hopper.setState(State.Idle);
           this.bMap.intake.up();
           this.bMap.intake.stop();
       }
       SmartDashboard.putNumber("xspeed", xspeed);
       SmartDashboard.putNumber("zrot", zrot);
       SmartDashboard.putNumber("aimerr", aimerr);
       SmartDashboard.putNumber("aim", Math.abs(this.bMap.vision.getAngle()));
       //this.bMap.Drive.TeleopDrive(xspeed, zrot + aimerr);
       this.bMap.Drive.TeleopDrive(xspeed, zrot);
       if(this.bMap.Xstick.getRawAxis(2) > 0.5){
           this.bMap.climber.set(0.75);
        } else {
            this.bMap.climber.set(0.0);
        }
   }
}
