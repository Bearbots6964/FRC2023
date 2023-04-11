package edu.bearbots.BearLib.drivebase;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public abstract class TankDrivebase extends Drivebase {
    private static DifferentialDrive differentialDrive;
    public TankDrivebase(CANSparkMax frontLeft, CANSparkMax backLeft, CANSparkMax frontRight, CANSparkMax backRight) {
        backLeft.follow(frontLeft);
        backRight.follow(frontRight);
        differentialDrive = new DifferentialDrive(frontLeft, frontRight);
    }
}