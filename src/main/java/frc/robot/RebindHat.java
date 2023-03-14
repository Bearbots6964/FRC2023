package frc.robot;

import frc.robot.RobotContainer;

public class RebindHat {
    public double toYAxis() {
        if (RobotContainer.m_armController.getPOV() == -1) {
            return 0
        }
        if (RobotContainer.m_armController.getPOV() >= 270 || RobotContainer.m_armController.getPOV() <= 90) {
            return -1;
        } else {
            
        }
    }
}
