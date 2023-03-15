// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class PlaceGamePieceCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Tank drive;

  private final Claw claw;
  private final Arm arm;

  private boolean readyToLiftArm = false;

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

    claw.clawMotor.getEncoder().setPosition(0);
  }

  @Override
  public void execute() {
    if (arm.allTheWayDownRear.get() == true) {
      arm.armMotor.set(0.4);
    } else {
      if (claw.clawMotor.getEncoder().getPosition() < 20) {
        claw.openClaw();
        readyToLiftArm = true;

        arm.armMotor.getEncoder().setPosition(0);
      }

      if (readyToLiftArm == true && arm.armMotor.getEncoder().getPosition() < 20) {
        arm.armMotor.set(-0.4);
      }
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
