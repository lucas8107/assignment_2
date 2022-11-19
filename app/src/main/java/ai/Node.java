package ai;
import java.util.LinkedList;
import util.Util;

public class Node implements Comparable<Node> {
	// 7x7 cells
	static int totalNodes = 0;
	
	public char[][] cells;
	private int[] slots;
	private boolean isMax;
	private int utility;
	private int move;
	private int depth;
	private char token;
	private char enemyToken;
	
	public Node(char[][] cells, boolean isMax, int[] slots, int move, int depth, char token) {
		this.cells = cells;
		this.slots = slots;
		this.isMax = isMax;
		this.utility = 0;
		this.move = move;
		this.depth = depth;
		this.token = token;
		if(token == 'X')
			this.enemyToken = 'O';
		else
			this.enemyToken = 'X';
	}
	
	public LinkedList<Node> makeDescendants() {
		
		LinkedList<Node> children = new LinkedList<Node>();
		int totalChildren = 7;
		
		if(Util.isSymmetric(this.cells))
			totalChildren = 4;
		
		for(int i = 0; i < totalChildren; i++) {
			if(this.slots[i] < 6) {
				char[][] temp = Util.copy2Darray(this.cells);
				int[] temp_2 = Util.copyArray(this.slots);
				temp_2[i]++;
				if(isMax)
					temp[this.slots[i]][i] = this.token;
				else
					temp[this.slots[i]][i] = this.enemyToken;
				children.add(new Node(temp, !isMax, temp_2, i, this.depth + 1, this.token));
			}
		}
		
		return children;
	}
	
	public int getMove() { return this.move; }
	public int getDepth() { return this.depth; }
	public char getToken() { return this.token; }
	public char getEnemyToken() { return this.enemyToken; }
	
	public void setUtility(int utility) { this.utility = utility; }

	@Override
	public int compareTo(Node o) {
		if(this.utility > o.utility)
			return 1;
		else if(this.utility == o.utility) {
			return 0;
		}
		else
			return -1;
	}
	
}
