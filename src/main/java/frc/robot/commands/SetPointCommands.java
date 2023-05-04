// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
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

    // finish when at setpoint
    @Override
    public boolean isFinished() {
      return arm.atSetpoint();
    }
  }

  public class moveToSetPoint2 extends CommandBase {
    private Arm arm;

    public moveToSetPoint2(Arm arm) {
      this.arm = arm;
      addRequirements(arm);
    }

    @Override
    public void initialize() {
      arm.moveToSetPoint2();
      arm.enable();
    }

    @Override
    public void end(boolean interrupted) {
      arm.disable();
    }

    // finish when at setpoint
    @Override
    public boolean isFinished() {
      return arm.atSetpoint();
    }
  }

  public class moveToSetPoint3 extends CommandBase {
    private Arm arm;

    public moveToSetPoint3(Arm arm) {
      this.arm = arm;
      addRequirements(arm);
    }

    @Override
    public void initialize() {
      arm.moveToSetPoint3();
      arm.enable();
    }

    @Override
    public void end(boolean interrupted) {
      arm.disable();
    }

    // finish when at setpoint
    @Override
    public boolean isFinished() {
      return arm.atSetpoint();
    }
  }

  public class moveToSetPoint4 extends CommandBase {
    private Arm arm;

    public moveToSetPoint4(Arm arm) {
      this.arm = arm;
      addRequirements(arm);
    }

    @Override
    public void initialize() {
      arm.moveToSetPoint4();
      arm.enable();
    }

    @Override
    public void end(boolean interrupted) {
      arm.disable();
    }

    // finish when at setpoint
    @Override
    public boolean isFinished() {
      return arm.atSetpoint();
    }
  }

  public class moveToSetPoint5 extends CommandBase {
    private Arm arm;

    public moveToSetPoint5(Arm arm) {
      this.arm = arm;
      addRequirements(arm);
    }

    @Override
    public void initialize() {
      arm.moveToSetPoint5();
      arm.enable();
    }

    @Override
    public void end(boolean interrupted) {
      arm.disable();
    }

    // finish when at setpoint
    @Override
    public boolean isFinished() {
      return arm.atSetpoint();
    }
  }

  public class moveToSetPoint6 extends CommandBase {
    private Arm arm;

    public moveToSetPoint6(Arm arm) {
      this.arm = arm;
      addRequirements(arm);
    }

    @Override
    public void initialize() {
      arm.moveToSetPoint6();
      arm.enable();
    }

    @Override
    public void end(boolean interrupted) {
      arm.disable();
    }

    // finish when at setpoint
    @Override
    public boolean isFinished() {
      return arm.atSetpoint();
    }
  }

  public class CancelSetPointCommands extends InstantCommand {
    private Arm arm;

    public CancelSetPointCommands(Arm arm) {
      this.arm = arm;
      addRequirements(arm);
    }

    @Override
    public void initialize() {
      arm.disable();
      arm.commandsAreCanceled = true;
    }
  }
}
