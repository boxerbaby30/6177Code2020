/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * Add your docs here.
 */
public class Hopper extends Thread{
    private VictorSPX topBelt, bottomBelt;
    private DutyCycleEncoder topEnc, bottomEnc;
    private DigitalInput entrance;
    private final double DPR = Math.PI * 1.5;
    private static int ballCounter;
    private State state;

    public enum State { Intake, Outtake, Shooting, Idle }

    public Hopper(){
        topBelt = new VictorSPX(1);
        bottomBelt = new VictorSPX(2);
        topBelt.configFactoryDefault();
        bottomBelt.configFactoryDefault();
        bottomBelt.setInverted(true);
        topBelt.setNeutralMode(NeutralMode.Brake);
        bottomBelt.setNeutralMode(NeutralMode.Brake);

        topEnc = new DutyCycleEncoder(1);
        bottomEnc = new DutyCycleEncoder(2);
        entrance = new DigitalInput(3);

        ballCounter = 0;
        state = State.Idle;
        topEnc.setDistancePerRotation(DPR);
        bottomEnc.setDistancePerRotation(DPR);
    }

    public void increment(double zSpeed, double zDist){
        if(getDistance() < zDist){
            topBelt.set(ControlMode.PercentOutput, zSpeed);
            bottomBelt.set(ControlMode.PercentOutput, zSpeed);
        } else {
            zero();
            ballCounter++;
        }
    }

    public void decrement(double zSpeed, double zDist){
        if(getDistance() < zDist){
            topBelt.set(ControlMode.PercentOutput, zSpeed);
            bottomBelt.set(ControlMode.PercentOutput, zSpeed);
        } else {
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
    public boolean isFull(){return (entrance.get() && ballCounter == 4);}
    public boolean isEmpty(){return (!entrance.get() && ballCounter == 0);}
    public void run(State state){
        while(RobotState.isEnabled()){
            SmartDashboard.putString("State", state.toString());
            if(ballCounter <= 4 && entrance.get() && state == State.Intake){
                System.out.println("intaking");
                increment(0.3, 7);
            }
            if((ballCounter > 0 || entrance.get()) && state == State.Outtake){
                System.out.println("outtaking");
                decrement(-0.3, 7);
            }
            if(ballCounter > 0 && state == State.Shooting){
                System.out.println("shooting");
                decrement(0.3, 7);
            }
        }
    }

    public synchronized void printTelemtry(){
        SmartDashboard.putBoolean("BallDetected", entrance.get());
        SmartDashboard.putNumber("BallCounter", ballCounter);
        SmartDashboard.putNumber("Distance", getDistance());
    }
}
