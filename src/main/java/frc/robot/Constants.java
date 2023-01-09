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
    

        public static final double kP = 1/3600;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kTargetAngle = 0;
        public static final double kDistance = 100;
        public static final double kSpeed = 0.7;
        // Motor Constants
        public static final int intakePort = 5;
        public static final int shooter_right=7;
       // public static final int shooter_left=8;
        public static final int shootRotate = 3;
        public static final int shootAngleRotate = 15;
        public static final int climbing = 8; 
        public static final int index2 = 10;
        public static final int index3 = 13;
        public static final int elevator = 14;
        public static final int rearLeftDrive = 2;
        public static final int frontLeftDrive = 1;
        public static final int rearRightDrive = 4; 
        public static final int frontRightDrive = 6;
        //public static final int ballLimitSwitchPort = 0; //change
        public static final int hoodLimitSwitchPort = 0;

        public static final int leftRotateTurretLimitSwitch = 1;
        public static final int rightRotateTurretLimitSwitch = 2;

        /*
        private SpeedController m_rearLeft = new PWMVictorSPX(0);
        private SpeedController m_frontLeft = new PWMVictorSPX(1);
        private SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);
      
        private SpeedController m_rearRight = new PWMVictorSPX(2);
        private SpeedController m_frontRight = new PWMVictorSPX(3);
        private SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
      
        private DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);*/


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