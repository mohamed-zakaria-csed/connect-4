package com.example.connect4;

public class Board {
    Column[] columns;
    int turn;

    public Board(){
        this.columns = new Column[7];
        for(int i = 0; i < 7; i++){
            columns[i] = new Column();
        }
        this.turn = 2;
    }
    public Board clone(){
        Board copy = new Board();
        copy.turn = this.turn;
        for (int i = 0; i < this.columns.length; i++) {
            copy.columns[i] = this.columns[i].clone();
        }
        return copy;
    }
    public boolean play(int columnNum){
        if(columns[columnNum].add(turn)){
            if(turn == 1)
                turn = 2;
            else
                turn = 1;
            return true;
        }
        else{
//            System.out.println("play in an available col");
            return false;
        }
    }

    public int countPoints(int player) {
        int rows = 6;
        int cols = 7;
        int count = 0;

        // Check horizontally
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <= cols - 4; j++) {
                if (columns[j].get(i) == player &&
                        columns[j + 1].get(i) == player &&
                        columns[j + 2].get(i) == player &&
                        columns[j + 3].get(i) == player) {
                    count++;
                }
            }
        }
        //System.out.println("h " + count);

        // Check vertically
        for (int i = 0; i <= rows - 4; i++) {
            for (int j = 0; j < cols; j++) {
                if (columns[j].get(i) == player &&
                        columns[j].get(i + 1) == player &&
                        columns[j].get(i + 2) == player &&
                        columns[j].get(i + 3) == player) {
                    count++;
                }
            }
        }
        //System.out.println("v " + count);

        // Check diagonally (positive slope)
        for (int i = 0; i <= rows - 4; i++) {
            for (int j = 0; j <= cols - 4; j++) {
                if (columns[j].get(i) == player &&
                        columns[j + 1].get(i + 1) == player &&
                        columns[j + 2].get(i + 2) == player &&
                        columns[j + 3].get(i + 3) == player) {
                    count++;
                }
            }
        }
        //System.out.println("p " + count);

        // Check diagonally (negative slope)
        for (int i = 0; i <= rows - 4; i++) {
            for (int j = 3; j < cols; j++) {
                if (columns[j].get(i) == player &&
                        columns[j - 1].get(i + 1) == player &&
                        columns[j - 2].get(i + 2) == player &&
                        columns[j - 3].get(i + 3) == player) {
                    count++;
                }
            }
        }
        //System.out.println("n " + count);

        return count;
    }

    public Boolean isFull(){
        int fullColumnsCount = 0;
        for(int i = 0; i < 7; i++){
            if(columns[i].isFilled())
                fullColumnsCount++;
        }
        if (fullColumnsCount == 7)
            return true;
        else
            return false;
    }
    public void printBoard(){
        for(int i = 5; i >= 0; i--){
            for(int j = 0; j < 7;j++){
                System.out.print(columns[j].get(i) + " ");
            }
            System.out.println();
        }
    }
    public void printBoard(String indent){
        for(int i = 5; i >= 0; i--){
            System.out.print(indent);
            for(int j = 0; j < 7;j++){
                System.out.print(columns[j].get(i) + " ");
            }
            System.out.println();
        }
    }
    public  int evaluateBoard(Board board, int player) {
        int score = 0;

        // Check for horizontal connections
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                score += evaluateLine(board.columns[col].get(row), board.columns[col+1].get(row), board.columns[col+2].get(row), board.columns[col+3].get(row), player);
            }
        }

        // Check for vertical connections
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 7; col++) {
                score += evaluateLine(board.columns[col].get(row), board.columns[col].get(row+1), board.columns[col].get(row+2), board.columns[col].get(row+3), player);
            }
        }

        // Check for diagonal connections (from bottom-left to top-right)
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                score += evaluateLine(board.columns[col].get(row), board.columns[col+1].get(row-1), board.columns[col+2].get(row-2), board.columns[col+3].get(row-3), player);
            }
        }

        // Check for diagonal connections (from top-left to bottom-right)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                score += evaluateLine(board.columns[col].get(row), board.columns[col+1].get(row+1), board.columns[col+2].get(row+2), board.columns[col+3].get(row+3), player);
            }
        }
        return score;
    }

    private int evaluateLine(int a, int b, int c, int d, int player) {
        int score = 0;
        int playerCount = 0;
        int opponentCount = 0;

        // Count the number of player and opponent discs in the line
        if (a == player) playerCount++;
        else if (a != 0) opponentCount++;

        if (b == player) playerCount++;
        else if (b != 0) opponentCount++;

        if (c == player) playerCount++;
        else if (c != 0) opponentCount++;

        if (d == player) playerCount++;
        else if (d != 0) opponentCount++;

        // Assign scores based on the number of player and opponent discs
        if (playerCount == 4) score += 1000;  // Player has four in a row
        else if (playerCount == 3 && opponentCount == 0) score += 100;  // Player has three in a row
        else if (playerCount == 2 && opponentCount == 0) score += 10;   // Player has two in a row
        else if (playerCount == 1 && opponentCount == 0) score += 1;    // Player has one in a row

        else if (opponentCount == 4) score -= 1000;  // Opponent has four in a row
        else if (opponentCount == 3 && playerCount == 0) score -= 100;  // Opponent has three in a row
        else if (opponentCount == 2 && playerCount == 0) score -= 10;   // Opponent has two in a row
        else if (opponentCount == 1 && playerCount == 0) score -= 1;    // Opponent has one in a row

        return score;
    }
}
