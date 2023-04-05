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

/**
 * This command balances the robot on the charge station. Default auto command.
 */
public class BalanceCommand extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final PID pidLoop;

  private final Tank driveBase;
  private double initPitch, maxPitch;
  private boolean onRamp;

  /** Create a new {@link BalanceCommand}. */
  public BalanceCommand(PID m_pid, Tank m_driveBase) {
    pidLoop = m_pid;
    driveBase = m_driveBase;
    addRequirements(pidLoop);
    addRequirements(driveBase);
  }

  @Override
  public void initialize() {
    driveBase.leftFront.setIdleMode(IdleMode.kBrake);
    driveBase.leftRear.setIdleMode(IdleMode.kBrake);
    driveBase.rightFront.setIdleMode(IdleMode.kBrake);
    driveBase.rightRear.setIdleMode(IdleMode.kBrake);

    pidLoop.resetPitch();
    initPitch = pidLoop.gyro.getPitch();
    SmartDashboard.putNumber("inital pitch", initPitch);
    maxPitch = 0;
    onRamp = false;
    // start driving forward
    driveBase.setAllMotors(0.5);
  }

  // avoid while loops inside execute
  @Override
  public void execute() {
    double pitchOffset = initPitch - pidLoop.gyro.getPitch();
    SmartDashboard.putNumber("pitch offset", pitchOffset);
    SmartDashboard.putBoolean("onRamp", onRamp);
    SmartDashboard.putNumber("max offset", maxPitch);
    SmartDashboard.putNumber(
        "motor speed", Constants.OperatorConstants.Pconstant * (pitchOffset));
    /*
     * if (pitchOffset > maxPitch) {
     * maxPitch = pitchOffset;
     * }
     */

    // set boolean to true when robot is on ramp to run p loop
    if (!onRamp && pitchOffset >= 16) {
      onRamp = true;
    }

    if (onRamp) {
      // dead zone of tilt
      if (Math.abs(pitchOffset) < 2) {
        driveBase.setAllMotors(0);
      } else {
        // run p loop
        driveBase.setAllMotors(Constants.OperatorConstants.Pconstant * (pitchOffset));
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
    // not used
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
