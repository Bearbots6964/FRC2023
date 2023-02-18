// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Turret;

public class ArmToThirdLevelCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Turret xSubsystem;

  private final Arm ySubsystem;

  public ArmToThirdLevelCommand(Turret xSubsystem1, Arm ySubsystem1) {
    xSubsystem = xSubsystem1;
    ySubsystem = ySubsystem1;
    addRequirements(xSubsystem);
    addRequirements(ySubsystem);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    ySubsystem.liftArmToNode(3);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
