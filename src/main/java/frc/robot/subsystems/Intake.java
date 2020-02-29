/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import ControlUtil.MasterControl;
/**
 * 
 * Add your docs here.
 */
public class Intake {
  private WPI_TalonSRX inMotor;
  private DoubleSolenoid pumpThing;
  private State state;
  public boolean running;
  
  public enum State {Intaking, Outtaking, Idle};

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Intake() {
    inMotor = new WPI_TalonSRX(1);
    DoubleSolenoid pumpThing = new DoubleSolenoid(1, 2);
    this.pumpThing.set(Value.kForward);
    inMotor.setInverted(false);
    state = State.Idle;

  }
  public synchronized void setState(State s) {
    this.state = s;
  }

  public void ExtendClaw() {
    this.pumpThing.set(Value.kForward);
  }

  public void retractClaw() {
    this.pumpThing.set(Value.kReverse);
  }

  public void inClaw() {
    this.inMotor.set(.7);
  }

  public void outClaw() {
    this.inMotor.set(-.7);
  }

  public void run() {
    State s = State.Idle;
    while(true) {
      if (RobotState.isEnabled()) {
        if (s == State.Intaking) {
          System.out.println("Intaking Claw");
          ExtendClaw();
          if (running == true) {
            inClaw();
          }
        } else if (s == State.Outtaking) {
          retractClaw();
        } else {
          System.out.println("Idle");
          running = false;
        }
      }
    } 
  }
}
