// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

public class PoleTracking extends PIDSubsystem {
  /** Creates a new Vision. */
  private static double kP = 0.1;

  private static double kI = 0;
  private static double kD = 0;

  public PIDController pidController;
  public Tank m_tank;

  public PoleTracking(Tank tank) {
    super(
        // The PIDController used by the subsystem
        new PIDController(kP, kI, kD));

    addChild(getName(), m_controller);
    m_tank = tank;
    m_controller.setSetpoint(0);
    m_controller.setTolerance(1);
    m_controller.setIntegratorRange(-0.43, 0.43);

    Shuffleboard.getTab(getName()).add(m_controller);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setDouble(1);

    if (0.50 < output) {
      output = 0.50;
    } else if (-0.50 > output) {
      output = -0.50;
    } else if (0.1 < output && output < 0.40) {
      output = 0.40;
    } else if (-0.40 < output && output < -0.1) {
      output = -0.40;
    }

    m_tank.arcadeDrive(0, -output * 1);
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
  }

  public boolean atSetpoint() {
    return m_controller.atSetpoint();
  }

  @Override
  public void setSetpoint(double setpoint) {
    m_controller.setSetpoint(setpoint);
  }

  public void setPID(double p, double i, double d) {
    m_controller.setPID(p, i, d);
  }

  public void setTolerance(double tolerance) {
    m_controller.setTolerance(tolerance);
  }

  @Override
  public void periodic() {
    super.periodic();
    SmartDashboard.putNumber(
        "calculated turning output from vision (pole)",
        m_controller.calculate(getMeasurement(), m_controller.getSetpoint()));
  }
}
