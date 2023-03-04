// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Tank;

/** An example command that uses an example subsystem. */
public class BalanceCommand extends CommandBase {
  private final AHRS gyro;
  private final Tank driveBase;
  private final PIDController pitchPIDController;
  private final double goalPitch = 0;
  private double initPitch;
  private boolean onRamp;

  public BalanceCommand(AHRS gyro, Tank m_driveBase) {
    this.gyro = gyro;
    driveBase = m_driveBase;

    addRequirements(driveBase);

    pitchPIDController = new PIDController(
        Constants.AutoConstants.PitchPID.proportionConstant,
        Constants.AutoConstants.PitchPID.integralConstant,
        Constants.AutoConstants.PitchPID.derivativeConstant
    );

    pitchPIDController.setTolerance(
        Constants.AutoConstants.PitchPID.positionToleranceDeg,
        Constants.AutoConstants.PitchPID.velocityToleranceDegPerSec
    );

    // pitchPIDController.setIntegratorRange(
    //     Constants.AutoConstants.PitchPID.integratorMinDeg,
    //     Constants.AutoConstants.PitchPID.integratorMaxDeg
    // );

    //pitchPIDController.setSetpoint(goalPitch);
  }

  @Override
  public void initialize() {
    driveBase.leftFront.setIdleMode(IdleMode.kBrake);
    driveBase.leftRear.setIdleMode(IdleMode.kBrake);
    driveBase.rightFront.setIdleMode(IdleMode.kBrake);
    driveBase.rightRear.setIdleMode(IdleMode.kBrake); 
    
    pitchPIDController.reset();

    initPitch = gyro.getPitch();
    SmartDashboard.putNumber("init pitch", initPitch);
    onRamp = false;
  }

  // avoid while loops inside execute
  @Override
  public void execute() {
    double pitchOffset = initPitch - gyro.getPitch();

    SmartDashboard.putNumber("pitch offset", pitchOffset);
    SmartDashboard.putBoolean("onRamp", onRamp);

    if (pitchOffset < 16 && !onRamp) {
      driveBase.setAllMotors(0.35);
    } else {
      onRamp = true;

      if (pitchOffset < Math.abs(3.5)) {
        driveBase.setAllMotors(0);
      } else {
        double PIDvalue = pitchPIDController.calculate(pitchOffset);
        SmartDashboard.putNumber("pid", PIDvalue);
        driveBase.setAllMotors(-PIDvalue);
      }

    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
