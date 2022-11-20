package ai;
import java.util.LinkedList;
import util.Util;

public class Node implements Comparable<Node> {
	public char[][] cells;
	private final int[] slots;
	private final boolean isMax;
	private int utility;
	private final int move;
	private final int depth;
	private final char token;
	private final char enemyToken;
	
	public Node(char[][] cells, boolean isMax, int[] slots, int move, int depth, char token) {
		this.cells = cells;
		this.slots = slots;
		this.isMax = isMax;
		this.utility = 0;
		this.move = move;
		this.depth = depth;
		this.token = token;
		this.enemyToken = token == 'X' ? 'O' : 'X';
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
				temp[this.slots[i]][i] = isMax ? this.token : this.enemyToken;
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
