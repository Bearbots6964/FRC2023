package frc.robot;

/**
 * @deprecated
 */
// dude come onthis is the deffinition fo unnesseary complecity
@Deprecated
public class RebindHat {
  public static double JoystickToYAxis() {}

  public static double JoystickToXAxis() {
    if (RobotContainer.m_armController.getPOV() == -1
        || RobotContainer.m_armController.getPOV() == 0
        || RobotContainer.m_armController.getPOV() == 180) {
      return 0;
    } else if (RobotContainer.m_armController.getPOV() > 180) {
      return -1;
    } else {
      return 1;
    }
  }

  public static double ControllerToYAxis() {
    if (RobotContainer.m_armController2.getPOV() == -1
        || RobotContainer.m_armController2.getPOV() == 90
        || RobotContainer.m_armController2.getPOV() == 270) {
      return 0;
    }
    if (RobotContainer.m_armController2.getPOV() > 270
        || RobotContainer.m_armController2.getPOV() < 90) {
      return 1;
    } else {
      return -1;
    }
  }

  public static double ControllerToXAxis() {
    if (RobotContainer.m_armController2.getPOV() == -1
        || RobotContainer.m_armController2.getPOV() == 0
        || RobotContainer.m_armController2.getPOV() == 180) {
      return 0;
    } else if (RobotContainer.m_armController2.getPOV() > 180) {
      return -1;
    } else {
      return 1;
    }
  }
}
