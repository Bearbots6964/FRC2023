// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.PID;
import frc.robot.subsystems.Tank;

/** An example command that uses an example subsystem. */
public class BalanceCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final PID pid;

  private final Tank driveBase;
  private double initPitch, max;
  private boolean onRamp;

  public BalanceCommand(PID m_pid, Tank m_driveBase) {
    pid = m_pid;
    driveBase = m_driveBase;
    addRequirements(pid);
    addRequirements(driveBase);
  }

  @Override
  public void initialize() {
    driveBase.leftFront.setIdleMode(IdleMode.kBrake);
    driveBase.leftRear.setIdleMode(IdleMode.kBrake);
    driveBase.rightFront.setIdleMode(IdleMode.kBrake);
    driveBase.rightRear.setIdleMode(IdleMode.kBrake);

    pid.resetPitch();
    initPitch = pid.gyro.getPitch();
    SmartDashboard.putNumber("init pitch", initPitch);
    max = 0;
    onRamp = false;
  }

  // avoid while loops inside execute
  @Override
  public void execute() {
    double pitchOffset = pid.gyro.getPitch() - initPitch;
    SmartDashboard.putNumber("pitch offset", pitchOffset);

    if (pitchOffset > max) {
      max = pitchOffset;
    }
    SmartDashboard.putNumber("max offset", max);

    if (pitchOffset < 15.7 && !onRamp) {
      driveBase.setAllMotors(0.5);
      SmartDashboard.putBoolean("onRamp", onRamp);

    } else {
      onRamp = true;
      SmartDashboard.putBoolean("onRamp", onRamp);

      if (Math.abs(pitchOffset) < 2) {
        driveBase.setAllMotors(0);
      } else {
        driveBase.setAllMotors(
            0.15 * (pitchOffset / Constants.OperatorConstants.ProportionalDivisor));

        SmartDashboard.putNumber(
            "motor speed", 0.15 * (pitchOffset / Constants.OperatorConstants.ProportionalDivisor));
      }
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
