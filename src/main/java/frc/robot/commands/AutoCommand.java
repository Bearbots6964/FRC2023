// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Mecanum;
import frc.robot.subsystems.PIDmecanum;

public class AutoCommand extends CommandBase {
  /** Example static factory for an autonomous command. */
  private final Mecanum driveSubsystem1;

  private final PIDmecanum driveSubsystem2;
  private double speed;
  private Timer timer = new Timer();

  public AutoCommand(Mecanum drive1, PIDmecanum drive2, double m_speed) {
    driveSubsystem1 = drive1;
    driveSubsystem2 = drive2;
    speed = m_speed;
  }

  @Override
  public void initialize() {
    timer.start();
    driveSubsystem2.preparePID();
    if (driveSubsystem2.count >= 3) {
      driveSubsystem2.startPID();
    }
  }

  @Override
  public void execute() {
    driveSubsystem2.PIDDrive();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return timer.get() > 15;
  }
}
