// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RobotDrive;


public class AutoCommand extends CommandBase {
  /** Example static factory for an autonomous command. */
  private final RobotDrive drive_subsystem;
  private double speed;

  public AutoCommand(RobotDrive drive, double m_speed) {
    drive_subsystem = drive;
    speed = m_speed;
    //throw new UnsupportedOperationException("This is a utility class!");
  }
}
