// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Tracking;

import java.util.Map;

import edu.bearbots.BearLib.drivebase.Drivebase;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Tank;

public class GoTo extends SubsystemBase {
  // Mostly just a class for Shuffleboard, displaying the PID values and where the robot is going/where the target is

  private ShuffleboardTab tab = Shuffleboard.getTab("Auto Pickup");
  private ShuffleboardLayout layout = tab.getLayout("Target", BuiltInLayouts.kGrid).withPosition(0, 0).withSize(2, 2);

  private GenericEntry x = layout.add("X", 0).withWidget(BuiltInWidgets.kNumberBar).withPosition(0, 0).withSize(1, 1).getEntry();
  private GenericEntry y = layout.add("Y", 0).withWidget(BuiltInWidgets.kNumberBar).withPosition(1, 0).withSize(1, 1).getEntry();
  private GenericEntry area = layout.add("Area", 0).withWidget(BuiltInWidgets.kNumberBar).withPosition(0, 1).withSize(1, 1).getEntry();
  private GenericEntry type = layout.add("Type", false).withWidget(BuiltInWidgets.kBooleanBox).withPosition(1, 1).withSize(1, 1).withProperties(Map.of("Color when true", "Blue", "Color when false", "#fff4d", "Title", "Target Class")).getEntry();

  

  /** Creates a new GoTo. */
  public GoTo() {
    addChild("Drivebase", Tank.drive);
    tab.add("Drivebase", Tank.drive).withWidget(BuiltInWidgets.kDifferentialDrive).withPosition(2, 0).withSize(2, 2);
    
    tab.addCamera("Limelight", "limelight", "http://10.69.64.11:5800").withPosition(0, 2).withSize(4, 2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
