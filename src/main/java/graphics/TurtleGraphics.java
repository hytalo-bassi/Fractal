package graphics;

import core.BaseLeaf;
import core.LSystemEngine;
import core.impl.leafs.SimpleLeaf;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Stack;
import model.Plant;
import model.TurtleState;

/**
 * Interprets L-System strings using turtle graphics commands and converts them into drawable geometric paths.
 * <p>
 * This class implements a turtle graphics interpreter that processes symbolic L-System strings
 * and generates corresponding visual representations. The interpreter maintains a virtual turtle
 * with position and orientation, responding to commands like move forward, turn, and branch.
 * </p>
 * <p>
 * The interpreter supports both simple single-character commands (F, f, +, -, [, ]) and
 * parametric modules with custom parameters. It also includes support for rendering leaves
 * with automatic transformation and placement.
 * </p>
 * <p>
 * <strong>Supported Commands:</strong>
 * <ul>
 * <li><strong>F</strong> - Move forward and draw a line</li>
 * <li><strong>f</strong> - Move forward without drawing</li>
 * <li><strong>+</strong> - Turn left by the angle increment</li>
 * <li><strong>-</strong> - Turn right by the angle increment</li>
 * <li><strong>[</strong> - Push current state onto the stack (begin branch)</li>
 * <li><strong>]</strong> - Pop state from the stack (end branch)</li>
 * <li><strong>0</strong> - Draw a leaf at the current position and angle</li>
 * <li><strong>F(n)</strong> - Move forward n units and draw</li>
 * <li><strong>A(degrees)</strong> - Turn by an arbitrary angle</li>
 * </ul>
 * </p>
 *
 * @author Hytalo Bassi
 * @version 1.0
 * @since 1.0
 * @see TurtlePath
 * @see TurtleState
 * @see Plant
 */
public class TurtleGraphics {
    /** Default size for leaf rendering. */
    private static final double DEFAULT_LEAF_SIZE = 5;

    /** Default stem length for leaves. */
    private static final double DEFAULT_LEAF_STEM_LENGTH = 1;

    /** Default width for leaves. */
    private static final double DEFAULT_LEAF_WIDTH = 1.5;

    /** Default angle increment for turns (in degrees). */
    private static final double DEFAULT_ANGLE_INCREMENT = 25;

    /** Default step size for forward movements. */
    private static final double DEFAULT_STEP_SIZE = 8.0;

    /** Default color for line rendering. */
    private static final Color DEFAULT_LINE_COLOR = Color.getHSBColor(0, 0, 0);

    /** Default starting angle (in degrees, pointing upward). */
    private static final double DEFAULT_STARTING_ANGLE = 90;

    /** Default color for leaf rendering. */
    private static final Color DEFAULT_LEAF_COLOR = Color.getHSBColor(0.3333f, 0.8f, 0.7f);

    /** The starting angle for the turtle (in radians). */
    private double startingAngle;

    /** The angle increment for turn commands (in radians). */
    private double angleIncrement;

    /** The step size for forward movement commands. */
    private double stepSize;

    /** The color used for rendering lines. */
    private Color lineColor;

    /** The color used for rendering leaves. */
    private Color leafColor;

    /** The leaf instance. */
    private BaseLeaf leaf = new SimpleLeaf(
        DEFAULT_LEAF_STEM_LENGTH,
        DEFAULT_LEAF_WIDTH,
        DEFAULT_LEAF_SIZE
    );
    // TODO: Get leaf instances from outside the TurtleGraphics.

    /**
     * Constructs a TurtleGraphics interpreter with default parameters.
     * <p>
     * Default values include:
     * <ul>
     * <li>Angle increment: 25 degrees</li>
     * <li>Step size: 8.0 units</li>
     * <li>Starting angle: 90 degrees (upward)</li>
     * <li>Line color: Black</li>
     * <li>Leaf color: Green (HSB: 0.3333, 0.8, 0.7)</li>
     * </ul>
     * </p>
     */
    public TurtleGraphics() {
        this(
                DEFAULT_ANGLE_INCREMENT,
                DEFAULT_STEP_SIZE,
                DEFAULT_STARTING_ANGLE,
                DEFAULT_LINE_COLOR,
                DEFAULT_LEAF_COLOR);
    }

