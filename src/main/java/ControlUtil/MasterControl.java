/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ControlUtil;

import frc.robot.subsystems.Intake;
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
       Intake in = new Intake();
       //this.bMap.Drive.TeleopDrive(this.bMap.Xstick.getRawAxis(2), this.bMap.Xstick.getRawAxis(5));
       if(this.bMap.Xstick.getRawButton(1)){
           //intaking
           this.bMap.hopper.setState(State.Intake);
       }
       else if(this.bMap.Xstick.getRawButton(2)){
           //outtaking
           this.bMap.hopper.setState(State.Outtake);
       }
       else if(this.bMap.Xstick.getRawButton(3)){
           //shooting
           this.bMap.hopper.setState(State.Shooting);
       } else if (this.bMap.Xstick.getRawButton(4)) {
           in.setState(State.Intaking);
           if (this.bMap.Xstick.getRawButton(5)) {
               in.running = true;
           }
       }
       else{
           this.bMap.hopper.setState(State.Idle);
       }
   }
}
