// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.interfaces.CANSparkMax;
import frc.robot.util.Alert;

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

  // public SparkMaxPIDController armPID;

  public AbsoluteEncoder encoder;

  double lastEncoderValue;
  int rotations = 1;

  public double target;

  // widgets for three set points, will be able to change them on the fly
  public GenericEntry setPoint1;
  public GenericEntry setPoint2;
  public GenericEntry setPoint3;

  private String errorText = "The arm has encountered an error!";
  private Alert alert = new Alert(errorText, Alert.AlertType.ERROR);

  public Arm() {
    super(
        // PIDController
        new PIDController(kP, kI, kD));
    m_controller.setTolerance(0.1);
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

/*     armMotor = new CANSparkMax(7, MotorType.kBrushless);
    armMotor.setIdleMode(IdleMode.kBrake);
    armMotor.setSmartCurrentLimit(40);
    armMotor.burnFlash();
    // set
    addChild("Arm Motor", armMotor); */

    armMotor = CANSparkMax.initMotor(7, MotorType.kBrushless, false, 40, IdleMode.kBrake, 0, alert, errorText);

    // armPID = armMotor.getPIDController();

    // armPID.setP(kP);
    // armPID.setI(kI);
    // armPID.setD(kD);
    // armPID.setIZone(kIz);
    // armPID.setFF(kFF);
    // armPID.setOutputRange(kMinOutput, kMaxOutput);

    // set the spark max to alternate encoder mode

    // configure the data port on top to be used with the REV Through Bore Encoder
    // using the absolute encoder adapter
    encoder = armMotor.getAbsoluteEncoder(Type.kDutyCycle);

    ShuffleboardLayout armLayout = Shuffleboard.getTab("Main").getLayout("Arm System");
    armLayout.addNumber("Arm Output", () -> armMotor.getAppliedOutput()).withProperties(Map.of("Min", -1, "Max", 1));

    encoderWidget = Shuffleboard.getTab("stuff").add("Arm Encoder", 0).getEntry();

    // will cause it to pick rotation zero if it is just under one rotation
    lastEncoderValue = .01;

    // configure the PID loop to use the alternate encoder
    // armPID.setFeedbackDevice(encoder);

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
  }

  @Override
  public void periodic() {
    super.periodic();
    double roundedVelocity = ((double) ((int) ((encoder.getVelocity() / 20) * 1000))) / 1000;

    // SmartDashboard.putBoolean("zeroDeg", allTheWayDownRear.get());
    double encoderValue = encoder.getPosition();
    double change = encoderValue - lastEncoderValue;
    double roundedPosition = ((double) ((int) (encoderValue * 100))) / 100;
    if (lastEncoderValue < 0.3 && encoderValue > 0.8) {
    rotations--;
    } else if (lastEncoderValue > 0.8 && encoderValue < 0.3) {
    rotations++;
    }
    // get the velocity of the encoder (in rotations per second) and if there is a
    // change between the signs of the velocity and the change in encoder value,
    // then there has been a Full Rotation(tm)
    // if (Math.signum(roundedVelocity) != Math.signum(roundedPosition)) {
    //   rotations += Math.signum(roundedVelocity);
    // }
    lastEncoderValue = encoderValue;

    encoderWidget.setDouble(encoderValue);



    SmartDashboard.putNumber("rotations", rotations);
    SmartDashboard.putNumber("velocity", encoder.getVelocity());
    SmartDashboard.putNumber("roundedVelocity", roundedVelocity);
    SmartDashboard.putNumber("roundedPosition", roundedPosition);

    // round velocity to the hundredths place
    

  }

  /**
   * uses input double value to set the motor speed of the arm
   */
  public void moveArm(double value) {
    double speed = 0.8;
    double motorDrive = value * speed;
    armMotor.set(motorDrive);
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
  //   this.target = target;
  //   m_controller.setSetpoint(target);
  //   going = true;
  //   if (target < 0) {
  //     going = false;
  //   }
  // }

  

  @Override
  protected void useOutput(double output, double setpoint) {
    if (0.50 < output) {
      output = 0.50;
    } else if (-0.50 > output) {
      output = -0.50;
    } else if (0.1 < output && output < 0.40) {
      output = 0.40;
    } else if (-0.40 < output && output < -0.1) {
      output = -0.40;
    }

    moveArm(-output);
  }

  @Override
  protected double getMeasurement() {
    return getAngle();
  }

  public boolean atSetpoint() {
    return m_controller.atSetpoint();
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

  public void moveToSetPoint1() {
    m_controller.setSetpoint(0.64);
  }

  public void moveToSetPoint2() {
    m_controller.setSetpoint(3.4);
  }

  public void moveToSetPoint3() {
    m_controller.setSetpoint(1.67);
  }
}
