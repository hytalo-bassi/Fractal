import core.LSystemEngine;
import core.impl.BinaryTree;
import graphics.Renderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import javax.swing.*;

/**
 * Panel that displays animated L-System turtle graphics.
 * Cycles through iterations automatically using a timer.
 */
class TurtlePanel extends JPanel implements ActionListener {
    private static final int TIMER_DELAY = 1000;
    private static final int MAX_ITERATIONS = 8;

    private LSystemEngine lsystemEngine;
    private int currentIteration = 0;
    private javax.swing.Timer timer;
    private String instructions;
    private final Renderer renderer;

    public TurtlePanel(LSystemEngine lSystemEngine) {
        renderer = new Renderer();
        this.lsystemEngine = lSystemEngine;
        timer = new javax.swing.Timer(TIMER_DELAY, this);
        timer.start();

        update();
    }

    /**
     * Update the L-System string each iteration
     */
    private void update() {
        instructions = lsystemEngine.generate(currentIteration);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentIteration = (currentIteration + 1) % (MAX_ITERATIONS + 1);
        update();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.white);

        g.setColor(Color.BLACK);
        g.drawString("Iteration: " + currentIteration, 10, 25);
        Graphics2D g2 = (Graphics2D) g;
        Point2D startPoint = new Point2D.Double(getWidth() / 2, getHeight());

        renderer.render(g2, instructions, startPoint, currentIteration);
    }
}

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Turtle Graphics in Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TurtlePanel panel = new TurtlePanel(new LSystemEngine(new BinaryTree()));
        frame.add(panel);

        frame.setVisible(true);
    }
}
