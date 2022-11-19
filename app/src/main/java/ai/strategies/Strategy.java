package ai.strategies;

import ai.Node;

public abstract class Strategy {
    protected int depthLimit = 9;
    protected long visitedNodes = 0;
	protected long totalTimeSpent = 0;

    public abstract Node bestMove(Node currentState);

    protected int utility(char[][] nodeCells, boolean isMax, char token, char enemyToken) {
		int acc = 0;
		int count = 0;
		int count2 = 0;
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				if(i < 6 && j < 4) {
					if(nodeCells[i][j] == token)
						count++;
					else if(nodeCells[i][j] == enemyToken)
						count2++;
					if(nodeCells[i][j + 1] == token)
						count++;
					else if(nodeCells[i][j + 1] == enemyToken)
						count2++;
					if(nodeCells[i][j + 2] == token)
						count++;
					else if(nodeCells[i][j + 2] == enemyToken)
						count2++;
					if(nodeCells[i][j + 3] == token)
						count++;
					else if(nodeCells[i][j + 3] == enemyToken)
						count2++;
				
					if(count2 == 0) {
						switch(count) {
							case 1:
								acc+=1;
								break;
							case 2:
								acc+=10;
								break;
							case 3:
								acc+=50;
								break;
							case 4:
								return 512;
						}
					}
					else if(count == 0) {
						switch(count2) {
							case 1:
								acc-=1;
								break;
							case 2:
								acc-=10;
								break;
							case 3:
								acc-=50;
								break;
							case 4:
								return -512;
						}
					}
				
					count = 0;
					count2 = 0;
				}
				
				if(i < 3 && j < 7) {
					if(nodeCells[i][j] == token)
						count++;
					else if(nodeCells[i][j] == enemyToken)
						count2++;
					if(nodeCells[i + 1][j] == token)
						count++;
					else if(nodeCells[i + 1][j] == enemyToken)
						count2++;
					if(nodeCells[i + 2][j] == token)
						count++;
					else if(nodeCells[i + 2][j] == enemyToken)
						count2++;
					if(nodeCells[i + 3][j] == token)
						count++;
					else if(nodeCells[i + 3][j] == enemyToken)
						count2++;
					
					if(count2 == 0) {
						switch(count) {
							case 1:
								acc+=1;
								break;
							case 2:
								acc+=10;
								break;
							case 3:
								acc+=50;
								break;
							case 4:
								return 512;
						}
					}
					else if(count == 0) {
						switch(count2) {
						case 1:
							acc-=1;
							break;
						case 2:
							acc-=10;
							break;
						case 3:
							acc-=50;
							break;
						case 4:
							return -512;
						}
					}
					
					count = 0;
					count2 = 0;
				}
				
				if(i < 3 && j < 4) {
					if(nodeCells[i][j] == token)
						count++;
					else if(nodeCells[i][j] == enemyToken)
						count2++;
					if(nodeCells[i + 1][j + 1] == token)
						count++;
					else if(nodeCells[i + 1][j + 1] == enemyToken)
						count2++;
					if(nodeCells[i + 2][j + 2] == token)
						count++;
					else if(nodeCells[i + 2][j + 2] == enemyToken)
						count2++;
					if(nodeCells[i + 3][j + 3] == token)
						count++;
					else if(nodeCells[i + 3][j + 3] == enemyToken)
						count2++;
					
					if(count2 == 0) {
						switch(count) {
							case 1:
								acc+=1;
								break;
							case 2:
								acc+=10;
								break;
							case 3:
								acc+=50;
								break;
							case 4:
								return 512;
						}
					}
					else if(count == 0) {
						switch(count2) {
						case 1:
							acc-=1;
							break;
						case 2:
							acc-=10;
							break;
						case 3:
							acc-=50;
							break;
						case 4:
							return -512;
						}
					}
					
					count = 0;
					count2 = 0;
					
					if(nodeCells[i][6 - j] == token)
						count++;
					else if(nodeCells[i][6 - j] == enemyToken)
						count2++;
					if(nodeCells[i + 1][6 - j - 1] == token)
						count++;
					else if(nodeCells[i + 1][6 - j - 1] == enemyToken)
						count2++;
					if(nodeCells[i + 2][6 - j - 2] == token)
						count++;
					else if(nodeCells[i + 2][6 - j - 2] == enemyToken)
						count2++;
					if(nodeCells[i + 3][6 - j - 3] == token)
						count++;
					else if(nodeCells[i + 3][6 - j - 3] == enemyToken)
						count2++;
					
					if(count2 == 0) {
						switch(count) {
							case 1:
								acc+=1;
								break;
							case 2:
								acc+=10;
								break;
							case 3:
								acc+=50;
								break;
							case 4:
								return 512;
						}
					}
					else if(count == 0) {
						switch(count2) {
						case 1:
							acc-=1;
							break;
						case 2:
							acc-=10;
							break;
						case 3:
							acc-=50;
							break;
						case 4:
							return -512;
						}
					}
					
					count = 0;
					count2 = 0;
				}
			}
		}
		
		if(isMax)
			acc += 16;
		else
			acc -= 16;
		
		
		return acc;
	}
	
	/**
	 * Check if the current state is a game over
	 * @param boardCells
	 * @return true if it's a terminal state or false otherwise
	 */
	public boolean isTerminal(char[][] boardCells) {
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
		
		return count == 0;
	}
	
	/**
	 * Set the depth search limit the will be
	 * used in both strategies (default is 9)
	 * @param depth
	 */
	public void setDepth(int depth) {
		this.depthLimit = depth;
	}
}
