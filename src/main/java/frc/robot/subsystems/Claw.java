// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Claw extends SubsystemBase {
    private CANSparkMax clawMotor;

  public Claw() {
    clawMotor = new CANSparkMax(8, MotorType.kBrushless);
  }

  public void periodic() {
  }

  public void closeClaw() {
    clawMotor.set(0.2);
  }

  public void openClaw() {
    clawMotor.set(-0.2);
  }

  public void stopClaw() {
    clawMotor.set(0);
  }

  public void simulationPeriodic() {
  }
}
