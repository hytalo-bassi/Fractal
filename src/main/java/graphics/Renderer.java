package graphics;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Renders L-System strings as graphics using turtle graphics interpretation.
 * Handles color schemes, stroke settings, and coordinate transformations.
 */
public class Renderer {
    
    private static final float BASE_STROKE_WIDTH = 2.0f;
    private static final float SATURATION = 0.8f;
    private static final float BRIGHTNESS = 0.9f;
    
    private final TurtleGraphics interpreter;
    
    public Renderer() {
        this.interpreter = new TurtleGraphics();
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
        // Calculate color based on iteration
        Color color = calculateIterationColor(iteration);
        g2d.setColor(color);
        
        // Set stroke
        g2d.setStroke(new BasicStroke(BASE_STROKE_WIDTH, 
                                    BasicStroke.CAP_ROUND, 
                                    BasicStroke.JOIN_ROUND));
    }
    
    /**
     * Calculates color based on iteration number using HSB color space
     */
    private Color calculateIterationColor(int iteration) {
        float hue = (iteration * 60f) % 360f / 360f; // Cycle through hues
        return Color.getHSBColor(hue, SATURATION, BRIGHTNESS);
    }
    
    /**
     * Renders the turtle path as line segments
     */
    private void renderPath(Graphics2D g2d, TurtlePath path) {
        List<Line2D.Double> lines = path.getLines();
        
        for (Line2D.Double line : lines) {
            g2d.draw(line);
        }
    }
    
    /**
     * Sets custom rendering parameters
     * @param strokeWidth Custom stroke width
     */
    public void setStrokeWidth(float strokeWidth) {
        // This could be extended to allow customization
        // For now, keeping the renderer simple
    }
}
