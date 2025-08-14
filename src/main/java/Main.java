import core.FractalBinaryTree;
import core.LSystemEngine;
import core.impl.SimplePlant;
import core.impl.BinaryTree;
import core.impl.KochSnowflake;
import graphics.Renderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

import javax.swing.*;

class TurtlePanel extends JPanel implements ActionListener {
	private LSystemEngine lsystemEngine;
	private int currentIteration = 0;
	private int maxIterations = 11;
	private javax.swing.Timer timer;
	private String instructions;

	public TurtlePanel(LSystemEngine lSystemEngine_) {
		lsystemEngine = lSystemEngine_;
		timer = new javax.swing.Timer(1000, this);
	        timer.start();

		update();
	}
	
	private void update() {
		instructions = lsystemEngine.generate(currentIteration);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		currentIteration = (currentIteration + 1) % (maxIterations + 1);
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
		Renderer renderer = new Renderer();
		renderer.render(g2, instructions, new Point2D.Double(getWidth() / 2, getHeight()), currentIteration);
	}

}

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Turtle Graphics in Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TurtlePanel panel = new TurtlePanel(new LSystemEngine(new KochSnowflake()));
        frame.add(panel);

        frame.setVisible(true);
    }
}

