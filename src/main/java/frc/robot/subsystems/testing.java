// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Servo;

public class testing extends SubsystemBase {
    public Servo servo;
  public testing() {
    servo = new Servo(0);
    servo.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    //servo.setSpeed(1.0); // to open
    //servo.setSpeed(-1.0); // to close
  }

  public void extendServo(){
    servo.setSpeed(1.0);
  }

  public void retractServo(){
    servo.setSpeed(-1.0);
  }


  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
