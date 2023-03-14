// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {

  public CANSparkMax armMotor;
  public DigitalInput allTheWayDownRear = new DigitalInput(1);
  public DigitalInput allTheWayDownFront = new DigitalInput(2);
  public double desiredArmAngle, currentArmAngle;

  public double gearRatio = 87;

  public Arm() {
    armMotor = new CANSparkMax(7, MotorType.kBrushless);
    armMotor.setIdleMode(IdleMode.kBrake);
    armMotor.setSmartCurrentLimit(20, 30);
    armMotor.burnFlash();
  }

  @Override
  public void periodic() {
    // moveArm(RobotContainer.getArmControllerLeftStickY());
  }

  public void moveArm(double leftStickYaxis) {
    double speed = 0.8;
    double motorDrive = leftStickYaxis * speed;
    armMotor.set(motorDrive);
  }

  public void moveArmToZeroDeg() {
    double speedY = 0.2;
    while (allTheWayDownFront.get() == true) {
      armMotor.set(-1 * speedY);
    }
    armMotor.set(0);
    armMotor.getEncoder().setPosition(0);
    currentArmAngle = 0;
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
