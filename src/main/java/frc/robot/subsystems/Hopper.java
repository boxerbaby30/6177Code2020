/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * Add your docs here.
 */
public class Hopper extends Thread{
    private VictorSPX topBelt, bottomBelt, roller;
    private DutyCycleEncoder topEnc, bottomEnc;
    private DigitalInput entrance, exit;
    private final double DPR = Math.PI * 1.88;
    private static int ballCounter;
    private State state;
    private boolean active;

    public enum State { Intake, Outtake, Shooting, Idle }

    public Hopper(){
        topBelt = new VictorSPX(1);
        bottomBelt = new VictorSPX(2);
        roller = new VictorSPX(3);
        roller.configFactoryDefault();
        topBelt.configFactoryDefault();
        bottomBelt.configFactoryDefault();
        bottomBelt.setInverted(true);
        roller.setInverted(true);
        topBelt.setNeutralMode(NeutralMode.Brake);
        bottomBelt.setNeutralMode(NeutralMode.Brake);
        roller.setNeutralMode(NeutralMode.Brake);

        topEnc = new DutyCycleEncoder(0);
        bottomEnc = new DutyCycleEncoder(1);
        entrance = new DigitalInput(2);
        //exit = new DigitalInput(3);

        ballCounter = 0;
        state = State.Idle;
        active = false;
        topEnc.setDistancePerRotation(DPR);
        bottomEnc.setDistancePerRotation(DPR);
    }

    public synchronized void setState(State s){ this.state = s; }

    public void activate(){ active = true;}

    public void increment(double zSpeed, double zDist){
        if(active && getDistance() < zDist){
            topBelt.set(ControlMode.PercentOutput, zSpeed);
            bottomBelt.set(ControlMode.PercentOutput, zSpeed);
        } else {
            active = false;
            zero();
            ballCounter++;
        }
    }

    public void decrement(double zSpeed, double zDist){
        if(active && getDistance() < zDist){
            topBelt.set(ControlMode.PercentOutput, zSpeed);
            bottomBelt.set(ControlMode.PercentOutput, zSpeed);
        } else {
            active = false;
            zero();
            ballCounter--;
        }
    }

    public double getDistance(){
        return (Math.abs(topEnc.getDistance()) + Math.abs(bottomEnc.getDistance())) / 2;
    }
    public void resetDistance(){
        topEnc.reset();
        bottomEnc.reset();
    }
    public void zero(){
        System.out.println("Zero Hopper");
        topBelt.set(ControlMode.PercentOutput, 0);
        bottomBelt.set(ControlMode.PercentOutput, 0);
        resetDistance();
    }
    public boolean isFull(){
        boolean full = entrance.get() /*&& exit.get()*/;
        if(full){ ballCounter = 5; }
        return(full);
    }
    public boolean isEmpty(){
        return (!entrance.get() /*&& !exit.get()*/ && ballCounter == 0);
    }
    public void run(){
        State s = State.Idle;
        while(true){
            if(RobotState.isEnabled()){
                if(!active){
                    synchronized(this){ s = this.state; }
                }
                SmartDashboard.putString("State", s.toString());
                if(/*!exit.get() &&*/ s == State.Intake){
                    System.out.println("intaking");
                    if(entrance.get()){activate();}
                    roller.set(ControlMode.PercentOutput, 0.5);
                    increment(0.25, 6.5);
                } else if((ballCounter > 0 || entrance.get()) && s == State.Outtake){
                    System.out.println("outtaking");
                    activate();
                    roller.set(ControlMode.PercentOutput, -0.5);
                    decrement(-0.25, 6.5);
                } else if(/*ballCounter > 0 &&*/ s == State.Shooting){
                    System.out.println("shooting");
                    roller.set(ControlMode.PercentOutput, 0.5);
                    activate();
                    decrement(0.25, 6.5);
                } else {
                    roller.set(ControlMode.PercentOutput, 0);
                    System.out.println("idle");
                    active = false;
                    zero();
                }
                try {
                    Thread.sleep(20);
                } catch(Exception e){
                    System.out.printf("Sleep Interrupted: %s\n", e);
                }
            }
        }
    }

    public synchronized void printTelemtry(){
        SmartDashboard.putBoolean("BallDetected", entrance.get());
        SmartDashboard.putNumber("BallCounter", ballCounter);
        SmartDashboard.putNumber("Distance", getDistance());
        SmartDashboard.putBoolean("Active", active);
    }
}
