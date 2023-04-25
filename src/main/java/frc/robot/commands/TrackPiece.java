// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Tank;
import frc.robot.subsystems.Vision;

public class TrackPiece extends CommandBase {
  /** Creates a new TrackPiece. */
  private Vision m_Vision;
  private Tank m_Tank;
  public TrackPiece(Vision vision, Tank tank) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_Vision = vision;
    m_Tank = tank;
    addRequirements(m_Vision, m_Tank);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Tank.arcadeDrive(0.05, m_Vision.getController().calculate(m_Vision.getMeasurement(), m_Vision.getSetpoint()));
    SmartDashboard.putString("a", "pid on");
  }

  @Override
  public void execute() {
    SmartDashboard.putNumber(getName(), m_Vision.getController().calculate(m_Vision.getMeasurement(), m_Vision.getSetpoint()));
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Vision.disable();
    m_Tank.arcadeDrive(0,0);
    SmartDashboard.putString("a", "pid off");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_Vision.atSetpoint();
  }
}
