package ai.strategies;

import java.util.Collections;
import java.util.LinkedList;

import ai.Node;

public class AlphaBeta extends Strategy {
    private int pruned = 0;

    public Node bestMove(Node currentState) {
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
    
    private int alphabeta(Node node, boolean isMax, int depth, int alpha, int beta) {
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

}