    /**
     * Constructs a TurtleGraphics interpreter with custom parameters.
     *
     * @param angleIncrement the angle increment for turn commands (in degrees)
     * @param stepSize the step size for forward movement commands
     * @param startingAngle the starting angle for the turtle (in degrees)
     * @param lineColor the color for rendering lines
     * @param leafColor the color for rendering leaves
     */
    public TurtleGraphics(
            double angleIncrement,
            double stepSize,
            double startingAngle,
            Color lineColor,
            Color leafColor) {
        this.angleIncrement = Math.toRadians(angleIncrement);
        this.stepSize = stepSize;
        this.startingAngle = Math.toRadians(startingAngle);
        this.lineColor = lineColor;
        this.leafColor = leafColor;
    }

    /**
     * Constructs a TurtleGraphics interpreter configured from a Plant model.
     * <p>
     * This constructor extracts rendering parameters from the Plant object,
     * using its default angle increment, step size, starting angle, and line color.
     * The leaf color is set to the default green.
     * </p>
     *
     * @param plant the plant model containing rendering parameters
     */
    public TurtleGraphics(Plant plant) {
        this(
                plant.getDefaultAngleIncrement(),
                plant.getDefaultStepSize(),
                plant.getStartingAngle(),
                plant.getLineColor(),
                DEFAULT_LEAF_COLOR);
    }

    /**
     * Updates the interpreter's parameters from a new Plant model.
     * <p>
     * This method reconfigures the interpreter to match the rendering parameters
     * of the specified plant, allowing dynamic switching between different plant
     * configurations without creating a new interpreter instance.
     * </p>
     *
     * @param newPlant the plant model containing the new rendering parameters
     */
    public void updatesInterpretation(Plant newPlant) {
        this.angleIncrement = Math.toRadians(newPlant.getDefaultAngleIncrement());
        this.stepSize = newPlant.getDefaultStepSize();
        this.startingAngle = Math.toRadians(newPlant.getStartingAngle());
        this.lineColor = newPlant.getLineColor();
        this.leafColor = DEFAULT_LEAF_COLOR;
    }

    /**
     * Interprets an L-System string as turtle graphics commands and generates a drawable path.
     * <p>
     * This method processes each symbol in the L-System string sequentially, maintaining
     * a turtle state that tracks position and orientation. It supports both simple commands
     * and parametric modules, using a stack to handle branching structures.
     * </p>
     *
     * @param lSystemString the L-System string containing turtle commands and modules
     * @param startPosition the starting position for the turtle
     * @return a TurtlePath containing the interpreted geometric paths
     */
    public TurtlePath interpret(String lSystemString, Point2D startPosition) {
        TurtlePath path = new TurtlePath(lineColor);
        TurtleState turtle =
                new TurtleState(startPosition.getX(), startPosition.getY(), startingAngle);
        Stack<TurtleState> stateStack = new Stack<>();

        for (String symbol : LSystemEngine.splitSymbols(lSystemString)) {
            // Modules have more than 3 chars, normal symbols just one
            if (symbol.length() == 1) {
                processCommand(symbol.charAt(0), turtle, stateStack, path);
            } else {
                String[] params = LSystemEngine.splitParameters(symbol);
                processCommand(symbol.charAt(0), params, turtle, stateStack, path);
            }
        }

        return path;
    }

    /**
     * Processes a single simple turtle graphics command.
     * <p>
     * This method handles single-character commands that don't require parameters,
     * such as basic movement, rotation, and state management operations.
     * </p>
     *
     * @param command the command character to process
     * @param turtle the current turtle state
     * @param stateStack the stack for managing branching states
     * @param path the path being constructed
     */
    private void processCommand(
            char command, TurtleState turtle, Stack<TurtleState> stateStack, TurtlePath path) {
        switch (command) {
            case 'F': // Move forward and draw
                moveForward(turtle, path, true);
                break;

            case 'f': // Move forward without drawing
                moveForward(turtle, path, false);
                break;

            case '+': // Turn left
                turtle.turn(angleIncrement);
                break;

            case '-': // Turn right
                turtle.turn(-angleIncrement);
                break;

            case '[': // Push state to stack
                stateStack.push(turtle.copy());
                break;

            case ']': // Pop state from stack
                if (!stateStack.isEmpty()) {
                    turtle.copyFrom(stateStack.pop());
                }
                break;
            case '0':
                growLeaf(turtle, path);
                break;
        }
    }

