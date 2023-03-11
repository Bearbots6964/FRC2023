// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.shuffleboard.*;

public class Arm extends SubsystemBase {
  public CANSparkMax yMotor;

  private GenericEntry freeCurrentWidget;
  private GenericEntry stallCurrentWidget;

  private double speed;
  private GenericEntry speedWidget;

  private ShuffleboardLayout layout;

  private int gearRatio = 87;

  public Arm() {
    yMotor = new CANSparkMax(7, MotorType.kBrushless);
    yMotor.setIdleMode(IdleMode.kBrake);
    yMotor.setSmartCurrentLimit(5, 10);
    yMotor.burnFlash();

    // create a list layout
    layout = Shuffleboard.getTab("Arm").getLayout("Arm", BuiltInLayouts.kList);

    // Create a Shuffleboard widget for the free current limit
    freeCurrentWidget = layout
      .add("Idle Current", 5)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 40))
      .getEntry();

    // Create a Shuffleboard widget for the stall current limit
    stallCurrentWidget = layout
      .add("Stall Current", 10)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 40))
      .getEntry();

    // Create a Shuffleboard widget for the speed
    speedWidget = layout
      .add("Speed Limit", 0.8)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1))
      .getEntry();


  }

  @Override
  public void periodic() {
    int freeCurrent;
    int stallCurrent;
    // This method will be called once per scheduler run
    freeCurrent = (int) freeCurrentWidget.getDouble(5);
    stallCurrent = (int) stallCurrentWidget.getDouble(10);
    speed = speedWidget.getDouble(0.8);

    yMotor.setSmartCurrentLimit(freeCurrent, stallCurrent);
  }

  public void moveArm(double leftStickYaxis) {
    double motorDrive = leftStickYaxis * speed;
    yMotor.set(motorDrive);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
