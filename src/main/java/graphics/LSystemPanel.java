package graphics;

import core.LSystemEngine;
import core.RuleController;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 * Panel that displays animated L-System turtle graphics.
 * Cycles through iterations automatically using a timer.
 */
public class LSystemPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final int TIMER_DELAY_MS = 1000;
    private static final int MAX_ITERATIONS = 8;

    private LSystemEngine lsystemEngine;
    private int currentIteration = 0;
    private String instructions;
    private final Timer animationTimer;
    private final Renderer renderer;
    private final RuleController controller;

    public LSystemPanel(RuleController controller, Renderer renderer) {
        this.controller = controller;
        this.renderer = renderer;

        animationTimer = createAnimationTimer();
        this.lsystemEngine = new LSystemEngine(controller.getRule());
        instructions = lsystemEngine.getRule().getAxiom();

        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
        startAnimation();
        setupKeyBindings();
    }

    private Timer createAnimationTimer() {
        return new Timer(TIMER_DELAY_MS, e -> updateLSystem());
    }

    private void startAnimation() {
        animationTimer.start();
    }

    /**
     * Update the L-System string and repaint
     */
    private void updateLSystem() {
        if (currentIteration >= MAX_ITERATIONS) {
            currentIteration = 0;
            instructions = lsystemEngine.getRule().getAxiom();
        } else {
            currentIteration++;
            instructions = lsystemEngine.applyRules(instructions);
        }

        repaint();
    }

    /**
     * Draws UI's info.
     * @param g2d the graphics2d instance to draw the info
     */
    private void drawUIInfo(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawString("Iteration: " + currentIteration, 10, 25);
    }

    /**
     * Renders the L-system at starting point.
     * @param g2d the graphics2d instance to render the system
     */
    private void drawLSystem(Graphics2D g2d) {
        Point2D startPoint = new Point2D.Double(getWidth() / 2, getHeight());

        renderer.render(g2d, instructions, startPoint, currentIteration);
    }

    /**
     * Sets up keyboard controls for rule switching.
     * LEFT arrow: previous rule, RIGHT arrow: next rule
     */
    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "previousRule");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "nextRule");

        actionMap.put(
                "previousRule",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switchRule(controller.shiftLeft());
                    }
                });

        actionMap.put(
                "nextRule",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switchRule(controller.shiftRight());
                    }
                });
    }

    /**
     * Switches to a different L-System rule and resets animation.
     * @param updatedController the rule controller after shifting operation
     */
    private void switchRule(RuleController updatedController) {
        lsystemEngine.setRule(updatedController.getRule());
        currentIteration = 0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        drawUIInfo(g2d);
        drawLSystem(g2d);
    }
}
