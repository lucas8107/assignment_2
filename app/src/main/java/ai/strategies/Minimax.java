package ai.strategies;

import java.util.LinkedList;

import ai.Node;

public class Minimax extends Strategy {
    public Node bestMove(Node currentState) {
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

    private int minimax(Node node, boolean isMax, int depth, char token) {
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
    
}
