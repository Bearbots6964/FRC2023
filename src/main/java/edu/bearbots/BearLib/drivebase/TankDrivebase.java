package edu.bearbots.BearLib.drivebase;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public abstract class TankDrivebase extends Drivebase {

    private CANSparkMax frontLeft;
    private CANSparkMax backLeft;
    private CANSparkMax frontRight;
    private CANSparkMax backRight;

    private static DifferentialDrive differentialDrive;

    public TankDrivebase(CANSparkMax frontLeft, CANSparkMax backLeft, CANSparkMax frontRight, CANSparkMax backRight) {
        backLeft.follow(frontLeft);
        backRight.follow(frontRight);
        differentialDrive = new DifferentialDrive(frontLeft, frontRight);

        this.frontLeft = frontLeft;
        this.backLeft = backLeft;
        this.frontRight = frontRight;
        this.backRight = backRight;
    }

    public static void arcadeDrive(double speed, double rotation) {
        differentialDrive.arcadeDrive(speed, rotation);
    }

    public void setSmartCurrentLimit(int stallSpeed, int freeSpeed) {
        this.setSmartCurrentLimit(stallSpeed, freeSpeed);
    }

    // configure a PID controller for the motor
    public void configurePID(double kP, double kI, double kD, double kIz, double kFF, double kMaxOutput, double kMinOutput) {
        frontLeft.getPIDController().setP(kP);
        frontLeft.getPIDController().setI(kI);
        frontLeft.getPIDController().setD(kD);
        frontLeft.getPIDController().setIZone(kIz);
        frontLeft.getPIDController().setFF(kFF);
        frontLeft.getPIDController().setOutputRange(kMinOutput, kMaxOutput);

        frontRight.getPIDController().setP(kP);
        frontRight.getPIDController().setI(kI);
        frontRight.getPIDController().setD(kD);
        frontRight.getPIDController().setIZone(kIz);
        frontRight.getPIDController().setFF(kFF);
        frontRight.getPIDController().setOutputRange(kMinOutput, kMaxOutput);
    }
}