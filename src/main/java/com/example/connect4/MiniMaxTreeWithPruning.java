package com.example.connect4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MiniMaxTreeWithPruning {

    TreeNode root;
    int depth;
    boolean isCalculated;
    int bestMove;
    public MiniMaxTreeWithPruning(State state, int depth) {
        this.root = new TreeNode(state,null, true);
        this.depth = depth;
        this.isCalculated = false;
        bestMove = 0;
    }

    public TreeNode miniMax(TreeNode node, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || node.state.board.isFull()) {
            node.state.heuristic = node.state.evaluateBoard(node.state.board, node.state.board.turn);
            return node; // Evaluate the heuristic value for the current state
        }

        if (maximizingPlayer) {
            int startCut = 0;
            boolean thereIsCut = false;
            int maxEval = Integer.MIN_VALUE;
            for (TreeNode child : node.getChildren()) {
                startCut++;
                int eval = miniMax(child, depth - 1, alpha, beta, false).state.heuristic;
//                if (eval >= maxEval){
//                    bestMove = getMoveBetween(child.state.board, node.state.board);
//                }
                maxEval = Math.max(maxEval, eval);
                if(maxEval >= beta){
                    thereIsCut = true;
                    break;
                }
                alpha = Math.max(alpha, eval);
            }
            node.state.heuristic = maxEval;
            if(thereIsCut){
                for (int i = startCut; i < node.children.size(); i++) {
                    node.children.get(i).isCut = true;
                }
            }
            return node;
        } else {
            int minEval = Integer.MAX_VALUE;
            int startCut = 0;
            boolean thereIsCut = false;
            for (TreeNode child : node.getChildren()) {
                startCut++;
                int eval = miniMax(child, depth - 1, alpha, beta, true).state.heuristic;
//                if (eval < minEval){
//                    bestMove = getMoveBetween(child.state.board, node.state.board);
//                }
                minEval = Math.min(minEval, eval);
                if(minEval <= alpha){
                    thereIsCut = true;
                    break;
                }
                beta = Math.min(beta, eval);
//                if (beta <= alpha) {
//                    break; // Alpha cut-off
//                }
            }
            node.state.heuristic = minEval;
            if(thereIsCut){
                for (int i = startCut; i < node.children.size(); i++) {
                    node.children.get(i).isCut = true;
                }
            }
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
            if(rootEval == root.children.get(j).state.heuristic){
                i = j;
                break;
            }
        }
        return getMoveBetween(root.state.board, root.children.get(i).state.board);
    }
//    private List<TreeNode> generateChildren(TreeNode node) {
//        List<TreeNode> children = new ArrayList<>();
//        for (int i = 0; i < 7; i++) {
//            Board next = this.root.board.clone();
//            if(next.play(i)){
//                children.add(new TreeNode(next, !node.isMax));
//            }
//        }
//        System.out.println(children.size());
//        // Implement logic to generate child nodes based on possible moves in Connect-4
//        // Populate the children list with new TreeNode instances
//        return children;
//    }
//    public int bestMove() {
////        if(!isCalculated){
////            this.root = miniMax(root, depth, true);
////        }
//        List<TreeNode> children = root.getChildren();
//        int bestMove = -1;
//        int bestValue = Integer.MIN_VALUE;
//        int alpha = Integer.MIN_VALUE;
//        int beta = Integer.MAX_VALUE;
//
//        for (int i = 0; i < children.size(); i++) {
//            TreeNode child = children.get(i);
//            int currentValue = miniMax(child, this.depth, alpha, beta, false).state.heuristic;
//            if (currentValue > bestValue) {
//                bestValue = currentValue;
//                bestMove = children.get(i).state.move;
//            }
//            alpha = Math.max(alpha, bestValue);
//            if (beta <= alpha) {
//                break; // Beta cut-off
//            }
//        }
//
//        return bestMove;
//    }

//    public int findBestMove() {
//        List<TreeNode> children = generateChildren(root);
//        int bestMove = -1;
//        int bestValue = Integer.MIN_VALUE;
//        int alpha = Integer.MIN_VALUE;
//        int beta = Integer.MAX_VALUE;
//
//        for (int i = 0; i < children.size(); i++) {
//            int currentValue = miniMax(children.get(i), this.depth, alpha, beta, false);
//            if (currentValue > bestValue) {
//                bestValue = currentValue;
//                bestMove = i;
//            }
//            alpha = Math.max(alpha, currentValue);
//        }
//
//        return bestMove;
//    }

    public static void main(String[] args) {
        Board board = new Board();
        State state = new State(board);
        Scanner scanner = new Scanner(System.in);
        while (!board.isFull()){
            System.out.println("Player number: " + board.turn);
            int input = scanner.nextInt();
            if(input == 10){
                break;
            }
            board.play(input);
            state.evaluateBoard(board, 1);
            MiniMaxTreeWithPruning miniMaxTree = new MiniMaxTreeWithPruning(state, 4);
            int x = miniMaxTree.bestMove;
            if(x == -1){
                System.out.println("No move");
            }else {
                System.out.println("Best Move = " + x);
                board.play(x);
                board.printBoard();
            }
        }
        System.out.println("Player = " + board.countPoints(2));
        System.out.println("PC = " + board.countPoints(1));
//        board.play(2);
//        MiniMaxTreeWithPruning miniMaxTree = new MiniMaxTreeWithPruning(board, 1);
//        int bestMove = miniMaxTree.findBestMove();
//        System.out.println("Best Move: " + bestMove);
    }
}
