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


    public Drivetrain(){
        leftFollower = new CANSparkMax(3, MotorType.kBrushless);
        leftMaster = new CANSparkMax(1, MotorType.kBrushless);
        leftFollower.restoreFactoryDefaults();
        leftMaster.restoreFactoryDefaults();
        leftFollower.follow(leftMaster);
        leftPID = leftMaster.getPIDController();
        leftEncoder = leftMaster.getEncoder();
        rightFollower = new CANSparkMax(4, MotorType.kBrushless);
        rightMaster = new CANSparkMax(2, MotorType.kBrushless);
        rightFollower.restoreFactoryDefaults();
        rightMaster.restoreFactoryDefaults();
        rightFollower.follow(rightMaster);
        rightPID = rightMaster.getPIDController();
        rightEncoder = rightMaster.getEncoder();
        leftMaster.setInverted(false);
        rightMaster.setInverted(true);

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

        kMaxVel = 0;
        kMinVel = 0;
        kMaxAcc = 0;
        allowedErr = 0;

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
        leftPID.setReference(left, ControlType.kSmartVelocity);
        rightPID.setReference(right, ControlType.kSmartVelocity);
    }

    public double getLeftVel(){
        return(leftEncoder.getVelocity());
    }

    public double getRightVel(){
        return(rightEncoder.getVelocity());
    }

    public void printTelemetry(){
        SmartDashboard.putNumber("Left Drivetrain Velocity", getLeftVel());
        SmartDashboard.putNumber("Right Drivetrain Velocity", getRightVel());
    }
}
