// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class PlaceGamePieceCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Tank drive;

  private final Claw claw; // NOPMD
  private final Arm arm;

  public PlaceGamePieceCommand(Tank drive, Claw claw, Arm arm) {
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
  }

  @Override
  public void execute() {
    if (arm.allTheWayDownRear
        .get()) { // the '== true' is implied, because the if statement is looking for the
      // expression to be true. If it is false, it will not run the code inside the if
      // statement, so we don't need to write it.
      arm.armMotor.set(0.4); // move the arm up
    } else {
      // claw.openClaw();
    }
  }

  @Override
  public void end(boolean interrupted) {
    arm.armMotor.set(0); // stop the arm motor
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
