package connect4;
import java.awt.Color;
import java.util.Scanner;

import ai.strategies.AlphaBeta;
import connect4.player.Player;
import connect4.player.ComputerPlayer;
import util.Util;


public class ConnectFour implements Runnable {
	public static final char[][] start = {{'-','-','-','-','-','-','-'},
									{'-','-','-','-','-','-','-'},
									{'-','-','-','-','-','-','-'},
									{'-','-','-','-','-','-','-'},
									{'-','-','-','-','-','-','-'},
									{'-','-','-','-','-','-','-'}};
	private final int[] slots = {0,  0,  0,  0,  0,  0,  0};
	private int turn = 0;
	
	public void run() {
		int NUMBER_OF_PLAYERS = 2;

		Scanner stdin = new Scanner(System.in);
		Player[] players = new Player[2];
		char[] tokens = {'X', 'O'};

		players[0] = new ComputerPlayer(new AlphaBeta());
		players[0].setToken(tokens[0]);

		players[1] = new ComputerPlayer(new AlphaBeta());
		players[1].setToken(tokens[1]);

		while(true) {
			if(isTerminal(start)) {
				System.out.println("Winner: ");
				break;
			}

			System.out.println("Player " + turn + " turn");
			int move = players[turn].makeMove(start, slots);

			if(isValid(move)) {
				start[slots[move]][move] = players[turn].getToken();
				slots[move]++;
				turn = (turn + 1) % NUMBER_OF_PLAYERS;
			} else {
				System.out.println("Player " + turn + " made an invalid move\n Please, try again");
			}

			Util.draw2Darray(start);
		}

		stdin.close();
	}
	
	private boolean isValid(int move) {
		return !(slots[move] >= 6);
	}
	
	public Color pickColor(String str, Scanner stdin) {
		System.out.print("Choose " + str + " color (BLUE - 1, RED - 2, GREEN - 3, YELLOW - 4, MAGENTA - 5): ");
		switch(stdin.nextInt()) {
			case 2: return Color.RED;
			case 3: return Color.GREEN;
			case 4: return Color.YELLOW;
			case 5: return Color.MAGENTA;
			case 1:
			default: return Color.BLUE;
		}
	}
	
	public static void main(String[] args) {
		ConnectFour game = new ConnectFour();
		game.run();
//		JFrame frame = new FieldGUI();
	}
	
	/**
	 * Check if the current state is a game over
	 * @param boardCells
	 * @return true if it's a terminal state or false otherwise
	 */
	public static boolean isTerminal(char[][] boardCells) {
		int count = 0;
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				if(boardCells[i][j] != '-') {
					if(j < 4) {
						if(boardCells[i][j] == boardCells[i][j + 1] && 
						   boardCells[i][j] == boardCells[i][j + 2] && 
						   boardCells[i][j] == boardCells[i][j + 3])
							return true;
					}
					if(i < 3) {
						if(boardCells[i][j] == boardCells[i + 1][j] && 
						   boardCells[i][j] == boardCells[i + 2][j] && 
						   boardCells[i][j] == boardCells[i + 3][j])
							return true;
					}
					if(i < 3 && j < 4) {
						if(boardCells[i][j] == boardCells[i + 1][j + 1] && 
						   boardCells[i][j] == boardCells[i + 2][j + 2] && 
						   boardCells[i][j] == boardCells[i + 3][j + 3])
							return true;
						if(boardCells[i][6 - j] != '-')
							if(boardCells[i][6 - j] == boardCells[i + 1][6 - j - 1] && 
						       boardCells[i][6 - j] == boardCells[i + 2][6 - j - 2] && 
						       boardCells[i][6 - j] == boardCells[i + 3][6 - j - 3])
								return true;
					}
				}
				else
					count++;
			}
		}

		return count == 0;
	}

}
