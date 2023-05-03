package frc.robot.subsystems;

import java.util.Map;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.REVLibError;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.interfaces.*;
import frc.robot.util.Alert;
import frc.robot.util.Alert.AlertType;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.SPI.Port;

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

  private double maxSpeed;
  public SimpleWidget maxSpeedWidget;
  private GenericEntry maxSpeedEntry;

  private int initialCurrentLimit = 30; // TODO tune this

  public static AHRS navx;

  private DataLog log = DataLogManager.getLog();

  // gyro logging
  private DoubleLogEntry pitchAngle;
  private BooleanLogEntry isCalibrating;
  private DoubleLogEntry yawAngle;

  // errors
  // Credit goes to Mechanical Advantage 6328 for the framework for this

  // errors for each of the motors
  private String leftFrontErrorText = "The left front drive motor has encountered an error!";
  private String leftRearErrorText = "The left rear drive motor has encountered an error!";
  private String rightFrontErrorText = "The right front drive motor has encountered an error!";
  private String rightRearErrorText = "The right rear drive motor has encountered an error!";

  private Alert leftFrontError = new Alert(leftFrontErrorText, AlertType.ERROR);
  private Alert leftRearError = new Alert(leftRearErrorText, AlertType.ERROR);
  private Alert rightFrontError = new Alert(rightFrontErrorText, AlertType.ERROR);
  private Alert rightRearError = new Alert(rightRearErrorText, AlertType.ERROR);

  // quick little function that can throw an error just by passing in another
  // function (e.g.
  // leftFront.setOpenLoopRampRate(Constants.CanConstants.kRampRate).withError(leftFrontError)
  // and have that automatically handle the error)
  private REVLibError withError(REVLibError error, Alert alert, String text) {
    if (error != REVLibError.kOk) {
      alert.set(true);
      alert.setText(text.concat(" Error: " + error.toString() + ""));
    }
    return error;
  }

  private CANSparkMax initMotor(int port, MotorType motorType, boolean isInverted, int smartCurrentLimit,
      IdleMode idleMode, double rampRate, Alert alert, String text) {
    CANSparkMax motor = new CANSparkMax(port, motorType);
    withError(motor.restoreFactoryDefaults(), alert, text);
    motor.setInverted(isInverted);
    withError(motor.setIdleMode(idleMode), alert, text);
    withError(motor.setSmartCurrentLimit(smartCurrentLimit), alert, text);
    withError(motor.setOpenLoopRampRate(rampRate), alert, text);
    motor.burnFlash();
    return motor;
  }

  /** */
  public Tank() {
    navx = new AHRS(Port.kMXP);
    addChild("NavX", navx);

    // start logging the navx data to the WPILib logger
    pitchAngle = new DoubleLogEntry(log, "NavX/Pitch Angle");
    isCalibrating = new BooleanLogEntry(log, "NavX/Is Calibrating");
    yawAngle = new DoubleLogEntry(log, "NavX/Yaw Angle");

    leftFront = initMotor(Constants.CanConstants.kLeftFrontMotorPort, MotorType.kBrushless, true, initialCurrentLimit,
        IdleMode.kBrake, Constants.CanConstants.kRampRate, leftFrontError, leftFrontErrorText);

    leftRear = initMotor(Constants.CanConstants.kLeftRearMotorPort, MotorType.kBrushless, true, initialCurrentLimit,
        IdleMode.kBrake, Constants.CanConstants.kRampRate, leftRearError, leftRearErrorText);

    left = new MotorControllerGroup(leftFront, leftRear);
    addChild("Left Side", left);

    rightFront = initMotor(Constants.CanConstants.kRightFrontMotorPort, MotorType.kBrushless, false,
        initialCurrentLimit, IdleMode.kBrake, Constants.CanConstants.kRampRate, rightFrontError, rightFrontErrorText);

    rightRear = initMotor(Constants.CanConstants.kRightRearMotorPort, MotorType.kBrushless, false, initialCurrentLimit,
        IdleMode.kBrake, Constants.CanConstants.kRampRate, rightRearError, rightRearErrorText);

    addChild("Right Rear", rightRear);

    right = new MotorControllerGroup(rightFront, rightRear);
    addChild("Right Side", right);

    all = new MotorControllerGroup(leftFront, leftRear, rightFront, rightRear);
    addChild("All", all);

    drive = new DifferentialDrive(left, right);
    addChild("Drive", drive);
    // create a differential drive widget

    drive.setSafetyEnabled(false);
    drive.setExpiration(0.1);

    drive.setMaxOutput(0.8);

    brakeMode = true;
    SmartDashboard.putBoolean("brakeMode", brakeMode);

    // set the max speed to the default value
    maxSpeed = Constants.CanConstants.maxSpeed;

    // set the current limits for all four motors
    leftFront.setSmartCurrentLimit(40, 50);
    leftRear.setSmartCurrentLimit(40, 50);
    rightFront.setSmartCurrentLimit(40, 50);
    rightRear.setSmartCurrentLimit(40, 50);

    Shuffleboard.getTab("Main").getLayout("Arm System").addNumber("Max Speed", this::getMaxSpeed)
        .withProperties(Map.of("Min", 0, "Max", 1));

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // log gyro data
    pitchAngle.append(navx.getPitch());
    isCalibrating.append(navx.isCalibrating());
    yawAngle.append(navx.getYaw());
  }

  public double getMaxSpeed() {
    return maxSpeed;
  }

  /**
   * Drives the robot using arcade drive.
   *
   * @param speed    The forward/backward speed.
   * @param rotation The rotation speed.
   */
  public void arcadeDrive(double speed, double rotation) {

    drive.arcadeDrive(
        -speed * Math.pow(Math.abs(speed), 0.5), rotation * Math.pow(Math.abs(rotation), 0.5));

  }

  public void increaseMaxSpeed() {
    if (Constants.CanConstants.maxSpeed >= 1) {
      Constants.CanConstants.maxSpeed = 1;
    } else {
      Constants.CanConstants.maxSpeed += Constants.CanConstants.maxSpeedIncrement;

      // set the max speed widget value to the newly increased max speed
      maxSpeedEntry.setDouble(maxSpeed);
    }
  }

  public void decreaseMaxSpeed() {
    if (Constants.CanConstants.maxSpeed <= 0) {
      Constants.CanConstants.maxSpeed = 0;
    } else {
      Constants.CanConstants.maxSpeed -= Constants.CanConstants.maxSpeedIncrement;
      // set the max speed widget value to the newly decreased max speed
      maxSpeedEntry.setDouble(maxSpeed);
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

    SmartDashboard.putBoolean("brakeMode", brakeMode);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    // ... do nothing
  }

  /** Change the ramp rate of the motor controllers. */
  public void setRampRate(double rampRate) {
    leftFront.setOpenLoopRampRate(rampRate);
    leftRear.setOpenLoopRampRate(rampRate);
    rightFront.setOpenLoopRampRate(rampRate);
    rightRear.setOpenLoopRampRate(rampRate);

  }

  // for some reason -speed is forward. dont ask me why
  public void setAllMotors(double speed) {

    leftFront.set(-speed);
    leftRear.set(-speed);
    rightFront.set(-speed);
    rightRear.set(-speed);

  }

  public double getLeftDistance() {
    double numRotations = (leftFront.getEncoder().getPosition() + leftRear.getEncoder().getPosition()) / 2;
    return -numRotations
        * Constants.AutoConstants.encoderFactor; // This is flipped to make forward positive
  }

  public double getRightDistance() {
    double numRotations = (rightFront.getEncoder().getPosition() + rightRear.getEncoder().getPosition()) / 2;
    return -numRotations
        * Constants.AutoConstants.encoderFactor; // This is flipped to make forward positive
  }

  public double getAverageDistance() {
    double numRotations = (getRightDistance() + getLeftDistance()) / 2;
    return numRotations;
  }

  public void setLeft(double speed) {
    left.set(speed);
  }

  public void setRight(double speed) {
    right.set(speed);
  }
}
