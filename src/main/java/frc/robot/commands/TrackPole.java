// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.PoleTracking;
import frc.robot.subsystems.Tank;

public class TrackPole extends CommandBase {
  /** Creates a new TrackPiece. */
  private PoleTracking m_PoleTracking;
  private Tank m_Tank;
  public TrackPole(PoleTracking poleTracking, Tank tank) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_PoleTracking = poleTracking;
    m_Tank = tank;
    addRequirements(m_PoleTracking, m_Tank);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //m_Tank.arcadeDrive(0.05, m_Vision.getController().calculate(m_Vision.getMeasurement(), m_Vision.getSetpoint()));
    m_PoleTracking.enable();
    SmartDashboard.putString("a", "pid on");

    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setDouble(1);

  }

  @Override
  public void execute() {
    //m_Tank.setAllMotors(-0.2);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setDouble(1);
    RobotContainer.rumbleGabeController(1);

    SmartDashboard.putNumber(getName(), m_PoleTracking.getController().calculate(m_PoleTracking.getMeasurement(), m_PoleTracking.getSetpoint()));
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.rumbleGabeController(0);
    m_PoleTracking.disable();
    m_Tank.arcadeDrive(0,0);
    SmartDashboard.putString("a", "pid off");

    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setDouble(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_PoleTracking.atSetpoint();
  }
}