    /**
     * Processes a single parametric turtle graphics command with parameters.
     * <p>
     * This method handles commands that include parameters, such as variable-length
     * forward movements or arbitrary angle rotations. If no parameters are provided,
     * it delegates to the simple command processor.
     * </p>
     *
     * @param command the command character to process
     * @param params the array of string parameters for the command
     * @param turtle the current turtle state
     * @param stateStack the stack for managing branching states
     * @param path the path being constructed
     */
    private void processCommand(
            char command,
            String[] params,
            TurtleState turtle,
            Stack<TurtleState> stateStack,
            TurtlePath path) {

        // If there is no parameter, consider it a normal L-System.
        if (params.length < 1) {
            processCommand(command, turtle, stateStack, path);
            return;
        }

        switch (command) {
            case 'F': // Move forward and draw
                moveForward(Double.parseDouble(params[0]), turtle, path, true);
                break;

            case 'f': // Move forward without drawing
                moveForward(turtle, path, false);
                break;

            case 'A': // Turn arbitrarily
                double rad = Math.toRadians(Double.parseDouble(params[0]));
                turtle.turn(rad);
                break;

            case '+': // Turn left
                turtle.turn(angleIncrement);
                break;

            case '-': // Turn right
                turtle.turn(-angleIncrement);
                break;

            case '[': // Push state to stack
                stateStack.push(turtle.copy());
                break;

            case ']': // Pop state from stack
                if (!stateStack.isEmpty()) {
                    turtle.copyFrom(stateStack.pop());
                }
                break;
        }
    }

    /**
     * Renders a leaf at the current turtle position and orientation.
     * <p>
     * This method creates or reuses a cached leaf geometry and transforms it
     * to match the turtle's current position and angle. The leaf is then
     * appended to the path with the configured leaf color.
     * </p>
     *
     * @param turtle the current turtle state determining leaf position and orientation
     * @param path the path to which the leaf will be appended
     */
    private void growLeaf(TurtleState turtle, TurtlePath path) {
        transformLeaf(path, turtle, leaf.getLeafPath());
    }

    /**
     * Transforms and appends a leaf path to the main path at the turtle's position.
     * <p>
     * This method applies an affine transformation to position and rotate the leaf
     * geometry according to the turtle's current state. The transformation accounts
     * for the starting angle to ensure correct leaf orientation.
     * </p>
     *
     * @param path the main path to which the leaf will be appended
     * @param turtle the turtle state holding position and direction values
     * @param leafPath the leaf geometry to be transformed
     */
    private void transformLeaf(TurtlePath path, TurtleState turtle, Path2D.Double leafPath) {
        AffineTransform transform = new AffineTransform();
        transform.translate(turtle.getX(), turtle.getY());
        transform.rotate(
                startingAngle - turtle.getAngle()); // this subtraction corrects the leaf angle

        path.append(leafPath.getPathIterator(transform), false, leafColor);
    }


    /**
     * Moves the turtle forward by the default step size, optionally drawing a line.
     * <p>
     * This is a convenience method that uses the interpreter's configured step size.
     * </p>
     *
     * @param turtle the turtle state to update
     * @param path the path to which a line may be added
     * @param draw if {@code true}, draws a line; if {@code false}, moves without drawing
     */
    private void moveForward(TurtleState turtle, TurtlePath path, boolean draw) {
        moveForward(stepSize, turtle, path, draw);
    }

    /**
     * Moves the turtle forward by a specified distance, optionally drawing a line.
     * <p>
     * This method calculates the new position based on the turtle's current angle
     * and updates the turtle state. If drawing is enabled, it adds a line segment
     * from the current position to the new position.
     * </p>
     *
     * @param step the distance to move forward
     * @param turtle the turtle state to update
     * @param path the path to which a line may be added
     * @param draw if {@code true}, draws a line; if {@code false}, moves without drawing
     */
    private void moveForward(double step, TurtleState turtle, TurtlePath path, boolean draw) {
        double currentX = turtle.getX();
        double currentY = turtle.getY();

        double newX = currentX + step * Math.cos(turtle.getAngle());
        double newY = currentY - step * Math.sin(turtle.getAngle());

        if (draw) {
            path.addLine(currentX, currentY, newX, newY);
        }

        turtle.moveTo(newX, newY);
    }
}
