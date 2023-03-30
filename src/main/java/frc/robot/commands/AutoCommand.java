// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

public class AutoCommand extends SequentialCommandGroup {
  public AutoCommand(PID m_pid, Tank m_driveBase, Arm m_arm, Claw m_claw) {
    addCommands(new PlaceConeSecondLevelCommand(m_driveBase, m_arm, m_claw), new BalanceCommand(m_pid, m_driveBase));

    //side autonomous
    //addCommands(new PlaceConeSecondLevelCommand(m_driveBase, m_arm, m_claw));

    //middle autonomous
    //addCommands(new PlaceConeSecondLevelCommand(m_driveBase, m_arm, m_claw), new BalanceCommand(m_pid, m_driveBase));


    // new BalanceCommand(m_pid, m_driveBase)
    // new PlaceConeSecondLevelCommand(m_driveBase, m_arm)
  }
}
