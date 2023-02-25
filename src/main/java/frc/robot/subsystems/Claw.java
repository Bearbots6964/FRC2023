// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

public class Claw extends SubsystemBase {
  private CANSparkMax clawMotor;
  ShuffleboardTab tab = Shuffleboard.getTab("Claw");
    Map<String, Object> widgetProperties = Map.of("min", 0, "max", 20, "blockIncrement", 1);
  private GenericEntry stallWidget = tab.add("Stall Limit", 10).withWidget(BuiltInWidgets.kNumberSlider).withProperties(widgetProperties).withPosition(0, 0).withSize(2, 1).getEntry();
  private GenericEntry freeWidget = tab.add("Free Limit", 11).withWidget(BuiltInWidgets.kNumberSlider).withProperties(widgetProperties).withPosition(2, 0).withSize(2, 1).getEntry();


  
  
  // gear ratio is 100:1

  public Claw() {
    clawMotor = new CANSparkMax(8, MotorType.kBrushless);
    clawMotor.setIdleMode(IdleMode.kBrake);
    clawMotor.setSmartCurrentLimit(10, 11);
    clawMotor.burnFlash();

    // create widget for setting the claw smart current limit. one slider for stall limit, one for free limit
    
    
  }

  public void periodic() {
    // set the smart current limit based on the values set in the shuffleboard widget
    ShuffleboardTab tab = Shuffleboard.getTab("Claw");
    int stallLimit = (int) stallWidget.getInteger(10);
    int freeLimit = (int) freeWidget.getInteger(11);
    clawMotor.setSmartCurrentLimit(stallLimit, freeLimit);
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

  public void simulationPeriodic() {}
}
