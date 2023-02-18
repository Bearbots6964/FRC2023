// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Tank;

public class DriveCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Tank m_drivebase;

  public DriveCommand(Tank subsystem) {
    m_drivebase = subsystem;
    addRequirements(m_drivebase);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    //double check getMaxSpeed(), might be wrong
    m_drivebase.arcadeDrive(
        RobotContainer.getLeftStickY() * RobotContainer.getMaxSpeed(),
        RobotContainer.getRightStickX() * RobotContainer.getMaxSpeed());
  }

  @Override
  public void end(boolean interrupted) {
    m_drivebase.arcadeDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
