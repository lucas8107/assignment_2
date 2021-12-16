package cf;

public class GameBuilder {

    public static Game buildGUIHxH() {
        Data data = new Data();
        Game game = new Game();
        Player player1 = new HumanPlayer("Player1", 'X');
        Player player2 = new HumanPlayer("Player2", 'O');
        ((HumanPlayer) player1).setEnvironment(new GUIGame(data));
        ((HumanPlayer) player2).setEnvironment(new GUIGame(data));
        game.addPlayer(player1);
        game.addPlayer(player2);

        new FieldGUI(game, data);

        return game;
    }

    public static Game buildGUIHxB() {
        Data data = new Data();
        Game game = new Game();

        FieldGUI gui = new FieldGUI(game, data);

        Player player1 = new HumanPlayer("Player1", 'X');
        Player player2 = new ComputerPlayer("Player2", 'O');
        ((HumanPlayer) player1).setEnvironment(new GUIGame(data));
        game.setGUI(gui);
        game.addPlayer(player1);
        game.addPlayer(player2);

        return game;
    }

    public static Game buildGUIBxB() {
        Game game = new Game();

        FieldGUI gui = new FieldGUI(game, null);

        Player player1 = new ComputerPlayer("Player1", 'X');
        Player player2 = new ComputerPlayer("Player2", 'O');
        game.setGUI(gui);
        game.addPlayer(player1);
        game.addPlayer(player2);

        return game;
    }

    public static Game buildHumanVsHuman() {
        Game game = new Game();
        Player player1 = new HumanPlayer("Player1", 'X');
        Player player2 = new HumanPlayer("Player2", 'O');
        ((HumanPlayer) player1).setEnvironment(new ConsoleGame());
        ((HumanPlayer) player2).setEnvironment(new ConsoleGame());
        game.addPlayer(player1);
        game.addPlayer(player2);
        return game;
    }

    public static Game buildHumanVsComputer() {
        Game game = new Game();
        Player player1 = new HumanPlayer("Player1", 'X');
        ((HumanPlayer) player1).setEnvironment(new ConsoleGame());
        game.addPlayer(player1);
        game.addPlayer(new ComputerPlayer("Player2", 'O'));
        return game;
    }

    public static Game buildComputerVsComputer() {
        Game game = new Game();
        game.addPlayer(new ComputerPlayer("Player1", 'X'));
        game.addPlayer(new ComputerPlayer("Player2", 'O'));
        return game;
    }

}
