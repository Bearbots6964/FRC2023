// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.interfaces.CANSparkMax;

public class Turret extends SubsystemBase {
  private CANSparkMax xMotor;

  public static RelativeEncoder xEncoder;

  public Turret() {
    xMotor = new CANSparkMax(6, MotorType.kBrushless);
    xMotor.setIdleMode(IdleMode.kBrake);
    xMotor.setSmartCurrentLimit(5, 10);
    xMotor.burnFlash();
    addChild("Turret Motor", xMotor);
    Shuffleboard.getTab("Motors").add("Turret", xMotor);
  }

  @Override
  public void periodic() {
    // nothing to do here
  }

  public void rotateTurret(double leftStickXaxis) {
    double speed = -1;
    double motorDrive = leftStickXaxis * speed;
    xMotor.set(motorDrive);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
