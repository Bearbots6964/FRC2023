package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Odometry extends SubsystemBase {
  private DifferentialDriveOdometry odometry;
  private AHRS gyro;
  private Tank tank;

  public Odometry(AHRS gyro, Tank tank) {
    this.gyro = gyro;
    this.tank = tank;

    odometry = new DifferentialDriveOdometry(
        gyro.getRotation2d(),
        tank.getLeftDistance(),
        tank.getRightDistance());
  }

  public void periodic() {
    odometry.update(gyro.getRotation2d(), tank.getLeftDistance(), tank.getRightDistance());
    SmartDashboard.putNumber("encoders offset X", odometry.getPoseMeters().getTranslation().getX());
    SmartDashboard.putNumber("encoders offset Y", odometry.getPoseMeters().getTranslation().getY());

  }
}
