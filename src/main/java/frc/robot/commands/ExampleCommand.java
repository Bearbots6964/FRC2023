// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ExampleSubsystem;
public class ExampleCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "PMD.UnusedImport"})
  private final ExampleSubsystem m_subsystem; // NOPMD

  public ExampleCommand(ExampleSubsystem subsystem) {
    m_subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    // Called when the command is initially scheduled.
  }

  @Override
  public void execute() {
    // Called every time the scheduler runs while the command is scheduled.
  }

  @Override
  public void end(boolean interrupted) {
    // Called once the command ends or is interrupted.
  }

  @Override
  public boolean isFinished() {
    // Return true when the command should end.
    return false;
  }
}
