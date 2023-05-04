// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.commands.SetPointCommands.CancelSetPointCommands;
import frc.robot.commands.SetPointCommands.moveToSetPoint1;
import frc.robot.commands.SetPointCommands.moveToSetPoint2;
import frc.robot.commands.SetPointCommands.moveToSetPoint3;
import frc.robot.commands.SetPointCommands.moveToSetPoint4;
import frc.robot.commands.SetPointCommands.moveToSetPoint5;
import frc.robot.commands.SetPointCommands.moveToSetPoint6;
import frc.robot.subsystems.*;
import frc.robot.util.SystemChecks;
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
  public boolean inverted = false;

  // private ShuffleboardLayout m_widget = Shuffleboard.getTab("Main").getLayout("Arm System", BuiltInLayouts.kList)
  //     .withPosition(34, 0).withSize(5, 8);

  // INSTANTIATES ALL JOYSTICKS


  public static final Joystick m_buttonPad = new Joystick(0);

  public static final XboxController m_armController = new XboxController(1);
  public static final XboxController m_driverController = new XboxController(2);

  // INSTANTIATES ALL SUBSYSTEMS
  private final Arm m_Arm = new Arm();
  private final Claw m_Claw = new Claw();
  private final Tank m_Tank = new Tank();
  private final AutoBalance m_Balance = new AutoBalance();
  private final PDP m_PDP = new PDP();
  private final AutoPiecePickUp m_Vision = new AutoPiecePickUp(m_Tank, m_Claw);
  private final PoleTracking m_PoleTracking = new PoleTracking(m_Tank);

  // INSTANTIATES ALL COMMANDS
  private final MoveClawCommand m_MoveClawCommand = new MoveClawCommand(m_Claw);
  private final MoveArmYCommand m_MoveArmYCommand = new MoveArmYCommand(m_Arm);
  private final DriveCommand m_DriveCommand = new DriveCommand(m_Tank);
  private final BalanceCommand m_BalanceCommand = new BalanceCommand(m_Balance, m_Tank);
  private final InvertDriveCommand m_InvertDriveCommand = new InvertDriveCommand(m_Tank, this);
  private final PlaceConeSecondLevelCommand m_PlaceConeSecondLevelCommand = new PlaceConeSecondLevelCommand(m_Tank,
      m_Arm, m_Claw);
  private final IncreaseMaxSpeedCommand m_IncreaseMaxSpeedCommand = new IncreaseMaxSpeedCommand(m_Tank);
  private final DecreaseMaxSpeedCommand m_DecreaseMaxSpeedCommand = new DecreaseMaxSpeedCommand(m_Tank);
  private final FineDriveCommand m_FineDriveCommand = new FineDriveCommand(m_Tank);
  // private final PlaceCubeSecondLevelCommand m_PlaceCubeSecondLevelCommand = new PlaceCubeSecondLevelCommand(m_Tank,
  //     m_Arm, m_Claw);
  private final TrackPiece m_TrackPiece = new TrackPiece(m_Vision, m_Tank, m_Claw);
  private final TrackPole m_TrackPole = new TrackPole(m_PoleTracking, m_Tank);

  private final AutoCommands m_AutoCommands = new AutoCommands();
  // auto commands
  private final AutoCommands.MiddleAutoWCone m_MiddleAutoWCone = m_AutoCommands.new MiddleAutoWCone(m_Balance, m_Tank,
      m_Arm,
      m_Claw);

  private final AutoCommands.MiddleAutoWCube m_MiddleAutoWCube = m_AutoCommands.new MiddleAutoWCube(m_Balance, m_Tank,
      m_Arm,
      m_Claw);

  private final AutoCommands.SideAutoWCone m_SideAutoWCone = m_AutoCommands.new SideAutoWCone(m_Balance, m_Tank, m_Arm,
      m_Claw);

  private final AutoCommands.SideAutoWCube m_SideAutoWCube = m_AutoCommands.new SideAutoWCube(m_Balance, m_Tank, m_Arm,
      m_Claw);

  private final AutoCommands.None m_None = m_AutoCommands.new None();

  private final AutoCommands.JustBalance m_JustBalance = m_AutoCommands.new JustBalance(m_Balance, m_Tank);

  private final SetPointCommands m_SetPointCommands = new SetPointCommands();
  // set point commands
  private final moveToSetPoint1 m_SetPoint1 = m_SetPointCommands.new moveToSetPoint1(m_Arm);
  private final moveToSetPoint2 m_SetPoint2 = m_SetPointCommands.new moveToSetPoint2(m_Arm);
  private final moveToSetPoint3 m_SetPoint3 = m_SetPointCommands.new moveToSetPoint3(m_Arm);
  private final moveToSetPoint4 m_SetPoint4 = m_SetPointCommands.new moveToSetPoint4(m_Arm);
  private final moveToSetPoint5 m_SetPoint5 = m_SetPointCommands.new moveToSetPoint5(m_Arm);
  private final moveToSetPoint6 m_SetPoint6 = m_SetPointCommands.new moveToSetPoint6(m_Arm);

  private final CancelSetPointCommands m_CancelSetPointCommands = m_SetPointCommands.new CancelSetPointCommands(m_Arm);

  SendableChooser<Command> m_chooser = new SendableChooser<>();

  // private final XTracking m_XTracking = new XTracking(m_Tank, null, m_Target);
  // private final YTracking m_YTracking = new YTr

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_chooser.setDefaultOption("Middle Auto W/ Cone", m_MiddleAutoWCone);
    m_chooser.addOption("Middle Auto W/ Cube", m_MiddleAutoWCube);
    m_chooser.addOption("Side Auto W/ Cone", m_SideAutoWCone);
    m_chooser.addOption("Side Auto W/ Cube", m_SideAutoWCube);
    m_chooser.addOption("Just Balance", m_JustBalance);
    m_chooser.addOption("None", m_None);
    SmartDashboard.putData("Auto mode", m_chooser);
    // Configure the button bindings
    configureButtonBindings();

    var mainTab = Shuffleboard.getTab("Main");

    var armTab = Shuffleboard.getTab("Arm");
    armTab.add(m_SetPoint1);
    armTab.add(m_SetPoint2);
    armTab.add(m_SetPoint3);

    // add the basic fms info widget to the main tab (we can just get it from
    // networktables)

    mainTab.getLayout("Arm System", BuiltInLayouts.kList).withPosition(34, 0).withSize(5, 8);
    Logger.configureLoggingAndConfig(this, false);

    mainTab.add(m_chooser).withPosition(0, 14).withSize(8, 5);

    // add the limelight stream to the main tab
    try {
      mainTab.addCamera("Limelight", "limelight", "http://10.69.64.11:5800").withPosition(8, 0).withSize(22, 19);
    } catch (Exception e) {
      System.out.println("limelight not found!");
      SystemChecks.alert.set(true);
    }

    // add the time widget to the main tab
    mainTab.addNumber("Timer", Timer::getMatchTime).withWidget("Match Time").withPosition(30, 0).withSize(4, 4);

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
    new JoystickButton(m_armController, XboxController.Button.kLeftBumper.value)
        .whileTrue(m_FineDriveCommand);
    new JoystickButton(m_armController, XboxController.Button.kY.value).whileTrue(m_PlaceConeSecondLevelCommand);

    new JoystickButton(m_armController, XboxController.Button.kX.value).whileTrue(m_TrackPiece);
    new JoystickButton(m_armController, XboxController.Button.kB.value).whileTrue(m_TrackPole);

    // joystick button 7 -> set point 1
    new JoystickButton(m_buttonPad, 7).onTrue(m_SetPoint1);
    // joystick button 8 -> set point 2
    new JoystickButton(m_buttonPad, 8).onTrue(m_SetPoint2);
    // joystick button 9 -> set point 5
    new JoystickButton(m_buttonPad, 9).onTrue(m_SetPoint5);
    // joystick button 10 -> set point 6
    new JoystickButton(m_buttonPad, 10).onTrue(m_SetPoint6);
    // joystick button 11 -> set point 4
    new JoystickButton(m_buttonPad, 11).onTrue(m_SetPoint4);
    // joystick button 12 -> set point 3
    new JoystickButton(m_buttonPad, 12).onTrue(m_SetPoint3);

    // right bumper on the arm controller cancels all set point commands
    new JoystickButton(m_armController, XboxController.Button.kRightBumper.value)
        .whileTrue(m_CancelSetPointCommands);

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
    double axis = m_armController.getRawAxis(1);
    if (Math.abs(axis) < 0.1) {
      axis = 0;
    }
    return axis;
  }

  public static double getControllerRightStickX() {
    double axis = m_armController.getRawAxis(4);
    if (Math.abs(axis) < 0.4) {
      axis = 0;
    }
    return axis;
  }

  public static double getControllerRightStickY() {
    double axis = m_armController.getRawAxis(5);
    if (Math.abs(axis) < 0.4) {
      axis = 0;
    }
    return -axis;
  }

  public static void rumbleGabeController(double rumble) {
    XboxController controller = m_driverController;
    controller.setRumble(RumbleType.kBothRumble, rumble);
  }

  /**
   * gets value from the right trigger axis (yes, it's an axis) for grabbing up
   * the game piece
   */

  public static double getClawInValue() {
    double axis = m_armController.getRightTriggerAxis();
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
    double axis = m_armController.getLeftTriggerAxis();
    if (Math.abs(axis) < 0.01) {
      axis = 0;
    }
    return axis;
  }

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_chooser.getSelected();
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

  }

}
