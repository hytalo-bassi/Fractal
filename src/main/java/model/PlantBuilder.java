package model;

import core.LSystemRule;
import java.awt.Color;

/**
 * A builder class for constructing {@link Plant} instances with customizable parameters.
 * <p>
 * This builder provides a fluent API for configuring plant rendering properties while
 * ensuring sensible defaults are applied. It follows the Builder pattern to allow
 * flexible and readable construction of complex Plant objects.
 * </p>
 * <p>
 * <strong>Usage example:</strong>
 * <pre>{@code
 * Plant plant = PlantBuilder.start(myRule, "Oak Tree")
 *     .setLineColor(Color.GREEN)
 *     .setStepSize(10.0)
 *     .setAngleIncrement(30)
 *     .build();
 * }</pre>
 * </p>
 *
 * @author Hytalo Bassi
 * @version 1.0
 * @since 1.0
 * @see Plant
 */
public class PlantBuilder {
    /** Default angle increment for rotational operations (in degrees). **/
    private static final double DEFAULT_ANGLE_INCREMENT = 25;

    /** Default starting angle for rendering (in degrees). */
    private static final double DEFAULT_STARTING_ANGLE = 90;

    /** Default step size for forward movements. */
    private static final double DEFAULT_STEP_SIZE = 8.0;

    /** Default line color for rendering. */
    private static final Color DEFAULT_LINE_COLOR = Color.BLACK;

    /** The L-System rule defining the plant's growth pattern. */
    private final LSystemRule rule;
    /** The display name of the plant. */
    private final String name;

    private Color lineColor;
    private double angleIncrement;
    private double startingAngle;
    private double stepSize;

    /**
     * Constructs a new PlantBuilder with the specified rule and name.
     * <p>
     * This constructor is private to enforce the use of the {@link #start(LSystemRule, String)}
     * factory method. All optional parameters are initialized to their default values.
     * </p>
     *
     * @param rule the L-System rule defining the plant's growth pattern
     * @param name the display name for the plant
     */
    private PlantBuilder(LSystemRule rule, String name) {
        this.lineColor = DEFAULT_LINE_COLOR;
        this.angleIncrement = DEFAULT_ANGLE_INCREMENT;
        this.startingAngle = DEFAULT_STARTING_ANGLE;
        this.stepSize = DEFAULT_STEP_SIZE;
        this.rule = rule;
        this.name = name;
    }

    /**
     * Creates a new PlantBuilder instance with the specified rule and name.
     * <p>
     * This is the entry point for building a Plant. All rendering parameters
     * will be initialized to their default values and can be customized using
     * the setter methods.
     * </p>
     *
     * @param rule the L-System rule defining the plant's growth pattern
     * @param name the display name for the plant
     * @return a new PlantBuilder instance
     * @throws NullPointerException if {@code rule} or {@code name} is {@code null}
     */
    public static PlantBuilder start(LSystemRule rule, String name) {
        return new PlantBuilder(rule, name);
    }

    /**
     * Sets the line color for rendering the plant.
     *
     * @param lineColor the color to use for drawing the plant's lines
     * @return this builder instance for method chaining
     */
    PlantBuilder setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    /**
     * Sets the angle increment for rotational operations.
     *
     * @param angleIncrement the angle increment in degrees
     * @return this builder instance for method chaining
     */
    PlantBuilder setAngleIncrement(double angleIncrement) {
        this.angleIncrement = angleIncrement;
        return this;
    }

    /**
     * Sets the starting angle at which rendering begins.
     *
     * @param startingAngle the starting angle in degrees
     * @return this builder instance for method chaining
     */
    PlantBuilder setStartingAngle(double startingAngle) {
        this.startingAngle = startingAngle;
        return this;
    }

    /**
     * Sets the step size for forward movements during rendering.
     *
     * @param stepSize the step size in pixels or units
     * @return this builder instance for method chaining
     */
    PlantBuilder setStepSize(double stepSize) {
        this.stepSize = stepSize;
        return this;
    }

    /**
     * Constructs and returns a new {@link Plant} instance with the configured parameters.
     * <p>
     * This method should be called after all desired properties have been set.
     * The builder can be reused to create multiple Plant instances.
     * </p>
     *
     * @return a new Plant instance with the configured properties
     */
    Plant build() {
        return new Plant(lineColor, stepSize, angleIncrement, startingAngle, rule, name);
    }
}
