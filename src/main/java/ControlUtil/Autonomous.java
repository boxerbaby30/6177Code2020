/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ControlUtil;

import frc.robot.subsystems.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Hopper.State;

/**
 * Add your docs here.
 */
public class Autonomous {
    RobotMap bMap;

    public Autonomous(RobotMap bmap){
        bMap = bmap;
    }

    public void run(){
        double rpm = 3500;
        double aimerr = 0;
        this.bMap.shooter.set(rpm);
        this.bMap.vision.lightOn(true);
        //this.bMap.shooter.setspd(spd);
        aimerr = 0.05 * this.bMap.vision.getAngle();
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
