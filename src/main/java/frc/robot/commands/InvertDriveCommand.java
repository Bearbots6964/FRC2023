// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Tank;

public class InvertDriveCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Tank m_drivebase;
  private final RobotContainer m_robot;

  public InvertDriveCommand(Tank subsystem, RobotContainer robot) {
    m_drivebase = subsystem;
    m_robot = robot;

    addRequirements(m_drivebase);
  }

  @Override
  public void initialize() {
    m_robot.inverted = true;
    m_robot.initTeleop();
  }

  @Override
  public void execute() {
    // double check getMaxSpeed(), might be wrong
    m_drivebase.arcadeDrive(
        -RobotContainer.getAdjustedTurningStickInput() * Constants.CanConstants.maxSpeed,
        RobotContainer.getAdjustedForwardStickInput() * 0.75);
  }

  @Override
  public void end(boolean interrupted) {
    m_drivebase.arcadeDrive(0, 0);
    m_robot.inverted = false;
    m_robot.initTeleop();
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
