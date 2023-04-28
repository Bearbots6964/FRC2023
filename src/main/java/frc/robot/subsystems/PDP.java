package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.PDPSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PDP extends SubsystemBase {
  public PowerDistribution pdp;
  ShuffleboardTab tab;
  ComplexWidget widget;
  private PDPSim pdpSim;

  public PDP() {
    pdp = new PowerDistribution(0, ModuleType.kCTRE);
    tab = Shuffleboard.getTab("PDP");
    pdp.clearStickyFaults();
    tab = Shuffleboard.getTab("PDP");
    addChild("PDP", pdp);
    widget = tab.add("PDP", pdp).withWidget(BuiltInWidgets.kPowerDistribution);
    if (!RobotBase.isReal()) {
      // this is a simulation, so add a simulated PDP
      pdpSim = new PDPSim(pdp);

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
    return pdp.getVoltage();
  }

  /**
   * @return the temperature of the PDP in degrees Celsius
   */
  public double getTemperature() {
    return pdp.getTemperature();
  }

  /**
   * @param param the channel to get the current from, or any string to get the
   *              total current
   * @return the current drawn from the specified channel, or all channels, in
   *         Amps
   */
  public double getCurrent(Object param) {
    if (param instanceof Integer)
      return pdp.getCurrent((int) param);
    else if (param instanceof String)
      return pdp.getTotalCurrent();
    else
      return 0;
  }

  /**
   * @return the total current drawn from the PDP in Amps
   */
  public double getTotalCurrent() {
    return pdp.getTotalCurrent();
  }

  /**
   * @return the total power drawn from the PDP in Watts
   */
  public double getTotalPower() {
    return pdp.getTotalPower();
  }

  /**
   * @return the total energy drawn from the PDP in Joules
   */
  public double getTotalEnergy() {
    return pdp.getTotalEnergy();
  }
}
