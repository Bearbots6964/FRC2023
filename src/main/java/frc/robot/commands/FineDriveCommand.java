// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RebindHat;
import frc.robot.subsystems.Tank;

public class FineDriveCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Tank m_drivebase;

  public FineDriveCommand(Tank subsystem) {
    m_drivebase = subsystem;
    addRequirements(m_drivebase);
  }

  @Override
  public void initialize() {
    // nothing to do here
  }

  @Override
  public void execute() {
    // double check getMaxSpeed(), might be wrong
    Tank.arcadeDrive(
        RebindHat.ControllerToYAxis() * 0.43, RebindHat.ControllerToXAxis() * 0.43);
  }

  @Override
  public void end(boolean interrupted) {
    Tank.arcadeDrive(0, 0);
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
