package connect4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FieldGUI extends JFrame implements MouseListener { 
	private int WIDTH = 490;

	private int HEIGHT_OFFSET;
	private int HEIGHT;
	private int CELL_WIDTH;
	private int CELL_HEIGHT;

  public FieldGUI() {
	  Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	  Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

	  int taskBarHeight = scrnSize.height - winSize.height;
	  System.out.println(taskBarHeight);
	  HEIGHT_OFFSET = taskBarHeight;
	  HEIGHT = 420 + HEIGHT_OFFSET;

	  CELL_WIDTH = WIDTH / 7;
	  CELL_HEIGHT = HEIGHT / 6;
	  setSize(WIDTH,HEIGHT);
	  setLocation(100,100);
	  addMouseListener(this);
	  setVisible(true);
	  this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	  this.getGraphics().drawString("Game", 0, 0);

  }

  void updateRatio() {

  }

  public void paint(Graphics g) {
	  Shape shape = g.getClip();
	  if(shape != null) {
		  Rectangle rectangle = shape.getBounds();
		  HEIGHT = rectangle.height;
		  WIDTH = rectangle.width;
		  CELL_WIDTH = WIDTH / 7;
		  CELL_HEIGHT = HEIGHT / 6;

		  g.setColor(Color.BLACK);
		  g.fillRect(0, HEIGHT_OFFSET, WIDTH, HEIGHT);
		  g.setColor(Color.WHITE);
		  for(int i = 0; i < 6; i++)
			  for(int j = 0; j < 7; j++) {
				  Color color = ConnectFour.start[i][j] == '-' ? Color.WHITE : Color.BLUE;
				  g.setColor(color);
				  g.fillOval(j * CELL_WIDTH, i * CELL_HEIGHT + HEIGHT_OFFSET, CELL_WIDTH, CELL_HEIGHT);
			  }
	  }
  }

  public void drawMove(int x, int y, Color color, Graphics g) {
	  g.setColor(color);
	  g.fillOval(x, y + HEIGHT_OFFSET, CELL_WIDTH, CELL_HEIGHT);
  }

  // The next 4 methods must be defined, but you won't use them.
  public void mouseReleased(MouseEvent e ) { }
  public void mouseEntered(MouseEvent e)   { }
  public void mouseExited(MouseEvent e)    { }
  public void mousePressed(MouseEvent e)   { }

  public void mouseClicked(MouseEvent e) {
	  int x = e.getX();   // x-coordinate of the mouse click
	  int y = e.getY();   // y-coordinate of the mouse click
	  x = (x/CELL_WIDTH)*CELL_WIDTH;
	  y = (y/CELL_HEIGHT)*CELL_HEIGHT;
	  System.out.println(x + " " + y);

	  ConnectFour.start[y/CELL_HEIGHT][x/CELL_WIDTH] = 'X';

	  drawMove(x, y, Color.RED, getGraphics());
	  notifyAll();
  }
}