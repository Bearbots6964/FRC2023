// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.PID;
import frc.robot.subsystems.Tank;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

public class AutoCommand extends CommandBase {
  /** Example static factory for an autonomous command. */
  private final Tank drive;

  private final PID pid;
  private double speed;
  private final Turret turret;
  private final Arm arm;
  private final Claw claw;
  private final Vision vision;
  private Timer timer = new Timer();

  public AutoCommand(
      Tank drive1, PID m_pid, Turret m_turret, Arm m_arm, Claw m_claw, Vision m_vision) {
    drive = drive1;
    pid = m_pid;
    turret = m_turret;
    arm = m_arm;
    claw = m_claw;
    vision = m_vision;
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
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return timer.get() > 15;
  }
}
