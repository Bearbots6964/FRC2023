// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import io.github.oblarg.oblog.Logger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public static boolean inverted = false;

  // INSTANTIATES ALL JOYSTICKS

  /** @deprecated The drivers are much more comfortable with an xbox controller */
  @Deprecated
  public static final Joystick m_armController = new Joystick(0);

  public static final XboxController m_armController2 = new XboxController(1);
  public static final XboxController m_driverController = new XboxController(2);

  // INSTANTIATES ALL SUBSYSTEMS
  private final Arm m_Arm = new Arm();
  private final Claw m_Claw = new Claw();
  private final Tank m_Tank = new Tank();
  private final PID m_PID = new PID();
  private final PDP m_PDP = new PDP();



  // INSTANTIATES ALL COMMANDS
  private final MoveClawCommand m_MoveClawCommand = new MoveClawCommand(m_Claw);
  private final MoveArmYCommand m_MoveArmYCommand = new MoveArmYCommand(m_Arm);
  private final DriveCommand m_DriveCommand = new DriveCommand(m_Tank);
  private final BalanceCommand m_BalanceCommand = new BalanceCommand(m_PID, m_Tank);
  private final AutoCommand m_AutoCommand = new AutoCommand(m_PID, m_Tank, m_Arm, m_Claw);
  private final InvertDriveCommand m_InvertDriveCommand = new InvertDriveCommand(m_Tank, this);
  private final PlaceConeSecondLevelCommand m_PlaceConeSecondLevelCommand = new PlaceConeSecondLevelCommand(m_Tank,
      m_Arm, m_Claw);
  private final IncreaseMaxSpeedCommand m_IncreaseMaxSpeedCommand = new IncreaseMaxSpeedCommand(m_Tank);
  private final DecreaseMaxSpeedCommand m_DecreaseMaxSpeedCommand = new DecreaseMaxSpeedCommand(m_Tank);
  private final FineDriveCommand m_FineDriveCommand = new FineDriveCommand(m_Tank);
  private final PlaceCubeSecondLevelCommand m_PlaceCubeSecondLevelCommand = new PlaceCubeSecondLevelCommand(m_Tank,
      m_Arm, m_Claw);

  private GenericEntry timeWidget = Shuffleboard.getTab("stuff").add("time", 0).withWidget("Match Time").getEntry();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // add a few things to the shuffleboard
    // joysticks
    var tab = Shuffleboard.getTab("Subsystems");
    tab.add(m_Arm);
    tab.add(m_AutoCommand);
    tab.add(m_BalanceCommand);
    tab.add(m_DecreaseMaxSpeedCommand);
    tab.add(m_DriveCommand);
    tab.add(m_FineDriveCommand);
    tab.add(m_IncreaseMaxSpeedCommand);
    tab.add(m_MoveArmYCommand);
    tab.add(m_PDP);
    tab.add(m_PlaceCubeSecondLevelCommand);

    tab.add(m_PID);
    tab.add(m_PlaceConeSecondLevelCommand);
    tab.add(m_Tank);
    tab.add(m_Claw);

    Logger.configureLoggingAndConfig(this, false);
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
        .toggleOnTrue(m_InvertDriveCommand);

    // new JoystickButton(m_armController2, XboxController.Button.kX.value)
    // .whileTrue(m_CloseClawCommand);
    // new JoystickButton(m_armController2, XboxController.Button.kY.value)
    // .whileTrue(m_OpenClawCommand);
    new JoystickButton(m_armController2, XboxController.Button.kLeftBumper.value)
        .whileTrue(m_FineDriveCommand);
    new JoystickButton(m_armController2, XboxController.Button.kY.value).whileTrue(m_PlaceConeSecondLevelCommand);

    // new JoystickButton(m_armController2, XboxController.Button.kB.value)
    // .whileTrue(m_PlaceConeSecondLevelCommand);
  }

  /**
   * gets the value of the left stick on the driver controller to be used for
   * turning
   */
  public static double getTurningStickInput() {
    // uses the left stick on the driver controller
    double axis = m_driverController.getRawAxis(1);
    // 0.60 is the minimum amount of power we need
    if (Math.abs(axis) < 0.01) {
      axis = 0;
    }
    return axis * -1;
  }

  /**
   * returns value used for turning
   */
  public static double getAdjustedTurningStickInput() {
    double val = getTurningStickInput();
    if (val > 0) {
      return (val * val + 0.3) / 1.3;
    } else if (val < 0) {
      return -(val * val + 0.3) / 1.3;
    } else {
      return 0;
    }
  }

  /**
   * gets the value of the right stick on the driver controller to be used for
   * forward and backward movement
   */
  public static double getForwardStickInput() {
    double axis = m_driverController.getRawAxis(4);
    if (Math.abs(axis) < 0.01) {
      axis = 0;
    }
    return axis;
  }

  /** returns value used for forward and backward movement */
  public static double getAdjustedForwardStickInput() {
    double val = getForwardStickInput();
    if (val > 0) {
      return (val * val + 0.1) / 1.1;
    } else if (val < 0) {
      return -(val * val + 0.1) / 1.1;
    } else {
      return 0;
    }
  }

  /**
   * This function gets the joystick value of the left stick on the arm controller
   * The left stick is used to move the arm up and down
   * The function returns the value of the left stick
   * The value of the left stick is put on the smart dashboard
   * 
   * @deprecated This controller is no longer used
   */
  @Deprecated
  public static double getJoystickArmControllerLeftStickY() {
    double axis = m_armController.getRawAxis(1);
    SmartDashboard.putNumber("arm left stick y", axis);

    if (Math.abs(axis) < 0.05) {
      axis = 0;
    }
    return axis;
  }

  /**
   * This function gets the joystick x-value of the right stick on the arm
   * controller
   * The right stick is used to move the arm left and right
   * The function returns the value of the right stick
   * The value of the right stick is put on the smart dashboard
   * 
   * @deprecated The turret is no longer on the arm, and this controller is no
   *             longer used
   */
  @Deprecated
  public static double getJoystickArmControllerRightStickX() { // "do not forget to remove this deprecated code someday"
                                                               // lmao never
    double axis = m_armController.getRawAxis(4);
    SmartDashboard.putNumber("arm right stick x", axis);

    if (Math.abs(axis) < 0.05) {
      axis = 0;
    }
    return axis;
  }

  /**
   * This function gets the y-value of the left stick on the arm controller
   * The left stick is used to move the arm up and down
   * The function returns the value of the left stick
   */
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

  /**
   * gets value from the right trigger axis (yes, it's an axis) for grabbing up
   * the game piece
   */

  public static double getClawInValue() {
    double axis = m_armController2.getRightTriggerAxis();
    if (Math.abs(axis) < 0.01) {
      axis = 0;
    }
    return axis;
  }

  /**
   * gets value from the left trigger axis (yes, it's an axis) for ejecting the
   * game piece
   */
  public static double getClawOutValue() {
    double axis = m_armController2.getLeftTriggerAxis();
    if (Math.abs(axis) < 0.01) {
      axis = 0;
    }
    return axis;
  }

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_AutoCommand;
  }

  public void initTeleop() {
    if (inverted) {
      m_Tank.setDefaultCommand(m_InvertDriveCommand);
    } else {
      m_Tank.setDefaultCommand(m_DriveCommand);
    }
    m_Arm.setDefaultCommand(m_MoveArmYCommand);
    m_Claw.setDefaultCommand(m_MoveClawCommand);
  }

  public void initTest() {
    // Set the default tank command to DriveCommand

  }

  public void robotPeriodic() {
    Logger.updateEntries();
    timeWidget.setDouble(DriverStation.getMatchTime());
  }
}
