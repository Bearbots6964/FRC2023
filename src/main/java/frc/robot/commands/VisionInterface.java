package frc.robot.commands;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.TrackingType;
import frc.robot.subsystems.Vision;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

public class VisionInterface extends CommandBase {
  private final Vision m_Vision;
  private TrackingType tape;
  private TrackingType tag;
  /*
   * 1.   Constructor - Might have parameters for this command such as target positions of devices. Should also set the name of the command for debugging purposes.
   *  This will be used if the status is viewed in the dashboard. And the command should require (reserve) any devices is might use.
   */
  public VisionInterface(Vision subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_Vision = subsystem;
    addRequirements(m_Vision);
  }

  //    initialize() - This method sets up the command and is called immediately before the command
  // is executed for the first time and every subsequent time it is started .
  //  Any initialization code should be here.
  public void initialize() {}

  /*
   *   execute() - This method is called periodically (about every 20ms) and does the work of the command. Sometimes, if there is a position a
   *  subsystem is moving to, the command might set the target position for the subsystem in initialize() and have an empty execute() method.
   */
  public void execute() {
    var result = m_Vision.getLatestResult();

    // If the limelight has detected something, call the function in OnTrigger.java
    if (result.hasTargets() == true) {
      // The limelight has detected something! If it is an AprilTag, call the function in
      // OnTrigger.java for them.
      if (Preferences.getInt(Vision.kLimelightPipelineKey, 69) == 2) {
        // Call the function in OnTrigger.java
        onTrigger.onTag();
      } else if (Preferences.getInt(Vision.kLimelightPipelineKey, 69) == 1) {
        // Call the function in OnTrigger.java
        onTrigger.onTape();
      } else {
        // Nothing has been detected. Carry on.
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  public boolean isFinished() {
    return false;
  }

  /*
   // Functions to get relevant data types.
   public PhotonPipelineResult result() {
      return m_Vision.getLatestResult();
   }
  public boolean hasTargets() {
      return result().hasTargets();
  }
  public List<PhotonTrackedTarget> targets() {
      return result().getTargets();
  }
  public PhotonTrackedTarget target() {
      return result().getBestTarget();
  }
  public DoubleSupplier yaw() {
      return target().getYaw();
  }
  public DoubleSupplier pitch() {
      return target().getPitch();
  }
  public DoubleSupplier area() {
      return target().getArea();
  }
  public Transform3d pose() {
      return target().getBestCameraToTarget();
  }
  public double skew() {
      if (Constants.VisionConstants.kTrackingType == tape) {
          return target().getSkew();
      } else {
          throw(new IllegalArgumentException("Tracking type is not set to tape."));
      }
  }
  public int targetID() {
      if (Constants.VisionConstants.kTrackingType == tag) {
          return target().getFiducialId();
      } else {
          throw(new IllegalArgumentException("Tracking type is not set to tag."));
      }
  }
  public double poseAmbiguity() {
      if (Constants.VisionConstants.kTrackingType == tag) {
          return target().getPoseAmbiguity();
      } else {
          throw(new IllegalArgumentException("Tracking type is not set to tag."));
      }
  }
  public Transform3d bestCameraToTarget() {
      if (Constants.VisionConstants.kTrackingType == tag) {
          return target().getBestCameraToTarget();
      } else {
          throw(new IllegalArgumentException("Tracking type is not set to tag."));
      }
  }
  public Transform3d alternateCameraToTarget() {
      if (Constants.VisionConstants.kTrackingType == tag) {
          return target().getAlternateCameraToTarget();
      } else {
          throw(new IllegalArgumentException("Tracking type is not set to tag."));
      }
  }
  */

  public PhotonPipelineResult result() {
    return m_Vision.getLatestResult();
  }

  public PhotonTrackedTarget target() {
    return result().getBestTarget();
  }

  public BooleanSupplier hasTargetsSupplier = () -> result().hasTargets();

  public DoubleSupplier yawSupplier = () -> target().getYaw();
  public DoubleSupplier pitchSupplier = () -> target().getPitch();
  public DoubleSupplier areaSupplier = () -> target().getArea();
  public Supplier<Transform3d> poseSupplier = () -> target().getBestCameraToTarget();
  public DoubleSupplier skewSupplier = () -> target().getSkew();
  public DoubleSupplier targetIDSupplier = () -> target().getFiducialId();
  public DoubleSupplier poseAmbiguitySupplier = () -> target().getPoseAmbiguity();
}
