package model;

/**
 * Represents the state of a turtle in turtle graphics.
 * Maintains position and orientation information.
 */
public class TurtleState {

    private double x;
    private double y;
    private double angle; // in radians

    /**
     * Creates a new turtle state
     * @param x Initial X coordinate
     * @param y Initial Y coordinate
     * @param angle Initial angle in radians
     */
    public TurtleState(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    /**
     * Copy constructor
     * @param other TurtleState to copy from
     */
    public TurtleState(TurtleState other) {
        this.x = other.x;
        this.y = other.y;
        this.angle = other.angle;
    }

    /**
     * Creates a copy of this turtle state
     * @return New TurtleState with same values
     */
    public TurtleState copy() {
        return new TurtleState(this);
    }

    /**
     * Copies values from another turtle state
     * @param other State to copy from
     */
    public void copyFrom(TurtleState other) {
        this.x = other.x;
        this.y = other.y;
        this.angle = other.angle;
    }

    /**
     * Moves turtle to new position
     * @param newX New X coordinate
     * @param newY New Y coordinate
     */
    public void moveTo(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    /**
     * Turns turtle by specified angle
     * @param deltaAngle Angle to turn by (in radians)
     */
    public void turn(double deltaAngle) {
        this.angle += deltaAngle;
    }

    // Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public String toString() {
        return String.format(
                "TurtleState{x=%.2f, y=%.2f, angle=%.2f}", x, y, Math.toDegrees(angle));
    }
}
