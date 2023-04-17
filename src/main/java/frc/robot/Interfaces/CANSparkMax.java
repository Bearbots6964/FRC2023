package frc.robot.interfaces;

import com.revrobotics.REVLibError;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

// This code has bugs.

public class CANSparkMax extends com.revrobotics.CANSparkMax implements Sendable {
  public CANSparkMax(int deviceId, MotorType type) {
    super(deviceId, type);
  }

  // for reference, here's all the methods that have a getter and, optionally, a
  // setter, in the
  // REVLib CANSparkMax class (brackets indicate a note, preceeding percent signs
  // indicate a method
  // that returns an object for--you guessed it-- more interfacing, a dollar sign
  // indicates a method
  // we won't be using, exclamation points indicate a method that returns an
  // object that we will be
  // using or that we need to pay attention to)
  /*
   * get() and set() -- these are the main methods for getting and setting the
   * output of the motor controller
   * % getAbsoluteEncoder() [no setter] -- returns an object for interfacing with
   * a connected absolute encoder
   * %$ getAlternateEncoder() [no setter] -- returns an object for interfacing
   * with a quadrature encoder connected to the alternate encoder pins
   * %$ getAnalog() [no setter] -- returns an object for interfacing with a
   * connected analog sensor
   * getAppliedOutput() [no setter] -- returns the output of the motor controller,
   * in the range [-1, 1]
   * getBusVoltage() [no setter] -- returns the bus voltage of the motor
   * controller
   * getClosedLoopRampRate() and setClosedLoopRampRate() -- gets and sets the
   * closed loop ramp rate of the motor controller
   * ! getEncoder() [no setter] -- returns an object for interfacing with a
   * connected encoder
   * getFaults() [no setter] -- returns an object for interfacing with the faults
   * of the motor controller
   * an extension of this is getStickyFaults(), which returns an object for
   * interfacing with the sticky faults of the motor controller
   * as well as getFault() and getStickyFault(), which return a boolean for
   * whether or not a specific fault is present
   * getFeedbackDeviceID() [no setter] -- returns the ID of the feedback device
   * set on the motor controller
   * % getForwardLimitSwitch() [no setter] -- returns an object for interfacing
   * with the forward limit switch
   * ! getIdleMode() and setIdleMode() -- gets and sets the idle mode of the motor
   * controller
   * getInverted() and setInverted() -- gets and sets the inversion of the motor
   * controller
   * getLastError() [no setter] -- returns the last error of the motor controller
   * getMotorTemperature() [no setter] -- returns the temperature of the motor
   * controller (at least, that's what we assume, the documentation is unclear)
   * getOutputCurrent() [no setter] -- returns the output current of the motor
   * controller
   * % getPIDController() [no setter] -- returns an object for interfacing with
   * the PID controller of the motor controller
   * %$ getReverseLimitSwitch() [no setter] -- returns an object for interfacing
   * with the reverse limit switch
   * getSoftLimit() and setSoftLimit() -- gets and sets the soft limit of the
   * motor controller
   * getSmartCurrentLimit() and setSmartCurrentLimit() -- gets and sets the smart
   * current limit of the motor controller
   * getVoltageCompensationNomininalVoltage() and
   * setVoltageCompensationNomininalVoltage() -- gets and sets the voltage
   * compensation nominal voltage of the motor controller
   *
   * is() statements -- these are all boolean methods that return whether or not a
   * specific condition is true
   *
   * isFollower() [no setter] -- returns a boolean for whether or not the motor
   * controller is a follower of another motor controller
   * isSoftLimitEnabled() [no setter] -- returns a boolean for whether or not the
   * soft limit is enabled
   *
   * set() statements -- these are all methods that set a specific property
   *
   * setCANTimeout() -- sets the CAN timeout of the motor controller
   * setVoltage() -- sets the voltage of the motor controller (This seems really
   * dangerous, but it's in the documentation, so we'll include it)
   *
   * inherited methods -- these are all methods that are inherited from various
   * other things
   *
   * getDeviceId() [no setter] -- returns the device ID of the motor controller
   * getFirmwareString() [no setter] -- returns the firmware string of the motor
   * controller
   * getFirmwareVersion() [no setter] -- returns the firmware version of the motor
   * controller
   * getInitialMotorType() [no setter] -- returns the initial motor type of the
   * motor controller
   * getMotorType() [no setter] -- returns the motor type of the motor controller
   * getSafeFloat() [no setter] -- returns the safe float of the motor controller
   * (??)
   * getSerialNumber() [no setter] -- returns the serial number of the motor
   * controller
   * restoreFactoryDefaults() -- restores the factory defaults of the motor
   * controller
   * setControlFramePeriodMs() -- sets the control frame period of the motor
   * controller
   * setPeriodicFramePeriod() -- sets the periodic frame period of the motor
   * controller
   * throwIfClosed() [no setter] -- throws an exception if the motor controller is
   * closed
   */

