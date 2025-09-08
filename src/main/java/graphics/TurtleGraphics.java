package graphics;

import core.LSystemEngine;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Stack;
import model.TurtleState;

/**
 * Interprets L-System strings using turtle graphics commands.
 * Converts symbolic representations into drawable geometric paths.
 */
public class TurtleGraphics {

    private static final double DEFAULT_LEAF_SIZE = 5;
    private static final double DEFAULT_LEAF_STEM_LENGTH = 1;
    private static final double DEFAULT_LEAF_WIDTH_RATIO = 0.3;
    private static final double DEFAULT_ANGLE_INCREMENT = Math.toRadians(25); // 25 degrees
    private static final double DEFAULT_STEP_SIZE = 8.0;

    private final double angleIncrement;
    private final double stepSize;

    private Path2D.Double cachedLeaf = null;

    /**
     * Creates interpreter with default parameters
     */
    public TurtleGraphics() {
        this(DEFAULT_ANGLE_INCREMENT, DEFAULT_STEP_SIZE);
    }

    /**
     * Creates interpreter with custom parameters
     * @param angleIncrement Angle increment for turns (in radians)
     * @param stepSize Step size for forward movement
     */
    public TurtleGraphics(double angleIncrement, double stepSize) {
        this.angleIncrement = angleIncrement;
        this.stepSize = stepSize;
    }

    /**
     * Interprets L-System string as turtle graphics commands
     * @param lSystemString String containing turtle commands
     * @param startPosition Starting position for turtle
     * @return TurtlePath containing the interpreted path
     */
    public TurtlePath interpret(String lSystemString, Point2D startPosition) {
        TurtlePath path = new TurtlePath();
        TurtleState turtle =
                new TurtleState(
                        startPosition.getX(),
                        startPosition.getY(),
                        Math.toRadians(90)); // Start pointing up
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
     * Processes a single turtle graphics command
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
     * Processes a single parametric turtle graphics command
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
     * Grows one leaf and appends it to the tree. The leaf position and direction is determined
     * by the last TurtleState.
     * @param turtle the last TurtleState
     * @param path the main path
     */
    private void growLeaf(TurtleState turtle, TurtlePath path) {
        if (cachedLeaf == null) {
            cachedLeaf =
                    leafPath(DEFAULT_LEAF_SIZE, DEFAULT_LEAF_WIDTH_RATIO, DEFAULT_LEAF_STEM_LENGTH);
        }

        transformLeaf(path, turtle, cachedLeaf);
    }

    /**
     * Transforms a leaf path, placing it a the last TurtleState's position and angle.
     *
     * @param path the main path to put the leaf.
     * @param turtle the TurtleState holding positions and direction values.
     * @param leafPath the actual leaf.
     */
    private void transformLeaf(TurtlePath path, TurtleState turtle, Path2D.Double leafPath) {
        AffineTransform transform = new AffineTransform();
        transform.translate(turtle.getX(), turtle.getY());
        transform.rotate(
                Math.toRadians(90)
                        - turtle.getAngle()); // we need to do this subtraction due to the
        // turtle initial state pointing up (90°).

        path.append(leafPath.getPathIterator(transform), false);
    }

    private Path2D.Double leafPath(double size, double widthRatio, double stemLength) {
        Path2D.Double leaf = new Path2D.Double();
        double width = size * widthRatio;

        leaf.moveTo(0, 0);

        leaf.lineTo(0, -stemLength);

        leaf.curveTo(
                -width * 0.3,
                -stemLength - size * 0.2, // Control point 1
                -width,
                -stemLength - size * 0.6, // Control point 2
                -width * 0.7,
                -stemLength - size);

        leaf.curveTo(
                -width * 0.3, -stemLength - size * 1.1, // Control point 1
                width * 0.3, -stemLength - size * 1.1, // Control point 2
                width * 0.7, -stemLength - size);

        leaf.curveTo(
                width,
                -stemLength - size * 0.6, // Control point 1
                width * 0.3,
                -stemLength - size * 0.2, // Control point 2
                0,
                -stemLength);

        leaf.lineTo(0, 0);

        leaf.closePath();
        return leaf;
    }

    /**
     * Moves turtle forward, optionally drawing a line
     */
    private void moveForward(TurtleState turtle, TurtlePath path, boolean draw) {
        moveForward(stepSize, turtle, path, draw);
    }

    /**
     * Moves turtle forward `step` units, optionally drawing a line
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
