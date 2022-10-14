package cf.player;

import ai.strategies.Strategy;

public class ComputerPlayer extends Player {
    Strategy strategy;

    public ComputerPlayer(Strategy strategy) {
        this.strategy = strategy;
    }
}
