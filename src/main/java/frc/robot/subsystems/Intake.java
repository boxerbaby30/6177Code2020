/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
/**
 * 
 * Add your docs here.
 */
public class Intake {
  private TalonSRX inmotor;
  private DoubleSolenoid pumpthing;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public Intake(){

    TalonSRX inmotor = new TalonSRX(1);
    DoubleSolenoid pumpthing = new DoubleSolenoid(1, 2);

  }
  
    (TalonSRX).setSpeed(.7)



}
