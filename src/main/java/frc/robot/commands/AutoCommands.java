// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

/**
 * This command will run the autonomous routine. In it, you can add commands to
 * be run in sequence.
 */
public class AutoCommands {
  public class SideAutoWCone extends SequentialCommandGroup {

    public SideAutoWCone(AutoBalence m_pid, Tank m_driveBase, Arm m_arm, Claw m_claw) {

      // pick one
      // sideAutoWCone(m_pid,m_driveBase,m_arm,m_claw);
      // sideAutoWCube(m_pid,m_driveBase,m_arm,m_claw);
      // middleAutoWCone(m_pid,m_driveBase,m_arm,m_claw);
      // middleAutoWCube(m_pid,m_driveBase,m_arm,m_claw);

      addCommands(new PlaceConeSecondLevelCommand(m_driveBase, m_arm, m_claw));
    }
  }

  public class SideAutoWCube extends SequentialCommandGroup {

    public SideAutoWCube(AutoBalence m_pid, Tank m_driveBase, Arm m_arm, Claw m_claw) {

      // pick one
      // sideAutoWCone(m_pid,m_driveBase,m_arm,m_claw);
      // sideAutoWCube(m_pid,m_driveBase,m_arm,m_claw);
      // middleAutoWCone(m_pid,m_driveBase,m_arm,m_claw);
      // middleAutoWCube(m_pid,m_driveBase,m_arm,m_claw);

      addCommands(new PlaceCubeSecondLevelCommand(m_driveBase, m_arm, m_claw));
    }
  }

  public class MiddleAutoWCone extends SequentialCommandGroup {

    public MiddleAutoWCone(AutoBalence m_pid, Tank m_driveBase, Arm m_arm, Claw m_claw) {

      // pick one
      // sideAutoWCone(m_pid,m_driveBase,m_arm,m_claw);
      // sideAutoWCube(m_pid,m_driveBase,m_arm,m_claw);
      // middleAutoWCone(m_pid,m_driveBase,m_arm,m_claw);
      // middleAutoWCube(m_pid,m_driveBase,m_arm,m_claw);
      addCommands(new PlaceConeSecondLevelCommand(m_driveBase, m_arm, m_claw),
          new BalanceCommand(m_pid, m_driveBase));
    }

  }

  public class MiddleAutoWCube extends SequentialCommandGroup {

    public MiddleAutoWCube(AutoBalence m_pid, Tank m_driveBase, Arm m_arm, Claw m_claw) {

      // pick one
      // sideAutoWCone(m_pid,m_driveBase,m_arm,m_claw);
      // sideAutoWCube(m_pid,m_driveBase,m_arm,m_claw);
      // middleAutoWCone(m_pid,m_driveBase,m_arm,m_claw);
      // middleAutoWCube(m_pid,m_driveBase,m_arm,m_claw);
      addCommands(new PlaceCubeSecondLevelCommand(m_driveBase, m_arm, m_claw),
          new BalanceCommand(m_pid, m_driveBase));
    }

  }

  public class None extends SequentialCommandGroup {

    public None() {

      // pick one
      // sideAutoWCone(m_pid,m_driveBase,m_arm,m_claw);
      // sideAutoWCube(m_pid,m_driveBase,m_arm,m_claw);
      // middleAutoWCone(m_pid,m_driveBase,m_arm,m_claw);
      // middleAutoWCube(m_pid,m_driveBase,m_arm,m_claw);
      
    }

  }

  public class JustBalance extends SequentialCommandGroup {

    public JustBalance(AutoBalence m_pid, Tank m_driveBase) {

      // pick one
      // sideAutoWCone(m_pid,m_driveBase,m_arm,m_claw);
      // sideAutoWCube(m_pid,m_driveBase,m_arm,m_claw);
      // middleAutoWCone(m_pid,m_driveBase,m_arm,m_claw);
      // middleAutoWCube(m_pid,m_driveBase,m_arm,m_claw);
      addCommands(new BalanceCommand(m_pid, m_driveBase));
    }

  }
}
