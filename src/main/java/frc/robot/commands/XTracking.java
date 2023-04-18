// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Tank;
import frc.robot.subsystems.Tracking.Target;
import frc.robot.RobotContainer;
import frc.robot.commands.*;

public class XTracking extends CommandBase {

  private double tx;
  /** Creates a new XTracking. */
  public XTracking(Tank tank, Target target) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(tank, target);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // stop the DriveCommand

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    tx = Target.getTX();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
