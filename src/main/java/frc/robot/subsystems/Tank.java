package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.Interfaces.*;

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
  /** */
  public Tank() {
    motorsTab = Shuffleboard.getTab("Motors");


    leftFront = new CANSparkMax(Constants.CanConstants.kLeftFrontMotorPort, MotorType.kBrushless);
    leftFront.restoreFactoryDefaults();
    leftFront.setInverted(true);
    leftFront.setIdleMode(IdleMode.kBrake);
    leftFront.setSmartCurrentLimit(40);
    // leftFront.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
    leftFront.burnFlash();
    motorsTab.add("Left Front", leftFront);

    leftRear = new CANSparkMax(Constants.CanConstants.kLeftRearMotorPort, MotorType.kBrushless);
    leftRear.restoreFactoryDefaults();
    leftRear.setInverted(true);
    leftRear.setIdleMode(IdleMode.kBrake);
    leftRear.setSmartCurrentLimit(40);
    // leftRear.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
    leftRear.burnFlash();
    motorsTab.add("Left Rear", leftRear);

    left = new MotorControllerGroup(leftFront, leftRear);
    addChild("Left Side", left);
    motorsTab.add("Left", left);

    rightFront = new CANSparkMax(Constants.CanConstants.kRightFrontMotorPort, MotorType.kBrushless);
    rightFront.restoreFactoryDefaults();
    rightFront.setInverted(false);

    rightFront.setIdleMode(IdleMode.kBrake);
    rightFront.setSmartCurrentLimit(40);
    // rightFront.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
    rightFront.burnFlash();
    motorsTab.add("Right Front", rightFront);

    rightRear = new CANSparkMax(Constants.CanConstants.kRightRearMotorPort, MotorType.kBrushless);
    rightRear.restoreFactoryDefaults();
    rightRear.setInverted(false);
    rightRear.setIdleMode(IdleMode.kBrake);
    rightRear.setSmartCurrentLimit(40);
    // rightRear.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
    rightRear.burnFlash();
    motorsTab.add("Right Rear", rightRear);

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
    leftFront.setSmartCurrentLimit(40, 60);
    leftRear.setSmartCurrentLimit(40, 60);
    rightFront.setSmartCurrentLimit(40, 60);
    rightRear.setSmartCurrentLimit(40, 60);

    tankLayout =
        Shuffleboard.getTab("Config")
            .getLayout("Tank", BuiltInLayouts.kList)
            .withSize(1, 4)
            .withPosition(0, 0);

    // create a new slider widget for the current limits
    stallWidget =
        tankLayout.add("Stall Limit", 40).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    freeWidget =
        tankLayout.add("Free Limit", 40).withWidget(BuiltInWidgets.kNumberSlider).getEntry();

    // create a new boolean box widget for the idle mode
    idleModeWidget = tankLayout.add("Braking Mode", false).withWidget(BuiltInWidgets.kBooleanBox);

    // create a new button widget for the idle mode switch
    idleModeSwitch =
        tankLayout
            .add("Switch Idle Mode", false)
            .withWidget(BuiltInWidgets.kToggleButton)
            .getEntry();

    // create a new slider widget for the ramp rate
    rampRateWidget =
        tankLayout
            .add("Ramp Rate", Constants.CanConstants.kRampRate)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .getEntry();

    // create a new slider widget for the max speed (don't call getEntry() yet, it can be changed
    // by button inputs)
    maxSpeedWidget = tankLayout.add("Max Speed", maxSpeed).withWidget(BuiltInWidgets.kNumberSlider);
    maxSpeedEntry = maxSpeedWidget.getEntry();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("leftStickY", RobotContainer.getDriverControllerLeftStickY());
    SmartDashboard.putNumber("rightStickX", RobotContainer.getDriverControllerRightStickX());

    // set all four motors to the stall and free limit widget values
    stallLimit = (int) stallWidget.getDouble(10);
    freeLimit = (int) freeWidget.getDouble(40);

    leftFront.setSmartCurrentLimit(stallLimit, freeLimit);
    leftRear.setSmartCurrentLimit(stallLimit, freeLimit);
    rightFront.setSmartCurrentLimit(stallLimit, freeLimit);
    rightRear.setSmartCurrentLimit(stallLimit, freeLimit);

    // set the ramp rate to the ramp rate widget value
    rampRate = rampRateWidget.getDouble(Constants.CanConstants.kRampRate);

    leftFront.setOpenLoopRampRate(rampRate);
    leftRear.setOpenLoopRampRate(rampRate);
    rightFront.setOpenLoopRampRate(rampRate);
    rightRear.setOpenLoopRampRate(rampRate);

    // check if the max speed widget value has changed
    if (maxSpeed != maxSpeedEntry.getDouble(maxSpeed)) {
      // set the max speed to the new value
      maxSpeed = maxSpeedEntry.getDouble(maxSpeed);
    }
    // set the widget value to the current max speed
    maxSpeedEntry.setDouble(maxSpeed);

    // i hate my life
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
  public void arcadeDrive(double speed, double rotation) {
    try {
      drive.arcadeDrive(
          -speed * Math.pow(Math.abs(speed), 0.5), rotation * Math.pow(Math.abs(rotation), 0.5));
    } catch (Exception e) {
      throw e;
    }
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
