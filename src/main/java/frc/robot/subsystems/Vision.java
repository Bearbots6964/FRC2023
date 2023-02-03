package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.List;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

public class Vision extends SubsystemBase {
  private PhotonCamera limelight;
  // ~-~-~-~-~-~-CONFIGURATION-~-~-~-~-~-~-~-
  // Constants such as camera and target height stored. Change per robot and goal!

  final double CAMERA_HEIGHT_METERS =
      Units.inchesToMeters(24); // Height of the camera above the ground

  final double TARGET_HEIGHT_METERS =
      Units.feetToMeters(5); // Height of the target above the ground

  // Angle between horizontal and the camera.

  final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0); // Camera pitch

  // Pipeline mode.
  public static final String kLimelightPipelineKey = "limelight-pipeline"; // Name of pipeline

  public Vision() {
    // Stuff goes here
    limelight =
        new PhotonCamera(
            "limelight"); // Create a PhotonCamera object. Name must match PhotonVision config

    // Set default pipeline
    if (!Preferences.containsKey(kLimelightPipelineKey)) {
      Preferences.setInt(kLimelightPipelineKey, 1); // Default to tape
    }
  }
  // ~-~-~-~-~-~-END CONFIGURATION-~-~-~-~-~-~-~-
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
   *
   * @return Whether or not there are targets.
   */
  public boolean hasTargets(PhotonPipelineResult result) {
    return result.hasTargets();
  }

  /**
   * Get the list of targets.
   *
   * @param result The pipeline result.
   * @return The list of targets.
   */
  public List<PhotonTrackedTarget> getTargets(PhotonPipelineResult result) {
    return result.getTargets();
  }

  /**
   * Get the best target.
   *
   * @param result The pipeline result.
   * @return The best target.
   */
  public PhotonTrackedTarget getBestTarget(PhotonPipelineResult result) {
    return result.getBestTarget();
  }

  /**
   * Get the yaw of a target.
   *
   * @param result The pipeline result.
   * @return The yaw of the target.
   */
  public double getYaw(PhotonTrackedTarget result) {
    return result.getYaw();
  }

  /**
   * Get the pitch of a target.
   *
   * @param result The pipeline result.
   * @return The pitch of the target.
   */
  public double getPitch(PhotonTrackedTarget result) {
    return result.getPitch();
  }

  /**
   * Get the area of a target.
   *
   * @param result The pipeline result.
   * @return The area of the target.
   */
  public double getArea(PhotonTrackedTarget result) {
    return result.getArea();
  }

  /**
   * Get the skew of a target. Only works for tape.
   *
   * @param result The pipeline result.
   * @return The skew of the target.
   */
  public double getSkew(PhotonTrackedTarget result) {
    return result.getSkew();
  }

  /**
   * Get the ID of a target. Only works for tags.
   *
   * @param result The pipeline result.
   * @return The ID of the target.
   */
  public int getID(PhotonTrackedTarget result) {
    return result.getFiducialId();
  }

  /**
   * Get the pose ambiguity of a target. Only works for tags.
   *
   * @param result The pipeline result.
   * @return The pose ambiguity of the target.
   */
  public double getPoseAmbiguity(PhotonTrackedTarget result) {
    return result.getPoseAmbiguity();
  }

  /**
   * Return a list of the 4 corners in image space (origin top left, x right, y down) of the target.
   *
   * @param result The pipeline result.
   * @return The list of corners.
   */
  public List<TargetCorner> getCorners(PhotonTrackedTarget result) {
    return result.getMinAreaRectCorners();
  }

  /**
   * Return a list of the n corners in image space (origin top left, x right, y down), in no
   * particular order, detected for this target.
   *
   * <p>For fiducials, the order is known and is always counter-clock wise around the tag, like so
   *
   * <p>spotless:off -> +X 3 ----- 2 | | | V | | +Y 0 ----- 1 spotless:on
   *
   * @param result The pipeline result.
   * @return The list of detected corners.
   */
  public List<TargetCorner> getDetectedCorners(PhotonTrackedTarget result) {
    return result.getDetectedCorners();
  }

  /**
   * Get the transform that maps camera space (X = forward, Y = left, Z = up) to object/fiducial tag
   * space (X forward, Y left, Z up) with the lowest reprojection error
   *
   * @param result The pipeline result.
   * @return The transform3d object of the best guess.
   */
  public Transform3d getBestCameraToTarget(PhotonTrackedTarget result) {
    return result.getBestCameraToTarget();
  }

  /**
   * Get the transform that maps camera space (X = forward, Y = left, Z = up) to object/fiducial tag
   * space (X forward, Y left, Z up) with the highest reprojection error
   */
  public Transform3d getAlternateCameraToTarget(PhotonTrackedTarget result) {
    return result.getAlternateCameraToTarget();
  }
}
