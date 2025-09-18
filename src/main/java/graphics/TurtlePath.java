package graphics;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a path created by turtle graphics interpretation.
 * Contains a complex path of curves and lines of the turtle.
 */
public class TurtlePath {
    private final Color color;
    private ColoredPath currentPath;
    private List<ColoredPath> coloredPaths;

    /**
     * Creates an empty turtle path
     */
    public TurtlePath(Color defaultColor) {
        currentPath = new ColoredPath();
        color = defaultColor;
        currentPath.setColor(defaultColor);

        coloredPaths = new ArrayList<>();
    }

    /**
     * Creates an empty turtle path
     */
    public TurtlePath() {
        color = Color.BLACK;
        currentPath = new ColoredPath();
        currentPath.setColor(color);
        coloredPaths = new ArrayList<>();
    }

    /**
     * Adds a line segment to the path
     * @param x1 Starting X coordinate
     * @param y1 Starting Y coordinate
     * @param x2 Ending X coordinate
     * @param y2 Ending Y coordinate
     */
    public void addLine(double x1, double y1, double x2, double y2) {
        currentPath.moveTo(x1, y1);
        currentPath.lineTo(x2, y2);
        finishPath();
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
    public List<Path2D.Double> getPaths() {
        List<Path2D.Double> paths2d = new ArrayList<>();
        for (ColoredPath coloredPath : coloredPaths) {
            paths2d.add(coloredPath.getPath());
        }

        return paths2d;
    }

    /**
     * Returns the path of the turtle
     * @return the path
     */
    public List<ColoredPath> getColoredPaths() {
        return coloredPaths;
    }

    /**
     * Append another path to the TurtlePath.
     * @param newPath the path to be appended
     * @param connect if true creates lines connecting the main path to the specified path
     */
    public void append(PathIterator newPath, boolean connect) {
        finishPath();
        currentPath.append(newPath, connect);
        finishPath();
    }

    /**
     * Append another path to the TurtlePath.
     * @param newPath the path to be appended
     * @param connect if true creates lines connecting the main path to the specified path
     */
    public void append(PathIterator newPath, boolean connect, Color newColor) {
        finishPath();
        currentPath.append(newPath, connect);
        currentPath.setColor(newColor);
        finishPath();
    }

    private void finishPath() {
        coloredPaths.add(currentPath);
        currentPath = new ColoredPath();
        currentPath.setColor(color);
    }

    public static class ColoredPath {
        private Color color;
        private Path2D.Double path;

        public ColoredPath(Color color) {
            this.color = color;
            path = new Path2D.Double();
        }

        public ColoredPath() {
            this.color = Color.BLACK;
            path = new Path2D.Double();
        }

        public void setColor(Color newColor) {
            color = newColor;
        }

        public Color getColor() {
            return this.color;
        }

        public Path2D.Double getPath() {
            return path;
        }

        public void append(PathIterator pi, boolean connect) {
            path.append(pi, connect);
        }

        public void moveTo(double x, double y) {
            path.moveTo(x, y);
        }

        public void lineTo(double x, double y) {
            path.lineTo(x, y);
        }
    }
}
