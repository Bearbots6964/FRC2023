// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Tracking;

import java.util.Map;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
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

  public boolean xIsDone = false;
  public boolean yIsDone = false;

  public double xOutput = 0;
  public double yOutput = 0;

  public double lensToFloor; // in meters
  public double floorToBottomOfCameraFOV; // in meters
  public double cameraAngleOfDepression = 24.85; // in degrees

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
    tclassEntry = targetLayout.add("Class", false).withWidget(BuiltInWidgets.kBooleanBox).withPosition(1, 1)
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

  public double getTX() {
    return NetworkTableInstance.getDefault().getTable(name).getEntry("tx").getDouble(0);

    // triangulate the position of the target in 2d space, relative to the center of
    // the bot (i.e. where the claw is). using the known angle of depression of the
    // camera (d), the distance from the lens to the floor (t), and the angle that
    // the bottom of the target is relative to what the camera can see i.e. the
    // difference between the vrtical field of view and the angle of the bottom of
    // the target (a) can let us calculate z, the distance between the lens and the
    // target, and q, the distance between the floor and the bottom of the target.
    // y = sec(d - a) * t
    // q = sqrt(z^2 - y^2)
    // and then convert all that to a 3d object

    

    

  }

  public double getTY() {
    return NetworkTableInstance.getDefault().getTable(name).getEntry("ty").getDouble(0);
  }

  public double getTA() {
    return NetworkTableInstance.getDefault().getTable(name).getEntry("ta").getDouble(0);
  }

  public double getTClass() {
    return NetworkTableInstance.getDefault().getTable(name).getEntry("tclass").getDouble(0);
  }


  public Translation3d getObject3DSpace() {
    tx = NetworkTableInstance.getDefault().getTable(name).getEntry("tx").getDouble(0);
    double[] tcorners = NetworkTableInstance.getDefault().getTable(name).getEntry("tcornxy").getDoubleArray(new double[0]);
    // get the average x and y coordinates of the bottom two sides of the target
    double cx = (tcorners[0] + tcorners[2]) / 2;
    double cy = (tcorners[1] + tcorners[3]) / 2;

    // if cx is not 0, translate 

    // triangulate the position of the target in 2d space, relative to the center of
    // the bot (i.e. where the claw is). using the known angle of depression of the
    // camera (d), the distance from the lens to the floor (t), and the angle that
    // the bottom of the target is relative to what the camera can see i.e. the
    // difference between the vertical field of view and the angle of the bottom of
    // the target (a) can let us calculate z, the distance between the lens and the
    // target, and q, the distance between the floor and the bottom of the target.
    // y = sec(d - a) * t
    // q = sqrt(z^2 - y^2)
    // and then convert all that to a 3d object

    double y = Math.tan(cameraAngleOfDepression - (ty + cameraAngleOfDepression)) * lensToFloor;
    double q = Math.sqrt(Math.pow(lensToFloor, 2) - Math.pow(y, 2));

    return new Translation3d(tx, y, q);
  }


}
