package graphics;

import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

/**
 * Represents a path created by turtle graphics interpretation.
 * Contains a complex path of curves and lines of the turtle.
 */
public class TurtlePath {
    private final Path2D.Double path;

    /**
     * Creates an empty turtle path
     */
    public TurtlePath() {
        this.path = new Path2D.Double();
    }

    /**
     * Adds a line segment to the path
     * @param x1 Starting X coordinate
     * @param y1 Starting Y coordinate
     * @param x2 Ending X coordinate
     * @param y2 Ending Y coordinate
     */
    public void addLine(double x1, double y1, double x2, double y2) {
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
    }

    /**
     * Adds a line segment to the path
     * @param line Line2D object to add
     */
    public void addLine(Line2D.Double line) {
        addLine(line.x1, line.y1, line.x2, line.y2);
    }

    /**
     * Returns the path of the turtle
     * @return the path
     */
    public Path2D.Double getPath() {
        Path2D.Double copiedPath = path;
        return copiedPath; // it can be modified, but will not affect the instance's path
    }

    /**
     * Append another path to the TurtlePath.
     * @param newPath the path to be appended
     * @param connect if true creates lines connecting the main path to the specified path
     */
    public void append(PathIterator newPath, boolean connect) {
        path.append(newPath, connect);
    }

    /**
     * Clears all path making it empty again.
     */
    public void clear() {
        path.reset();
    }
}
