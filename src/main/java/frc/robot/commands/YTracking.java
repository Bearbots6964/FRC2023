// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Tank;
import frc.robot.subsystems.Tracking.Constants;
import frc.robot.subsystems.Tracking.Target;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class YTracking extends PIDCommand {
  public static double kOutput;
  /** Creates a new YTracking. */
  public YTracking() {
    super(
        // The controller that the command will use
        new PIDController(Constants.yConstants.getkP(), Constants.yConstants.getkI(), Constants.yConstants.getkD()),
        // This should return the measurement
        Target::getTY,
        // This should return the setpoint (can also be a constant)
        () -> Constants.yConstants.getkSetpoint(),
        // This uses the output
        output -> {
          // Use the output here
          kOutput = output;
          Tank.arcadeDrive(XTracking.kOutput, output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
