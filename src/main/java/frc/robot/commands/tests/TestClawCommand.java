package frc.robot.commands.tests;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Claw;

public class TestClawCommand extends InstantCommand {
  private final Claw m_claw;

  public TestClawCommand(Claw subsystem) {
    m_claw = subsystem;
    addRequirements(m_claw);
  }

  @Override
  public void execute() {
    m_claw.openClaw();
    Timer.delay(0.5);
    m_claw.closeClaw();
    Timer.delay(0.5);
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
