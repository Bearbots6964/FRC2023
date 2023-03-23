package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PID extends SubsystemBase {
  
  private final double kP = 0.18;
  private final double kI = 0.025;
  private final double kD = 0.3;
  private double P, I, D, errorSum, errorRate, lastTimeStamp, iLimit, lastError;
  public double toleranceDeg;
  public int count;
  public Tank tank;
  public ShuffleboardTab tab;
  public ShuffleboardTab dash;

  public AHRS gyro = Tank.gyro;

  private float initPitch;

  public PIDController pidController;

  public PID() {
    toleranceDeg = 0.5;
    iLimit = 2.0;


    initPitch = gyro.getPitch();
    pidController = new PIDController(P, I, D);
    addChild("PID Controller", pidController);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("pitch angle", gyro.getPitch());
    SmartDashboard.putBoolean("gyro cal", gyro.isCalibrating());
    SmartDashboard.putNumber("yaw angle", gyro.getYaw());
    SmartDashboard.putNumber("pitch offset", getPitchOffset());
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

    pidController.setP(P);
    pidController.setI(I);
    pidController.setD(D);

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
