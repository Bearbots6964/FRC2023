// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

/** Add your docs here. */
public class SetPointCommands {
    public class moveToSetPoint1 extends CommandBase {
        private Arm arm;

        public moveToSetPoint1(Arm arm) {
            this.arm = arm;
            addRequirements(arm);
        }

        @Override
        public void initialize() {
            arm.moveToSetPoint1();
            arm.enable();
        }

        @Override
        public void end(boolean interrupted) {
            arm.disable();
        }
    }
}
