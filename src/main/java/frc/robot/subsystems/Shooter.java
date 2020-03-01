/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.ControlType;
/**
 * Add your docs here.
 */
public class Shooter {
    private CANSparkMax master, follower;
    private CANEncoder enc;
    private CANPIDController pid;
    private final double err = 0.01;
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double kF = 0.00017241;
    private double kIzone = 0;

    private double kMin = -1;
    private double kMax = 1;
    //private double kMaxRPM = 5500;

    public Shooter(){
        master = new CANSparkMax(5, MotorType.kBrushless);
        follower = new CANSparkMax(6, MotorType.kBrushless);
        master.restoreFactoryDefaults();
        follower.restoreFactoryDefaults();
        follower.setInverted(true);
        follower.follow(master);
        pid = master.getPIDController();
        enc = master.getEncoder();
        pid.setP(kP);
        pid.setI(kI);
        pid.setD(kD);
        pid.setFF(kF);
        pid.setIZone(kIzone);
        pid.setOutputRange(kMin, kMax);
    }

    public void set(double rpm){
        pid.setReference(rpm, ControlType.kVelocity); 
    }

    public boolean ready(double rpm){
        return(Math.abs(enc.getVelocity() - rpm) < err);
    }

    public void printTelemetry(){
        SmartDashboard.putNumber("Shooter Speed", enc.getVelocity());
    }
}
