// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class PlaceConeSecondLevelCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Tank drive; 
  private final Arm arm;

  private boolean readyToMoveArmBack = false;
  private boolean end = false;

  public PlaceConeSecondLevelCommand(Tank drive, Arm arm) {
    this.drive = drive;
    this.arm = arm;
    addRequirements(drive);
    addRequirements(arm);
  }

  @Override
  public void initialize() {
    drive.leftFront.getEncoder().setPosition(0);
    drive.leftRear.getEncoder().setPosition(0);
    drive.rightFront.getEncoder().setPosition(0);
    drive.rightRear.getEncoder().setPosition(0);

    arm.armMotor.getEncoder().setPosition(0);
  }


  //double check constants
  @Override
  public void execute() {
    if (Math.abs(arm.armMotor.getEncoder().getPosition()) < 80) {
          arm.armMotor.set(0.4); //move the arm until cone gets in 
    } else {
      if(Math.abs(drive.getAverageDistance()) < 10){
        drive.arcadeDrive(-2, 0); //move back so that cone falls in
      } else{
        drive.arcadeDrive(0, 0);; //stop moving 
        readyToMoveArmBack = true; //ready to move the arm back and continue balance
        arm.armMotor.getEncoder().setPosition(0);
      }
    }

    if(readyToMoveArmBack){
      if(Math.abs(arm.armMotor.getEncoder().getPosition()) < 80){
        arm.armMotor.set(-0.4); //moves the arm back to starting position
        end = true;
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
    arm.armMotor.set(0); // stop the arm motor
  }

  @Override
  public boolean isFinished() {
    return end;
  }
}
