// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private static double lastAxis;

  public static final Joystick m_armController = new Joystick(0);
  public static final XboxController m_armController2 = new XboxController(1);
  public static final XboxController m_driverController = new XboxController(2);

  // INSTANTIATES ALL SUBSYSTEMS
  private final Arm m_Arm = new Arm();
  private final Claw m_claw = new Claw();
  private final Tank m_Tank = new Tank();
  private final Turret m_Turret = new Turret();
  private final PID m_PID = new PID();
  private final PDP m_PDP = new PDP();

  // INSTANTIATES ALL COMMANDS
  private final OpenClawCommand m_OpenClawCommand = new OpenClawCommand(m_claw);
  private final CloseClawCommand m_CloseClawCommand = new CloseClawCommand(m_claw);
  private final MoveArmXCommand m_MoveArmXCommand = new MoveArmXCommand(m_Turret);
  private final MoveArmYCommand m_MoveArmYCommand = new MoveArmYCommand(m_Arm);
  private final DriveCommand m_DriveCommand = new DriveCommand(m_Tank);
  private final BalanceCommand m_BalanceCommand = new BalanceCommand(m_PID, m_Tank);
  private final PlaceConeSecondLevelCommand m_PlaceConeSecondLevelCommand =
      new PlaceConeSecondLevelCommand(m_Tank, m_Arm);
  private final AutoCommand m_AutoCommand = new AutoCommand(m_PID, m_Tank, m_claw, m_Arm);
  private final IncreaseMaxSpeedCommand m_IncreaseMaxSpeedCommand =
      new IncreaseMaxSpeedCommand(m_Tank);
  private final DecreaseMaxSpeedCommand m_DecreaseMaxSpeedCommand =
      new DecreaseMaxSpeedCommand(m_Tank);
  private final SwitchIdleModeCommmand m_SwitchIdleModeCommmand =
      new SwitchIdleModeCommmand(m_Tank);
  private final FineDriveCommand m_FineDriveCommand = new FineDriveCommand(m_Tank);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // add a few things to the shuffleboard
    // joysticks
    var tab = Shuffleboard.getTab("Subsystems");
    tab.add(m_Arm);
    tab.add(m_AutoCommand);
    tab.add(m_BalanceCommand);
    tab.add(m_CloseClawCommand);
    tab.add(m_DecreaseMaxSpeedCommand);
    tab.add(m_DriveCommand);
    tab.add(m_FineDriveCommand);
    tab.add(m_IncreaseMaxSpeedCommand);
    tab.add(m_MoveArmYCommand);
    tab.add(m_PDP);

    tab.add(m_PID);
    tab.add(m_PlaceConeSecondLevelCommand);
    tab.add(m_SwitchIdleModeCommmand);
    tab.add(m_Tank);
    tab.add(m_Turret);
    tab.add(m_claw);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  private void configureButtonBindings() {
    new JoystickButton(m_driverController, XboxController.Button.kY.value)
        .whileTrue(m_BalanceCommand);
    new JoystickButton(m_driverController, XboxController.Button.kStart.value)
        .whileTrue(m_IncreaseMaxSpeedCommand);
    new JoystickButton(m_driverController, XboxController.Button.kBack.value)
        .whileTrue(m_DecreaseMaxSpeedCommand);
    new JoystickButton(m_driverController, XboxController.Button.kX.value)
        .whileTrue(m_SwitchIdleModeCommmand);

    new JoystickButton(m_armController2, XboxController.Button.kX.value)
        .whileTrue(m_OpenClawCommand);
    new JoystickButton(m_armController2, XboxController.Button.kY.value)
        .whileTrue(m_CloseClawCommand);
    new JoystickButton(m_armController2, XboxController.Button.kA.value)
        .whileTrue(m_FineDriveCommand);
    // new JoystickButton(m_armController2,
    // XboxController.Button.kY.value).whileTrue(m_PlaceCubeFirstLevelCommand);
    // new JoystickButton(m_armController2,
    // XboxController.Button.kX.value).whileTrue(m_PlaceConeSecondLevelCommand);
  }

  public static double getDriverControllerLeftStickY() {
    double axis = m_driverController.getRawAxis(1);
    // 0.60 is the minimum amount of power we need
    if (Math.abs(axis) < 0.01) {
      axis = 0;
    }
    return axis * -1;
  }

  public static double getDriverControllerLeftStickYAdjusted() {
    double val = getDriverControllerLeftStickY();
    if (val > 0) {
      return (val * val + 0.6) / 1.6;
    } else if (val < 0) {
      return -(val * val + 0.6) / 1.6;
    } else {
      return 0;
    }
  }

  public static double getDriverControllerRightStickX() {
    double axis = m_driverController.getRawAxis(4);
    if (Math.abs(axis) < 0.01) {
      axis = 0;
    }
    return axis;
  }

  public static double getDriverControllerRightStickXAdjusted() {
    double val = getDriverControllerRightStickX();
    if (val > 0) {
      return (val * val + 0.6) / 1.6;
    } else if (val < 0) {
      return -(val * val + 0.6) / 1.6;
    } else {
      return 0;
    }
  }

  public static double getJoystickArmControllerLeftStickY() {
    double axis = m_armController.getRawAxis(1);
    SmartDashboard.putNumber("arm left stick y", axis);

    if (Math.abs(axis) < 0.05) {
      axis = 0;
    }
    return axis;
  }

  public static double getJoystickArmControllerRightStickX() {
    double axis = m_armController.getRawAxis(4);
    SmartDashboard.putNumber("arm right stick x", axis);

    if (Math.abs(axis) < 0.05) {
      axis = 0;
    }
    return axis;
  }

  public static double getControllerLeftStickY() {
    double axis = m_armController2.getRawAxis(1);
    if (Math.abs(axis) < 0.1) {
      axis = 0;
    }
    return axis;
  }

  public static double getControllerRightStickX() {
    double axis = m_armController2.getRawAxis(4);
    if (Math.abs(axis) < 0.4) {
      axis = 0;
    }
    return axis;
  }

  public static double getJoystickArmY() {
    double axis = m_armController.getRawAxis(1);
    if (Math.abs(axis) < 0.15) {
      axis = 0;
    }
    return axis;
  }

  public static double getJoystickArmTwist() {
    double axis = m_armController.getRawAxis(2);
    if (Math.abs(axis) < 0.15) {
      axis = 0;
    }
    return axis;
  }

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_AutoCommand;
  }

  public void initTeleop() {
    // Set the default tank command to DriveCommand
    m_Tank.setDefaultCommand(m_DriveCommand);
    m_Turret.setDefaultCommand(m_MoveArmXCommand);
    m_Arm.setDefaultCommand(m_MoveArmYCommand);
  }
}
