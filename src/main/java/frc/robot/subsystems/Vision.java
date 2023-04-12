package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry ty = table.getEntry("ty");

  double targetOffsetAngle_Vertical = ty.getDouble(0);
  // Pipeline mode.

  private double limelightMountAngleDegrees = 0;
  private double limelightLensHeightInches = 6.25;
  private double goalHeightInches = 24.125;
  private double angleToGoalRadians = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
  private double angleToGoalDegrees = Units.radiansToDegrees(angleToGoalRadians);
  private double distanceFromLimelightToGoalInches =
      (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);
  public GenericEntry widget;

  public Vision() {
    // Stuff goes here
    widget =
        Shuffleboard.getTab("stuff")
            .add(
                "distance",
                -(goalHeightInches - limelightLensHeightInches)
                    / Math.tan(limelightMountAngleDegrees + ty.getDouble(0)))
            .withWidget(BuiltInWidgets.kGraph)
            .getEntry();
  }

  // display camera on shuffleboard

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    widget.setDouble(
        -(goalHeightInches - limelightLensHeightInches)
            / Math.tan(limelightMountAngleDegrees + ty.getDouble(0)));
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
