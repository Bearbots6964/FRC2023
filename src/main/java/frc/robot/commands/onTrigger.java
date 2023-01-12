package frc.robot.commands;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
/**
 * A set of commands called when a trigger is called.
 */
public class onTrigger {
    // On limelight detection of an AprilTag

    /**
     * Set of actions to do when a limelight detects an AprilTag.
     * Edit this method to change what happens when a tag is detected.
     */
    public static void onTag() {
        // A tag has been detected. What should we do?
    }
    /**
     * Set of actions to do when a limelight detects a tape.
     * Edit this method to change what happens when a tape is detected.
     */
    public static void onTape() {
        // A tape has been detected. What should we do?
    }

     
    /*
     * // First, set variables to the latest result from the vision system. Get as many shared things
    // as possible. After, if a constant defined in Constants.java is set to "tape" get skew as
    // well. Otherwise, get AprilTag data.

    // Query the latest result from PhotonVision.
    var result = m_Vision.getLatestResult();
    // Check if the latest result has any targets.
    boolean hasTargets = result.hasTargets();
    // Get a list of currently tracked targets.
    List<PhotonTrackedTarget> targets = result.getTargets();
    // Get the current best target.
    PhotonTrackedTarget target = result.getBestTarget();
    // Get information from target.
    double yaw = target.getYaw();
    double pitch = target.getPitch();
    double area = target.getArea();
    Transform3d pose = target.getBestCameraToTarget();
    List<TargetCorner> corners = target.getDetectedCorners();

    // Send all this data to shuffleboard.
    Shuffleboard.getTab("Vision").add("Yaw", yaw);
    Shuffleboard.getTab("Vision").add("Pitch", pitch);
    Shuffleboard.getTab("Vision").add("Area", area);
    Shuffleboard.getTab("Vision").add("Pose", pose);

    Shuffleboard.getTab("Vision").add("Corners", corners);

    // Now, we check if trackingType from the Constants file is set to "tape" or "tag". We then send
    // the relevant data to Shuffleboard.
    var trackingType = Constants.VisionConstants.kTrackingType;
    if (trackingType == tape) {
      // If it is set to "tape", get the skew of the target.
      double skew = target.getSkew();
    } else if (trackingType == tag) {
      // Get information from target.
      int targetID = target.getFiducialId();
      double poseAmbiguity = target.getPoseAmbiguity();
      Transform3d bestCameraToTarget = target.getBestCameraToTarget();
      Transform3d alternateCameraToTarget = target.getAlternateCameraToTarget();
      Shuffleboard.getTab("Vision").addNumber("Target ID", targetIDSupplier);
      Shuffleboard.getTab("Vision").addNumber("Pose Ambiguity", poseAmbiguitySupplier);
      Shuffleboard.getTab("Vision").add("Best Camera to Target", bestCameraToTarget);
      Shuffleboard.getTab("Vision").add("Alternate Camera to Target", alternateCameraToTarget);
    }
     */
}
