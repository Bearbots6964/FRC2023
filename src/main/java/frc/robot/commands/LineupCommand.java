package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LineupPID;
import frc.robot.subsystems.Tank;

public class LineupCommand extends CommandBase {
    private LineupPID m_lineupPID;
    private double m_output;

    public LineupCommand(LineupPID lineupPID) {
        m_lineupPID = lineupPID;
        addRequirements(m_lineupPID);
    }

    @Override
    public void initialize() {
        m_lineupPID.setSetpoint();
    }

    @Override
    public void execute() {
        m_output = m_lineupPID.m_output;
        Tank.arcadeDrive(0, m_output);
    }

    @Override
    public void end(boolean interrupted) {
        m_lineupPID.disable();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}