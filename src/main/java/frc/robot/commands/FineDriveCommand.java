// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RebindHat;
import frc.robot.subsystems.Tank;

public class FineDriveCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Tank m_drivebase;
  private double speedMult = 0.43;
  private GenericEntry multWidget;

  public FineDriveCommand(Tank subsystem) {
    m_drivebase = subsystem;
    addRequirements(m_drivebase);
    multWidget = Shuffleboard.getTab("Fine Drive").add("Speed", speedMult).getEntry();
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    // double check getMaxSpeed(), might be wrong
    m_drivebase.arcadeDrive(
        RebindHat.ControllerToYAxis() * multWidget.getDouble(0), RebindHat.ControllerToXAxis() * multWidget.getDouble(0));
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
