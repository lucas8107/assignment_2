package cf;

import java.util.Scanner;

import ai.Minimax;
import ai.Node;

public abstract class Player {

    private char token;
    private String name;

    public Player(String s, char c) {
        token = c;
        name = s;
    }

    public void setToken(char c) {
        token = c;
    }

    public char getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public abstract int getMove(Board board);
}

class HumanPlayer extends Player {

    private GameEnvironment gameEnvironment;

    public HumanPlayer(String s, char c) {
        super(s, c);
    }

    static Scanner stdin = new Scanner(System.in);

    public void setEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    public int getMove(Board board) {
        return gameEnvironment.waitPlayerInput();
    }
}

class ComputerPlayer extends Player {

    private int nodeDepth = 0;
    private FieldGUI gui;

    public ComputerPlayer(String s, char c) {
        super(s, c);
    }

    public int getMove(Board board) {
        Node node = new Node(board.start.clone(), true, board.slots.clone(), -1, ++nodeDepth, this.getToken());

        int move = Minimax.bestMoveAB(node).getMove();

        return move;
    }
}