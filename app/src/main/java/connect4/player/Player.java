package connect4.player;

import java.awt.Color;

public abstract class Player {
    private Color color;
    private char token;

    public void setToken(char token) {
        this.token = token;
    }

    public char getToken() { return this.token; }

    public void setColor(Color color) {
        this.color = color;
    }

    public int makeMove(char[][] start, int[] cols) {
        return 0;
    }
}
