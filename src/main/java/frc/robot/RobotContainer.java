// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.Constants.AutoConstants;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //RR 1/11/2022
  public static final XboxController m_driverController = new XboxController(3);
  public static final XboxController m_driverController2 = new XboxController(1);//change
  public static final PS4Controller m_controller = new PS4Controller(3);

  //INSTANTIATES ALL SUBSYSTEMS
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final RobotDrive m_RobotDrive = new RobotDrive();

  //INSTANTIATES ALL COMMANDS
  private final ExampleCommand m_exampleCommand = new ExampleCommand(m_exampleSubsystem);
  private final AutoCommand m_AutoCommand = new AutoCommand(m_RobotDrive,.5);



  private static AHRS m_gyro = new AHRS(SPI.Port.kMXP); //HC - 01/13/22


  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    //(ShootingRotate.m_AngleRotateEncoder).setPosition(0);
    (RobotDrive.m_RLencoder).setPosition(0);
    (RobotDrive.m_RRencoder).setPosition(0);
    (RobotDrive.m_FLencoder).setPosition(0);
    (RobotDrive.m_FRencoder).setPosition(0);
    m_gyro.calibrate();
  }



  public static double getLeftStickY(){
    double axis = m_driverController.getRawAxis(0);
    if(Math.abs(axis) < 0.02)
    {
      axis = 0;
    }
    return axis;
  }

  public static double getLeftStickX(){
    double axis=m_driverController.getRawAxis(1);
    if(axis<0.02&&axis>-0.02)
    {
      axis=0;
    }
    return axis;
  }

  public static double getRightStickX(){
    double axis=m_driverController.getRawAxis(4);
    if(axis<0.02&&axis>-0.02)
    {
      axis=0;
    }
    SmartDashboard.putNumber("Right Stick, X axis", axis);
    return axis;
  }
  
  public static double  getRightStickY(){
    double axis=m_driverController.getRawAxis(5);
    if(axis<0.02&&axis>-0.02)
    {
      axis=0;
    }
    SmartDashboard.putNumber("Right Stick, Y axis", axis);
    return axis;
  }


  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //new JoystickButton(m_driverController, XboxController.Button.kB.value).whileHeld();
    //new JoystickButton(m_driverController, XboxController.Button.kX.value).whileHeld();
    //new JoystickButton(m_driverController, XboxController.Button.kRightBumper.value).whileHeld();
    //new JoystickButton(m_driverController, XboxController.Button.kY.value).whileHeld();
    //new JoystickButton(m_driverController, XboxController.Button.kA.value).whileHeld();
    //new JoystickButton(m_driverController, XboxController.Button.kLeftBumper.value).whileHeld();

    //new JoystickButton(m_driverController2, XboxController.Button.kA.value).whileHeld();
    //new JoystickButton(m_driverController2, XboxController.Button.kY.value).whileHeld();

  }


  public static XboxController getDriveJoystick(){
    return m_driverController;
    
  }


  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_AutoCommand;
  }

}
