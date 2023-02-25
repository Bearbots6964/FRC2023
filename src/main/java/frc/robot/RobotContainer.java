// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
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

  // The robot's subsystems and commands are defined here...
  // RR 1/11/2022
  public static final XboxController m_armController = new XboxController(0);
  public static final XboxController m_driverController = new XboxController(1);

  // INSTANTIATES ALL SUBSYSTEMS
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Arm m_Arm = new Arm();
  private final Claw m_claw = new Claw();
  private final Tank m_Tank = new Tank();
  private final Turret m_Turret = new Turret();
  // private final Vision m_vision = new Vision();
  private final PID m_PID = new PID();
  private final Odometry m_Odometry = new Odometry(m_PID.gyro, m_Tank);


  // INSTANTIATES ALL COMMANDS
  // private final ExampleCommand m_exampleCommand = new ExampleCommand(m_exampleSubsystem);
  private final OpenClawCommand m_OpenClawCommand = new OpenClawCommand(m_claw);
  private final CloseClawCommand m_CloseClawCommand = new CloseClawCommand(m_claw);
  // private final BalanceCommand m_ChargeUpBalanceCommand = new BalanceCommand(m_PID, m_Tank);
  // private final ArmToFirstLevelCommand m_ArmToFirstLevelCommand = new ArmToFirstLevelCommand(m_Turret, m_Arm);
  // private final ArmToSecondLevelCommand m_ArmToSecondLevelCommand = new ArmToSecondLevelCommand(m_Turret, m_Arm);
  // private final ArmToThirdLevelCommand m_ArmToThirdLevelCommand = new ArmToThirdLevelCommand(m_Turret, m_Arm);
  private final MoveArmXCommand m_MoveArmXCommand = new MoveArmXCommand(m_Turret);
  private final MoveArmYCommand m_MoveArmYCommand = new MoveArmYCommand(m_Arm);
  private final DriveCommand m_DriveCommand = new DriveCommand(m_Tank);
  private final BalanceCommand m_BalanceCommand = new BalanceCommand(m_PID, m_Tank);
  private final AutoCommand m_AutoCommand = new AutoCommand(m_PID, m_Tank);
  private final IncreaseMaxSpeedCommand m_IncreaseMaxSpeedCommand = new IncreaseMaxSpeedCommand(m_Tank);
  private final DecreaseMaxSpeedCommand m_DecreaseMaxSpeedCommand = new DecreaseMaxSpeedCommand(m_Tank);
  private final SwitchIdleModeCommmand m_SwitchIdleModeCommmand = new SwitchIdleModeCommmand(m_Tank);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  private void configureButtonBindings() {
    new JoystickButton(m_driverController, XboxController.Button.kY.value).whileTrue(m_BalanceCommand);
    new JoystickButton(m_driverController, XboxController.Button.kStart.value).whileTrue(m_IncreaseMaxSpeedCommand);
    new JoystickButton(m_driverController, XboxController.Button.kBack.value).whileTrue(m_DecreaseMaxSpeedCommand);
    new JoystickButton(m_driverController, XboxController.Button.kX.value).whileTrue(m_SwitchIdleModeCommmand);

    new JoystickButton(m_armController, XboxController.Button.kA.value).whileTrue(m_CloseClawCommand);
    new JoystickButton(m_armController, XboxController.Button.kB.value).whileTrue(m_OpenClawCommand);
  }

  public static double getLeftStickY() {
    double axis = m_driverController.getRawAxis(1);
    if (Math.abs(axis) < 0.03) {
      axis = 0;
    }
    return axis * -1;
  }

  public static double getLeftStickX() {
    double axis = m_driverController.getRawAxis(0);
    if (Math.abs(axis) < 0.03) {
      axis = 0;
    }
    return axis;
  }

  public static double getRightStickX() {
    double axis = m_driverController.getRawAxis(4);
    if (Math.abs(axis) < 0.03) {
      axis = 0;
    }
    return axis;
  }

  public static double getRightStickY() {
    double axis = m_driverController.getRawAxis(5);
    if (Math.abs(axis) < 0.03) {
      axis = 0;
    }
    return axis;
  }

  public static double getArmControllerLeftStickY() {
    double axis = m_armController.getRawAxis(1);
    SmartDashboard.putNumber("arm left stick y", axis);

    if (Math.abs(axis) < 0.05) {
      axis = 0;
    }
    return axis * -1;
  }

  public static double getArmControllerRightStickX() {
    double axis = m_armController.getRawAxis(4);
    SmartDashboard.putNumber("arm right stick x", axis);

    if (Math.abs(axis) < 0.05) {
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
