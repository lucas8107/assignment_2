package cf;

import java.util.Scanner;

class Data {

    private int move;

    public void send(int move) {
        this.move = move;
    }

    public int receive() {
        return this.move;
    }

}

public interface GameEnvironment {

    public int waitPlayerInput();
}

class GUIGame implements GameEnvironment {

    private final Data inputPipe;

    GUIGame(Data data) {
        inputPipe = data;
    }

    public int waitPlayerInput() {
        synchronized (inputPipe) {
            try {
                inputPipe.wait();
                return inputPipe.receive();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

}

class ConsoleGame implements GameEnvironment {

    Scanner stdin;

    public ConsoleGame() {
        stdin = new Scanner(System.in);
    }

    public int waitPlayerInput() {
        return stdin.nextInt() - 1;
    }

}