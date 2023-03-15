package frc.robot.commands.tests;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Tank;

public class TestDrivebaseCommand extends InstantCommand {
    private final Tank m_drivebase;

    public TestDrivebaseCommand(Tank subsystem) {
        m_drivebase = subsystem;
        addRequirements(m_drivebase);
    }

    @Override
    public void execute() {
        m_drivebase.arcadeDrive(1, 0);
        Timer.delay(1);
        m_drivebase.arcadeDrive(0, 0);
        Timer.delay(1);
        m_drivebase.arcadeDrive(-1, 0);
        Timer.delay(1);
        m_drivebase.arcadeDrive(0, 0);
        Timer.delay(1);
        m_drivebase.arcadeDrive(0, 1);
        Timer.delay(1);
        m_drivebase.arcadeDrive(0, 0);
        Timer.delay(1);
        m_drivebase.arcadeDrive(0, -1);
        Timer.delay(1);
        m_drivebase.arcadeDrive(0, 0);
        Timer.delay(1);
    }

    @Override
    public void end(boolean interrupted) {
        m_drivebase.arcadeDrive(0, 0);
    }

    
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}