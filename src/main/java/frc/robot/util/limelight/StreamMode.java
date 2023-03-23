package frc.robot.util.limelight;

import java.util.Objects;

public enum StreamMode {
    STANDARD(0),
    PIP_MAIN(1),
    PIP_SECONDARY(2);
    private int value;

    StreamMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StreamMode fromValue(int value) {
        for (StreamMode streamMode : StreamMode.values()) {
            if (Objects.equals(streamMode.value, value)) {
                return streamMode;
            }
        }
        return null;
    }
}
