// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public class Constants {
  /**
   * public static final class DriveConstants { public static final int kLeftMotor1Port = 0; public
   * static final int kLeftMotor2Port = 1; public static final int kRightMotor1Port = 2; public
   * static final int kRightMotor2Port = 3; }
   */
  public class Drivebase {
    public static final double MaxSpeed = 0.5; // Maximum speed, from 0 to 1.

    public class Left {
      public static final boolean IsInverted = true;
    }

    public class Right {
      public static final boolean IsInverted = false;

      public class OperatorConstants {
        public static final int kDriverControllerPort = 0;
      }

      public class VisionConstants {
        public static final String kCameraName = "limelight";
        public static final double kCameraHeightMeters = 0.6096;
        public static final double kTargetHeightMeters = 1.524;
        public static final double kCameraPitchRadians = 0;

        // Set the camera type.
        // private static final TrackingType tape;
        // public static final TrackingType kTrackingType = tape;
      }
    }
  }
}
