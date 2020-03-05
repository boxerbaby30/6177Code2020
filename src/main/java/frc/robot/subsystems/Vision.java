/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Vision {
    double kpAim;
    double kpDistance;
    double minAimCommand;
    NetworkTable table;

    NetworkTableEntry tx, ty, tv;

    double thetaToGround = 0;
    double heightDiff = 90.75 - 34.5;

    public Vision(){
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
    }

    public double seesTarget(){return(tv.getDouble(0));}
    public double getAngle(){return(tx.getDouble(0));}
    public void setPipeline(int pl){table.getEntry("pipeline").setNumber(pl);}

    public double getDist(){
        double theta = ty.getDouble(0);
        return(heightDiff/Math.tan(thetaToGround+theta));
    }

    public void lightOn(boolean on){
        if(on){table.getEntry("ledMode").setNumber(0);}
        else{table.getEntry("ledMode").setNumber(1);}
    }

    public void processOn(boolean on){
        if(on){table.getEntry("camMode").setNumber(0);}
        else{table.getEntry("camMode").setNumber(1);}
    }

    public void PiP(boolean top){
        if(top){table.getEntry("stream").setNumber(1);}
        else{table.getEntry("stream").setNumber(2);}
    }

    public void printTelemetry(){
        SmartDashboard.putNumber("tx", tx.getDouble(0));
        SmartDashboard.putNumber("ty", ty.getDouble(0));
        SmartDashboard.putNumber("tv", tv.getDouble(0));
        if(seesTarget() == 1){
            SmartDashboard.putNumber("VisionDistance", getDist());
            SmartDashboard.putNumber("Angle", getAngle());
        }
    }
}
