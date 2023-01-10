package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

public class Vision extends SubsystemBase {
  private PhotonCamera limelight;

  // Constants such as camera and target height stored. Change per robot and goal!

  final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(24);

  final double TARGET_HEIGHT_METERS = Units.feetToMeters(5);

  // Angle between horizontal and the camera.

  final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);

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

  public PhotonPipelineResult getLatestResult() {
    return limelight.getLatestResult();
  }
}
