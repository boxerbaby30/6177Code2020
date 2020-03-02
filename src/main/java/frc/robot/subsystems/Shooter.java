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
import com.revrobotics.CANSparkMax.IdleMode;
/**
 * Add your docs here.
 */
public class Shooter {
    private CANSparkMax master, follower;
    private CANEncoder enc, enc2;
    private CANPIDController pid, pid2;
    private final double err = 100;
    private double kP = 0.0007;
    private double kI = 0;
    private double kD = 0;
    private double kF = 0.000190;
    private double kIzone = 0;

    private double kMin = -1;
    private double kMax = 1;
    //private double kMaxRPM = 5500;

    public Shooter(){
        master = new CANSparkMax(6, MotorType.kBrushless);
        follower = new CANSparkMax(5, MotorType.kBrushless);
        master.restoreFactoryDefaults();
        follower.restoreFactoryDefaults();
        master.setIdleMode(IdleMode.kCoast);
        follower.setIdleMode(IdleMode.kCoast);
        //follower.setInverted(true);
        //follower.follow(master);
        pid = master.getPIDController();
        enc = master.getEncoder();
        pid2 = follower.getPIDController();
        enc2 = follower.getEncoder();
        pid.setP(kP);
        pid.setI(kI);
        pid.setD(kD);
        pid.setFF(kF);
        pid.setIZone(kIzone);
        pid.setOutputRange(kMin, kMax);

        pid2.setP(kP);
        pid2.setI(kI);
        pid2.setD(kD);
        pid2.setFF(kF);
        pid2.setIZone(kIzone);
        pid2.setOutputRange(kMin, kMax);
    }

    public void set(double rpm){
        pid.setReference(rpm, ControlType.kVelocity); 
        pid2.setReference(-rpm, ControlType.kVelocity);
    }

    public void setspd(double spd){
        pid.setReference(spd, ControlType.kDutyCycle);
        pid2.setReference(-spd, ControlType.kDutyCycle); 
    }

    public boolean ready(double rpm){
        return(Math.abs(enc.getVelocity() - rpm) < err);
    }

    public void printTelemetry(){
        SmartDashboard.putNumber("Shooter Speed", enc.getVelocity());
        SmartDashboard.putNumber("ShooterMaster Power", master.getAppliedOutput());
        SmartDashboard.putNumber("ShooterFollower Power", follower.getAppliedOutput());
    }
}
