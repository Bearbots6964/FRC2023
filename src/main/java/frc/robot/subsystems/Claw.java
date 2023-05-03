// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.interfaces.*;

public class Claw extends SubsystemBase {
  public CANSparkMax clawMotor;

  // gear ratio is 100:1

  public Claw() {
    clawMotor = new CANSparkMax(8, MotorType.kBrushless);
    clawMotor.setIdleMode(IdleMode.kBrake);
    clawMotor.setSmartCurrentLimit(20);
    clawMotor.burnFlash();
    addChild("Claw Motor", clawMotor);

    Shuffleboard.getTab("Main").getLayout("Arm System").addNumber("Claw", () -> clawMotor.getAppliedOutput()).withProperties(Map.of("Min", -1, "Max", 1));

  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("rightTrigger", RobotContainer.getClawInValue());
  }

  public void closeClaw() {
    clawMotor.set(0.75);
  }

  public void openClaw() {
    clawMotor.set(-0.75);
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
