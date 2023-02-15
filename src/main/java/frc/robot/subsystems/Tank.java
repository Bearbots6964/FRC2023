package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.commands.*;

/** */
public class Tank extends SubsystemBase {

  public CANSparkMax lFront;
  public CANSparkMax lRear;
  public CANSparkMax rFront;
  public CANSparkMax rRear;

  public MotorControllerGroup left;
  public MotorControllerGroup right;

  public DifferentialDrive drive;

  public boolean automate;

  public Tank() {
    /*LEFT */
    lFront = new CANSparkMax(4, MotorType.kBrushless);
    lFront.restoreFactoryDefaults();
    lFront.setInverted(false);
    lFront.setIdleMode(IdleMode.kCoast);
    lFront.burnFlash();

    lRear = new CANSparkMax(14, MotorType.kBrushless);
    lRear.restoreFactoryDefaults();
    lRear.setInverted(false);
    lRear.setIdleMode(IdleMode.kCoast);
    lRear.burnFlash();

    left = new MotorControllerGroup(lFront, lRear);
    addChild("Left", left);

    /*RIGHT */
    rFront = new CANSparkMax(2, MotorType.kBrushless);
    rFront.restoreFactoryDefaults();
    rFront.setInverted(false);
    rFront.setIdleMode(IdleMode.kCoast);
    rFront.burnFlash();

    rRear = new CANSparkMax(13, MotorType.kBrushless);
    rRear.restoreFactoryDefaults();
    rRear.setInverted(false);
    rRear.setIdleMode(IdleMode.kCoast);
    rRear.burnFlash();

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
    left.setInverted(true);
    right.setInverted(false);
    drive.arcadeDrive(speed, rotation);
  }
}
