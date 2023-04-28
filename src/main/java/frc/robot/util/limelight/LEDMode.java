package frc.robot.util.limelight;

import java.util.Objects;

public enum LEDMode {
    PIPELINE(0),
    OFF(1),
    BLINK(2),
    ON(3);
    private int value;

    LEDMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static LEDMode fromValue(int value) {
        for (LEDMode ledMode : LEDMode.values()) {
            if (Objects.equals(ledMode.value, value)) {
                return ledMode;
            }
        }
        return null;
    }
}
