package frc.robot.util.limelight;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Limelight {
    private NetworkTable table;
    private NetworkTableEntry tv, tx, ty, ta, ts, tl, ledMode, pipeline, stream;
    private List<Pipeline> knownPipelines;
    private Target lastTarget = null;


    public Limelight(Pipeline... knownPipelines) {
        this("limelight", knownPipelines);
    }

    public Limelight(String tableName, Pipeline... knownPipelines) {
        this.table = NetworkTableInstance.getDefault().getTable(tableName);
        this.tv = table.getEntry("tv");
        this.tx = table.getEntry("tx");
        this.ty = table.getEntry("ty");
        this.ta = table.getEntry("ta");
        this.ts = table.getEntry("ts");
        this.tl = table.getEntry("tl");
        this.pipeline = table.getEntry("pipeline");
        this.ledMode = table.getEntry("ledMode");
        this.stream = table.getEntry("stream");
        this.knownPipelines = Arrays.asList(knownPipelines);
    }

    public Optional<Target> getTarget() {
        boolean exists = tv.getDouble(0.0) > 0.0;
        if (!exists) {
            return Optional.empty();
        }
        return Optional.of(lastTarget = new Target(
                tx.getDouble(0.0),
                ty.getDouble(0.0),
                ta.getDouble(0.0),
                ts.getDouble(0.0),
                tl.getDouble(0.0)));
    }

    public Optional<Target> getLastTarget() {
        return Optional.ofNullable(lastTarget);
    }

    public LEDMode getLEDMode() {
        return LEDMode.fromValue(this.ledMode.getNumber(0.0).intValue());
    }

    public void setLEDMode(LEDMode ledMode) {
        this.ledMode.setNumber(ledMode.getValue());
    }

    public int getPipelineId() {
        return this.pipeline.getNumber(0.0).intValue();
    }

    public Optional<Pipeline> getPipeline() {
        int id = getPipelineId();
        return knownPipelines.stream().filter(pipeline -> Objects.equals(pipeline.getId(), id)).findFirst();
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline.setNumber(pipeline.getId());
    }

    public StreamMode getStreamMode() {
        return StreamMode.fromValue(this.stream.getNumber(0.0).intValue());
    }

    public void setStreamMode(StreamMode streamMode) {
        this.stream.setNumber(streamMode.getValue());
    }
}
