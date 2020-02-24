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
       //this.bMap.Drive.TeleopDrive(this.bMap.Xstick.getRawAxis(2), this.bMap.Xstick.getRawAxis(5));
       if(this.bMap.Xstick.getRawButton(1)){
           //intaking
           this.bMap.hopper.intaking(true);
           this.bMap.hopper.outtaking(false);
           this.bMap.hopper.shooting(false);
       }
       else if(this.bMap.Xstick.getRawButton(2)){
           //outtaking
           this.bMap.hopper.outtaking(true);
           this.bMap.hopper.shooting(false);
           this.bMap.hopper.intaking(false);
       }
       else if(this.bMap.Xstick.getRawButton(3)){
           //shooting
           this.bMap.hopper.shooting(true);
           this.bMap.hopper.outtaking(false);
           this.bMap.hopper.intaking(false);
       }
       else{
           System.out.println("idle");
           this.bMap.hopper.zero();
       }
   }
}
