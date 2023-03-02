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
   * @return All properties, as an {@link Array}.
   */ 
  public Array[] getProperties() { // we don't know what type we return until we process it in the if statement
    // BASIC LIMELIGHT DATA
    var tx =     NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    var ty =  NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    var tv =  NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    var ta =  NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    var tl =  NetworkTableInstance.getDefault().getTable("limelight").getEntry("tl").getDouble(0);
    var cl =  NetworkTableInstance.getDefault().getTable("limelight").getEntry("cl").getDouble(0);
    var tshort = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tshort").getDouble(0);
    var tlong =  NetworkTableInstance.getDefault().getTable("limelight").getEntry("tlong").getDouble(0);
    var thor =   NetworkTableInstance.getDefault().getTable("limelight").getEntry("thor").getDouble(0);
    var tvert =  NetworkTableInstance.getDefault().getTable("limelight").getEntry("tvert").getDouble(0);

    // APRILTAG & 3D DATA
    var botpose_wpiblue =        NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose_wpiblue")       .getDouble(0);
    var botpose_wpired =         NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose_wpired")        .getDouble(0);
    var botpose =                NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose")               .getDouble(0);
    var camerapose_targetspace = NetworkTableInstance.getDefault().getTable("limelight").getEntry("camerapose_targetspace").getDouble(0);
    var targetpose_cameraspace = NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetpose_cameraspace").getDouble(0);
    var targetpose_robotspace =  NetworkTableInstance.getDefault().getTable("limelight").getEntry("targetpose_robotspace") .getDouble(0);
    var botpose_targetspace =    NetworkTableInstance.getDefault().getTable("limelight").getEntry("botpose_targetspace")   .getDouble(0);
    var camerapose_robotspace =  NetworkTableInstance.getDefault().getTable("limelight").getEntry("camerapose_robotspace") .getDouble(0);
    var tid =                    NetworkTableInstance.getDefault().getTable("limelight").getEntry("tid")                   .getDouble(0);

    // CORNERS*
    var tcornxy = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tcornxy").getDouble(0);

    // RAW CONTOUR DATA*
    var tx0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx0").getDouble(0);
    var ty0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty0").getDouble(0);
    var ta0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta0").getDouble(0);
    var ts0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ts0").getDouble(0);
    var tx1 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx1").getDouble(0);
    var ty1 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty1").getDouble(0);
    var ta1 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta1").getDouble(0);
    var ts1 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ts1").getDouble(0);
    var tx2 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx2").getDouble(0);
    var ty2 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty2").getDouble(0);
    var ta2 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta2").getDouble(0);
    var ts2 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ts2").getDouble(0);
    //// CROSSHAIRS (in normalized screen space)
    var cx0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("cx0").getDouble(0);
    var cy0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("cy0").getDouble(0);
    var cx1 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("cx1").getDouble(0);
    var cy1 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("cy1").getDouble(0);

    // Place each chunk of properties into an array
    var basicLimelightData = new Array(tx, ty, tv, ta, tl, cl, tshort, tlong, thor, tvert);
    var aprilTagAnd3DData = new Array(botpose_wpiblue, botpose_wpired, botpose, camerapose_targetspace, targetpose_cameraspace, targetpose_robotspace, botpose_targetspace, camerapose_robotspace, tid);
    var corners = new Array(tcornxy);
    var rawContourData = new Array(tx0, ty0, ta0, ts0, tx1, ty1, ta1, ts1, tx2, ty2, ta2, ts2);
    var crosshairs = new Array(cx0, cy0, cx1, cy1);

    // Place each array into a larger array (with names for easy access)
    var properties = new Array(basicLimelightData, aprilTagAnd3DData, corners, rawContourData, crosshairs);

    // Return the array of arrays
    return properties;

  }

  /**
   * Get a specified property chunk from the Limelight.
   * @param chunk The chunk of properties to get.
   */
  public Array getProperties(int chunk) {
    return getProperties()[chunk];
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
