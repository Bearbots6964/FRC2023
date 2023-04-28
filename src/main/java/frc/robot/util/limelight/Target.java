package frc.robot.util.limelight;

public class Target {
    private double x, y, area, skew, latency;

    public Target(double x, double y, double area, double skew, double latency) {
        this.x = x;
        this.y = y;
        this.area = area;
        this.skew = skew;
        this.latency = latency;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getArea() {
        return area;
    }

    public double getSkew() {
        return skew;
    }

    public double getLatency() {
        return latency;
    }
}
