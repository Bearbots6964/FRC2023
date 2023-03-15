package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  // Pipeline mode.

  public Vision() {
    // Stuff goes here
  }

  // display camera on shuffleboard

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  /**
   * Get the selected property from the NetworkTable.
   *
   * @param property The property to fetch.
   * @return The fetched property.
   */
  public double[]
      getProperties() { // we don't know what type we return until we process it in the if statement
    //
    var tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    var ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    var tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    var ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    var tl = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tl").getDouble(0);
    var cl = NetworkTableInstance.getDefault().getTable("limelight").getEntry("cl").getDouble(0);
    var tshort =
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("tshort").getDouble(0);
    var tlong =
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("tlong").getDouble(0);
    var thor =
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("thor").getDouble(0);
    var tvert =
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("tvert").getDouble(0);

    return new double[] {tx, ty, tv, ta, tl, cl, tshort, tlong, thor, tvert};
  }
}
