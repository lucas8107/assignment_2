package cf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

@SuppressWarnings("serial")
public class FieldGUI extends JFrame implements MouseListener { 

  private int x;   // leftmost pixel in circle has this x-coordinate
  private int y;   // topmost  pixel in circle has this y-coordinate

  public FieldGUI() {
	  setSize(490,420);
	  setLocation(100,100);
	  addMouseListener(this);
	  setVisible(true);
	  startF(this.getGraphics());
      startF(this.getGraphics());
	  this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	  this.getGraphics().drawString("Game", 0, 0);
  }

  public void paint(Graphics g) {
	  
  }
  
  public void startF(Graphics g) {
	  g.setColor(Color.BLACK);
	  g.fillRect(0, 0, 490, 420);
	  g.setColor(Color.WHITE);
	  for(int i = 0; i < 490; i+=70) {
		  for(int j = 0; j < 420; j+=70) {
			  g.fillOval(i, j, 70, 70);
		  }
	  }
  }
  
  public void drawMove(int x, int y, Color color) {
	  Graphics g = this.getGraphics();
	  g.setColor(color);
	  g.fillOval(x, y, 70, 70);
	  this.update(g);
  }

  // The next 4 methods must be defined, but you won't use them.
  public void mouseReleased(MouseEvent e ) { }
  public void mouseEntered(MouseEvent e)   { }
  public void mouseExited(MouseEvent e)    { }
  public void mousePressed(MouseEvent e)   { }

  public void mouseClicked(MouseEvent e) { 
	  x = e.getX();   // x-coordinate of the mouse click
	  y = e.getY();   // y-coordinate of the mouse click
	  x = (x/70)*70;
	  y = (y/70)*70;
	  System.out.println(x + " " + y);
	  System.out.println(ConnectFour.player + " " + ConnectFour.slots[x/70]);
	  
	  if(ConnectFour.slots[x/70] < 6 && ConnectFour.player == true) {
		  System.out.println(x + " " + (420 - (ConnectFour.slots[y/70] + 1) * 70));
		  System.out.println(y/70 + " " +  x/70);
		  System.out.println(Arrays.toString(ConnectFour.slots));
		  drawMove(x, 420 - (ConnectFour.slots[x/70] + 1) * 70, ConnectFour.colorHuman);
		  ConnectFour.makeMove(x/70, ConnectFour.you);
	  }
  }
  
  public void drawX(int y, int x) {
	  Graphics g = getGraphics();
	  System.out.println(x + " " + y);
	  x = x * 70;
	  y = 420 - y * 70;
	  g.drawLine(x, y, x + 70, y + 70);
	  g.drawLine(x + 70, y, x, y + 70);
  }


}