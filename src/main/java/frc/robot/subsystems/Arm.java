// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.interfaces.CANSparkMax;

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

  public REVPhysicsSim physicsSim;

  // public SparkMaxPIDController armPID;

  public AbsoluteEncoder encoder;

  double lastEncoderValue;
  int rotations=1;

  public double target;



// widgets for three set points, will be able to change them on the fly
  public GenericEntry setPoint1;
  public GenericEntry setPoint2;
  public GenericEntry setPoint3;





  public Arm() {
    super(
        // PIDController
        new PIDController(kP, kI, kD));
    m_controller.setTolerance(0.1);
    m_controller.setSetpoint(0);
    going = false;

    // kP = 0.0001;
    // kI = 0;
    // kD = 0;
    // kIz = 0;
    // kFF = 0;
    // kMaxOutput = 1;
    // kMinOutput = -1;

    // kSetpoint = 0;

    armMotor = new CANSparkMax(7, MotorType.kBrushless);
    armMotor.setIdleMode(IdleMode.kBrake);
    armMotor.setSmartCurrentLimit(30, 40);
    armMotor.burnFlash();
    // set
    addChild("Arm Motor", armMotor);

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

    encoderWidget = Shuffleboard.getTab("Motors").add("Arm Encoder", armMotor.getEncoder().getPosition()).getEntry();

    Shuffleboard.getTab("Motors").add("Arm", armMotor);
    //will cause it to pick rotation zero if it is just under one rotation
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
  }

  @Override
  public void periodic() {
    // SmartDashboard.putBoolean("zeroDeg", allTheWayDownRear.get());
    double encoderValue = armMotor.getEncoder().getPosition();
    double change = encoderValue - lastEncoderValue;
    if (Math.abs(change) > 0.5) {
      if (change < 0) {
        rotations++;
      } else {
        rotations--;
      }
    }
    lastEncoderValue = encoderValue;

    encoderWidget.setDouble(encoderValue);

    if (going) {
      super.periodic();
      SmartDashboard.putNumber("caluclated power to arm form PID",
          m_controller.calculate(getMeasurement(), m_controller.getSetpoint()));
    }

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
    return armMotor.getEncoder().getPosition() + rotations;
  }

  // public void setArmAngle(double angle) {
  // //desiredArmAngle = angle;
  // //armPID.setReference(angle, CANSparkMax.ControlType.kPosition);
  // }

  // public void putArmAtFront() {
  // setArmAngle(armToFrontAngle);
  // }

  public void setTarget(double target) {
    this.target = target;
    m_controller.setSetpoint(target);
    going=true;
    if(target<0){
      going=false;
    }
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    moveArm(output);
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
    setTarget(setPoint1.getDouble(0));
  }

  public void moveToSetPoint2() {
    setTarget(setPoint2.getDouble(0));
  }

  public void moveToSetPoint3() {
    setTarget(setPoint3.getDouble(0));
  }
}