  // IGNORE EVERYTHING ABOVE THIS! I rewrote the entire thing, and it's much
  // better now!

  // here's all the methods found in CANSparkMaxLowLevel using the magic of regex
  // (public [a-zA-Z]*
  // get[a-zA-z_]*())
  /*
   * getFirmwareVersion
   * getFirmwareString
   * getDeviceId
   * getInitialMotorType
   * getMotorType
   * getSafeFloat
   * getRaw
   *
   * and all the set methods (public [a-zA-Z]* set[a-zA-z_]*())
   *
   * setControlFramePeriodMs
   * setPeriodicFramePeriod
   *
   * and all the public variables that we can *technically* access, and we
   * probably shouldn't, but we can, so safety be damned (safety? i hardly know
   * her!)
   *
   * public class PeriodicStatus0 {
   * public double appliedOutput;
   * public short faults;
   * public short stickyFaults;
   * public byte lock;
   * public MotorType motorType;
   * public boolean isFollower;
   * public boolean isInverted;
   * public boolean roboRIO;
   * }
   * 
   * public class PeriodicStatus1 {
   * public double sensorVelocity;
   * public byte motorTemperature;
   * public double busVoltage;
   * public double outputCurrent;
   * }
   * 
   * public class PeriodicStatus2 {
   * public double sensorPosition;
   * public double iAccum;
   * }
   *
   * now, naturally, there's some things we can't access, like the CAN ID, because
   * that's private, and the firmware version, because that's protected, but we
   * can access the rest of it, so we're going to. see above for the list of
   * methods we can access
   *
   * (water break! if you're actually reading this, you're a trooper, and i salute
   * you. even the best of us need water sometimes, so go get some, and then come
   * back and finish reading this. i'll wait.)
   */

  // here's all the methods found in CANSparkMax using the magic of regex (public
  // [a-zA-Z]*
  // get[a-zA-z_]*())
  /*
   * get()
   * getInverted()
   * getEncoder() [optionally, it takes 2 paramaters, encoderType and
   * countsPerRev]
   * getAlternateEncoder() [optionally, it takes 2 paramaters, encoderType and
   * countsPerRev]
   * getAnalog(mode) [no docs on what mode is, so that's cool]
   * getAbsoluteEncoder()
   * getPIDController()
   * getForwardLimitSwitch()
   * getReverseLimitSwitch()
   * getIdleMode()
   * getVoltageCompensationNomininalVoltage()
   * getOpenLoopRampRate()
   * getClosedLoopRampRate()
   * getFaults()
   * getStickyFaults()
   * getFault(fault id)
   * getStickyFault(fault id)
   * getBusVoltage()
   * getAppliedOutput()
   * getOutputCurrent()
   * getMotorTemperature()
   * getSoftLimit()
   * getLastError()
   *
   * and all the set methods (public [a-zA-Z]* set[a-zA-z_]*())
   *
   * set()
   * setVoltage() [also seems unsafe, but it's in the documentation, so we'll
   * include it]
   * setInverted()
   * setSmartCurrentLimit() [the library actually instantiated this a bunch of
   * times, so we'll just include the first one]
   * setSecondaryCurrentLimit()
   * setIdleMode()
   * setOpenLoopRampRate()
   * setClosedLoopRampRate()
   * setCANTimeout()
   * setSoftLimit()
   *
   */

