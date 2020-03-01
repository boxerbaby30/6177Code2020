/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
/**
 * 
 * Add your docs here.
 */
public class Intake {
  private WPI_TalonSRX inMotor;
  private DoubleSolenoid pumpThing;

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public Intake() {
    inMotor = new WPI_TalonSRX(1);
    this.pumpThing = new DoubleSolenoid(2, 1);
    this.pumpThing.set(Value.kForward);
    inMotor.setInverted(false);
  }

  public void up() {
    this.pumpThing.set(Value.kForward);
  }

  public void down() {
    this.pumpThing.set(Value.kReverse);
  }

  public void in() {
    this.inMotor.set(.7);
  }

  public void out() {
    this.inMotor.set(-.7);
  }

  public void stop(){
    this.inMotor.set(0.0);
  }
  
  public void close(){
    this.pumpThing.close();
  }
}
