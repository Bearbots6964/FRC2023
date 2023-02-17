// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PID;
import frc.robot.subsystems.Tank;

public class AutoCommand extends CommandBase {
  /** Example static factory for an autonomous command. */
  private final Tank drive;

  private final PID pid;
  private double speed;
  private Timer timer = new Timer();

  public AutoCommand(Tank drive1, PID m_pid, double m_speed) {
    drive = drive1;
    pid = m_pid;
    speed = m_speed;
  }

  @Override
  public void initialize() {
    timer.start();
    pid.preparePID();
    if (pid.count >= 3) {
      pid.startPID();
    }
  }

  @Override
  public void execute() {
    pid.PIDDrive();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return timer.get() > 15;
  }
}
