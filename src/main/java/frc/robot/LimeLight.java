// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;

import frc.robot.RobotContainer;
import frc.robot.Constants.AutoConstants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

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

  public static double calcHoodAngle(){
    double distance = calcDistance(limelightTrackingY());
    final double const1 = 272.6176;
    final double const2 = 118.6994;
    final double const3 = 31.02895;
    final double exp1 = 27.63411;
    final double exp2 = 0.02933411;

    return const1 - (const2 + const1)/Math.pow(1 + Math.pow((distance/const3), exp1), exp2);
  }


  public static double calcDistance(double ty){
    final double a1 = 32;
    final double h1 = 29.5;
    final double h2 = 104;

    return ((h2-h1) / Math.tan((a1+ty)*Math.PI/180))+20;
  }


  public static double getShootSpeedValue(){//implement vision here later- RR 1/11/2022
    double velocity = limelightTrackingArea() * 3.0 + 3.234324;//change this is a function
    return velocity; 
  }
  

  //unkown calc speed method
   public static double calcSpeed(){
    double distance = calcDistance(limelightTrackingY());
    final double m = 0.00084;
    final double b = 0.502;//hi
    double speed;
    if(limelightTrackTarget()){
      speed = m * distance + b + RobotContainer.distanceError;
     } else{
       speed = 0.5;
    }
    return speed;
  }


  public static boolean limelightTrackTarget() {
    if(Math.abs(limelightTrackingX()) < 2.0){
      return true;
    }
    return false;
  }
}