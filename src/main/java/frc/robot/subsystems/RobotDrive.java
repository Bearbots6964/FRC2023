// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.Constants.AutoConstants;
import frc.robot.RobotContainer;

public class RobotDrive extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private static CANSparkMax m_rearLeft;
  private static CANSparkMax m_frontLeft;
  private MotorControllerGroup m_left;

  private static CANSparkMax m_rearRight;
  private static CANSparkMax m_frontRight;
  private MotorControllerGroup m_right;

  public static RelativeEncoder m_RLencoder;
  public static RelativeEncoder m_FLencoder;
  public static RelativeEncoder m_RRencoder;
  public static RelativeEncoder m_FRencoder;

  public AHRS gyro;
  private Timer timer;
  private static double kP, kI, kD, P, I, D, errorSum, errorRate, lastTimeStamp, iLimit, lastError;
  public double tolerance, error; 
  private static boolean automate;
  public int count;


  public RobotDrive() {
    m_rearLeft = new CANSparkMax(AutoConstants.rearLeftDrive, MotorType.kBrushless);
    m_frontLeft = new CANSparkMax(AutoConstants.frontLeftDrive, MotorType.kBrushless);
    m_left = new MotorControllerGroup(m_frontLeft, m_rearLeft);
    m_rearRight = new CANSparkMax(AutoConstants.rearRightDrive, MotorType.kBrushless);
    m_frontRight = new CANSparkMax(AutoConstants.frontRightDrive, MotorType.kBrushless);
    m_right = new MotorControllerGroup(m_frontRight, m_rearRight);
    m_RLencoder = m_rearLeft.getEncoder();
    m_FLencoder = m_frontLeft.getEncoder();
    m_RRencoder = m_rearRight.getEncoder();
    m_FRencoder = m_frontRight.getEncoder();
    gyro = new AHRS(SPI.Port.kMXP);
    timer = new Timer();
    kP = 0.1;
    kI = 0.1;
    kD = 0.1;
    automate = false;
    iLimit = 3;
    tolerance = 0.5;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    error = gyro.getPitch();
    if(gyro.getPitch() == 15){
      count++;
    }

    if(automate == true){
      PIDDrive();
     }
     else{
      //this.arcadeDriveSimple(RobotContainer.getLeftStickX(), RobotContainer.getLeftStickY(), .7);
     } 
  }

  
  public void preparePID(){
    while(gyro.getPitch() < 15){
      m_left.set(0.2);
      m_right.set(0.2);
    }
  }

  public void startPID(){
    switchToAuto();
    errorSum = 0;
    lastError = 0;
    timer.start();
    lastTimeStamp = timer.getFPGATimestamp();
    lastError = gyro.getPitch();
  }

  public double PID(){
     //integral
     if(Math.abs(error) < iLimit){
      errorSum += error;
    }

    //derivative
    double deltaT = timer.getFPGATimestamp() - lastTimeStamp;
    errorRate = (error - lastError) / deltaT;
    lastError = error;
    lastTimeStamp = timer.getFPGATimestamp();

    P = kP * error;
    I = kI * errorSum;
    D = kD * errorRate;

    double outputSpeed = P + I + D;

    return outputSpeed;
  }

  public void PIDDrive(){
    if(error > tolerance){
      m_left.set(this.PID());
      m_right.set(this.PID());
    }
    else{
      m_left.set(0);
      m_right.set(0);
    }
  }

  public void switchToAuto(){
    automate = true;
  }

  public void switchToJoystick(){
    automate = false;
  }

  public void arcadeDriveSimple(double leftStickXaxis, double leftStickYaxis, double maxSpeed){

    double drivePower = -.6*Math.pow(leftStickXaxis, 3);
    double leftDrive = drivePower + leftStickYaxis*0.5; //*0.5
    double rightDrive  = drivePower - leftStickYaxis*0.5;
    leftDrive = leftDrive*maxSpeed;
    rightDrive = rightDrive*maxSpeed;

    m_left.set(leftDrive);
    m_right.set(-rightDrive);
  }

  public void resetEncoders(){
    (RobotDrive.m_RLencoder).setPosition(0);
    (RobotDrive.m_RRencoder).setPosition(0);
    (RobotDrive.m_FLencoder).setPosition(0);
    (RobotDrive.m_FRencoder).setPosition(0);
  }

  public static double getTurnAmount(){
    // 0.375 units/degree cw
    final double encPerRotation = 0.375;
    return 1/encPerRotation * ((Math.abs(m_FRencoder.getPosition())+Math.abs(m_RRencoder.getPosition())+Math.abs(m_RLencoder.getPosition())+Math.abs(m_RRencoder.getPosition()))/4.0);
  }

  public double getSpeed(){
    return (m_FLencoder.getVelocity() + m_FRencoder.getVelocity()+m_RLencoder.getVelocity()+m_RRencoder.getVelocity())/4;
  }

  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
