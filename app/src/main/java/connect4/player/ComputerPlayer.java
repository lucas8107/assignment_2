package connect4.player;

import ai.strategies.Strategy;
import ai.Node;

public class ComputerPlayer extends Player {
    private final Strategy strategy;
    private int lastDepth = 0;

    public ComputerPlayer(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public int makeMove(char[][] start, int[] slots) {
        Node node = new Node(start, true, slots, -1, ++this.lastDepth, this.getToken());
        return strategy.bestMove(node).getMove();
    }
}
