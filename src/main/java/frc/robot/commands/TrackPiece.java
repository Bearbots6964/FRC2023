// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.AutoPiecePickUp;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Tank;

public class TrackPiece extends CommandBase {
  /** Creates a new TrackPiece. */
  private AutoPiecePickUp m_Vision;

  private Tank m_Tank;
  private Claw m_Claw;

  public TrackPiece(AutoPiecePickUp vision, Tank tank, Claw claw) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_Vision = vision;
    m_Tank = tank;
    m_Claw = claw;
    addRequirements(m_Vision, m_Tank, m_Claw);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // m_Tank.arcadeDrive(0.05, m_Vision.getController().calculate(m_Vision.getMeasurement(),
    // m_Vision.getSetpoint()));
    m_Vision.enable();
    SmartDashboard.putString("a", "pid on");
    m_Claw.closeClaw();

    // set pipeline to 2
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setDouble(2);
  }

  @Override
  public void execute() {
    // m_Tank.setAllMotors(-0.2);
    RobotContainer.rumbleGabeController(1);

    SmartDashboard.putNumber(
        getName(),
        m_Vision.getController().calculate(m_Vision.getMeasurement(), m_Vision.getSetpoint()));
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.rumbleGabeController(0);
    m_Vision.disable();
    m_Tank.arcadeDrive(0, 0);
    SmartDashboard.putString("a", "pid off");
    m_Claw.stopClaw();

    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setDouble(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
