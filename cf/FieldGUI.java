package cf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FieldGUI extends JFrame implements MouseListener {

	private final int WIDTH = 490;
	private final int HEIGHT = 490;
	private final int CIRCLE_DIAMETER = 70;
	private Game game;
	private Color[] colors = { Color.RED, Color.BLUE };
	// private int[] slots = { 0, 0, 0, 0, 0, 0, 0 };
	private Data inputPipe;

	// public FieldGUI(Game game) {
	public FieldGUI(Game game, Data data) {
		super();

		this.game = game;
		setSize(WIDTH, HEIGHT);
		setLocation(100, 100);
		addMouseListener(this);
		setVisible(true);
		startF(this.getGraphics());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getGraphics().drawString("Game", 0, 0);
		this.inputPipe = data;
		// this.game.start();
	}

	public void paint(Graphics g) {
		System.out.println("Painting");
	}

	public void startF(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.WHITE);
		for (int i = 0; i < WIDTH; i += CIRCLE_DIAMETER) {
			for (int j = 0; j < HEIGHT; j += CIRCLE_DIAMETER) {
				g.fillOval(i, j, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
			}
		}
	}

	public void drawMove(int x, int y, Color color) {
		Graphics g = this.getGraphics();
		g.setColor(color);
		g.fillOval(x, y, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
		// this.update(g);
	}

	// The next 4 methods must be defined, but you won't use them.
	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	// Similar to next int
	public void mouseClicked(MouseEvent e) {
		if (game.getBoard().checkState() != Board.State.NONTERMINAL)
			return;
		if (game.getCurrentPlayer() instanceof ComputerPlayer)
			return;
		int[] slots = game.getBoard().slots;

		int x = e.getX();
		x -= x % CIRCLE_DIAMETER;

		if (slots[x / CIRCLE_DIAMETER] < 6) {
			drawMove(x, HEIGHT - (slots[x / CIRCLE_DIAMETER] + 1) * CIRCLE_DIAMETER, colors[game.getTurn()]);
		}

		synchronized (inputPipe) {
			inputPipe.send(x / CIRCLE_DIAMETER);
			inputPipe.notify();
		}
	}

}