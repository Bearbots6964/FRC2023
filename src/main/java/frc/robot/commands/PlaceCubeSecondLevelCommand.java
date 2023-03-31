// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class PlaceCubeSecondLevelCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Tank drive;
  private final Claw claw;
  private final Arm arm;
  private GenericEntry widget;

  private boolean firstStep = true;

  public PlaceCubeSecondLevelCommand(Tank drive, Arm arm, Claw claw) {
    this.drive = drive;
    this.arm = arm;
    this.claw = claw;
    addRequirements(drive);
    addRequirements(arm);

  }

  @Override
  public void initialize() {
    /*
     * drive.leftFront.getEncoder().setPosition(0);
     * drive.leftRear.getEncoder().setPosition(0);
     * drive.rightFront.getEncoder().setPosition(0);
     * drive.rightRear.getEncoder().setPosition(0);
     */


    widget = Shuffleboard.getTab("stuff").add("arm thing", arm.armMotor.getEncoder().getPosition()).getEntry();

  }

  // double check constants
  @Override
  public void execute() {
    widget.setDouble(arm.armMotor.getEncoder().getPosition());

    if (firstStep) {
      claw.closeClaw();
      arm.armMotor.set(0.25);
    }

    if (arm.armMotor.getEncoder().getPosition() >= 60) {
      firstStep = false;
      arm.armMotor.set(0);
      claw.openClaw();
    }

    // go over charge station
    /*
     * if (Math.abs(drive.getAverageDistance()) < 4.95 && firstStep == false) {
     * claw.stopClaw();
     * drive.setAllMotors(-0.3); // move back so that cone falls in
     * arm.armMotor.set(-0.5);
     * }
     * 
     * if (Math.abs(drive.getAverageDistance()) >= 4.95) {
     * drive.setAllMotors(0);
     * }
     */

  }

  @Override
  public void end(boolean interrupted) {
    /*
     * drive.leftFront.getEncoder().setPosition(0);
     * drive.leftRear.getEncoder().setPosition(0);
     * drive.rightFront.getEncoder().setPosition(0);
     * drive.rightRear.getEncoder().setPosition(0);
     */


    // drive.setAllMotors(0);
    arm.armMotor.set(0); // stop the arm motor
  }

  @Override
  public boolean isFinished() {
    /* return Math.abs(drive.getAverageDistance()) >= 4.95; */
    return false;
  }
}
