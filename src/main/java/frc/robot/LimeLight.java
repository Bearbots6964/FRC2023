// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public class LimeLight{

  public LimeLight() {}

  public static double limelightTrackingX() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(3);
  }

  public static double limelightTrackingY() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(3);
  }

  public static double limelightTrackingArea() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
  }

  public static double calcDistance(double ty){
    final double a1 = 32;
    final double h1 = 29.5;
    final double h2 = 104;

    return ((h2-h1) / Math.tan((a1+ty)*Math.PI/180))+20;
  }

  public static boolean limelightTrackTarget() {
    if(Math.abs(limelightTrackingX()) < 2.0){
      return true;
    }
    return false;
  }
}