package frc.robot.subsystems;

import java.lang.reflect.Array;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
public class Vision extends SubsystemBase {
  private ColorSensorV3 colorSensor;
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  // Pipeline mode.
  public static final String kLimelightPipelineKey = "limelight-pipeline";

  public Vision() {
    // Stuff goes here
    colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    // Set default pipeline
    if (!Preferences.containsKey(kLimelightPipelineKey)) {
      Preferences.setInt(kLimelightPipelineKey, 1); // Default to tape
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    var result = limelight.getLatestResult();
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  /**
   * Get the selected property from the NetworkTable.
   * @param property The property to fetch.
   * @return The fetched property.
   */ 
  public Array[] getProperties() { // we don't know what type we return until we process it in the if statement
    var tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    var ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    var tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    var ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    var tl = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    var cl = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    var tshort = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    var tlong = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    var thor = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    var tvert = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);








  }



  /**
   * Change the Limelight pipeline.
   *
   * @param pipeline The pipeline to change to.
   */
  public void changePipeline(int pipeline) {
    limelight.setPipelineIndex(pipeline); // As of now, 1 is tape, 2 is tag
    Preferences.setInt(
        kLimelightPipelineKey,
        pipeline); // Save the pipeline index to preferences so we know what we're doing
  }
}
