package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CanConstants;
import frc.robot.RobotContainer;

public class Tank extends SubsystemBase {
  public CANSparkMax leftFront;
  public CANSparkMax leftRear;
  public CANSparkMax rightFront;
  public CANSparkMax rightRear;

  public MotorControllerGroup left;
  public MotorControllerGroup right;
  public MotorControllerGroup all;

  public DifferentialDrive drive;

  public boolean brakeMode;

  /** */
  public Tank() {
    if (CanConstants.kBaseType == "tank") {
      leftFront = new CANSparkMax(Constants.CanConstants.kLeftFrontMotorPort, MotorType.kBrushless);
      leftFront.restoreFactoryDefaults();
      leftFront.setInverted(true);
      leftFront.setIdleMode(IdleMode.kBrake);
      leftFront.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
      leftFront.burnFlash();

      leftRear = new CANSparkMax(Constants.CanConstants.kLeftRearMotorPort, MotorType.kBrushless);
      leftRear.restoreFactoryDefaults();
      leftRear.setInverted(true);
      leftRear.setIdleMode(IdleMode.kBrake);
      leftRear.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
      leftRear.burnFlash();

      left = new MotorControllerGroup(leftFront, leftRear);
      addChild("left", left);

      rightFront = new CANSparkMax(Constants.CanConstants.kRightFrontMotorPort, MotorType.kBrushless);
      rightFront.restoreFactoryDefaults();
      rightFront.setInverted(false);
      rightFront.setIdleMode(IdleMode.kBrake);
      rightFront.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
      rightFront.burnFlash();

      rightRear = new CANSparkMax(Constants.CanConstants.kRightRearMotorPort, MotorType.kBrushless);
      rightRear.restoreFactoryDefaults();
      rightRear.setInverted(false);
      rightRear.setIdleMode(IdleMode.kBrake);
      rightRear.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
      rightRear.burnFlash();

      right = new MotorControllerGroup(rightFront, rightRear);
      addChild("right", right);

      all = new MotorControllerGroup(leftFront, leftRear, rightFront, rightRear);
      addChild("All", all);

      drive = new DifferentialDrive(left, right);
      addChild("Drive", drive);
      drive.setSafetyEnabled(false);
      drive.setExpiration(0.1);
      
      drive.setMaxOutput(1.0);

      brakeMode = false;
      SmartDashboard.putBoolean("brakeMode", brakeMode);
    }
  }

  @Override
  public void periodic() {

    SmartDashboard.putNumber("leftStickY", RobotContainer.getLeftStickY());
    SmartDashboard.putNumber("rightStickX", RobotContainer.getRightStickX());

    SmartDashboard.putNumber("leftFront", leftFront.get());
    SmartDashboard.putNumber("leftRear", leftRear.get());
    SmartDashboard.putNumber("rightFront", rightFront.get());
    SmartDashboard.putNumber("rightRear", rightRear.get());

    SmartDashboard.putNumber("left", left.get());
    SmartDashboard.putNumber("right", right.get());

    SmartDashboard.putNumber("maxSpeed", Constants.CanConstants.maxSpeed);


  }

  /**
   * Drives the robot using arcade drive.
   *
   * @param speed The forward/backward speed.
   * @param rotation The rotation speed.
   */
  public void arcadeDrive(double speed, double rotation) {
    try {
      drive.arcadeDrive(-speed * Math.pow(Math.abs(speed), 0.5), rotation * Math.pow(Math.abs(rotation), 0.5));
    } catch (Exception e) {
      throw e;
    }
  }

  public void increaseMaxSpeed() {
    if (Constants.CanConstants.maxSpeed >= 1) {
      Constants.CanConstants.maxSpeed = 1;
    } else {
      Constants.CanConstants.maxSpeed += Constants.CanConstants.maxSpeedIncrement;
    }
  }

  public void decreaseMaxSpeed() {
    if (Constants.CanConstants.maxSpeed <= 0) {
      Constants.CanConstants.maxSpeed = 0;
    } else {
      Constants.CanConstants.maxSpeed -= Constants.CanConstants.maxSpeedIncrement;
    }
  }

  public void switchIdleMode() {
    if (brakeMode == true) {
      leftFront.setIdleMode(IdleMode.kCoast);
      leftRear.setIdleMode(IdleMode.kCoast);
      rightFront.setIdleMode(IdleMode.kCoast);
      rightRear.setIdleMode(IdleMode.kCoast);
    }

    if (brakeMode == false) {
      leftFront.setIdleMode(IdleMode.kBrake);
      leftRear.setIdleMode(IdleMode.kBrake);
      rightFront.setIdleMode(IdleMode.kBrake);
      rightRear.setIdleMode(IdleMode.kBrake);
    }

    brakeMode = !brakeMode;

    SmartDashboard.putBoolean("brakekMode", brakeMode);
  }

  @Override
  public void simulationPeriodic() {
  }

  /** Change the ramp rate of the motor controllers. */
  public void setRampRate(double rampRate) {
    try {
      leftFront.setOpenLoopRampRate(rampRate);
      leftRear.setOpenLoopRampRate(rampRate);
      rightFront.setOpenLoopRampRate(rampRate);
      rightRear.setOpenLoopRampRate(rampRate);
    } catch (Exception e) {
      throw e;
    }
  }

  // for some reason -speed is forward. dont ask me why
  public void setAllMotors(double speed) {
    try {
      leftFront.set(-speed);
      leftRear.set(-speed);
      rightFront.set(-speed);
      rightRear.set(-speed);
    } catch (Exception e) {
      throw e;
    }
  }

  public double getLeftDistance() {
    double numRotations = (leftFront.getEncoder().getPosition() + leftRear.getEncoder().getPosition()) / 2;
    return -numRotations * Constants.AutoConstants.encoderFactor; // This is flipped to make forward positive
  }

  public double getRightDistance() {
    double numRotations = (rightFront.getEncoder().getPosition() + rightRear.getEncoder().getPosition()) / 2;
    return -numRotations * Constants.AutoConstants.encoderFactor; // This is flipped to make forward positive
  }
}
