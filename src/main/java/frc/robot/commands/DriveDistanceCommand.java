// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Odometry;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Tank;

public class DriveDistanceCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Gyro pid;
  private final Tank tank;
  private final float distance;

  public DriveDistanceCommand(Gyro pid, Tank tank,Odometry odometry, float distance) {
    this.pid = pid;
    this.tank = tank;
    this.odometry = odometry;
    this.distance = distance;
    addRequirements(pid);
    addRequirements(tank);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
