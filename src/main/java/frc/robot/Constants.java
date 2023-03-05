// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class AutoConstants {
    public final XboxController m_driverController = new XboxController(1);
    public final Joystick m_driverJoystick = new Joystick(2);

    // wheel diameter is 6in, 0.1524 in meters
    public static final double wheelDiameterMeters = 0.1524;
    public static final double tankDriveGearRatio = 8.45;
    public static final double encoderFactor = wheelDiameterMeters * Math.PI / tankDriveGearRatio;
  }

  public static final class CanConstants {
    public static final int kLeftFrontMotorPort = 3;
    public static final int kLeftRearMotorPort = 2;
    public static final int kRightFrontMotorPort = 4;
    public static final int kRightRearMotorPort = 5;
    public static final double kRampRate = 0.25;

    public static double maxSpeed = 0.7;
    public static final double maxSpeedIncrement = 0.1;

    public static final String kBaseType = "tank";
  }

  public static final class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final double ProportionalDivisor = 18;
  }

  public static final class VisionConstants {
    public static final String kCameraName = "limelight";
    public static final double kCameraHeightMeters = 0.6096;
    public static final double kTargetHeightMeters = 1.524;
    public static final double kCameraPitchRadians = 0;
  }
}
