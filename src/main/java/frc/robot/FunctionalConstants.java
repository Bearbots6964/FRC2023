package frc.robot;

public class FunctionalConstants {

  /**
   * Functional constant for {@link frc.robot.Constants}.
   *
   * @param IsInverted Whether or not the motor is inverted. Boolean.
   * @return The result of the function, either -1 or 1. Double.
   */
  public static double InversionMultiplier(boolean IsInverted) {
    double Result;
    if (IsInverted == true) {
      Result = -1.0;
    } else if (IsInverted == false) {
      Result = 1.0;
    } else {
      throw new NullPointerException("Boolean is neither true nor false!");
    }
    return Result;
  }
}
