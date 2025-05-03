package com.example.connect4;

import java.util.List;

public class MiniMaxTree {
    TreeNode root;
    boolean isCalculated;
    int depth;
    int bestMove;
    // Constructor
    public MiniMaxTree(State state, int depth) {
        this.depth = depth;
        this.root = new TreeNode(state, null, true);// Assume starting as Max player
        this.isCalculated = false;
        bestMove = 0;
    }

    // MiniMax algorithm
    public TreeNode miniMax(TreeNode node, int depth, boolean maximizingPlayer) {
        isCalculated = true;
        if (depth == 0 || node.state.board.isFull()) {
            node.state.heuristic = node.state.evaluateBoard(node.state.board, node.state.board.turn);
            return node; // Evaluate the heuristic value for the current state
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (TreeNode child : node.getChildren()) {
                int eval = miniMax(child, depth - 1, false).state.heuristic;
//                if (eval >= maxEval){
//                    bestMove = getMoveBetween(child.state.board, node.state.board);
//                }
                maxEval = Math.max(maxEval, eval);
            }
            node.state.heuristic = maxEval;
            return node;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (TreeNode child : node.getChildren()) {
                int eval = miniMax(child, depth - 1, true).state.heuristic;
//                if (eval < minEval){
//                    bestMove = getMoveBetween(child.state.board, node.state.board);
//                }
                minEval = Math.min(minEval, eval);
            }
            node.state.heuristic = minEval;
            return node;
        }
    }

    private int getMoveBetween(Board board, Board board1) {
        for (int j = 0; j < board.columns.length; j++) {
            if(board.columns[j].cells != board1.columns[j].cells){
                return j;
            }
        }
        return -1;
    }

    public int bestMove(){
        int rootEval = root.state.heuristic;
        int i = 0;
        for (int j = 0; j < root.children.size(); j++) {
            if(root.children.get(j).isCut){
                break;
            }
            if(rootEval == root.children.get(j).state.heuristic){
                i = j;
                break;
            }
        }
        return getMoveBetween(root.state.board, root.children.get(i).state.board);

    }

    // Method to find the best move using MiniMax
//    public int findBestMove() {
//        TreeNode[] children = root.getChildren();
//        int bestMove = -1;
//        int bestValue = Integer.MIN_VALUE;
//
//        for (int i = 0; i < children.length; i++) {
//            int currentValue = miniMax(children[i], /* Choose a suitable depth */, false);
//            if (currentValue > bestValue) {
//                bestValue = currentValue;
//                bestMove = i;
//            }
//        }
//
//        return bestMove;
//    }

}
