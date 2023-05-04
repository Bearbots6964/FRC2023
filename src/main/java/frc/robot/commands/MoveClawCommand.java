// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Claw;

public class MoveClawCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Claw m_subsystem;

  public MoveClawCommand(Claw subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if (Math.abs(RobotContainer.getClawOutValue()) < 0.2) {
      m_subsystem.clawMotor.set(RobotContainer.getClawInValue() * 0.85);
    } else {
      m_subsystem.clawMotor.set(-RobotContainer.getClawOutValue() * 0.85);
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_subsystem.stopClaw();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
