package graphics;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a path created by turtle graphics interpretation.
 * Contains a collection of line segments that form the complete drawing.
 */
public class TurtlePath {

    private final List<Line2D.Double> lines;

    /**
     * Creates an empty turtle path
     */
    public TurtlePath() {
        this.lines = new ArrayList<>();
    }

    /**
     * Adds a line segment to the path
     * @param x1 Starting X coordinate
     * @param y1 Starting Y coordinate
     * @param x2 Ending X coordinate
     * @param y2 Ending Y coordinate
     */
    public void addLine(double x1, double y1, double x2, double y2) {
        lines.add(new Line2D.Double(x1, y1, x2, y2));
    }

    /**
     * Adds a line segment to the path
     * @param line Line2D object to add
     */
    public void addLine(Line2D.Double line) {
        lines.add(new Line2D.Double(line.x1, line.y1, line.x2, line.y2)); // Defensive copy
    }

    /**
     * Gets all line segments in the path
     * @return Unmodifiable list of line segments
     */
    public List<Line2D.Double> getLines() {
        return Collections.unmodifiableList(lines);
    }

    /**
     * Gets the number of line segments in the path
     * @return Number of line segments
     */
    public int getLineCount() {
        return lines.size();
    }

    /**
     * Checks if the path is empty
     * @return true if path contains no line segments
     */
    public boolean isEmpty() {
        return lines.isEmpty();
    }

    /**
     * Clears all line segments from the path
     */
    public void clear() {
        lines.clear();
    }

    /**
     * Gets the total length of the path
     * @return Sum of all line segment lengths
     */
    public double getTotalLength() {
        return lines.stream()
                .mapToDouble(
                        line ->
                                Math.sqrt(
                                        Math.pow(line.x2 - line.x1, 2)
                                                + Math.pow(line.y2 - line.y1, 2)))
                .sum();
    }

    @Override
    public String toString() {
        return String.format(
                "TurtlePath{lines=%d, totalLength=%.2f}", lines.size(), getTotalLength());
    }
}
