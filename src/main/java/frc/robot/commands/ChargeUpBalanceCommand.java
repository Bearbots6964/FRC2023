// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Mecanum;
import frc.robot.subsystems.Tank;
import frc.robot.subsystems.PID;

/** An example command that uses an example subsystem. */
public class ChargeUpBalanceCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final PID pid;
  private final Tank driveBase;

  boolean isFinished = false;
  boolean inErrorZone = false;
  int count;

  public ChargeUpBalanceCommand(PID m_pid, Tank m_driveBase) {
    pid = m_pid;
    driveBase = m_driveBase;

    addRequirements(pid);
    addRequirements(driveBase);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    driveBase.automate = true;
    while(PID.gyro.getPitch() < 14){
      pid.testMoveForward();
    }
  }

  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
