// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.REVPhysicsSim;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.interfaces.CANSparkMax;

public class Arm extends SubsystemBase {

  public CANSparkMax armMotor;
  public DigitalInput allTheWayDownRear = new DigitalInput(1);
  public double desiredArmAngle;
  public double currentArmAngle;

  public double gearRatio = 87;

  public GenericEntry encoderWidget;

  public REVPhysicsSim physicsSim;

  public Arm() {
    armMotor = new CANSparkMax(7, MotorType.kBrushless);
    armMotor.setIdleMode(IdleMode.kBrake);
    armMotor.setSmartCurrentLimit(30, 40);
    armMotor.burnFlash();
    addChild("Arm Motor", armMotor);

    encoderWidget =
        Shuffleboard.getTab("Motors")
            .add("Arm Encoder", armMotor.getEncoder().getPosition())
            .getEntry();

    Shuffleboard.getTab("Motors").add("Arm", armMotor);

    // add simulation data
    if (RobotBase.isSimulation()) {
      physicsSim = new REVPhysicsSim();
      physicsSim.addSparkMax(armMotor, 5000, 20);
    }
  }

  @Override
  public void periodic() {
    // SmartDashboard.putBoolean("zeroDeg", allTheWayDownRear.get());

    encoderWidget.setDouble(armMotor.getEncoder().getPosition());
  }

  /** uses input double value to set the motor speed of the arm */
  public void moveArm(double value) {
    double speed = 0.8;
    double motorDrive = value * speed;
    armMotor.set(motorDrive);
  }

  public void moveArmToZeroDeg() {}

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
