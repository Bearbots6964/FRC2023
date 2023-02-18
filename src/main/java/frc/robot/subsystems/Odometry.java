// This file will be used to configure odometry and kinematics.
package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Odometry extends SubsystemBase {
  // put some stuff here
  private final AHRS NavX = PID.gyro;
  private final DifferentialDriveKinematics kinematics =
      new DifferentialDriveKinematics(
          Units.inchesToMeters(26.0)); // this is the distance between the tracks

  // Initialize the wheelSpeeds and linear/angularVelocity variables
  private DifferentialDriveWheelSpeeds wheelSpeeds;
  private ChassisSpeeds chassisSpeeds;
  private double linearVelocity;
  private double angularVelocity;
  private DifferentialDriveOdometry odometry;

  // Create methods to fetch the information

  // TODO@TheGamer1002 - Add periodic methods

  /**
   * Returns the chassis speeds given a kinematics instance and wheel speeds.
   *
   * @param kinematics Distance between the tracks as a {@link
   *     edu.wpi.first.math.kinematics.DifferentialDriveKinematics} object.
   * @param wheelSpeeds The speed of the left and right wheels, as a {@link
   *     edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds} object.
   * @return The chassis speed of the robot.
   */
  public ChassisSpeeds getChassisSpeeds(
      DifferentialDriveKinematics kinematics, DifferentialDriveWheelSpeeds wheelSpeeds) {
    return kinematics.toChassisSpeeds(wheelSpeeds);
  }

  /**
   * Returns the linear velocity given chassis speeds.
   *
   * @param chassisSpeeds The speed of the chassis as a {@link
   *     edu.wpi.first.math.kinematics.ChassisSpeeds} object.
   * @return The linear velocity of the robot.
   */
  public double getLinearVelocity(ChassisSpeeds chassisSpeeds) {
    return chassisSpeeds.vxMetersPerSecond;
  }

  /**
   * Returns the angular velocity given chassis speeds.
   *
   * @param chassisSpeeds The speed of the chassis as a {@link
   *     edu.wpi.first.math.kinematics.ChassisSpeeds} object.
   * @return The angular velocity of the robot.
   */
  public double getAngularVelocity(ChassisSpeeds chassisSpeeds) {
    return chassisSpeeds.omegaRadiansPerSecond;
  }

  /**
   * Returns the angle of the gyroscope as a Rotation2d object.
   *
   * @return The angle of the gyroscope as a {@link edu.wpi.first.math.geometry.Rotation2d} object.
   */
  public Rotation2d getRotation2d() {
    return NavX.getRotation2d();
  }

  /**
   * Returns the differential drive odometry object.
   *
   * @return The differential drive odometry object.
   * @param rotation The angle of the gyroscope as a {@link edu.wpi.first.math.geometry.Rotation2d}
   *     object.
   * @param leftDistance The distance the left encoder has traveled.
   * @param rightDistance The distance the right encoder has traveled.
   * @param pose2d The pose of the robot as a {@link edu.wpi.first.math.geometry.Pose2d} object.
   */
  public DifferentialDriveOdometry getOdometry(
      Rotation2d rotation, double leftDistance, double rightDistance, Pose2d pose2d) {
    odometry = new DifferentialDriveOdometry(rotation, leftDistance, rightDistance, pose2d);
    return odometry;
  }
}
