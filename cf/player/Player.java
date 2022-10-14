package cf.player;

import java.awt.Color;

public abstract class Player {
    private Color color;
    private char token;

    public void setToken(char token) {
        this.token = token;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void makeMove(int col, char token) {
		if(slots[col] >= 6) {
			System.out.println("Invalid move");
			return;
		}
		
		start[slots[col]][col] = token;
		slots[col]++;
		player = !player;
	}
}
