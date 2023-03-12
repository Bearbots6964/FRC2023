// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  public CANSparkMax yMotor;
  public double gearRatio = 87;

  public Arm() {
    yMotor = new CANSparkMax(7, MotorType.kBrushless);
    yMotor.setIdleMode(IdleMode.kBrake);
    yMotor.setSmartCurrentLimit(5, 10);
    yMotor.burnFlash();
  }

  @Override
  public void periodic() {
    // moveArm(RobotContainer.getArmControllerLeftStickY());
  }

  public void moveArm(double leftStickYaxis) {
    double speed = 0.8;
    double motorDrive = leftStickYaxis * speed;
    yMotor.set(motorDrive);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
