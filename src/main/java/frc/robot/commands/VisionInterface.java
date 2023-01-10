package frc.robot.commands;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Constants;
import frc.robot.subsystems.Vision;
import frc.robot.trackingType;
import edu.wpi.first.wpilibj.shuffleboard.*;

public class VisionInterface extends CommandBase {
    private final Vision m_Vision;
    private trackingType tape;
    private trackingType tag;
     /*
    * 1.   Constructor - Might have parameters for this command such as target positions of devices. Should also set the name of the command for debugging purposes.
    *  This will be used if the status is viewed in the dashboard. And the command should require (reserve) any devices is might use.
    */
    public VisionInterface(Vision subsystem) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_Vision = subsystem;
        addRequirements(m_Vision);
     }
 
     //    initialize() - This method sets up the command and is called immediately before the command is executed for the first time and every subsequent time it is started .
     //  Any initialization code should be here.
     public void initialize() {
     }
 
     /*
      *   execute() - This method is called periodically (about every 20ms) and does the work of the command. Sometimes, if there is a position a
      *  subsystem is moving to, the command might set the target position for the subsystem in initialize() and have an empty execute() method.
      */
     public void execute() {
        // First, set variables to the latest result from the vision system. Get as many shared things as possible. After, if a constant defined in Constants.java is set to "tape" get skew as well. Otherwise, get AprilTag data.
        
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

        // Now, we check if trackingType from the Constants file is set to "tape" or "tag". We then send the relevant data to Shuffleboard.
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
