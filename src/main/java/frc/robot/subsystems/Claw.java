// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Interfaces.*;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.Map;

public class Claw extends SubsystemBase {
  private CANSparkMax clawMotor;
  
  
  private GenericEntry stallWidget;
  private GenericEntry freeWidget;
  private ShuffleboardLayout layout;

  // gear ratio is 100:1

  public Claw() {
    clawMotor = new CANSparkMax(8, MotorType.kBrushless);
    clawMotor.setIdleMode(IdleMode.kBrake);
    clawMotor.setSmartCurrentLimit(14, 11);
    clawMotor.burnFlash();

    Shuffleboard.getTab("Motors").add("Claw", clawMotor);

    layout = Shuffleboard.getTab("Config").getLayout("Claw", BuiltInLayouts.kList);

    stallWidget =
        layout
            .add("Stall Limit", 10)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 20))
            .getEntry();
    freeWidget =
        layout
            .add("Free Limit", 11)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 20))
            .getEntry();
  }

  @Override
  public void periodic() {
    double freeLimit;
    double stallLimit;
    stallLimit = stallWidget.getDouble(10);
    freeLimit = freeWidget.getDouble(11);

    clawMotor.setSmartCurrentLimit((int) stallLimit, (int) freeLimit);
  }

  public void closeClaw() {
    clawMotor.set(0.75);
  }

  public void openClaw() {
    clawMotor.set(-0.5);
  }

  public void stopClaw() {
    clawMotor.set(0);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    // ...but we don't have time to code it :-/
  }
}
