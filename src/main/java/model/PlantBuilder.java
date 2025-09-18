package model;

import java.awt.Color;

import core.LSystemRule;

public class PlantBuilder {
    private static final double DEFAULT_ANGLE_INCREMENT = Math.toRadians(25); // 25 degrees
    private static final double DEFAULT_STARTING_ANGLE = Math.toRadians(90);
    private static final double DEFAULT_STEP_SIZE = 8.0;
    private static final Color DEFAULT_LINE_COLOR = Color.BLACK;

    private final LSystemRule rule;
    private final String name;

    private Color lineColor;
    private double angleIncrement;
    private double startingAngle;
    private double stepSize;

    private PlantBuilder(LSystemRule rule, String name) {
        this.lineColor = DEFAULT_LINE_COLOR;
        this.angleIncrement = DEFAULT_ANGLE_INCREMENT;
        this.startingAngle = DEFAULT_STARTING_ANGLE;
        this.stepSize = DEFAULT_STEP_SIZE;
        this.rule = rule;
        this.name = name;
    }

    public static PlantBuilder start(LSystemRule rule, String name) {
        return new PlantBuilder(rule, name);
    }

    PlantBuilder setLineColor(Color lineColor) { this.lineColor = lineColor; return this; }
    PlantBuilder setAngleIncrement(double angleIncrement) { this.angleIncrement = angleIncrement; return this; }
    PlantBuilder setStartingAngle(double startingAngle) { this.startingAngle = startingAngle; return this; }
    PlantBuilder setStepSize(double stepSize) { this.stepSize = stepSize; return this; }

    Plant build() {
        return new Plant(lineColor, stepSize, angleIncrement, startingAngle, rule, name);
    }
}
