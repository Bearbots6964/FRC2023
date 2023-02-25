// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

public class AutoCommand extends SequentialCommandGroup {
  public AutoCommand(AHRS gyro, Tank m_driveBase) {
    addCommands(new BalanceCommand(gyro, m_driveBase));
  }
}
