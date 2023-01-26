// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MechanumDrive;
import edu.wpi.first.wpilibj.Timer;


public class AutoCommand extends CommandBase {
  /** Example static factory for an autonomous command. */
  private final MechanumDrive driveSubsystem;
  private double speed;
  private Timer timer = new Timer();

  public AutoCommand(MechanumDrive drive, double m_speed) {
    driveSubsystem = drive;
    speed = m_speed;
  }

  @Override
  public void initialize() {
    timer.start();
    driveSubsystem.preparePID();
    if(driveSubsystem.count >= 3){
        driveSubsystem.startPID();
    }
  }

  @Override
  public void execute() {
    driveSubsystem.PIDDrive();  
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return timer.get() > 15;
  }
}
