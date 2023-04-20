// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Tank;

public class PickUpPieceCommand extends CommandBase {
  /** Creates a new PickUpPieceCommand. */
  private Claw m_Claw;
  private Tank m_Tank;
  public PickUpPieceCommand(Claw claw, Tank tank) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_Claw = claw;
    m_Tank = tank;
    addRequirements(claw, tank);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // This is the big one. First, make sure that the PID loop is finished
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Claw.clawMotor.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_Claw.clawMotor.getEncoder().getVelocity() <= 0.2;
  }
}
