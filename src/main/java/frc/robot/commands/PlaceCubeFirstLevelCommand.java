// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class PlaceCubeFirstLevelCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Tank drive;
  private final Claw claw; 
  private final Arm arm;

  private boolean readyToMoveArmBack = false;
  private boolean end = false;

  public PlaceCubeFirstLevelCommand(Tank drive, Claw claw, Arm arm) {
    this.drive = drive;
    this.claw = claw;
    this.arm = arm;
    addRequirements(drive);
    addRequirements(claw);
    addRequirements(arm);
  }

  @Override
  public void initialize() {
    drive.leftFront.getEncoder().setPosition(0);
    drive.leftRear.getEncoder().setPosition(0);
    drive.rightFront.getEncoder().setPosition(0);
    drive.rightRear.getEncoder().setPosition(0);

    arm.armMotor.getEncoder().setPosition(0);

    claw.clawMotor.getEncoder().setPosition(0);
  }


  //double check constants
  @Override
  public void execute() {
    if (Math.abs(arm.armMotor.getEncoder().getPosition()) < 100) {
          arm.armMotor.set(0.4); //move the arm back until it can reach botton
    } else {
      if(Math.abs(claw.clawMotor.getEncoder().getPosition()) < 20){
        claw.openClaw(); //open claw a little and drops cube
      } else{
        claw.stopClaw(); //stop claw
        readyToMoveArmBack = true; //ready to move the arm back and continue balance
        arm.armMotor.getEncoder().setPosition(0);
      }
    }

    if(readyToMoveArmBack){
      if(Math.abs(arm.armMotor.getEncoder().getPosition()) < 100){
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
