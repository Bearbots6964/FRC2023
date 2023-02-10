package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Constants;
public class Tank extends SubsystemBase {
  public CANSparkMax leftFront;
  public CANSparkMax leftRear;
  public MotorControllerGroup left;

  public CANSparkMax rightFront;
  public CANSparkMax rightRear;
  public MotorControllerGroup right;

  public DifferentialDrive Tank = new DifferentialDrive(left, right);
  public boolean automate = false;

  public Tank() {
    leftFront = new CANSparkMax(Constants.CanConstants.kLeftFrontMotorPort, MotorType.kBrushless);
    leftFront.restoreFactoryDefaults();
    leftFront.setInverted(true);
    leftFront.setIdleMode(IdleMode.kCoast);
    leftFront.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
    leftFront.burnFlash();

    leftRear = new CANSparkMax(Constants.CanConstants.kLeftRearMotorPort, MotorType.kBrushless);
    leftRear.restoreFactoryDefaults();
    leftRear.setInverted(true);
    leftRear.setIdleMode(IdleMode.kCoast);
    leftRear.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
    leftRear.burnFlash();

    left = new MotorControllerGroup(leftFront, leftRear);
    addChild("left", left);

    rightFront = new CANSparkMax(Constants.CanConstants.kRightFrontMotorPort, MotorType.kBrushless);
    rightFront.restoreFactoryDefaults();
    rightFront.setInverted(false);
    rightFront.setIdleMode(IdleMode.kCoast);
    rightFront.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
    rightFront.burnFlash();

    rightRear = new CANSparkMax(Constants.CanConstants.kRightRearMotorPort, MotorType.kBrushless);
    rightRear.restoreFactoryDefaults();
    rightRear.setInverted(false);
    rightRear.setIdleMode(IdleMode.kCoast);
    rightRear.setOpenLoopRampRate(Constants.CanConstants.kRampRate);
    rightRear.burnFlash();
    right = new MotorControllerGroup(rightFront, rightRear);
    addChild("right", right);
  }



  @Override
  public void periodic() {
    Tank.arcadeDrive(-RobotContainer.getLeftStickY(), -RobotContainer.getLeftStickX());
  }

  /**
   * Drives the robot using arcade drive.
   *
   * @param speed The forward/backward speed.
   * @param rotation The rotation speed.
   */
  public void arcadeDrive(double speed, double rotation) {
    left.setInverted(true);
    right.setInverted(false);
    Tank.arcadeDrive(speed, rotation);
  }
  /**
   * Get the total distance travelled by a motor controller group, averaged across the two motors.
   */
  public double getDistance() {
    return (leftFront.getEncoder().getPosition() + rightFront.getEncoder().getPosition()) / 2;
  }
}
