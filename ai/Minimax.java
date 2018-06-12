package ai;
import java.util.Collections;
import java.util.LinkedList;;

public abstract class Minimax {
	
	private static int depthLimit = 9;
	private static int pruned = 0;
	private static long totalTimeSpent = 0;
	public static long visitedNodes = 0;

	/**
	 * Return the next best move regarding the depth
	 * using minimax strategy
	 * @param currentState
	 * @return The descendant Node with best utility
	 */
	public static Node bestMove(Node currentState) {
		long deltaTime = System.currentTimeMillis();
		long deltaVisited = visitedNodes;
		LinkedList<Node> children = currentState.makeDescendants();
		Node max = children.getFirst();
		
		for(int i = 0; i < children.size(); i++) {
			int temp = minimax(children.get(i), false, 1, currentState.getToken());
			System.out.println(temp);
			children.get(i).setUtility(temp);
			if(max.compareTo(children.get(i)) < 0)
				max = children.get(i);
		}
		
		deltaTime = System.currentTimeMillis() - deltaTime;
		totalTimeSpent += deltaTime;
		deltaVisited = visitedNodes - deltaVisited;
		System.out.println("Minimax status: ");
		System.out.printf("It took: %d ms\n", deltaTime);
		System.out.printf("Total time spent until now: %d ms\n", totalTimeSpent);
		System.out.println("Nodes visited now (" + currentState.getToken() + "): " + deltaVisited);
		System.out.println("Total nodes visited: " + visitedNodes);
		return max;
		
	}
	
