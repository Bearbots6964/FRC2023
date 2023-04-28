package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AutoArmPostioning extends PIDSubsystem {
    private static double kP = 0.18;
    private static double kI = 0;
    private static double kD = 0;
    public PIDController m_controller;
    public double target;
    public Arm m_arm;

    public AutoArmPostioning(Arm arm) {
        super(
                // PIDController
                new PIDController(kP, kI, kD));
        m_arm = arm;
        m_controller.setTolerance(0.1);
        m_controller.setSetpoint(0);
    }

    public void setTarget(double target) {
        this.target = target;
        m_controller.setSetpoint(target);
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        m_arm.moveArm(output);
    }

    @Override
    protected double getMeasurement() {
        return m_arm.getAngle();
    }

    public boolean atSetpoint() {
        return m_controller.atSetpoint();
    }

    @Override
    public void setSetpoint(double setpoint) {
        m_controller.setSetpoint(setpoint);
    }

    public void setPID(double p, double i, double d) {
        m_controller.setPID(p, i, d);
    }

    public void setTolerance(double tolerance) {
        m_controller.setTolerance(tolerance);
    }

    @Override
    public void periodic() {
        super.periodic();
        SmartDashboard.putNumber("caluclated power to arm form PID",
                m_controller.calculate(getMeasurement(), m_controller.getSetpoint()));
    }

}
