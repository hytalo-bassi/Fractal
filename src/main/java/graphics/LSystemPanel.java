package graphics;

import core.LSystemEngine;
import core.RandomSingleton;
import core.RuleController;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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

    private boolean paused = false;

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

    private void restartAnimation() {
        currentIteration = 0;
        instructions = lsystemEngine.getRule().getAxiom();
        // In order to recreate the same tree as the first iteration
        // we need to reset the states of the SplittableRandom, otherwise
        // it will continue giving the next pseudo-random numbers in the sequence
        // instead of coming to the first number in the sequence.
        RandomSingleton.getInstance().reset();
    }

    /**
     * Update the L-System string and repaint. Changes
     */
    private void updateLSystem() {
        if (currentIteration >= MAX_ITERATIONS) {
            restartAnimation();
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
        long seed = RandomSingleton.getInstance().getSeed();
        g2d.setColor(Color.BLACK);
        g2d.drawString("Iteration: " + currentIteration, 10, 25);
        g2d.drawString("Seed: " + seed, 10, 50);
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
     * Pauses and unpauses the animation.
     */
    private void pauseAndUnpause() {
        paused = !paused;

        if (paused) animationTimer.stop();
        else animationTimer.start();
    }

    /**
     * Updates the RandomSingleton instance's seed and restarts the animation, updating
     * the tree and UI's info.
     */
    private void regenerateSeed() {
        RandomSingleton.getInstance().generateSeed();
        restartAnimation();
    }

    /**
     * Sets up keyboard controls for rule switching, seed generation and animation control.
     * LEFT arrow: previous rule, RIGHT arrow: next rule, SPACE: pauses and unpauses the animation, S: changes the seed and restarts animation
     */
    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "previousRule");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "nextRule");
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "pauseAndUnpause");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "regenerateSeed");

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

        actionMap.put(
                "pauseAndUnpause",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pauseAndUnpause();
                    }
                });

        actionMap.put(
                "regenerateSeed",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        regenerateSeed();
                    }
                });
    }

    /**
     * Switches to a different L-System rule and resets animation and instructions.
     * @param updatedController the rule controller after shifting operation
     */
    private void switchRule(RuleController updatedController) {
        lsystemEngine.setRule(updatedController.getRule());
        currentIteration = 0;
        instructions = lsystemEngine.getRule().getAxiom();
        repaint();
    }

    /**
     * Set Graphics2D parameters to draw smoother lines
     * @param g2 the Graphics2D instance
     */
    private void setAntialiasing(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        
        setAntialiasing(g2d);

        drawUIInfo(g2d);
        drawLSystem(g2d);
    }
}
