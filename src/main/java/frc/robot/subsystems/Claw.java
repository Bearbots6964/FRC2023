// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Claw extends SubsystemBase {
  private CANSparkMax clawMotor;

  // gear ratio is 100:1

  public Claw() {
    clawMotor = new CANSparkMax(8, MotorType.kBrushless);
    clawMotor.setIdleMode(IdleMode.kBrake);
    clawMotor.setSmartCurrentLimit(14, 11);
    clawMotor.burnFlash();
  }

  public void periodic() {}

  public void closeClaw() {
    clawMotor.set(0.75);
  }

  public void openClaw() {
    clawMotor.set(-0.5);
  }

  public void stopClaw() {
    clawMotor.set(0);
  }

  public void simulationPeriodic() {}
}
