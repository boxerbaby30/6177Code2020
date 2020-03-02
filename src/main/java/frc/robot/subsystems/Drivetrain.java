/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */

public class Drivetrain {
    private CANSparkMax leftMaster, rightMaster, leftFollower, rightFollower;
    private CANPIDController leftPID, rightPID;
    private CANEncoder leftEncoder, rightEncoder;

    private double lP, lI, lD, lF, lIzone;
    private double rP, rI, rD, rF, rIzone;
    private double kMax, kMin;
    private double kMaxVel, kMinVel, kMaxAcc, allowedErr;
    
    private int smartMotionSlot = 0;
    private double gearRatio = 12.755;
    private double wheelSize = 6.0;

    public Drivetrain(){
        leftFollower = new CANSparkMax(3, MotorType.kBrushless);
        leftMaster = new CANSparkMax(1, MotorType.kBrushless);
        leftFollower.restoreFactoryDefaults();
        leftMaster.restoreFactoryDefaults();
        leftFollower.follow(leftMaster);
        leftMaster.setSmartCurrentLimit(40);
        leftFollower.setSmartCurrentLimit(40);
        leftPID = leftMaster.getPIDController();
        leftEncoder = leftMaster.getEncoder();

        rightFollower = new CANSparkMax(4, MotorType.kBrushless);
        rightMaster = new CANSparkMax(2, MotorType.kBrushless);
        rightFollower.restoreFactoryDefaults();
        rightMaster.restoreFactoryDefaults();
        rightFollower.follow(rightMaster);
        rightMaster.setSmartCurrentLimit(40);
        rightFollower.setSmartCurrentLimit(40);
        rightPID = rightMaster.getPIDController();
        rightEncoder = rightMaster.getEncoder();

        leftMaster.setInverted(true);
        rightMaster.setInverted(false);

        //leftEncoder.setPositionConversionFactor(Math.PI*wheelSize/gearRatio);//Inches
        //leftEncoder.setVelocityConversionFactor(Math.PI*wheelSize/(12*gearRatio*60));//FPS

        lP = 0.0;
        lI = 0.0;
        lD = 0.0;
        lF = 0.0;
        lIzone = 0.0;

        rP = 0.0;
        rI = 0.0;
        rD = 0.0;
        rF = 0.0;
        rIzone = 0.0;

        kMax = 1;
        kMin = -1;

        kMaxVel = 12;
        kMinVel = 0.01;
        kMaxAcc = 5700/1.5;
        allowedErr = 0.02;

        leftPID.setP(lP);
        leftPID.setI(lI);
        leftPID.setD(lD);
        leftPID.setFF(lF);
        leftPID.setIZone(lIzone);

        rightPID.setP(rP);
        rightPID.setI(rI);
        rightPID.setD(rD);
        rightPID.setFF(rF);
        rightPID.setIZone(rIzone);

        leftPID.setOutputRange(kMin, kMax);
        rightPID.setOutputRange(kMin, kMax);

        leftPID.setSmartMotionMaxVelocity(kMaxVel, smartMotionSlot);
        leftPID.setSmartMotionMinOutputVelocity(kMinVel, smartMotionSlot);
        leftPID.setSmartMotionMaxAccel(kMaxAcc, smartMotionSlot);
        leftPID.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

        rightPID.setSmartMotionMaxVelocity(kMaxVel, smartMotionSlot);
        rightPID.setSmartMotionMinOutputVelocity(kMinVel, smartMotionSlot);
        rightPID.setSmartMotionMaxAccel(kMaxAcc, smartMotionSlot);
        rightPID.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
    }

    public void TeleopDrive(double xSpeed, double zRotation) {
        double left = xSpeed - zRotation;
        double right = zRotation + xSpeed;
        if(Math.abs(left) < 0.09){left = 0;}
        if(Math.abs(right) < 0.09){right = 0;}
        leftPID.setReference(left, ControlType.kDutyCycle);
        rightPID.setReference(right, ControlType.kDutyCycle);
    }
    public double getLeftVel(){ return(leftEncoder.getVelocity()); }
    public double getRightVel(){ return(rightEncoder.getVelocity()); }
    public double getLeftDist(){ return(leftEncoder.getPosition()); }
    public double getRightDist(){ return(rightEncoder.getPosition()); }
    public void printTelemetry(){
        SmartDashboard.putNumber("Left Drivetrain Velocity", getLeftVel());
        SmartDashboard.putNumber("Right Drivetrain Velocity", getRightVel());
        SmartDashboard.putNumber("Left Drivetrain Velocity", getLeftDist());
        SmartDashboard.putNumber("Right Drivetrain Velocity", getRightDist());
    }
    public void ZeroEncoders(){
        leftEncoder.setPositionConversionFactor(0.0);
        rightEncoder.setPosition(0.0);
    }
}
