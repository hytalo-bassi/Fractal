package graphics;

import graphics.TurtlePath.ColoredPath;
import java.awt.*;
import java.awt.geom.Point2D;
import model.Plant;

/**
 * Sets rendering options and draws a TurtleGraphics.
 * <p>
 * This class controls the rendering part of the application, setting properties on how to draw a segment, updating
 * interpretation and actually rendering a Path to a Graphics2D. It mantains a {@link TurtleGraphics} interpreter 
 * instance and force updates to the interpreter when changing plants.
 * </p>
 * <p>
 * The renderer receives a list of {@link ColoredPath} from the interpreter and then draws each segment changing its
 * colors automatically. A client can render a LSystem string by calling {@link #render(Graphics2D, String, Point2D, int)}
 * method and informing the needed arguments. The renderer method start drawing at a specified starting point.
 * </p>
 * 
 * @author Hytalo Bassi
 * @version 1.0
 * @since 1.0
 * @see TurtleGraphics
 * @see TurtlePath
 */
public class Renderer {

    /* Default line stroke. */
    private static final float BASE_STROKE_WIDTH = 2.0f;

    /* LSystem interpreter instance */
    private final TurtleGraphics interpreter;

    /**
     * Constructs a new Renderer with default properties.
     * <p>
     * The constructor initializes a default TurtleGraphics instance and uses it to interpret the LSystem.
     * </p>
     */
    public Renderer() {
        this.interpreter = new TurtleGraphics();
    }

    /**
     * Constructs a new Renderer with custom plant's properties.
     * <p>
     * The constructor initializes a new TurtleGraphics with custom properties from the plant instance.
     * </p>
     * 
     * @param plant the plant instance to get the interpreter properties from.
     */
    public Renderer(Plant plant) {
        this.interpreter = new TurtleGraphics(plant);
    }

    /**
     * Updates the interpretation by changing the plant.
     * <p>
     * It calls the {@link TurtleGraphics}'s {@code updatesInterpretation(Plant)} method to update the interpretation
     * to the newPlant.
     * </p>
     * 
     * @param newPlant the new plant instance to get the interpreter properties from.
     */
    public void updatesInterpreter(Plant newPlant) {
        interpreter.updatesInterpretation(newPlant);
    }

    /**
     * Renders a LSystem.
     * <p>
     * The method calls {@link #setupGraphicsContext(Graphics2D, int)} then receives a path
     * from the interpreter and start drawing the path by calling {@link #renderPath(Graphics2D, TurtlePath)}.
     * </p>
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
     * Sets up graphics context with appropriate features.
     * <p>
     * Sets up the Graphics2D's stroke.
     * </p>
     * 
     * @param g2d the graphics2d instance
     * @param iteration is not used anymore, set it to whatever you like.
     */
    private void setupGraphicsContext(Graphics2D g2d, int iteration) {
        g2d.setStroke(
                new BasicStroke(BASE_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    /**
     * Draws the turtle path.
     * 
     * <p>
     * The method loops through a list of {@code ColoredPath} and each iteration it draws a segment (line)
     * with some color.
     * </p>
     * 
     * @param g2d the graphics2d instance
     * @param path the path to draw.
     */
    private void renderPath(Graphics2D g2d, TurtlePath path) {
        for (ColoredPath coloredPath : path.getColoredPaths()) {
            g2d.setColor(coloredPath.getColor());
            g2d.draw(coloredPath.getPath());
        }
    }
}
