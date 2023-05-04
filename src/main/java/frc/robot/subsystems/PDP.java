package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.Map;

public class PDP extends SubsystemBase {
  public PowerDistribution m_pdp;
  ShuffleboardTab tab;
  ComplexWidget widget;

  public PDP() {
    m_pdp = new PowerDistribution(0, ModuleType.kCTRE);
    m_pdp.clearStickyFaults();
    tab = Shuffleboard.getTab("Main");
    addChild("PDP", m_pdp);
    widget =
        tab.add("PDP", m_pdp)
            .withWidget(BuiltInWidgets.kPowerDistribution)
            .withPosition(0, 3)
            .withSize(8, 11)
            .withProperties(Map.of("Show voltage and current values", true));

    if (!RobotBase.isReal()) {
      // this is a simulation, so add a simulated PDP

    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  /**
   * @return the voltage of the PDP in Volts
   */
  public double getVoltage() {
    return m_pdp.getVoltage();
  }

  /**
   * @return the temperature of the PDP in degrees Celsius
   */
  public double getTemperature() {
    return m_pdp.getTemperature();
  }

  /**
   * @param param the channel to get the current from, or any string to get the total current
   * @return the current drawn from the specified channel, or all channels, in Amps
   */
  public double getCurrent(Object param) {
    if (param instanceof Integer) return m_pdp.getCurrent((int) param);
    else if (param instanceof String) return m_pdp.getTotalCurrent();
    else return 0;
  }

  /**
   * @return the total current drawn from the PDP in Amps
   */
  public double getTotalCurrent() {
    return m_pdp.getTotalCurrent();
  }

  /**
   * @return the total power drawn from the PDP in Watts
   */
  public double getTotalPower() {
    return m_pdp.getTotalPower();
  }

  /**
   * @return the total energy drawn from the PDP in Joules
   */
  public double getTotalEnergy() {
    return m_pdp.getTotalEnergy();
  }
}
