// RobotBuilder Version: 4.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Subsystem.

package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.PIDmecanum;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Interfaces.*;

public class Mecanum extends SubsystemBase{
    public CANSparkMax leftFront;
    public CANSparkMax leftRear;
    public CANSparkMax rightFront;
    public CANSparkMax rightRear;

    public static MotorControllerGroup leftMotors, rightMotors, allMotors;
    public MecanumDrive mecanumDrive;

   // Here's the layout:
    // 5  ^  3
    // 2  ^  4
public Mecanum() {
leftFront = new CANSparkMax(5, MotorType.kBrushless);
leftFront.restoreFactoryDefaults() ;  
leftFront.setInverted(true);
leftFront.setIdleMode(IdleMode.kCoast);
leftFront.burnFlash();

leftRear = new CANSparkMax(2, MotorType.kBrushless);
leftRear.restoreFactoryDefaults();  
leftRear.setInverted(true);
leftRear.setIdleMode(IdleMode.kCoast);
leftRear.burnFlash();

rightFront = new CANSparkMax(3, MotorType.kBrushless);
rightFront.restoreFactoryDefaults();  
rightFront.setInverted(false);
rightFront.setIdleMode(IdleMode.kCoast);
rightFront.burnFlash();

rightRear = new CANSparkMax(4, MotorType.kBrushless);
rightRear.restoreFactoryDefaults();  
rightRear.setInverted(false);
rightRear.setIdleMode(IdleMode.kCoast);
rightRear.burnFlash();
  
leftMotors = new MotorControllerGroup(leftFront, leftRear);
rightMotors = new MotorControllerGroup(rightFront, rightRear);
allMotors = new MotorControllerGroup(leftFront, leftRear);


mecanumDrive = new MecanumDrive(leftFront, leftRear, rightFront, rightRear);
addChild("Mecanum Drive",mecanumDrive);
mecanumDrive.setSafetyEnabled(true);
mecanumDrive.setExpiration(0.1);
mecanumDrive.setMaxOutput(1.0);
}

 /**
 * Drive method for te mecanum base.
 * 
 * <p>Uses driveCartesian as the driver.
 * @param y      Forward and back value, perhaps from a joystick.
 * @param x      Right and left value, also perhaps from a joystick.
 * @param z      Rotation value, from-AHA! You thought I was about to type "perhaps from a joystick!" You fool! It might come from a SEPERATE joystick, because a joystick only has 2 axes!
 */
private double forword = 0, rotate = 0, strafe = 0;

 @Override
 public void periodic() {
   mecanumDrive.driveCartesian(RobotContainer.getJoystickYAxis() * RobotContainer.getMaxSpeed(), -RobotContainer.getJoystickXAxis() * RobotContainer.getMaxSpeed(), -RobotContainer.getJoystickZAxis() * RobotContainer.getMaxSpeed());

   //mecanumDrive.driveCartesian(forword,strafe,rotate);

   SmartDashboard.putNumber("x axis", RobotContainer.getJoystickXAxis());
   SmartDashboard.putNumber("y axis", RobotContainer.getJoystickYAxis());
   SmartDashboard.putNumber("z axis", RobotContainer.getJoystickZAxis());
   SmartDashboard.putNumber("max speed", RobotContainer.getMaxSpeed());
 }    
  
public void driveForward(){
  forword = RobotContainer.getJoystickYAxis()* RobotContainer.getMaxSpeed();
}
public void rotate(){
  rotate =  -RobotContainer.getJoystickZAxis() * RobotContainer.getMaxSpeed();
}
 public void strafe(){
  strafe = RobotContainer.getJoystickXAxis() * RobotContainer.getMaxSpeed();
 }
//  public void driveForward(double yAxis){
//     leftFront.set(yAxis);
//     leftRear.set(yAxis);
//     rightFront.set(yAxis);
//     rightRear.set(yAxis);
//  }
 
//  public void rotate(double twistAxis){
//   if(twistAxis == 0){
//     leftFront.set(0);
//     leftRear.set(0);
//     rightFront.set(0);
//     rightRear.set(0);
//   }
//   if(twistAxis < 0){
//     leftFront.set(twistAxis);
//     leftRear.set(twistAxis);
//     rightFront.set(-twistAxis);
//     rightRear.set(-twi);
//   }
//   if(twistAxis > 0){
//     leftMotors.set(twistAxis);
//     rightMotors.set(-twistAxis);
//   }
//  }

//  public void strafe(double xAxis){
//   if(xAxis == 0){
//     allMotors.set(0);
//   }
//   if(xAxis < 0){
//     leftFront.set(xAxis);
//     leftRear.set(-xAxis);
//     rightFront.set(-xAxis);
//     rightRear.set(xAxis);
//   }
//   if(xAxis > 0){
//     leftFront.set(xAxis);
//     leftRear.set(-xAxis);
//     rightFront.set(-xAxis);
//     rightRear.set(xAxis);
//   }
//  }

public Rotation2d getYawAngle(){
  return Rotation2d.fromDegrees(PIDmecanum.gyro.getAngle());
}

@Override
public void simulationPeriodic() {
}
}