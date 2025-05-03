package com.example.connect4;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void print(TreeNode node, String indent){
//        node.state.board.printBoard(indent);
        System.out.println(indent + "eval = " +node.state.heuristic + ", isPruned = " + node.isCut);
    }
    public static void printTree(TreeNode node, int k){
        String indent = "";
        for (int i = 0; i < k; i++) {
            indent += "   ";
        }
        if (node == null){
            return;
        }
        print(node, indent);
        for (int i = 0; i < node.children.size(); i++) {
            printTree(node.children.get(i), k + 1);
        }
    }

    public static void main(String[] args){
        Board board = new Board();
        State state = new State(board);
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

//        Test No pruning: success
//        board.play(2);
//        board.play(3);
//        board.play(2);
//        MiniMaxTree tree = new MiniMaxTree(state, 3);
//        TreeNode node = tree.miniMax(tree.root, 3, true);
//        printTree(node,0);
//        System.out.println("Best = " + tree.bestMove());

//        Test Pruning: success
//        board.play(2);
//        board.play(3);
        board.play(2);
        MiniMaxTreeWithPruning tree = new MiniMaxTreeWithPruning(state, 2);
        TreeNode node = tree.miniMax(tree.root, 2,Integer.MIN_VALUE,Integer.MAX_VALUE, true);
        printTree(node,0);
        System.out.println("Best = " + tree.bestMove());

//        while (!board.isFull()){
//            System.out.println("Turn = " + board.turn);
//            int input = scanner.nextInt();
//            if(input == 10){
//                break;
//            }
//            if(!board.play(input)){
//                continue;
//            };
//            board.printBoard();
//            System.out.println("Turn = " + board.turn);
//            MiniMaxTreeWithPruning tree = new MiniMaxTreeWithPruning(state, 2);
//            TreeNode node = tree.miniMax(tree.root, 2,Integer.MIN_VALUE,Integer.MAX_VALUE, true);
//            int x = tree.bestMove();
//            if(x == -1){
//                System.out.println("No move");
//            }else {
//                board.play(x);
//                board.printBoard();
//            }
//        }
    }
}
