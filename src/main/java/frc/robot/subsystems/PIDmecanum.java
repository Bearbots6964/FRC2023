// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PIDmecanum extends SubsystemBase {
  public static AHRS gyro;
  private static Timer timer;
  private static double kP, kI, kD, P, I, D, errorSum, errorRate, lastTimeStamp, iLimit, lastError;
  public double tolerance;
  public static double error, limitError;
  private static boolean automate;
  public int count;

  public PIDmecanum() {
    timer = new Timer();
    automate = false;
    kP = 0.18;
    kI = 0.025;
    kD = 0.3;
    tolerance = 0.5;
    iLimit = 2.0;
    gyro = new AHRS(SPI.Port.kMXP);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("pitch angle", gyro.getPitch());
    error = gyro.getPitch();
    if (error > 14.1 && error < 15.1) {
      limitError = error;
    }
  }

  public void preparePID() {
    if (!(error < limitError)) {
      Mecanum.m_left.set(0.2);
      Mecanum.m_right.set(0.2);
    }
  }

  public void startPID() {
    switchToAuto();
    errorSum = 0;
    lastError = 0;
    timer.start();
    lastTimeStamp = timer.getFPGATimestamp();
    lastError = gyro.getPitch();
  }

  public static double PID() {
    // integral
    if (Math.abs(error) < iLimit) {
      errorSum += error;
    }

    // derivative
    double deltaT = timer.getFPGATimestamp() - lastTimeStamp;
    errorRate = (error - lastError) / deltaT;
    lastError = error;
    lastTimeStamp = timer.getFPGATimestamp();

    P = kP * error;
    I = kI * errorSum;
    D = kD * errorRate;

    double outputSpeed = P + I + D;

    return outputSpeed;
  }

  public void PIDDrive() {
    if (error > tolerance) {
      Mecanum.m_left.set(PIDmecanum.PID());
      Mecanum.m_right.set(PIDmecanum.PID());
    } else {
      Mecanum.m_left.set(0);
      Mecanum.m_right.set(0);
    }
  }

  public void switchToAuto() {
    automate = true;
  }

  public void switchToJoystick() {
    automate = false;
  }

  @Override
  public void simulationPeriodic() {}
}
