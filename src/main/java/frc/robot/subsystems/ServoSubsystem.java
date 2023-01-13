package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// An actuator subsystem
public class ServoSubsystem extends SubsystemBase {
  // Create linear actuator on PWM 0.
  private final Servo testServo = new Servo(0);

  // Set servo to 0.9 when triggered. Upon end, set servo to 0.1. I'm not sure if this code works
  // someone please test it.
  public CommandBase servoTest() {
    return this.startEnd(() -> testServo.set(0.9), () -> testServo.set(0.1));
  }
}
