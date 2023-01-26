// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;




public class RotatingArm extends SubsystemBase {
    private CANSparkMax xMotor;
    private CANSparkMax yMotor;

  public RotatingArm() {
    xMotor = new CANSparkMax(6, MotorType.kBrushless);
    yMotor = new CANSparkMax(7, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("left stick x", RobotContainer.getLeftStickX());
    SmartDashboard.putNumber("left stick y", RobotContainer.getLeftStickY());
  }

  public void rotateArm(double leftStickXaxis){
    double speed = 0.4;
    double motorDrive = leftStickXaxis*speed;
    xMotor.set(motorDrive);
  }

  public void liftArm(double leftStickYaxis){
    double speed = 0.4;
    double motorDrive = leftStickYaxis*speed;
    yMotor.set(motorDrive);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
