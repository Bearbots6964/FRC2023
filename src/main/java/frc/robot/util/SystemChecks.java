package frc.robot.util;

import edu.wpi.first.networktables.NetworkTableInstance;

public class SystemChecks {
  public static Alert alert = new Alert("Limelight not found", Alert.AlertType.WARNING);

  public static void checkLimelight() {
    // check for the limelight in NetworkTables
    if (!NetworkTableInstance.getDefault().getTable("limelight").containsKey("tx")) {

      alert.set(true);
    } else {
      alert.set(false);
    }
  }
}
