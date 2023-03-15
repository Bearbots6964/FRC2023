package frc.robot.commands.tests;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Turret;

public class TestTurretCommand extends InstantCommand {
    private final Turret m_turret;

    public TestTurretCommand(Turret subsystem) {
        m_turret = subsystem;
        addRequirements(m_turret);
    }

    @Override
    public void execute() {
        m_turret.rotateTurret(1);
        Timer.delay(1);
        m_turret.rotateTurret(0);
        Timer.delay(1);
        m_turret.rotateTurret(-1);
        Timer.delay(1);
        m_turret.rotateTurret(0);
        Timer.delay(1);
    }

    @Override
    public void end(boolean interrupted) {
        m_turret.rotateTurret(0);
    }

    
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
