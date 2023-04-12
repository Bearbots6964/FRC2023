package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.REVPhysicsSim;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.interfaces.*;

public class Tank extends SubsystemBase {
  public CANSparkMax leftFront;
  public CANSparkMax leftRear;
  public CANSparkMax rightFront;
  public CANSparkMax rightRear;

  public MotorControllerGroup left;
  public MotorControllerGroup right;
  public MotorControllerGroup all;

  public static DifferentialDrive drive;

  public boolean brakeMode;
  private GenericEntry stallWidget;
  private GenericEntry freeWidget;
  private int stallLimit;
  private int freeLimit;

  private SimpleWidget idleModeWidget;
  private GenericEntry idleModeSwitch;

  private double rampRate;
  private GenericEntry rampRateWidget;

  private double maxSpeed;
  public SimpleWidget maxSpeedWidget;
  private GenericEntry maxSpeedEntry;

  private ShuffleboardLayout tankLayout;

  private ShuffleboardTab motorsTab;

  private DifferentialDriveOdometry odometry;

  public static AHRS gyro;

  private Field2d field2d;

  private int initialCurrentLimit = 30; // TODO tune this

  private REVPhysicsSim physicsSim;

  /** */
  public Tank() {

    gyro = new AHRS(SPI.Port.kMXP);
    addChild("Gyro", gyro);
    Shuffleboard.getTab("Driver").add("Gyro", gyro);

    motorsTab = Shuffleboard.getTab("Motors");

    leftFront = new CANSparkMax(Constants.CanConstants.kLeftFrontMotorPort, MotorType.kBrushless);
    leftFront.restoreFactoryDefaults();
    leftFront.setInverted(true);
    leftFront.setIdleMode(IdleMode.kBrake);
    leftFront.setSmartCurrentLimit(initialCurrentLimit);
    leftFront.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
    leftFront.burnFlash();
    motorsTab.add("Left Front", leftFront);
    addChild("Left Front", leftFront);

    leftRear = new CANSparkMax(Constants.CanConstants.kLeftRearMotorPort, MotorType.kBrushless);
    leftRear.restoreFactoryDefaults();
    leftRear.setInverted(true);
    leftRear.setIdleMode(IdleMode.kBrake);
    leftRear.setSmartCurrentLimit(initialCurrentLimit);
    leftRear.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
    leftRear.burnFlash();
    motorsTab.add("Left Rear", leftRear);
    addChild("Left Rear", leftRear);

    left = new MotorControllerGroup(leftFront, leftRear);
    addChild("Left Side", left);
    motorsTab.add("Left", left);

    rightFront = new CANSparkMax(Constants.CanConstants.kRightFrontMotorPort, MotorType.kBrushless);
    rightFront.restoreFactoryDefaults();
    rightFront.setInverted(false);

    rightFront.setIdleMode(IdleMode.kBrake);
    rightFront.setSmartCurrentLimit(initialCurrentLimit);
    rightFront.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
    rightFront.burnFlash();
    motorsTab.add("Right Front", rightFront);
    addChild("Right Front", rightFront);

    rightRear = new CANSparkMax(Constants.CanConstants.kRightRearMotorPort, MotorType.kBrushless);
    rightRear.restoreFactoryDefaults();
    rightRear.setInverted(false);
    rightRear.setIdleMode(IdleMode.kBrake);
    rightRear.setSmartCurrentLimit(initialCurrentLimit);
    rightRear.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
    rightRear.burnFlash();
    motorsTab.add("Right Rear", rightRear);

    addChild("Right Rear", rightRear);

    right = new MotorControllerGroup(rightFront, rightRear);
    addChild("Right Side", right);
    motorsTab.add("Right", right);

    all = new MotorControllerGroup(leftFront, leftRear, rightFront, rightRear);
    addChild("All", all);
    motorsTab.add("All", all);

    drive = new DifferentialDrive(left, right);
    addChild("Drive", drive);
    motorsTab.add("Drive", drive).withWidget(BuiltInWidgets.kDifferentialDrive);
    // create a differential drive widget

    drive.setSafetyEnabled(false);
    drive.setExpiration(0.1);

    drive.setMaxOutput(1.0);

    brakeMode = true;
    SmartDashboard.putBoolean("brakeMode", brakeMode);

    // set the max speed to the default value
    maxSpeed = Constants.CanConstants.maxSpeed;

    // set the current limits for all four motors
    leftFront.setSmartCurrentLimit(40, 50);
    leftRear.setSmartCurrentLimit(40, 50);
    rightFront.setSmartCurrentLimit(40, 50);
    rightRear.setSmartCurrentLimit(40, 50);

    // implement odometry
    odometry =
        new DifferentialDriveOdometry(
            Rotation2d.fromDegrees(gyro.getAngle()),
            Units.inchesToMeters(
                (leftFront.getEncoder().getPosition() / 10)
                    * (leftFront.getEncoder().getPosition() / 10)
                    * Math.PI),
            Units.inchesToMeters(
                (rightFront.getEncoder().getPosition() / 10)
                    * (rightFront.getEncoder().getPosition() / 10)
                    * Math.PI));

    field2d = new Field2d();
    SmartDashboard.putData(field2d);

    // create a physics simulation for the robot
    if (RobotBase.isSimulation()) {
      physicsSim = new REVPhysicsSim();
      // add all the spark maxes to the simulation
      physicsSim.addSparkMax(leftFront, (float) 2.6, 445);
      physicsSim.addSparkMax(leftRear, (float) 2.6, 445);
      physicsSim.addSparkMax(rightFront, (float) 2.6, 445);
      physicsSim.addSparkMax(rightRear, (float) 2.6, 445);
      // run the simulation
      physicsSim.run();
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("leftStickY", RobotContainer.getTurningStickInput());
    SmartDashboard.putNumber("rightStickX", RobotContainer.getForwardStickInput());

    // i hate my life

    odometry.update(
        Rotation2d.fromDegrees(gyro.getAngle()),
        Units.inchesToMeters(
            (leftFront.getEncoder().getPosition() / 10)
                * (leftFront.getEncoder().getPosition() / 10)
                * Math.PI),
        Units.inchesToMeters(
            (rightFront.getEncoder().getPosition() / 10)
                * (rightFront.getEncoder().getPosition() / 10)
                * Math.PI));

    field2d.setRobotPose(odometry.getPoseMeters());
  }

  public double getMaxSpeed() {
    return maxSpeed;
  }

  /**
   * Drives the robot using arcade drive.
   *
   * @param speed The forward/backward speed.
   * @param rotation The rotation speed.
   */
  public static void arcadeDrive(double speed, double rotation) {

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
    double numRotations =
        (leftFront.getEncoder().getPosition() + leftRear.getEncoder().getPosition()) / 2;
    return -numRotations
        * Constants.AutoConstants.encoderFactor; // This is flipped to make forward positive
  }

  public double getRightDistance() {
    double numRotations =
        (rightFront.getEncoder().getPosition() + rightRear.getEncoder().getPosition()) / 2;
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
