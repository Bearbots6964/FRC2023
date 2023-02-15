// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

public class Turret extends SubsystemBase {
  private CANSparkMax xMotor;

  public static RelativeEncoder xEncoder;

  public Turret() {
    xMotor = new CANSparkMax(6, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
  }

  public void rotateArm(double leftStickXaxis) {
    double speed = 0.4;
    double motorDrive = leftStickXaxis * speed;
    xMotor.set(motorDrive);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
