package frc.robot.subsystems;

import java.util.Map;

import org.photonvision.PhotonCamera;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Vision extends SubsystemBase {

  private ColorSensorV3 colorSensor;

  // Constants such as camera and target height stored. Change per robot and goal!

  final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(24);

  final double TARGET_HEIGHT_METERS = Units.feetToMeters(5);

  // Angle between horizontal and the camera.

  final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);

  // Pipeline mode.
  public static final String kLimelightPipelineKey = "limelight-pipeline";

  public Vision() {
    // Stuff goes here

    colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    // Set default pipeline
    if (!Preferences.containsKey(kLimelightPipelineKey)) {
      Preferences.setInt(kLimelightPipelineKey, 1); // Default to tape
    }

    // Create a Shuffleboard camera widget with the properties of the limelight specified on the NetworkTables server (resolution, fps, etc.) from the NetworkTables server.
    Shuffleboard.getTab("Camera")
      .addCamera("Limelight", "Limelight", "http://10.69.64.11:5800")
      .withProperties(Map.of(
        "stream", "mjpeg",
        "compression", "0",
        "width", "320",
        "height", "240"))
      .withSize(2, 2)
      .withPosition(0, 0)
      .withWidget("Camera Stream");

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }




}
