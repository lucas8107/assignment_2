package cf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FieldGUI extends JFrame implements MouseListener {

	private final String TITLE = "ConnectFour, by Oak :D";
	private final int WIDTH = 490;
	private final int HEIGHT = 420;
	private int CIRCLE_DIAMETER = 70;

	private Game game;
	private Color[] colors = { Color.RED, Color.BLUE };
	private Data inputPipe;
	private int topOffset;
	private int bottomOffset;
	private int leftOffset;
	private int rightOffset;

	public FieldGUI(Game game, Data data) {
		super();

		topOffset = 31;
		bottomOffset = 8;
		leftOffset = 8;
		rightOffset = 8;

		this.game = game;

		setTitle(TITLE);

		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int screen_width = (int)size.getWidth();
		int screen_height = (int)size.getHeight();

		// setSize(WIDTH, HEIGHT);

		// topOffset = getInsets().top;
		// bottomOffset = getInsets().bottom;
		// leftOffset = getInsets().left;
		// rightOffset = getInsets().right;

		setSize(WIDTH + leftOffset + rightOffset, HEIGHT + topOffset + bottomOffset);
		// setPreferredSize(new Dimension(WIDTH, HEIGHT));
		// pack();

		setLocationRelativeTo(null);

		addMouseListener(this);
		setVisible(true);
		System.out.println(getInsets().top + ", " + getInsets().bottom + ", " + getInsets().left + ", " + getInsets().right);
		
		drawBoard();

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.inputPipe = data;

	}

	public void paint(Graphics g) {
	}

	public String getDefaultTitle() {
		return TITLE;
	}

	private Graphics2D getAAGraphics2D(Graphics g) {
		Graphics2D aaGraphics = (Graphics2D) g.create();

		RenderingHints hints = new RenderingHints(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
		);
		aaGraphics.setRenderingHints(hints);

		return aaGraphics;
	}

	public void drawBoard() {
		Graphics2D g2d = getAAGraphics2D(this.getGraphics());
		g2d.setColor(Color.BLACK);
		g2d.fillRect(leftOffset, topOffset, WIDTH, HEIGHT);
		g2d.setColor(Color.WHITE);
		for (int i = leftOffset; i < WIDTH - rightOffset; i += CIRCLE_DIAMETER) {
			for (int j = topOffset; j < HEIGHT - bottomOffset; j += CIRCLE_DIAMETER) {
				System.out.println(i + ", " + j);
				if (i == j && i == 0)
					g2d.fillOval(i, j, 5, 5);
				g2d.fillOval(i, j, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
			}
		}
		g2d.dispose();
	}

	public void drawMove(int x, int[] slots, int turn) {
		Graphics2D g2d = getAAGraphics2D(this.getGraphics());
		int translated_x = x * CIRCLE_DIAMETER;
		g2d.setColor(colors[turn]);
		g2d.fillOval(translated_x + leftOffset,  HEIGHT + topOffset - (slots[translated_x / CIRCLE_DIAMETER]) * CIRCLE_DIAMETER, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
		g2d.dispose();
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

	public void mouseClicked(MouseEvent e) {
		if (game.getBoard().checkState() != Board.State.NONTERMINAL)
			return;
		if (game.getCurrentPlayer() instanceof ComputerPlayer)
			return;

		int x = e.getX();
		x -= x % CIRCLE_DIAMETER;

		synchronized (inputPipe) {
			inputPipe.send(x / CIRCLE_DIAMETER);
			inputPipe.notify();
		}
	}

}