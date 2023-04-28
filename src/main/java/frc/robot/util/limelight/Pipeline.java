package frc.robot.util.limelight;

public class Pipeline {
    private String name;
    private int id;

    public Pipeline(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
