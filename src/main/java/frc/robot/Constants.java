// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
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
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class VisionConstants {
    public static final String kCameraName = "limelight";
    public static final double kCameraHeightMeters = 0.6096;
    public static final double kTargetHeightMeters = 1.524;
    public static final double kCameraPitchRadians = 0;

    // Set the camera type.
    private static TrackingType tape;
    public static final TrackingType kTrackingType = tape;
  }

  public class AutoConstants {
    public final XboxController m_driverController = new XboxController(1);
    public final Joystick m_driverJoystick = new Joystick(2);
    
  }
}

    // Constants copied and pasted from 2020 code

