// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.interfaces.CANSparkMax;
import frc.robot.util.Alert;
import java.util.Map;

public class Arm extends PIDSubsystem {

  private static double kP = 0.18;
  private static double kI = 0;
  private static double kD = 0;

  public boolean going;

  public double armToFrontAngle = 3;
  public double armToBackAngle = -3;

  public CANSparkMax armMotor;
  public DigitalInput allTheWayDownRear = new DigitalInput(1);
  // public double desiredArmAngle;
  // public double currentArmAngle;

  public double gearRatio = 87;

  public GenericEntry encoderWidget;
  public GenericEntry appliedOutputWidget;

  public REVPhysicsSim physicsSim;

  public SparkMaxPIDController armPID;

  public AbsoluteEncoder encoder;

  double lastEncoderValue;
  int rotations = 2;

  public double target;

  // widgets for three set points, will be able to change them on the fly
  public GenericEntry setPoint1;
  public GenericEntry setPoint2;
  public GenericEntry setPoint3;

  private String errorText = "The arm has encountered an error!";
  private Alert alert = new Alert(errorText, Alert.AlertType.ERROR);

  public boolean commandsAreCanceled = false;

  private double absoluteEncoderMultiplicity;

  public Arm() {
    super(
        // PIDController
        new PIDController(kP, kI, kD));
    m_controller.setTolerance(0.02);
    m_controller.setSetpoint(0.625);
    going = false;

    // kP = 0.0001;
    // kI = 0;
    // kD = 0;
    // kIz = 0;
    // kFF = 0;
    // kMaxOutput = 1;
    // kMinOutput = -1;

    // kSetpoint = 0;

    /*
     * armMotor = new CANSparkMax(7, MotorType.kBrushless);
     * armMotor.setIdleMode(IdleMode.kBrake);
     * armMotor.setSmartCurrentLimit(40);
     * armMotor.burnFlash();
     * // set
     * addChild("Arm Motor", armMotor);
     */

    armMotor =
        CANSparkMax.initMotor(
            7, MotorType.kBrushless, false, 40, IdleMode.kBrake, 0, alert, errorText);

    armPID = armMotor.getPIDController();

    armPID.setP(0.4);
    armPID.setI(0);
    armPID.setD(0.08);
    // armPID.setIZone(kIz);
    armPID.setFF(0.08);
    armPID.setOutputRange(-0.8, 0.8);

    // disable the spark max's internal PID loop
    armMotor.getPIDController().setReference(0, ControlType.kDutyCycle);

    armMotor.set(0); // just to make sure we don't fuck up anything else

    // set the spark max to alternate encoder mode

    // configure the data port on top to be used with the REV Through Bore Encoder
    // using the absolute encoder adapter
    encoder = armMotor.getAbsoluteEncoder(Type.kDutyCycle);

    ShuffleboardLayout armLayout =
        Shuffleboard.getTab("Main")
            .getLayout("Arm System", BuiltInLayouts.kList)
            .withPosition(34, 0)
            .withSize(5, 8);
    armLayout
        .addNumber("Arm Output", () -> armMotor.getAppliedOutput())
        .withProperties(Map.of("Min", -1, "Max", 1));

    encoderWidget = Shuffleboard.getTab("stuff").add("Arm Encoder", 0).getEntry();

    // will cause it to pick rotation zero if it is just under one rotation
    lastEncoderValue = 0.01;

    // add simulation data
    if (RobotBase.isSimulation()) {
      physicsSim = new REVPhysicsSim();
      physicsSim.addSparkMax(armMotor, 5000, 20);
    }

    // widgets
    setPoint1 = Shuffleboard.getTab("Motors").add("Set Point 1", 0).getEntry();
    setPoint2 = Shuffleboard.getTab("Motors").add("Set Point 2", 0).getEntry();
    setPoint3 = Shuffleboard.getTab("Motors").add("Set Point 3", 0).getEntry();

    Shuffleboard.getTab("Motors").add("Arm PID", m_controller);

    addChild("Arm PID", m_controller);

    Shuffleboard.getTab("Motors").add("Arm motor", armMotor);

    // now comes the fun part
    // ENCODER CO-CALIBRATION
    // FINAL BOSS ~ 100% ------------------------------

    // first store the current encoder value from both the absolute encoder and the integrated,
    // relative encoder
    // the relative encoder is nice because it provides a value that actually increments, unlike the
    // absolute encoder which is just a value from 0 to 1
    // that's a pain to increment

    double currentAbsoluteEncoderValue = encoder.getPosition();
    double currentRelativeEncoderValue = armMotor.getEncoder().getPosition();

    // so likely the absolute encoder value is going to be something like 0.5, and the relative
    // encoder value is going to be something like 0
    // our challenge is to multiply the absolute encoder value by some number and add that to the
    // relative encoder value
    // the benefit of this is that we can now use the relative encoder value to determine
    // everything, because unlike the normal approach, the value persists
    // across power cycles

    double absoluteEncoderCenter = 0.5;
    absoluteEncoderMultiplicity = 75;

    // okay so now we plug the difference between the current absolute encoder value and the center
    // into the gear ratio to get us the relative encoder value (or, should I say, the relative
    // encoder value we know is accurate)

    double relativeEncoderValue =
        (currentAbsoluteEncoderValue - absoluteEncoderCenter) * absoluteEncoderMultiplicity;

    armMotor.getEncoder().setPosition(relativeEncoderValue);
  }

