/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Drivetrain;
/**
 * Add your docs here.
 */
public class RobotMap {
    public XboxController Xstick;
    public Drivetrain Drive;
    public Hopper hopper;
    public Intake intake;
    public Shooter shooter;
    public Vision vision;
    public Climber climber;

    public RobotMap(){
        this.Xstick = new XboxController(0);
        this.Drive = new Drivetrain();
        this.hopper = new Hopper();
        this.intake = new Intake();
        this.shooter = new Shooter();
        this.vision = new Vision();
        this.climber = new Climber();
    }

}
