// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

public class AutoCommand extends SequentialCommandGroup {
  public AutoCommand(PID m_pid, Tank m_driveBase, Claw m_claw, Arm m_arm) {
    addCommands(new PlaceConeSecondLevelCommand(m_driveBase, m_arm));

    // new BalanceCommand(m_pid, m_driveBase)
    // new PlaceCubeFirstLevelCommand(m_driveBase, m_claw, m_arm)
    // new PlaceConeSecondLevelCommand(m_driveBase, m_arm)
  }
}
