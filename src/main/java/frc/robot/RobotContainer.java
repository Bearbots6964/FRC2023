// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
  public static final XboxController m_driverController = new XboxController(1);
  public static final Joystick m_joystick1 = new Joystick(2);

  // INSTANTIATES ALL SUBSYSTEMS
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Arm m_Arm = new Arm();
  private final Claw m_claw = new Claw();
  private final Mecanum m_MechanumDrive = new Mecanum();
  private final Tank m_Tank = new Tank();
  private final Turret m_Turret = new Turret();
  private final Vision m_Vision = new Vision();
  private final PID m_PID = new PID(m_Tank);

  // INSTANTIATES ALL COMMANDS
  private final ExampleCommand m_exampleCommand = new ExampleCommand(m_exampleSubsystem);
  private final OpenClawCommand m_OpenClawCommand = new OpenClawCommand(m_claw);
  private final CloseClawCommand m_CloseClawCommand = new CloseClawCommand(m_claw);
  private final BalanceCommand m_ChargeUpBalanceCommand = new BalanceCommand(m_PID, m_Tank);
  private final ArmToFirstLevelCommand m_ArmToFirstLevelCommand =
      new ArmToFirstLevelCommand(m_Turret, m_Arm);
  private final ArmToSecondLevelCommand m_ArmToSecondLevelCommand =
      new ArmToSecondLevelCommand(m_Turret, m_Arm);
  private final ArmToThirdLevelCommand m_ArmToThirdLevelCommand =
      new ArmToThirdLevelCommand(m_Turret, m_Arm);
  private final DriveCommand m_DriveCommand = new DriveCommand(m_Tank);
  private final AutoCommand m_AutoCommand =
      new AutoCommand(m_Tank, m_PID, m_Turret, m_Arm, m_claw, m_Vision);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    // Set the default tank command to DriveCommand
    m_Tank.setDefaultCommand(m_DriveCommand);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  private void configureButtonBindings() {
    // new JoystickButton(m_driverController,
    // XboxController.Button.kRightBumper.value).whileTrue(m_CloseClawCommand);
    // new JoystickButton(m_driverController,
    // XboxController.Button.kLeftBumper.value).whileTrue(m_OpenClawCommand);

    new JoystickButton(m_joystick1, Joystick.ButtonType.kTrigger.value)
        .whileTrue(m_CloseClawCommand);
    new JoystickButton(m_joystick1, Joystick.ButtonType.kTop.value).whileTrue(m_OpenClawCommand);
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

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_AutoCommand;
  }
}
