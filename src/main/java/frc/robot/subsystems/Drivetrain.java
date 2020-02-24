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

/**
 * Add your docs here.
 */

public class Drivetrain {
    private CANSparkMax leftMaster, rightMaster, leftFollower, rightFollower;
    private CANPIDController leftPID, rightPID;
    private CANEncoder leftEncoder, rightEncoder;

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
        rightMaster.setInverted(false);
    }

    public void TeleopDrive(double xSpeed, double zRotation) {
        double left = xSpeed - zRotation;
        double right = zRotation + xSpeed;
        leftPID.setReference(left, ControlType.kDutyCycle);
        rightPID.setReference(right, ControlType.kDutyCycle);
    }
}
