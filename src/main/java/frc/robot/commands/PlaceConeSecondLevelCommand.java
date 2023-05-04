// Copyright (c) Elliot Snyder, Parker Brownlowe, and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WTFPL license file in the internet. Any questions about the mental
// health of the authors can be shouted into the endless void of the internet.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class PlaceConeSecondLevelCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Tank drive;

  private final Claw claw;
  private final Arm arm;
  private boolean coneOut;
  private boolean clawDown;

  private boolean armBack;

  public PlaceConeSecondLevelCommand(Tank drive, Arm arm, Claw claw) {
    this.drive = drive;
    this.arm = arm;
    this.claw = claw;
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
    // starting movement
    claw.closeClaw();
    arm.armMotor.set(0.5);
  }

  // double check constants
  @Override
  public void execute() {

    if (!clawDown && Math.abs(arm.armMotor.getEncoder().getPosition()) >= 115) {
      claw.stopClaw();
      arm.armMotor.set(0);
      drive.setAllMotors(-0.3); // move back so that cone falls in
      clawDown = true;
    }
    if (!coneOut && Math.abs(drive.getAverageDistance()) >= 1.00) {
      coneOut = true;
      arm.armMotor.set(-0.5);
    }
    if (coneOut && !armBack && arm.armMotor.getEncoder().getPosition() < 10) {
      armBack = true;
      arm.armMotor.set(0);
    }
    // stops after reaching a certian point
    /*
     * if (Math.abs(drive.getAverageDistance()) >= 4.95) {
     * drive.setAllMotors(0);
     * }
     */
  }

  @Override
  public void end(boolean interrupted) {
    drive.leftFront.getEncoder().setPosition(0);
    drive.leftRear.getEncoder().setPosition(0);
    drive.rightFront.getEncoder().setPosition(0);
    drive.rightRear.getEncoder().setPosition(0);
    arm.armMotor.getEncoder().setPosition(0);

    drive.setAllMotors(0);
    arm.armMotor.set(0); // stop the arm motor
    claw.stopClaw();
  }

  @Override
  public boolean isFinished() {
    return Math.abs(drive.getAverageDistance()) >= 4.95;
  }
}
