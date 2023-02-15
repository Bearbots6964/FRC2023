package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.CanConstants;
import frc.robot.Constants;
public class Tank extends SubsystemBase {
  public CANSparkMax leftFront;
  public CANSparkMax leftRear;
  public MotorControllerGroup left;

  public CANSparkMax rightFront;
  public CANSparkMax rightRear;
  public MotorControllerGroup right;

  public DifferentialDrive drive;

  public boolean automate;


  /** */
  public Tank() {
    if(CanConstants.kBaseType == "tank") {
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

    right = new MotorControllerGroup(rFront, rRear);
    addChild("Right", right);

    drive = new DifferentialDrive(left, right);
    addChild("Drive", drive);
    drive.setSafetyEnabled(true);
    drive.setExpiration(0.1);
    drive.setMaxOutput(1.0);

    automate = false;
  }


  @Override
  public void periodic() {
  }

  @Override
  public void simulationPeriodic() {
  }

  /**
   * Drives the robot using arcade drive.
   *
   * @param speed The forward/backward speed.
   * @param rotation The rotation speed.
   */
  public void arcadeDrive(double speed, double rotation) {
    try {
      tank.arcadeDrive(speed * RobotContainer.getMaxSpeed(), rotation * RobotContainer.getMaxSpeed());
    } catch (Exception e) {
      throw e;
    }
  }
  /**
   * Get the total distance travelled by a motor controller group, averaged across the two motors.
   */
  public double getDistance() {
    try {
      return (leftFront.getEncoder().getPosition() + rightFront.getEncoder().getPosition()) / 2;
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Change the ramp rate of the motor controllers.
   */
  public void setRampRate(double rampRate) {
    try {
    leftFront.setOpenLoopRampRate(rampRate);
    leftRear.setOpenLoopRampRate(rampRate);
    rightFront.setOpenLoopRampRate(rampRate);
    rightRear.setOpenLoopRampRate(rampRate);
    } catch (Exception e) {
      throw e;
    }
  }
}
