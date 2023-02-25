// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.*;

public class Arm extends SubsystemBase {
  private CANSparkMax yMotor;

  public static RelativeEncoder yEncoder;

  public static DigitalInput zeroDegreesLS = new DigitalInput(1);

  public double desiredArmAngle, currentArmAngle;
  
  public double gearRatio = 87;

  public Arm() {
    yMotor = new CANSparkMax(7, MotorType.kBrushless);
    yMotor.setIdleMode(IdleMode.kBrake);
    yMotor.setSmartCurrentLimit(5, 10);
    yMotor.burnFlash();

    yEncoder = yMotor.getEncoder();
  }

  @Override
  public void periodic() {
<<<<<<< Updated upstream
    SmartDashboard.putNumber("Arm Encoder", yEncoder.getPosition());
    SmartDashboard.putNumber("Arm Angle", currentArmAngle);
    SmartDashboard.putBoolean("Zero Degrees LS", zeroDegreesLS.get());

=======

    // Get info from NetworkTables
    yMotor.setSmartCurrentLimit(NetworkTables.getInt(NetworkTableInstance.getDefault().getTable("arm"), "stallCurrent"), NetworkTables.getInt(arm, "freeCurrent"));
  
>>>>>>> Stashed changes
  }

  public void liftArm(double leftStickYaxis) {
    double speed = 0.8;
    double motorDrive = leftStickYaxis * speed;
    yMotor.set(motorDrive);
  }

  public void moveArmToZeroDeg() {
    double speedY = 0.2;
    while (zeroDegreesLS.get() == true) {
      yMotor.set(-1 * speedY);
    }
    yMotor.set(0);
    yEncoder.setPosition(0);
    currentArmAngle = 0;
  }

  public double degreesToRotations(double difference) {
    // 0.42 degrees/spin of the motor
    return difference / 0.42;
  }

  // the diseredArmAngle is currently not set to anything because since we don't know the length of
  // the amr, we can't calculate the angle it should be
  public void liftArmToNode(int level) {
    double speedY = 0.15;
    double difference = desiredArmAngle - currentArmAngle;
    yEncoder.setPosition(0);
    while (Math.abs(yEncoder.getPosition()) < degreesToRotations(difference)) {
      yMotor.set(-speedY);
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  
}
