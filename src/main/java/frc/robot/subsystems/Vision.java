package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

public class Vision extends SubsystemBase {
  private PhotonCamera limelight;

  // Constants such as camera and target height stored. Change per robot and goal!

  final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(24);

  final double TARGET_HEIGHT_METERS = Units.feetToMeters(5);

  // Angle between horizontal and the camera.

  final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);

  // Pipeline mode.
  public static final String kLimelightPipelineKey = "limelight-pipeline";

  public Vision() {
    // Stuff goes here
    limelight = new PhotonCamera("limelight");

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
   * Get the latest pipeline result.
   *
   * @return The latest pipeline result.
   */
  public PhotonPipelineResult getLatestResult() {
    return limelight.getLatestResult();
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

  /**
   * Check for the existence of targets.
   * @return Whether or not there are targets.
   */
  public boolean hasTargets(PhotonPipelineResult result) {
    return result.hasTargets();
  }

  /**
   * Get the list of targets.
   * @param result The pipeline result.
   * @return The list of targets.
   */
  public List<PhotonTrackedTarget> getTargets(PhotonPipelineResult result) {
    return result.getTargets();
  }

  /**
   * Get the best target.
   * @param result The pipeline result.
   * @return The best target.
   */
  public PhotonTrackedTarget getBestTarget(PhotonPipelineResult result) {
    return result.getBestTarget();
  }

  /**
   * Get the yaw of a target.
   * @param result The pipeline result.
   * @return The yaw of the target.
   */
  public double getYaw(PhotonTrackedTarget result) {
    return result.getYaw();
  }

  /**
   * Get the pitch of a target.
   * @param result The pipeline result.
   * @return The pitch of the target.
   */
  public double getPitch(PhotonTrackedTarget result) {
    return result.getPitch();
  }

  /**
   * Get the area of a target.
   * @param result The pipeline result.
   * @return The area of the target.
   */
  public double getArea(PhotonTrackedTarget result) {
    return result.getArea();
  }

  /**
   * Get the skew of a target. Only works for tape.
   * @param result The pipeline result.
   * @return The skew of the target.
   */
  public double getSkew(PhotonTrackedTarget result) {
    return result.getSkew();
  }

  /**
   * Get the pose of a target.
   * @param result The pipeline result.
   * @return The pose of the target.
   */
  public Transform2d getPose(PhotonTrackedTarget result) {
    return result.getCameraToTarget();
  }
}
