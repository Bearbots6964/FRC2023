package frc.robot.constants;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class AutoConstants {
   
    public final XboxController m_driverController = new XboxController(1);
    public final Joystick m_driverJoystick = new Joystick(2);

    // BEN DRIVE CONSTANTS
    public static final double ksVolts = 5;
    public static final double kvVoltSecondsPerMeter = 5;
    public static final double kaVoltSecondsSquaredPerMeter = 5;

    public static final double kPDriveVel = 5;

    public static final double kTrackwidthMeters = 0.55;
    public final DifferentialDriveKinematics kDriveKinematics =
        new DifferentialDriveKinematics(kTrackwidthMeters);

    public static final double kMaxSpeedMetersPerSecond = 5;
    public static final double kMaxAccelerationMetersPerSecondSquared = 5;

    // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;

    public final int[] kLeftEncoderPorts = {0, 1};
    public final int[] kRightEncoderPorts = {2, 3};
    public static final boolean kLeftEncoderReversed = false;
    public static final boolean kRightEncoderReversed = false;

    public static final double kEncoderDistancePerPulse = 5;
    
}