  @Override
  public void periodic() {
    super.periodic();
    double roundedVelocity = ((double) ((int) ((encoder.getVelocity() - 0.265) * 100))) / 100;

    // SmartDashboard.putBoolean("zeroDeg", allTheWayDownRear.get());
    double encoderValue = encoder.getPosition();

    double roundedPosition = ((double) ((int) (encoderValue * 100))) / 100;

    double roundedDifference =
        ((double) ((int) (lastEncoderValue * 100))) / 100
            - ((double) ((int) (encoderValue * 100))) / 100;

    double supposedPosition =
        armMotor.getEncoder().getPosition()
            / absoluteEncoderMultiplicity; // this /should/ correspond roughly to the absolute
    // encoder value
    // if (lastEncoderValue < 0.3 && encoderValue > 0.8) {
    //   rotations--;
    // } else if (lastEncoderValue > 0.8 && encoderValue < 0.3) {
    //   rotations++;
    // }
    // get the velocity of the encoder (in rotations per second) and if there is a
    // change between the signs of the velocity and the change in encoder value,
    // then there has been a Full Rotation(tm)
    if (Math.signum(roundedVelocity) != Math.signum(roundedDifference)) {
      rotations += Math.signum(roundedVelocity);
    }
    lastEncoderValue = encoderValue;

    encoderWidget.setDouble(encoderValue);

    SmartDashboard.putNumber("rotations", rotations);
    SmartDashboard.putNumber("velocity", encoder.getVelocity());
    SmartDashboard.putNumber("roundedVelocity", roundedVelocity);
    SmartDashboard.putNumber("roundedPosition", roundedPosition);
    SmartDashboard.putNumber("Supposed position", supposedPosition);

    // round velocity to the hundredths place

  }

  /** uses input double value to set the motor speed of the arm */
  public void moveArm(double value) {
    double speed = 0.8;
    double motorDrive = value * speed;
    armMotor.set(-motorDrive);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public double getAngle() {
    return encoder.getPosition() + rotations;
  }

  // public void setArmAngle(double angle) {
  // //desiredArmAngle = angle;
  // //armPID.setReference(angle, CANSparkMax.ControlType.kPosition);
  // }

  // public void putArmAtFront() {
  // setArmAngle(armToFrontAngle);
  // }

  // public void setTarget(double target) {
  // this.target = target;
  // m_controller.setSetpoint(target);
  // going = true;
  // if (target < 0) {
  // going = false;
  // }
  // }

  @Override
  protected void useOutput(double output, double setpoint) {
    if (0.05 < output && output < 0.75) {
      output = 0.75;
    } else if (-0.75 < output && output < -0.05) {
      output = -0.75;
    } else if (0 < output && output < 0.05) {
      output = 0.25;
    } else if (-0.05 < output && output < 0) {
      output = -0.25;
    }

    moveArm(-output);
  }

  @Override
  protected double getMeasurement() {
    return getAngle();
  }

  public boolean atSetpoint() {
    if (commandsAreCanceled) {
      commandsAreCanceled = false;
      return true;
    } else {
      return m_controller.atSetpoint();
    }
  }

  @Override
  public void setSetpoint(double setpoint) {
    m_controller.setSetpoint(setpoint);
  }

  public void setPID(double p, double i, double d) {
    m_controller.setPID(p, i, d);
  }

  public void setTolerance(double tolerance) {
    m_controller.setTolerance(tolerance);
  }

  // this is for button 7- picking up cubes and downed cones
  public void moveToSetPoint1() {
    m_controller.setSetpoint(2.98);
    // armPID.setReference(223.5, CANSparkMax.ControlType.kPosition);
  }

  // this is for button 8- picking up the upright cones
  public void moveToSetPoint2() {
    m_controller.setSetpoint(0.17);
    // armPID.setReference(12.75, CANSparkMax.ControlType.kPosition);
  }

  // this is for button 12- arm up for when we are driving
  public void moveToSetPoint3() {
    m_controller.setSetpoint(2);
    // armPID.setReference(150, CANSparkMax.ControlType.kPosition);
  }

  // this is for button 11- balancing at the end of the match
  public void moveToSetPoint4() {
    m_controller.setSetpoint(2.15);
    // armPID.setReference(161.25, CANSparkMax.ControlType.kPosition);
  }

  // this is for button 9- general cube placing
  public void moveToSetPoint5() {
    m_controller.setSetpoint(1.02);
    // armPID.setReference(76.5, CANSparkMax.ControlType.kPosition);
  }

  // this is for button 10- general cone placing
  public void moveToSetPoint6() {
    m_controller.setSetpoint(0.85);
    // armPID.setReference(63.75, CANSparkMax.ControlType.kPosition);
  }
}
