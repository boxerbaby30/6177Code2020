/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


//import edu.wpi.first.wpilibj.VictorSP;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Timer;


public class ColorWheel {
    private VictorSPX motor;
    //private Timer timer;
    //private boolean running;

    public ColorWheel() {
        motor = new VictorSPX(9);
    }

    public void run() {
        motor.set(ControlMode.PercentOutput, .8);
    }
}