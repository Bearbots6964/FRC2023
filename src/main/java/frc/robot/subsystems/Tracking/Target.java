// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Tracking;

import java.util.Map;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class Target extends SubsystemBase {
  private static String name = "limelight";
  private double tx;
  private double ty;
  private double ta;
  private double tclass;

  private ShuffleboardTab targetingTab = Shuffleboard.getTab("Targeting");
  private ShuffleboardTab runTab = Shuffleboard.getTab("Target Locked");
  private CvSink camera = CameraServer.getVideo("limelight");
  private ShuffleboardLayout targetLayout;

  private GenericEntry txEntry;
  private GenericEntry tyEntry;
  private GenericEntry taEntry;
  private GenericEntry tclassEntry;
  private GenericEntry targetedEntry;
  private static LimelightHelpers limelightHelpers = new LimelightHelpers();

  /** Creates a new Target. */
  public Target() {
    targetLayout = targetingTab.getLayout("Target", BuiltInLayouts.kGrid).withSize(19, 18).withPosition(0, 0)
        .withProperties(Map.of("Label position", "BOTTOM", "Number of columns", 2, "Number of rows", 2));

    txEntry = targetLayout.add("tx", 0).withPosition(0, 0)
        .withProperties(Map.of("Min", -29.8, "Max", 29.8, "Title", "Target X")).getEntry();
    tyEntry = targetLayout.add("ty", 0).withPosition(1, 0)
        .withProperties(Map.of("Min", -24.85, "Max", 24.85, "Title", "Target Y")).getEntry();
    taEntry = targetLayout.add("ta", 0).withPosition(0, 1)
        .withProperties(Map.of("Min", 0, "Max", 100, "Title", "Target Area")).getEntry();
    tclassEntry = targetLayout.add("tclass", false).withWidget(BuiltInWidgets.kBooleanBox).withPosition(1, 1)
        .withProperties(Map.of("Color when true", "Blue", "Color when false", "#fff4d", "Title", "Target Class"))
        .getEntry();

    targetingTab.addCamera("Camera", "limelight", "http://10.69.64.11:5800").withPosition(18, 1).withSize(20, 14);
    targetedEntry = targetingTab.add("Targeted", false).withWidget(BuiltInWidgets.kBooleanBox).withPosition(18, 0)
        .withProperties(Map.of("Color when true", "Green", "Color when false", "Black", "Title", "Target Acquired?"))
        .getEntry();

    Shuffleboard.selectTab("Targeting");


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    tx = limelightHelpers.getTX(name);
    ty = limelightHelpers.getTY(name);
    ta = limelightHelpers.getTA(name);
    tclass = NetworkTableInstance.getDefault().getTable(name).getEntry("tclass").getDouble(0);

    txEntry.setDouble(tx);
    tyEntry.setDouble(ty);
    taEntry.setDouble(ta);
    tclassEntry.setDouble(tclass);
  }

  public static double getTX() {
    return NetworkTableInstance.getDefault().getTable(name).getEntry("tx").getDouble(0);
  }
  public static double getTY() {
    return NetworkTableInstance.getDefault().getTable(name).getEntry("ty").getDouble(0);
  }
  public double getTA() {
    return NetworkTableInstance.getDefault().getTable(name).getEntry("ta").getDouble(0);
  }
  public double getTClass() {
    return NetworkTableInstance.getDefault().getTable(name).getEntry("tclass").getDouble(0);
  }
}
