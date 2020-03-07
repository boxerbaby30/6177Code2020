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
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMax.IdleMode;
/**
 * Add your docs here.
 */
public class Shooter {
    private CANSparkMax master;
    private CANEncoder enc;
    private CANPIDController pid;
    private final double err = 100;
    private double kP = 0.011;
    private double kI = 0.0;
    private double kD = 0.0;
    private double kF = 0.000299;
    private double kIzone = 0;

    private double kMin = -1;
    private double kMax = 1;
    //private double kMaxRPM = 5500;

    public Shooter(){
        master = new CANSparkMax(5, MotorType.kBrushed);
        master.restoreFactoryDefaults();
        master.setIdleMode(IdleMode.kCoast);
        //master.setInverted(true);
        pid = master.getPIDController();
        enc = master.getEncoder(EncoderType.kQuadrature, 8192);
        enc.setInverted(true);
        pid.setP(kP);
        pid.setI(kI);
        pid.setD(kD);
        pid.setFF(kF);
        pid.setIZone(kIzone);
        pid.setOutputRange(kMin, kMax);
        master.setSmartCurrentLimit(30);
    }

    public void set(double rpm){
        pid.setReference(rpm, ControlType.kVelocity);
    }

    public void setspd(double spd){
        pid.setReference(spd, ControlType.kDutyCycle);
    }

    public boolean ready(double rpm){
        return(Math.abs(enc.getVelocity() - rpm) < err);
    }

    public void printTelemetry(){
        SmartDashboard.putNumber("Shooter Speed", enc.getVelocity());
        SmartDashboard.putNumber("ShooterMaster Power", master.getAppliedOutput());
    }
}