	/**
	 * Return the next best move regarding the depth
	 * using alpha - beta strategy (pruning nodes)
	 * @param currentState
	 * @return The descendant Node with best utility
	 */
	public static Node bestMoveAB(Node currentState) {
		long deltaTime = System.currentTimeMillis();
		long deltaVisited = visitedNodes;
		pruned = 0;
		LinkedList<Node> children = currentState.makeDescendants();
		
		Node cursor;
		for(int i = 0; i < children.size(); i++) {
			cursor = children.get(i);
			cursor.setUtility(utility(cursor.cells, false, cursor.getToken(), cursor.getEnemyToken()));
		}
		
		Collections.sort(children, Collections.reverseOrder());
		
		Node max = children.getFirst();
		
		for(int i = 0; i < children.size(); i++) {
			int temp = alphabeta(children.get(i), false, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
			System.out.println(temp);
			children.get(i).setUtility(temp);
			if(max.compareTo(children.get(i)) < 0)
				max = children.get(i);
		}
		
		deltaTime = System.currentTimeMillis() - deltaTime;
		totalTimeSpent += deltaTime;
		deltaVisited = visitedNodes - deltaVisited;
		System.out.println("AlphaBeta status: ");
		System.out.printf("It took: %d ms\n", deltaTime);
		System.out.printf("Total time spent until now: %d ms\n", totalTimeSpent);
		System.out.println("Nodes visited now: " + deltaVisited);
		System.out.println("Pruned nodes (player" + currentState.getToken() + "): " + pruned);
		System.out.println("Total nodes visited: " + visitedNodes);
		return max;
		
	}
	
	private static int alphabeta(Node node, boolean isMax, int depth, int alpha, int beta) {
		if(isTerminal(node.cells) || depth == depthLimit)
				return utility(node.cells, isMax, node.getToken(), node.getEnemyToken());
		
		if(isMax) {
			int best = Integer.MIN_VALUE;
			for(Node child: node.makeDescendants()) {
				int v = alphabeta(child, false, depth + 1, alpha, beta);
				if(depth == 1)
					System.out.println("	" + v);
				best = Math.max(best, v);
				alpha = Math.max(best, alpha);
				visitedNodes++;
				if(best >= beta) {
					pruned++;
					return best;
				}
			}
			return best;
		}
		else {
			int best = Integer.MAX_VALUE;
			for(Node child: node.makeDescendants()) {
				int v = alphabeta(child, true, depth + 1, alpha, beta);
				if(depth == 1)
					System.out.println("	" + v);
				best = Math.min(best, v);
				beta = Math.min(best, beta);
				visitedNodes++;
				if(best <= alpha) {
					pruned++;
					return best;
				}
			}
			return best;
		}
	}
	
	private static int minimax(Node node, boolean isMax, int depth, char token) {
		if(isTerminal(node.cells) || depth == depthLimit)
			return utility(node.cells, isMax, node.getToken(), node.getEnemyToken());
		
		if(isMax) {
			int best = Integer.MIN_VALUE;
			for(Node child: node.makeDescendants()) {
				int v = minimax(child, false, depth + 1, node.getToken());
				if(depth == 1)
					System.out.println("	" + v);
				visitedNodes++;
				best = Math.max(best, v);
			}
			return best;
		}
		else {
			int best = Integer.MAX_VALUE;
			for(Node child: node.makeDescendants()) {
				int v = minimax(child, true, depth + 1, node.getToken());
				if(depth == 1)
					System.out.println("	" + v);
				visitedNodes++;
				best = Math.min(best, v);
			}
			return best;
		}
	}
	
	private static int utility(char[][] nodeCells, boolean isMax, char token, char enemyToken) {
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
	
	@SuppressWarnings("unused")
	@Deprecated
	private static int utilitySlower(char[][] nodeCells, boolean isMax) {
		int acc = 0;
		int count = 0;
		int count2 = 0;
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 4; j++) {
				
				if(nodeCells[i][j] == 'X')
					count++;
				else if(nodeCells[i][j] == 'O')
					count2++;
				if(nodeCells[i][j + 1] == 'X')
					count++;
				else if(nodeCells[i][j + 1] == 'O')
					count2++;
				if(nodeCells[i][j + 2] == 'X')
					count++;
				else if(nodeCells[i][j + 2] == 'O')
					count2++;
				if(nodeCells[i][j + 3] == 'X')
					count++;
				else if(nodeCells[i][j + 3] == 'O')
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
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 7; j++) {
				
				if(nodeCells[i][j] == 'X')
					count++;
				else if(nodeCells[i][j] == 'O')
					count2++;
				if(nodeCells[i + 1][j] == 'X')
					count++;
				else if(nodeCells[i + 1][j] == 'O')
					count2++;
				if(nodeCells[i + 2][j] == 'X')
					count++;
				else if(nodeCells[i + 2][j] == 'O')
					count2++;
				if(nodeCells[i + 3][j] == 'X')
					count++;
				else if(nodeCells[i + 3][j] == 'O')
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
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 4; j++) {
				
				if(nodeCells[i][j] == 'X')
					count++;
				else if(nodeCells[i][j] == 'O')
					count2++;
				if(nodeCells[i + 1][j + 1] == 'X')
					count++;
				else if(nodeCells[i + 1][j + 1] == 'O')
					count2++;
				if(nodeCells[i + 2][j + 2] == 'X')
					count++;
				else if(nodeCells[i + 2][j + 2] == 'O')
					count2++;
				if(nodeCells[i + 3][j + 3] == 'X')
					count++;
				else if(nodeCells[i + 3][j + 3] == 'O')
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
				
				if(nodeCells[i][6 - j] == 'X')
					count++;
				else if(nodeCells[i][6 - j] == 'O')
					count2++;
				if(nodeCells[i + 1][6 - j - 1] == 'X')
					count++;
				else if(nodeCells[i + 1][6 - j - 1] == 'O')
					count2++;
				if(nodeCells[i + 2][6 - j - 2] == 'X')
					count++;
				else if(nodeCells[i + 2][6 - j - 2] == 'O')
					count2++;
				if(nodeCells[i + 3][6 - j - 3] == 'X')
					count++;
				else if(nodeCells[i + 3][6 - j - 3] == 'O')
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
		
		
		
		return (count == 0)? true:false;
		
		//return false;
		
	}
	
	/**
	 * Set the depth search limit the will be
	 * used in both strategies (default is 9)
	 * @param depth
	 */
	public static void setDepth(int depth) {
		depthLimit = depth;
	}
	
}
