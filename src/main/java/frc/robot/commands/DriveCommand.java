// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.security.Guard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Tank;

public class DriveCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final Tank m_drivebase;




  public DriveCommand(Tank subsystem) {
    m_drivebase = subsystem;
    addRequirements(m_drivebase);
  }

  @Override
  public void initialize() {
    // nothing to do here
  }

  @Override
  public void execute() {
    // double check getMaxSpeed(), might be wrong
    
    Tank.arcadeDrive(
        /* rotation */RobotContainer.getAdjustedTurningStickInput() * Constants.CanConstants.maxSpeed,
        /* speed */RobotContainer.getAdjustedForwardStickInput() * 0.7);

    SmartDashboard.putNumber("maxSpeed", Constants.CanConstants.maxSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    Tank.arcadeDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    // we need to return the value in Constants.isDriveCommandFinished but in a static way while keeping the value non-static in Constants
    private static final boolean x = Constants.isDriveCommandFinished;
    return x;
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
