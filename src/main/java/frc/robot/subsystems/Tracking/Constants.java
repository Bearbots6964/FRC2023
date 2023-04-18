// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Tracking;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
/**
 * Class for the tracking constants.
 */
public class Constants extends SubsystemBase {

  public static class xConstants {
    public static double kP = 0.0001;
    public static double kI = 0;
    public static double kD = 0;
    public static double kIz = 0;
    public static double kFF = 0;
    public static double kMaxOutput = 1;
    public static double kMinOutput = -1;
    public static double kSetpoint = 0;

    public static double lowerBound;
    public static double upperBound;

    /**
     * @return the lowerBound
     */
    public static double getLowerBound() {
      return lowerBound;
    }
    /**
     * @param lowerBound the lowerBound to set
     */
    public static void setLowerBound(double lowerBound) {
      xConstants.lowerBound = lowerBound;
    }
    /**
     * @return the upperBound
     */
    public static double getUpperBound() {
      return upperBound;
    }
    /**
     * @param upperBound the upperBound to set
     */
    public static void setUpperBound(double upperBound) {
      xConstants.upperBound = upperBound;
    }
    /**
     * @return the kP
     */
    public static double getkP() {
      return kP;
    }
    /**
     * @param kP the kP to set
     */
    public static void setkP(double kP) {
      xConstants.kP = kP;
    }
    /**
     * @return the kI
     */
    public static double getkI() {
      return kI;
    }
    /**
     * @param kI the kI to set
     */
    public void setkI(double kI) {
      this.kI = kI;
    }
    /**
     * @return the kD
     */
    public static double getkD() {
      return kD;
    }
    /**
     * @param kD the kD to set
     */
    public void setkD(double kD) {
      this.kD = kD;
    }
    /**
     * @return the kIz
     */
    public double getkIz() {
      return kIz;
    }
    /**
     * @param kIz the kIz to set
     */
    public void setkIz(double kIz) {
      this.kIz = kIz;
    }
    /**
     * @return the kFF
     */
    public double getkFF() {
      return kFF;
    }
    /**
     * @param kFF the kFF to set
     */
    public void setkFF(double kFF) {
      this.kFF = kFF;
    }
    /**
     * @return the kMaxOutput
     */
    public double getkMaxOutput() {
      return kMaxOutput;
    }
    /**
     * @param kMaxOutput the kMaxOutput to set
     */
    public void setkMaxOutput(double kMaxOutput) {
      this.kMaxOutput = kMaxOutput;
    }
    /**
     * @return the kMinOutput
     */
    public double getkMinOutput() {
      return kMinOutput;
    }
    /**
     * @param kMinOutput the kMinOutput to set
     */
    public void setkMinOutput(double kMinOutput) {
      this.kMinOutput = kMinOutput;
    }
    /**
     * @return the kSetpoint
     */
    public static double getkSetpoint() {
      return kSetpoint;
    }
    /**
     * @param kSetpoint the kSetpoint to set
     */
    public void setkSetpoint(double kSetpoint) {
      this.kSetpoint = kSetpoint;
    }
  }

  public static class yConstants {
    public static double kP = 0.0001;
    public static double kI = 0;
    public static double kD = 0;
    public double kIz = 0;
    public double kFF = 0;
    public double kMaxOutput = 1;
    public double kMinOutput = -1;
    public static double kSetpoint = 0;

    public static double lowerBound = 0;
    public static double upperBound = 0;
    /**
     * @return the lowerBound
     */
    public static double getLowerBound() {
      return lowerBound;
    }
    /**
     * @param lowerBound the lowerBound to set
     */
    public static void setLowerBound(double lowerBound) {
      yConstants.lowerBound = lowerBound;
    }
    /**
     * @return the upperBound
     */
    public static double getUpperBound() {
      return upperBound;
    }
    /**
     * @param upperBound the upperBound to set
     */
    public static void setUpperBound(double upperBound) {
      yConstants.upperBound = upperBound;
    }
    /**
     * @return the kP
     */
    public static double getkP() {
      return kP;
    }
    /**
     * @param kP the kP to set
     */
    public void setkP(double kP) {
      this.kP = kP;
    }
    /**
     * @return the kI
     */
    public static double getkI() {
      return kI;
    }
    /**
     * @param kI the kI to set
     */
    public void setkI(double kI) {
      this.kI = kI;
    }
    /**
     * @return the kD
     */
    public static double getkD() {
      return kD;
    }
    /**
     * @param kD the kD to set
     */
    public void setkD(double kD) {
      this.kD = kD;
    }
    /**
     * @return the kIz
     */
    public double getkIz() {
      return kIz;
    }
    /**
     * @param kIz the kIz to set
     */
    public void setkIz(double kIz) {
      this.kIz = kIz;
    }
    /**
     * @return the kFF
     */
    public double getkFF() {
      return kFF;
    }
    /**
     * @param kFF the kFF to set
     */
    public void setkFF(double kFF) {
      this.kFF = kFF;
    }
    /**
     * @return the kMaxOutput
     */
    public double getkMaxOutput() {
      return kMaxOutput;
    }
    /**
     * @param kMaxOutput the kMaxOutput to set
     */
    public void setkMaxOutput(double kMaxOutput) {
      this.kMaxOutput = kMaxOutput;
    }
    /**
     * @return the kMinOutput
     */
    public double getkMinOutput() {
      return kMinOutput;
    }
    /**
     * @param kMinOutput the kMinOutput to set
     */
    public void setkMinOutput(double kMinOutput) {
      this.kMinOutput = kMinOutput;
    }
    /**
     * @return the kSetpoint
     */
    public static double getkSetpoint() {
      return kSetpoint;
    }
    /**
     * @param kSetpoint the kSetpoint to set
     */
    public void setkSetpoint(double kSetpoint) {
      this.kSetpoint = kSetpoint;
    }
  }
  /** Creates a new X. */
  public Constants() {
    
  }

  @Override
  public void periodic() {
    // Use the output here
  }


}
