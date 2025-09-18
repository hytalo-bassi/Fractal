package model;

import core.LSystemRule;
import java.awt.Color;

/**
 * Holds plant's rendering properties like line color, leaf color and so on.
 */
public class Plant {
    private final LSystemRule rule;
    private final String name;

    private Color lineColor;
    private double defaultStepSize;
    private double defaultAngleIncrement;
    private double startingAngle;

    public Plant(
            Color lineColor,
            double defaultStepSize,
            double defaultAngleIncrement,
            double startingAngle,
            LSystemRule rule,
            String name) {
        this.lineColor = lineColor;
        this.defaultStepSize = defaultStepSize;
        this.defaultAngleIncrement = defaultAngleIncrement;
        this.startingAngle = startingAngle;
        this.rule = rule;
        this.name = name;
    }

    public LSystemRule getRule() {
        return rule;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public double getDefaultStepSize() {
        return defaultStepSize;
    }

    public double getDefaultAngleIncrement() {
        return defaultAngleIncrement;
    }

    public double getStartingAngle() {
        return startingAngle;
    }

    public String getName() {
        return name;
    }
}
