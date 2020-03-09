/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * Add your docs here.
 */
public class Climber {
    TalonSRX master;
    VictorSP follower;

    public Climber(){
        master = new TalonSRX(2);
        follower = new VictorSP(0);
        master.setNeutralMode(NeutralMode.Brake);
    }

    public void set(double power){
        master.set(ControlMode.PercentOutput, -power);
        follower.set(-power);
    }
}
