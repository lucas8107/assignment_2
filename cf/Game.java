package cf;

public class Game extends Thread {

    private Player players[];
    private int playerCount = 0;
    private Board board;
    private int turn = -1;
    private FieldGUI gui;

    public Game() {
        players = new Player[2];
        board = new Board();
    }

    public void addPlayer(Player player) {
        players[playerCount++] = player;
    }

    public int getTurn() {
        return turn;
    }

    public Player getCurrentPlayer() {
        return players[turn];
    }

    public void setGUI(FieldGUI gui) {
        this.gui = gui;
    }

    public void run() {
        Board.State state;
        boolean canChangeTurn = true;

        do {
            if (canChangeTurn)
                turn = (turn + 1) % players.length;
            board.draw2Darray();
            int move = players[turn].getMove(board);

            if (gui != null)
                gui.setTitle(gui.TITLE + "(" + players[turn].getName() + "'s turn)");

            canChangeTurn = board.makeMove(move, players[turn].getToken());

            if (canChangeTurn && gui != null)
                gui.drawMove(move, board.slots, turn);

            state = board.checkState();
        } while (state == Board.State.NONTERMINAL);

        board.draw2Darray();
        printWinner(players[turn]);
    }

    private void printWinner(Player player) {
        System.out.println("The winner is " + player.getName() + "(" + player.getToken() + ")");
    }

    public Board getBoard() {
        return board;
    }

}
