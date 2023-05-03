// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.AutoBalence;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Tank;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SideAutoWCone extends SequentialCommandGroup {
  /** Creates a new SideAutoWCone. */
  public SideAutoWCone(AutoBalence m_pid, Tank m_driveBase, Arm m_arm, Claw m_claw) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new PlaceConeSecondLevelCommand(m_driveBase, m_arm, m_claw));
  }
}
