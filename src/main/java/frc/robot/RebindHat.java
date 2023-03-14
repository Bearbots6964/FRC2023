package frc.robot;

import frc.robot.RobotContainer;

public class RebindHat {
    public static double toYAxis() {
        if (RobotContainer.m_armController.getPOV() == -1 || RobotContainer.m_armController.getPOV() == 90 || RobotContainer.m_armController.getPOV() == 270) {
            return 0;
        }
        if (RobotContainer.m_armController.getPOV() > 270 || RobotContainer.m_armController.getPOV() < 90) {
            return 1;
        } else {
            return -1;
        }
    }

    public static double toXAxis() {
        if (RobotContainer.m_armController.getPOV() == -1 || RobotContainer.m_armController.getPOV() == 0 || RobotContainer.m_armController.getPOV() == 180) {
            return 0;
        } else if (RobotContainer.m_armController.getPOV() > 180) {
            return -1;
        } else {
            return 1;
        }
    }
}
