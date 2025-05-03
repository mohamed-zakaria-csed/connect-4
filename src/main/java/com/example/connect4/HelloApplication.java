package com.example.connect4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class HelloApplication extends Application {
    Board board = new Board();
    State state = new State(board);
    Random random = new Random();
    Scanner scanner = new Scanner(System.in);

    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private int[][] gameBoard = new int[ROWS][COLUMNS];
    private static int withPruning;
    private static int depth = 2;

    @Override
    public void start(Stage primaryStage) {
        boardTransformTo2DArray(board);
        GridPane gridPane = createGameBoard();

        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setTitle("Connect 4 Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void boardTransformTo2DArray(Board board){
        for(int i = 5; i >= 0; i--){
            for(int j = 0; j < 7;j++){
                gameBoard[ROWS - 1 - i][j] = board.columns[j].get(i);
            }
        }
    }
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
    private GridPane createGameBoard() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        Button[][] grd = new Button[6][7];
        Label label = new Label("Computer\nScore: 0");
        gridPane.add(label, 0, ROWS+1);
        Label label2 = new Label("Player\nScore: 0");
        gridPane.add(label2, 1, ROWS+1);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Button cell = new Button();
                cell.setShape(new Circle(1.5));
                cell.setStyle("-fx-background-color: grey");
                cell.setMinSize(50, 50);
                int finalI = i;
                int finalJ = j;
                cell.setOnAction(e -> handleButtonClick(finalI, finalJ, grd, label, label2));

                grd[i][j] = cell;
                gridPane.add(cell, j, i);
            }
        }
        for (int j = 0; j < COLUMNS; j++) {
            Separator separator = new Separator();
            gridPane.add(separator, j, ROWS);
        }
        return gridPane;
    }

    private void handleButtonClick(int row, int col, Button[][] grd, Label label1, Label label2){
        if(board.isFull()) return;
//        System.out.println("Player number: " + board.turn);
        int input = col;
        TreeNode node;
        if(!board.play(input)){
            return;
        }
//        state.evaluateBoard(board, 1);
        int x;
        if(withPruning == 0) {
            MiniMaxTree tree = new MiniMaxTree(state, depth);
            node = tree.miniMax(tree.root, depth, true);
            x = tree.bestMove();
        }else {
            MiniMaxTreeWithPruning tree = new MiniMaxTreeWithPruning(state, depth);
            node = tree.miniMax(tree.root, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            x = tree.bestMove();
        }
        if(x == -1){
            System.out.println("No move");
        }else {
//            System.out.println("Best Move = " + x);
            board.play(x);
            boardTransformTo2DArray(board);
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if(gameBoard[i][j] == 0)
                    grd[i][j].setStyle("-fx-background-color: grey");
                else if(gameBoard[i][j] == 1)
                    grd[i][j].setStyle("-fx-background-color: orangered");
                else
                    grd[i][j].setStyle("-fx-background-color: MediumSeaGreen");
            }
        }
        label1.setText("Computer\nScore: " + board.countPoints(1));
        label2.setText("Player\nScore: " + board.countPoints(2));
        printTree(node,0);
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("MinMax without pruning -> 0     ||    MinMax with pruning -> 1");
        withPruning = scanner.nextInt();
        System.out.println("Depth of MinMax algorithm: ");
        depth = scanner.nextInt();
        launch(args);
    }
}