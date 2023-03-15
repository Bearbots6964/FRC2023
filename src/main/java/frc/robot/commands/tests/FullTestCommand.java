package frc.robot.commands.tests;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class FullTestCommand extends InstantCommand {
    private final TestArmCommand m_arm;
    private final TestClawCommand m_claw;
    private final TestDrivebaseCommand m_drive;
    private final TestTurretCommand m_turret;

    public FullTestCommand(TestArmCommand arm, TestClawCommand claw, TestDrivebaseCommand drive, TestTurretCommand turret) {
        m_arm = arm;
        m_claw = claw;
        m_drive = drive;
        m_turret = turret;
    }

    @Override
    public void execute() {
        m_arm.execute();
        m_claw.execute();
        m_drive.execute();
        m_turret.execute();
    }
    
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}