package graphics;

import graphics.TurtlePath.ColoredPath;
import java.awt.*;
import java.awt.geom.Point2D;
import model.Plant;

/**
 * Renders L-System strings as graphics using turtle graphics interpretation.
 * Handles color schemes, stroke settings, and coordinate transformations.
 */
public class Renderer {

    private static final float BASE_STROKE_WIDTH = 2.0f;

    private final TurtleGraphics interpreter;

    public Renderer() {
        this.interpreter = new TurtleGraphics();
    }

    public Renderer(Plant plant) {
        this.interpreter = new TurtleGraphics(plant);
    }

    public void updatesInterpreter(Plant newPlant) {
        interpreter.updatesInterpretation(newPlant);
    }

    /**
     * Renders an L-System string to the graphics context
     * @param g2d Graphics context to render to
     * @param lSystemString L-System string to render
     * @param startPosition Starting position for rendering
     * @param iteration Current iteration (used for coloring)
     */
    public void render(Graphics2D g2d, String lSystemString, Point2D startPosition, int iteration) {
        if (lSystemString == null || lSystemString.isEmpty()) {
            return;
        }

        setupGraphicsContext(g2d, iteration);

        TurtlePath path = interpreter.interpret(lSystemString, startPosition);

        renderPath(g2d, path);
    }

    /**
     * Sets up graphics context with appropriate colors and stroke
     */
    private void setupGraphicsContext(Graphics2D g2d, int iteration) {
        g2d.setStroke(
                new BasicStroke(BASE_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    /**
     * Renders the turtle path
     */
    private void renderPath(Graphics2D g2d, TurtlePath path) {
        for (ColoredPath coloredPath : path.getColoredPaths()) {
            g2d.setColor(coloredPath.getColor());
            g2d.draw(coloredPath.getPath());
        }
    }
}
