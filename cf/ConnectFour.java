package cf;
import java.awt.Color;
import java.util.Scanner;
import ai.Minimax;
import ai.Node;
import util.Util;

public class ConnectFour implements Runnable {

	public static char[][] start = {{'-','-','-','-','-','-','-'},
									{'-','-','-','-','-','-','-'},
									{'-','-','-','-','-','-','-'},
									{'-','-','-','-','-','-','-'},
									{'-','-','-','-','-','-','-'},
									{'-','-','-','-','-','-','-'}};
	public static int[] slots = {   0,  0,  0,  0,  0,  0,  0};

	static boolean player = false;
	static boolean botXbot = false;
	public static boolean tie = false;
	static FieldGUI field;
	static Color colorAI, colorHuman;
	static char ai;
	static char you;
	
	public void run() {
		// TODO Auto-generated method stub
		Scanner stdin = new Scanner(System.in);
		
		
		
		System.out.print("Choose the depth limit: ");
		Minimax.setDepth(stdin.nextInt());
		System.out.print("Choose the algorithm (1 - Minimax/2 - AlphaBeta): ");
		int minmaxOrAlphabeta = stdin.nextInt();
		System.out.print("Bot x Bot or Bot vs Human (1 - bxb / 2 - bxh)?");
		if(stdin.nextInt() == 1)
			botXbot = true;
		else
			botXbot = false;
		
		if(!botXbot) {
			System.out.print("Would you like to start (1 - yes/2 - no)?: ");
			if(stdin.nextInt() == 2)
				player = false;
			else
				player = true;
		}
		
		System.out.print("Would you like to use GUI (Status on console) (1 - yes/2 - no)?:");
		boolean useGUI;
		if(stdin.nextInt() == 1)
			useGUI = true;
		else
			useGUI = false;
		
		if(useGUI) {
			if(!botXbot) {
				colorAI = pickColor("AI", stdin);
				colorHuman = pickColor("Human", stdin);
			}
			else {
				colorAI = Color.RED;
				colorHuman = Color.BLUE;
			}
			ai = 'X';
			you = 'O';
		}
		else {
			int aux;
			if(!botXbot) {
				System.out.print("Choose your token (1 - X/ 2 - O): ");
				aux = stdin.nextInt();
			}
			else
				aux = 2;
			if(aux == 1) {
				ai = 'O';
				you = 'X';
			}
			else {
				ai = 'X';
				you = 'O';
			}
		}

		Node node = new Node(start, true, slots.clone(), -1, 0, ai);
		Node node_2 = null;
		if(botXbot)
			node_2 = new Node(start, true, slots.clone(), -1, 0, you);
		if(useGUI)
			field = new FieldGUI();
		
		while(true) {
			
			if(!player) {
				node = new Node(start.clone(), true, slots.clone(), -1, node.getDepth() + 1, ai);
				//Util.draw2Darray(start);
				if(isTerminal(start) && !tie) {
					System.out.println("Winner: " + you);
					break;
				}
				else if(tie) {
					System.out.println("Draw");
					break;
				}
				System.out.println("AI's turn...");
				if(minmaxOrAlphabeta == 1)
					makeMove((node = Minimax.bestMove(node)).getMove(), ai);
				else
					makeMove((node = Minimax.bestMoveAB(node)).getMove(), ai);
				if(useGUI)
					field.drawMove(node.getMove() * 70, (6 - slots[node.getMove()])*70, colorAI);
				Util.draw2Darray(start);
				
			}
			else {
				
				
				if(botXbot) {
					node_2 = new Node(start.clone(), true, slots.clone(), -1, node_2.getDepth() + 1, you);
					//Util.draw2Darray(start);
					if(isTerminal(start) && !tie) {
						System.out.println("Winner: " + ai);
						break;
					}
					else if(tie) {
						System.out.println("Draw");
						break;
					}
					System.out.println("AI's turn...");
					if(minmaxOrAlphabeta == 1)
						makeMove((node_2 = Minimax.bestMove(node_2)).getMove(), you);
					else
						makeMove((node_2 = Minimax.bestMoveAB(node_2)).getMove(), you);
					if(useGUI)
						field.drawMove(node_2.getMove() * 70, (6 - slots[node_2.getMove()])*70, colorHuman);
					Util.draw2Darray(start);
				}
				else if(!useGUI) {
					if(isTerminal(start) && !tie) {
						System.out.println("Winner: " + ai);
						break;
					}
					else if(tie) {
						System.out.println("Draw");
						break;
					}
					System.out.print("Choose a column: ");
					makeMove(stdin.nextInt() - 1, you);
				}
				else {
                    //TODO
                    
                    try {
                        Thread.sleep(0);
                    }
                    catch(InterruptedException ex) {
                        
                    }
                    
                    if(isTerminal(start) && !tie) {
						System.out.println("Winner: " + ai);
						break;
					}
					else if(tie) {
						System.out.println("Draw");
						break;
					}
				}
				
			}
		}
		
		
		player = false;
		
		stdin.close();
	}
	
	public static void makeMove(int col, char token) {
		if(slots[col] >= 6) {
			System.out.println("Invalid move");
			return;
		}
		
		start[slots[col]][col] = token;
		slots[col]++;
		player = !player;
	}
	
	public Color pickColor(String str, Scanner stdin) {
		System.out.print("Choose " + str + " color (BLUE - 1, RED - 2, GREEN - 3, YELLOW - 4, MAGENTA - 5): ");
		switch(stdin.nextInt()) {
			case 1: return Color.BLUE;
			case 2: return Color.RED;
			case 3: return Color.GREEN;
			case 4: return Color.YELLOW;
			case 5: return Color.MAGENTA;
			default: return Color.BLUE;
		}
	}
	
	public static void main(String args[]) {
		ConnectFour game = new ConnectFour();
		game.run();
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
					if(i < 6 && j < 4) {
						if(boardCells[i][j] == boardCells[i][j + 1] && 
						   boardCells[i][j] == boardCells[i][j + 2] && 
						   boardCells[i][j] == boardCells[i][j + 3])
							return true;
					}
					if(i < 3 && j < 7) {
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
		
		if(count == 0) {
			tie = true;
			return true;
		}
		
		return false;
		//return false;
		
	}

}
