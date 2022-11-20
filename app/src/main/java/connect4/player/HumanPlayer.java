package connect4.player;

import java.util.Scanner;

public class HumanPlayer extends Player {
    Scanner stdin;
    public HumanPlayer(Scanner stdin) {
        this.stdin = stdin;
    }

    @Override
    public int makeMove(char[][] start, int[] cols) {
        return stdin.nextInt() - 1;
    }
}
