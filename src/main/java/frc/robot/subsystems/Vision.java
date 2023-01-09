package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.common.*;
import org.photonvision.common.dataflow.*;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

public class Vision extends SubsystemBase {
    private PhotonCamera limelight;
    
    public Vision() {
        // Stuff goes here
        limelight = new PhotonCamera("limelight");
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

    public PhotonPipelineResult getPipeline() {
        var result = limelight.getLatestResult();
        return result;
    }
    public boolean hasTargets() {
        boolean hasTargets = getPipeline().hasTargets();
        return hasTargets;
    }
    public List<PhotonTrackedTarget> getTargetList() {
        List<PhotonTrackedTarget> targetList = getPipeline().getTargets();
        return targetList;
    }
    public PhotonTrackedTarget getBestTarget() {
        PhotonTrackedTarget target = getPipeline().getBestTarget();
        return target;
    }
    public double getBestTargetYaw() {
        double yaw = getBestTarget().getYaw();
        return yaw;
    }
    public double getBestTargetPitch() {
        double pitch = getBestTarget().getPitch();
        return pitch;
    }
    public double getBestTargetArea() {
        double area = getBestTarget().getArea();
        return area;
    }
    public double getBestTargetSkew() {
        double skew = getBestTarget().getSkew();
        return skew;
    }
    public List<TargetCorner> getBestTargetCorners() {
        List<TargetCorner> corners = getBestTarget().getCorners();
        return corners;
    }
}
