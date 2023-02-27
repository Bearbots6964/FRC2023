package frc.robot.Interfaces;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

public class iDashControls {


	/**
     * Creates a new widget on Shuffleboard with the specified ID with the ability to get the value, implemented in a different method.
     *
     */
    public GenericEntry newWidget(String widgetID, String widgetName, String widgetType, double widgetValue) {
        GenericEntry widget = Shuffleboard.getTab("Controls").add(widgetID, widgetName).withWidget(widgetType).withPosition(0, 0).withSize(2, 1).withProperties(Map.of("Value", widgetValue)).getEntry();
        return widget;
    }

    /**
     * Gets the value of the widget with the specified ID.
     *
     */
    public double getWidgetValue(GenericEntry widget) {
        return widget.getDouble(0);
    }
}
