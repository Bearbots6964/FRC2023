// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Interfaces.CANSparkMax;

public class Arm extends SubsystemBase {

  public CANSparkMax armMotor;
  public DigitalInput allTheWayDownRear = new DigitalInput(1);
  public double desiredArmAngle;
  public double currentArmAngle;

  public double gearRatio = 87;

  public Arm() {
    armMotor = new CANSparkMax(7, MotorType.kBrushless);
    armMotor.setIdleMode(IdleMode.kBrake);
    armMotor.setSmartCurrentLimit(20, 40);
    armMotor.burnFlash();
    addChild("Arm Motor", armMotor);

    Shuffleboard.getTab("Motors").add("Arm", armMotor);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("zeroDeg", allTheWayDownRear.get());
  }

  public void moveArm(double leftStickYaxis) {
    double speed = 0.8;
    double motorDrive = leftStickYaxis * speed;
    armMotor.set(motorDrive);
  }

  public void moveArmToZeroDeg() {
    double speedY = 0.2;
    while (allTheWayDownRear
        .get()) { // the '== true' is implied, because the if statement is looking for the
      // expression to be true. If it is false, it will not run the code inside the if
      // statement, so we don't need to write it.
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
