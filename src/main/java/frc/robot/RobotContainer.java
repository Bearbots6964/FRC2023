// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.*;
// import frc.robot.Constants.AutoConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // RR 1/11/2022
  public static final XboxController m_driverController = new XboxController(0);
  // public static final XboxController m_driverController2 = new XboxController(1);//change
  public static final Joystick m_joystick1 = new Joystick(1);

  // INSTANTIATES ALL SUBSYSTEMS
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Mecanum m_MechanumDrive = new Mecanum();
  private final RotatingArm m_rotatingArm = new RotatingArm();
  private final Claw m_claw = new Claw();
  private final PIDmecanum m_PIDmecanum = new PIDmecanum();
  // INSTANTIATES ALL COMMANDS
  private final ExampleCommand m_exampleCommand = new ExampleCommand(m_exampleSubsystem);
  private final AutoCommand m_AutoCommand = new AutoCommand(m_MechanumDrive, m_PIDmecanum, .5);
  private final OpenClawCommand m_OpenClawCommand = new OpenClawCommand(m_claw);
  private final CloseClawCommand m_CloseClawCommand = new CloseClawCommand(m_claw);

  private final Joystick m_driverJoystick = new Joystick(1);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    // (RobotDrive.m_RLencoder).setPosition(0);
    // (RobotDrive.m_RRencoder).setPosition(0);
    // (RobotDrive.m_FLencoder).setPosition(0);
    // (RobotDrive.m_FRencoder).setPosition(0);
    // m_gyro.calibrate();
  }

  public static double getLeftStickY() {
    double axis = m_driverController.getRawAxis(1);
    if (Math.abs(axis) < 0.02) {
      axis = 0;
    }
    return axis * -1;
  }

  public static double getLeftStickX() {
    double axis = m_driverController.getRawAxis(0);
    if (axis < 0.02 && axis > -0.02) {
      axis = 0;
    }
    return axis;
  }

  public static double getRightStickX() {
    double axis = m_driverController.getRawAxis(4);
    if (axis < 0.02 && axis > -0.02) {
      axis = 0;
    }
    return axis;
  }

  public static double getRightStickY() {
    double axis = m_driverController.getRawAxis(5);
    if (axis < 0.02 && axis > -0.02) {
      axis = 0;
    }
    return axis;
  }

  public static double getJoystickXAxis() {
    double axis = m_joystick1.getRawAxis(0);
    if (Math.abs(axis) < 0.02) {
      axis = 0;
    }
    return axis;
  }

  public static double getJoystickYAxis() {
    double axis = m_joystick1.getRawAxis(1);
    if (Math.abs(axis) < 0.02) {
      axis = 0;
    }
    return axis;
  }

  // z-axis is twist
  public static double getJoystickZAxis() {
    double axis = m_joystick1.getRawAxis(2);
    if (Math.abs(axis) < 0.2) {
      axis = 0;
    }
    return axis;
  }

  public static double getMaxSpeed() {
    return (m_joystick1.getRawAxis(3) + 1) / 2;
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Not sure if it works because I don't know how to use simulations
    // new JoystickButton(m_driverJoystick, 1).whileTrue(m_servoSubsystem.servoTest());

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  // public Command getAutonomousCommand() {
  // An example command will be run in autonomous
  // return Autos.exampleAuto(m_exampleSubsystem);
  // }
  private void configureButtonBindings() {
    // new JoystickButton(m_driverController,
    // XboxController.Button.kB.value).whileHeld(m_TestLeftFront);
    // new JoystickButton(m_driverController, XboxController.Button.kX.value).whileHeld();
    // new JoystickButton(m_driverController, XboxController.Button.kRightBumper.value).whileHeld();
    new JoystickButton(m_driverController, XboxController.Button.kY.value)
        .whileHeld(m_CloseClawCommand);
    new JoystickButton(m_driverController, XboxController.Button.kA.value)
        .whileHeld(m_OpenClawCommand);
    // new JoystickButton(m_driverController, XboxController.Button.kLeftBumper.value).whileHeld();

    // new JoystickButton(m_driverController2, XboxController.Button.kA.value).whileHeld();
    // new JoystickButton(m_driverController2, XboxController.Button.kY.value).whileHeld();

  }

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_AutoCommand;
  }
}
