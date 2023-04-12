package edu.bearbots.BearLib.drivebase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.REVLibError;

/** Abstract class to handle common drivebase motor controller functions. */
public abstract class Drivebase {
  // for all drive bases? don't implement motors
  // odometry
  // driving
  // move to

  /**
   * Sets the current limit for Spark Max motor controllers, given a free limit and a stall limit
   * (in amps) as well as any number of Spark Maxes.
   *
   * @param freeLimit The free limit (in amps)
   * @param stallLimit The stall limit (in amps)
   * @param motor The Spark Maxes
   * @return An array of errors, if any
   */
  public REVLibError[] setCurrentLimit(int freeLimit, int stallLimit, CANSparkMax... motor) {
    REVLibError[] errors = new REVLibError[motor.length];
    int i = 0;

    for (CANSparkMax m : motor) {
      errors[i] = m.setSmartCurrentLimit(freeLimit, stallLimit);
      i++;
    }

    return errors;
  }

  /**
   * Sets the idle mode for Spark Max motor controllers, given an idle mode and any number of Spark
   * Maxes.
   *
   * @param mode The idle mode
   * @param motor The Spark Maxes
   * @return An array of errors, if any
   */
  public REVLibError[] setIdleMode(IdleMode mode, CANSparkMax... motor) {
    REVLibError[] errors = new REVLibError[motor.length];
    int i = 0;

    for (CANSparkMax m : motor) {
      errors[i] = m.setIdleMode(mode);
      i++;
    }

    return errors;
  }
}
// inclue
