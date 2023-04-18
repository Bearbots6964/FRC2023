// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Tank;
import frc.robot.subsystems.Tracking.Constants;
import frc.robot.subsystems.Tracking.Target;
import frc.robot.subsystems.Tracking.Constants.xConstants;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class XTracking extends PIDCommand {

  private double kp;
  private double ki;
  private double kd;

  public static double kOutput;

  private static Tank m_tank;

  /** Creates a new XTracking. */
  public XTracking(Tank tank) {

    super(
        // The controller that the command will use
        new PIDController(Constants.xConstants.getkP(), Constants.xConstants.getkI(), Constants.xConstants.getkD()),
        // This should return the measurement
        Target::getTX,
        // This should return the setpoint (can also be a constant)
        () -> Constants.xConstants.getkSetpoint(),
        // This uses the output
        output -> {
          // Use the output here
          kOutput = output;

          m_tank.arcadeDrive(YTracking.kOutput, output);
        });
    addRequirements(tank);
    m_tank = tank;
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Constants.xConstants.getLowerBound() < (Target.getTX() - Constants.xConstants.getkSetpoint())
        && (Target.getTX() - Constants.xConstants.getkSetpoint()) < Constants.xConstants.getUpperBound();
  }
}
