package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Tank;

public class LineupPID extends PIDSubsystem {
    private static double kP = 0.0;
    private static double kI = 0.0;
    private static double kD = 0.0;
    private PIDController m_controller;
    public double m_output;
    public double m_setpoint;

    public LineupPID() {
        super(new PIDController(kP, kI, kD));
        addChild("Lineup Controller", getController());
        // add that sendable to the shuffleboard
        Shuffleboard.getTab("Subsystems").add(getController());
        m_controller = getController();
        
    }

    @Override
    public void useOutput(double output, double setpoint) {
        // Use the output here
        m_output = output;
        m_setpoint = setpoint;
    }

    @Override
    public double getMeasurement() {
        // Return the distance from the best retroreflective tape pole (determined by the Limelight itself, configured in the Limelight's web interface) to the center of the robot
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);

        
    }

    // Set the PID controller's setpoint to the distance from the best retroreflective tape pole (determined by the Limelight itself, configured in the Limelight's web interface) to the center of the robot
    public void setSetpoint() {
        m_controller.setSetpoint(getMeasurement());
    }

}