  // oh no there's more
  /*
   * RelativeEncoder has more things! yayyyyy
   *
   * getters
   *
   * getPosition()
   * getVelocity()
   * getPositionConversionFactor()
   * getVelocityConversionFactor()
   * getAverageDepth()
   * getMeasurementPeriod()
   * getCountsPerRevolutions()
   * getInverted()
   *
   * setters
   *
   * setPosition()
   * setPositionConversionFactor()
   * setVelocityConversionFactor()
   * setAverageDepth()
   * setMeasurementPeriod()
   * setInverted()
   *
   * Copilot, you seem to know what you're doing. I'll let you fill in the blanks
   * for what I'm missing.
   *
   *
   */

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("SparkMax");
    builder.addDoubleProperty("Applied Output", this::getAppliedOutput, null);
    builder.addDoubleProperty("Bus Voltage", this::getBusVoltage, null);
    builder.addDoubleProperty(
        "Closed Loop Ramp Rate", this::getClosedLoopRampRate, this::setClosedLoopRampRate);
    builder.addDoubleProperty("Output Current", this::getOutputCurrent, null);
    builder.addDoubleProperty("Motor Temperature", this::getMotorTemperature, null);
    builder.addDoubleProperty("Feedback Device ID", this::getFeedbackDeviceID, null);
    builder.addDoubleProperty("Device ID", this::getDeviceId, null);
    builder.addDoubleProperty("Firmware Version", this::getFirmwareVersion, null);
    builder.addBooleanProperty("Inverted", this::getInverted, this::setInverted);
    builder.addBooleanProperty("Follower", this::isFollower, null);
    builder.addStringProperty("Firmware String", this::getFirmwareString, null);
    builder.addDoubleProperty(
        "Open Loop Ramp Rate", this::getOpenLoopRampRate, this::setOpenLoopRampRate);
    // fault stuff
    builder.addDoubleProperty("Faults", this::getFaults, null);
    builder.addBooleanProperty("Brownout?", () -> getFault(FaultID.kBrownout), null);
    builder.addBooleanProperty("Overcurrent?", () -> getFault(FaultID.kOvercurrent), null);
    builder.addBooleanProperty("IWDT Reset?", () -> getFault(FaultID.kIWDTReset), null);
    builder.addBooleanProperty("Motor Fault?", () -> getFault(FaultID.kMotorFault), null);
    builder.addBooleanProperty("Sensor Fault?", () -> getFault(FaultID.kSensorFault), null);
    builder.addBooleanProperty("Stall?", () -> getFault(FaultID.kStall), null);
    builder.addBooleanProperty("EEPROM CRC?", () -> getFault(FaultID.kEEPROMCRC), null);
    builder.addBooleanProperty("CAN Transmit?", () -> getFault(FaultID.kCANTX), null);
    builder.addBooleanProperty("CAN Receive?", () -> getFault(FaultID.kCANRX), null);
    builder.addBooleanProperty("Has Reset?", () -> getStickyFault(FaultID.kHasReset), null);
    builder.addBooleanProperty("DRV Fault?", () -> getStickyFault(FaultID.kDRVFault), null);
    builder.addBooleanProperty("Other Fault?", () -> getStickyFault(FaultID.kOtherFault), null);
    builder.addBooleanProperty(
        "Forward Soft Limit?", () -> getStickyFault(FaultID.kSoftLimitFwd), null);
    builder.addBooleanProperty(
        "Reverse Soft Limit?", () -> getStickyFault(FaultID.kSoftLimitRev), null);
    builder.addBooleanProperty(
        "Forward Hard Limit?", () -> getStickyFault(FaultID.kHardLimitFwd), null);
    builder.addBooleanProperty(
        "Reverse Hard Limit?", () -> getStickyFault(FaultID.kHardLimitRev), null);

    builder.addDoubleProperty("Sticky Faults", this::getStickyFaults, null);
    builder.addBooleanProperty("Brownout?*", () -> getStickyFault(FaultID.kBrownout), null);
    builder.addBooleanProperty("Overcurrent?*", () -> getStickyFault(FaultID.kOvercurrent), null);
    builder.addBooleanProperty("IWDT Reset?*", () -> getStickyFault(FaultID.kIWDTReset), null);
    builder.addBooleanProperty("Motor Fault?*", () -> getStickyFault(FaultID.kMotorFault), null);
    builder.addBooleanProperty("Sensor Fault?*", () -> getStickyFault(FaultID.kSensorFault), null);
    builder.addBooleanProperty("Stall?*", () -> getStickyFault(FaultID.kStall), null);
    builder.addBooleanProperty("EEPROM CRC?*", () -> getStickyFault(FaultID.kEEPROMCRC), null);
    builder.addBooleanProperty("CAN Transmit?*", () -> getStickyFault(FaultID.kCANTX), null);
    builder.addBooleanProperty("CAN Receive?*", () -> getStickyFault(FaultID.kCANRX), null);
    builder.addBooleanProperty("Has Reset?*", () -> getStickyFault(FaultID.kHasReset), null);
    builder.addBooleanProperty("DRV Fault?*", () -> getStickyFault(FaultID.kDRVFault), null);
    builder.addBooleanProperty("Other Fault?*", () -> getStickyFault(FaultID.kOtherFault), null);
    builder.addBooleanProperty(
        "Forward Soft Limit?*", () -> getStickyFault(FaultID.kSoftLimitFwd), null);
    builder.addBooleanProperty(
        "Reverse Soft Limit?*", () -> getStickyFault(FaultID.kSoftLimitRev), null);
    builder.addBooleanProperty(
        "Forward Hard Limit?*", () -> getStickyFault(FaultID.kHardLimitFwd), null);
    builder.addBooleanProperty(
        "Reverse Hard Limit?*", () -> getStickyFault(FaultID.kHardLimitRev), null);

