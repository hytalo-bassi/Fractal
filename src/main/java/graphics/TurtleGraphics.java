package graphics;

import java.awt.geom.Point2D;
import model.TurtleState;
import java.util.Stack;

/**
 * Interprets L-System strings using turtle graphics commands.
 * Converts symbolic representations into drawable geometric paths.
 */
public class TurtleGraphics {
    
    private static final double DEFAULT_ANGLE_INCREMENT = Math.toRadians(25); // 25 degrees
    private static final double DEFAULT_STEP_SIZE = 8.0;
    
    private final double angleIncrement;
    private final double stepSize;
    
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
        TurtleState turtle = new TurtleState(startPosition.getX(), 
                                           startPosition.getY(), 
                                           Math.toRadians(90)); // Start pointing up
        Stack<TurtleState> stateStack = new Stack<>();
        
        for (char command : lSystemString.toCharArray()) {
            processCommand(command, turtle, stateStack, path);
        }
        
        return path;
    }
    
    /**
     * Processes a single turtle graphics command
     */
    private void processCommand(char command, TurtleState turtle, 
                              Stack<TurtleState> stateStack, TurtlePath path) {
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
                
        }
    }
    
    /**
     * Moves turtle forward, optionally drawing a line
     */
    private void moveForward(TurtleState turtle, TurtlePath path, boolean draw) {
        double currentX = turtle.getX();
        double currentY = turtle.getY();
        
        double newX = currentX + stepSize * Math.cos(turtle.getAngle());
        double newY = currentY - stepSize * Math.sin(turtle.getAngle()); 

        if (draw) {
            path.addLine(currentX, currentY, newX, newY);
        }
        
        turtle.moveTo(newX, newY);
    }
}

