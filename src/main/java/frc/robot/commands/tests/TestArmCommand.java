package frc.robot.commands.tests;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class TestArmCommand extends InstantCommand {
    private final Arm m_arm;

    public TestArmCommand(Arm subsystem) {
        m_arm = subsystem;
        addRequirements(m_arm);
    }

    @Override
    public void execute() {
        m_arm.moveArm(1);
        Timer.delay(1);
        m_arm.moveArm(0);
        Timer.delay(1);
        m_arm.moveArm(-1);
        Timer.delay(1);
        m_arm.moveArmToZeroDeg();
    }

    
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
