// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // Constants copied and pasted from 2020 code
    public static final class AutoConstants{
        public static final XboxController m_driverController = new XboxController(1);
        public static final Joystick m_driverJoystick = new Joystick(2);

        


        //BEN DRIVE CONSTANTS
        public static final double ksVolts=5;
        public static final double kvVoltSecondsPerMeter=5;
        public static final double kaVoltSecondsSquaredPerMeter=5;
    
        public static final double kPDriveVel=5;

        public static final double kTrackwidthMeters = 0.55;
        public static final DifferentialDriveKinematics kDriveKinematics =
        new DifferentialDriveKinematics(kTrackwidthMeters);

        public static final double kMaxSpeedMetersPerSecond=5 ;
        public static final double kMaxAccelerationMetersPerSecondSquared=5;

        // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

        public static final int[] kLeftEncoderPorts= {0,1};
        public static final int[] kRightEncoderPorts= {2,3};
        public static final boolean kLeftEncoderReversed = false;
        public static final boolean kRightEncoderReversed = false;

        public static final double kEncoderDistancePerPulse=5;
        
    }
}