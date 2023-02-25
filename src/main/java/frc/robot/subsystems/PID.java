package frc.robot.subsystems;

import java.util.Map;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// TODO@totbas1 - Have PID require either mecanum OR tank -- update in the initializer for each
// command to the base to correspond to the selected one
// In the meantime, I'll try not to touch this file :-)

public class PID extends SubsystemBase {
  public AHRS gyro;
  private final double kP = 0.18;
  private final double kI = 0.025;
  private final double kD = 0.3;
  private double P, I, D, errorSum, errorRate, lastTimeStamp, iLimit, lastError;
  public double toleranceDeg;
  public int count;
  public Tank tank;

  private float initPitch;

  public PID() {
    toleranceDeg = 0.5;
    iLimit = 2.0;
    gyro = new AHRS(SPI.Port.kMXP);

    initPitch = gyro.getPitch();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("pitch angle", gyro.getPitch());
    SmartDashboard.putBoolean("gyro cal", gyro.isCalibrating());
    SmartDashboard.putNumber("yaw angle", gyro.getYaw());
    SmartDashboard.putNumber("pitch offset", getPitchOffset());
    SmartDashboard.putNumber("roll", gyro.getRoll());
    SmartDashboard.putNumber("compass heading", gyro.getCompassHeading());
    SmartDashboard.putNumber("velocity x", gyro.getVelocityX());
    SmartDashboard.putNumber("velocity y", gyro.getVelocityY());
    SmartDashboard.putNumber("velocity z", gyro.getVelocityZ());
    SmartDashboard.putNumber("accel x", gyro.getWorldLinearAccelX());
    SmartDashboard.putNumber("accel y", gyro.getWorldLinearAccelY());
    SmartDashboard.putNumber("accel z", gyro.getWorldLinearAccelZ());

    // translate everything from meters to inches
    SmartDashboard.putNumber("velocity x in", gyro.getVelocityX() * 39.37);
    SmartDashboard.putNumber("velocity y in", gyro.getVelocityY() * 39.37);
    SmartDashboard.putNumber("velocity z in", gyro.getVelocityZ() * 39.37);
    SmartDashboard.putNumber("accel x in", gyro.getWorldLinearAccelX() * 39.37);
    SmartDashboard.putNumber("accel y in", gyro.getWorldLinearAccelY() * 39.37);
    SmartDashboard.putNumber("accel z in", gyro.getWorldLinearAccelZ() * 39.37);

    /**  create Shuffleboard widgets for the gyro and accelerometer
    Shuffleboard.getTab("Gyro")
        .add("pitch angle", gyro.getPitch())
        .withWidget("Number Slider")
        .withProperties(Map.of("min", -180, "max", 180))
        .getEntry();

    Shuffleboard.getTab("Gyro")
        .add("yaw angle", gyro.getYaw())
        .withWidget("Number Slider")
        .withProperties(Map.of("min", -180, "max", 180))
        .getEntry();

    Shuffleboard.getTab("Gyro")
        .add("roll", gyro.getRoll())
        .withWidget("Number Slider")
        .withProperties(Map.of("min", -180, "max", 180))
        .getEntry();

    Shuffleboard.getTab("Gyro")
        .add("compass heading", gyro.getCompassHeading())
        .withWidget("Number Slider")
        .withProperties(Map.of("min", -180, "max", 180))
        .getEntry();

    Shuffleboard.getTab("Gyro")
        .add("velocity x", gyro.getVelocityX())
        .withWidget("Number Slider")
        .withProperties(Map.of("min", -10, "max", 10))
        .getEntry();

    Shuffleboard.getTab("Gyro")
        .add("velocity y", gyro.getVelocityY())
        .withWidget("Number Slider")
        .withProperties(Map.of("min", -10, "max", 10))
        .getEntry();

    Shuffleboard.getTab("Gyro")
        .add("velocity z", gyro.getVelocityZ())
        .withWidget("Number Slider")
        .withProperties(Map.of("min", -10, "max", 10))
        .getEntry();

    Shuffleboard.getTab("Gyro")
        .add("accel x", gyro.getWorldLinearAccelX())
        .withWidget("Number Slider")
        .withProperties(Map.of("min", -10, "max", 10))
        .getEntry();

    Shuffleboard.getTab("Gyro")
        .add("accel y", gyro.getWorldLinearAccelY())
        .withWidget("Number Slider")
        .withProperties(Map.of("min", -10, "max", 10))
        .getEntry();

    Shuffleboard.getTab("Gyro")
        .add("accel z", gyro.getWorldLinearAccelZ())
        .withWidget("Number Slider")
        .withProperties(Map.of("min", -10, "max", 10))
        .getEntry();
    */
  }

  public void startPID() {
    errorSum = 0;
    lastTimeStamp = Timer.getFPGATimestamp();
    lastError = getPitchOffset();
  }

  public double PIDoutput() {
    float errorDeg = getPitchOffset();

    // integral
    if (Math.abs(errorDeg) < iLimit) {
      errorSum += errorDeg;
    }

    // derivative
    double deltaT = Timer.getFPGATimestamp() - lastTimeStamp;
    errorRate = (errorDeg - lastError) / deltaT;
    lastError = errorDeg;
    lastTimeStamp = Timer.getFPGATimestamp();

    P = kP * errorDeg;
    I = kI * errorSum;
    D = kD * errorRate;

    double outputSpeed = P + I + D;

    return outputSpeed;
  }

  public void resetPitch() {
    initPitch = gyro.getPitch();
  }

  private float getPitchOffset() {
    return initPitch - gyro.getPitch();
  }

  @Override
  public void simulationPeriodic() {}
}
