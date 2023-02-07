package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.constants.CANConstants;
public abstract class Tank extends SubsystemBase {
  public CANSparkMax leftFront;
  public CANSparkMax leftRear;
  public MotorControllerGroup left;

  public CANSparkMax rightFront;
  public CANSparkMax rightRear;
  public MotorControllerGroup right;

  public DifferentialDrive Tank = new DifferentialDrive(left, right);

  public Tank() {
    leftFront = new CANSparkMax(CANConstants.kLeftFrontMotorPort, MotorType.kBrushless);
    leftFront.restoreFactoryDefaults();
    leftFront.setInverted(true);
    leftFront.setIdleMode(IdleMode.kCoast);
    leftFront.burnFlash();

    leftRear = new CANSparkMax(CANConstants.kLeftRearMotorPort, MotorType.kBrushless);
    leftRear.restoreFactoryDefaults();
    leftRear.setInverted(true);
    leftRear.setIdleMode(IdleMode.kCoast);
    leftRear.burnFlash();

    left = new MotorControllerGroup(leftFront, leftRear);

    rightFront = new CANSparkMax(CANConstants.kRightFrontMotorPort, MotorType.kBrushless);
    rightFront.restoreFactoryDefaults();
    rightFront.setInverted(false);
    rightFront.setIdleMode(IdleMode.kCoast);
    rightFront.burnFlash();

    rightRear = new CANSparkMax(CANConstants.kRightRearMotorPort, MotorType.kBrushless);
    rightRear.restoreFactoryDefaults();
    rightRear.setInverted(false);
    rightRear.setIdleMode(IdleMode.kCoast);
    rightRear.burnFlash();
    right = new MotorControllerGroup(rightFront, rightRear);
  }



  @Override
  public void periodic() {
    Tank.arcadeDrive(-RobotContainer.getLeftStickY(), -RobotContainer.getLeftStickX());
  }

}
