package model;

import core.LSystemRule;
import java.awt.Color;


/**
 * Represents a plant model defined by L-System rules and rendering parameters.
 * <p>
 * This immutable class encapsulates the graphical and mathematical properties
 * needed to render a fractal plant using Lindenmayer System (L-System) rules.
 * Each plant instance combines visual attributes such as color and angles with
 * the underlying L-System rule that governs its growth pattern.
 * </p>
 *
 * @author Hytalo Bassi
 * @version 1.0
 * @since 1.0
 */
public class Plant {
    private final LSystemRule rule;
    /** The display name of this plant. */
    private final String name;

    private Color lineColor;

    /** The default distance for each forward step in the rendering. Might change depending on the LSystem */
    private double defaultStepSize;

    /** The default angle (in degrees) for rotational operations. Might change depending on the LSystem */
    private double defaultAngleIncrement;
    private double startingAngle;

    /**
     * Constructs a new Plant with the specified rendering parameters and L-System rule.
     *
     * @param lineColor the color to use when drawing the plant's lines
     * @param defaultStepSize the default distance to move forward for each step
     * @param defaultAngleIncrement the default angle increment (in degrees) for turns
     * @param startingAngle the initial angle (in degrees) at which to begin rendering
     * @param rule the L-System rule defining the plant's growth pattern
     * @param name the display name for this plant
     * @throws NullPointerException if {@code rule} or {@code name} is {@code null}
     */
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

    /**
     * Returns the L-System rule associated with this plant.
     *
     * @return the L-System rule defining the plant's growth pattern
     */
    public LSystemRule getRule() {
        return rule;
    }

    /**
     * Returns the line color used to render this plant.
     *
     * @return the line color
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * Returns the default step size for forward movements during rendering.
     *
     * @return the default step size in pixels or units
     */
    public double getDefaultStepSize() {
        return defaultStepSize;
    }

    /**
     * Returns the default angle increment for rotational operations.
     *
     * @return the default angle increment in degrees
     */
    public double getDefaultAngleIncrement() {
        return defaultAngleIncrement;
    }

    /**
     * Returns the starting angle at which rendering begins.
     *
     * @return the starting angle in degrees
     */
    public double getStartingAngle() {
        return startingAngle;
    }

    /**
     * Returns the display name of this plant.
     *
     * @return the plant's name
     */
    public String getName() {
        return name;
    }
}
