/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ControlUtil;

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
       double rpm = 3000;
       //double spd = 0.5;
       this.bMap.Drive.TeleopDrive(this.bMap.Xstick.getRawAxis(1), this.bMap.Xstick.getRawAxis(4));
       //this.bMap.intake.up();
       //this.bMap.intake.stop();
       if(this.bMap.Xstick.getRawButton(1)){
           //intaking
           this.bMap.hopper.setState(State.Intake);
           this.bMap.intake.down();
           this.bMap.intake.in();
           this.bMap.shooter.set(0);
       }
       else if(this.bMap.Xstick.getRawButton(2)){
           //outtaking
           this.bMap.shooter.set(0);
           this.bMap.hopper.setState(State.Outtake);
           this.bMap.intake.down();
           this.bMap.intake.out();
       }
       else if(this.bMap.Xstick.getRawButton(3)){
           //shooting
           this.bMap.shooter.set(rpm);
           //this.bMap.shooter.setspd(spd);
           if(this.bMap.shooter.ready(rpm)){
               this.bMap.hopper.setState(State.Shooting);
           } else {
               this.bMap.hopper.setState(State.Idle);
           }
           this.bMap.intake.up();
           this.bMap.intake.stop();
       } else{
           this.bMap.shooter.set(0);
           this.bMap.hopper.setState(State.Idle);
           this.bMap.intake.up();
           this.bMap.intake.stop();
       }
   }
}