    // brake mode
    // this one is a bit weird. we have to create a switch statement to get the
    // right value, because
    // it's either gonna be kCoast or kBrake
    // we do that outside the initSendable() method, because it's a bit too long to
    // put in there,
    // and I don't think it'll work. If I put outside, I'll be able to call it in
    // other places too.
    builder.addBooleanProperty("Brake Mode", this::isBraking, this::setBrakeMode);
    builder.addBooleanProperty("Coast Mode", this::isCoasting, this::setCoastMode);

    // current limits
    builder.addIntegerProperty("Current Limit", null, this::setSmartCurrentLimit);


    // pid stuff
    builder.addDoubleProperty("P", this::getP, this::setP);
    builder.addDoubleProperty("I", this::getI, this::setI);
    builder.addDoubleProperty("D", this::getD, this::setD);
    builder.addDoubleProperty("F", this::getF, this::setF);
    builder.addDoubleProperty("IZone", this::getIZone, this::setIZone);
    builder.addDoubleProperty("Min Output Range", this::getMinOutputRange, this::setMinOutputRange);
    builder.addDoubleProperty("Max Output Range", this::getMaxOutputRange, this::setMaxOutputRange);

    // encoder stuff
    builder.addDoubleProperty("Encoder Position", this::getAbsoluteEncoderPosition, null);
    builder.addDoubleProperty("Encoder Velocity", this::getAbsoluteEncoderVelocity, null);

  }

  /**
   * Returns true if the motor controller is braking for its idle mode.
   *
   * @return true if braking, false if coasting
   */
  public boolean isBraking() {
    switch (getIdleMode()) {
      case kCoast:
        return false;
      case kBrake:
        return true;
      default:
        return false;
    }
  }

  /**
   * Returns true if the motor controller is coasting for its idle mode.
   *
   * @return true if coasting, false if braking
   */
  public boolean isCoasting() {
    switch (getIdleMode()) {
      case kCoast:
        return true;
      case kBrake:
        return false;
      default:
        return false;
    }
  }

  /**
   * Sets the brake mode of the motor controller.
   *
   * @param brake true to brake, false to coast
   */
  public void setBrakeMode(boolean brake) {
    if (brake) {
      setIdleMode(IdleMode.kBrake);
    } else {
      setIdleMode(IdleMode.kCoast);
    }
  }

  /**
   * Sets the brake mode of the motor controller.
   *
   * @param coast true to coast, false to brake
   */
  public void setCoastMode(boolean coast) {
    if (coast) {
      setIdleMode(IdleMode.kCoast);
    } else {
      setIdleMode(IdleMode.kBrake);
    }
  }

  public REVLibError setSmartCurrentLimit(Long limit) {
    throwIfClosed();
    return setSmartCurrentLimit(limit.intValue(), 0, 20000);
  }


  // pid stuff
  public void setP(double p) {
    throwIfClosed();
    getPIDController().setP(p);
  }
  public void setI(double i) {
    throwIfClosed();
    getPIDController().setI(i);
  }
  public void setD(double d) {
    throwIfClosed();
    getPIDController().setD(d);
  }
  public void setF(double f) {
    throwIfClosed();
    getPIDController().setFF(f);
  }
  public void setIZone(double iZone) {
    throwIfClosed();
    getPIDController().setIZone(iZone);
  }
  public void setMinOutputRange(double minOutputRange) {
    throwIfClosed();
    getPIDController().setOutputRange(minOutputRange, getMaxOutputRange());
  }
  public void setMaxOutputRange(double maxOutputRange) {
    throwIfClosed();
    getPIDController().setOutputRange(getMinOutputRange(), maxOutputRange);
  }

  public double getP() {
    throwIfClosed();
    return getPIDController().getP();
  }
  public double getI() {
    throwIfClosed();
    return getPIDController().getI();
  }
  public double getD() {
    throwIfClosed();
    return getPIDController().getD();
  }
  public double getF() {
    throwIfClosed();
    return getPIDController().getFF();
  }
  public double getIZone() {
    throwIfClosed();
    return getPIDController().getIZone();
  }
  public double getMinOutputRange() {
    throwIfClosed();
    return getPIDController().getOutputMin();
  }
  public double getMaxOutputRange() {
    throwIfClosed();
    return getPIDController().getOutputMax();
  }


  // absolute encoder stuff
  public double getAbsoluteEncoderPosition() {
    throwIfClosed();
    return getAlternateEncoder(4096).getPosition();
  }
  public double getAbsoluteEncoderVelocity() {
    throwIfClosed();
    return getAlternateEncoder(4096).getVelocity();
  }



}
