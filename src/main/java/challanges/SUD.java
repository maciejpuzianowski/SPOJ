package challanges;

import java.util.Scanner;

public class SUD {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long stop;
        Scanner sc = new Scanner(System.in);

        int cases = 0;
        if(sc.hasNextLine()){
            cases = Integer.parseInt(sc.nextLine());
        }

        for (int i = 0; i < cases; i++) {

            String sudoku = sc.nextLine();
            int[][] board = new int[9][9];
            int boardIdx = 0;

            for(int j = 0; j < 9; j++){
                for(int k = 0; k < 9; k++){
                    var character = sudoku.charAt(boardIdx);
                    board[j][k] = character != '.' ? Integer.parseInt(String.valueOf(character)) : 0;
                    boardIdx++;
                }
            }

            var sud = new Sudoku(board);
            if(sud.solve()) sud.print();
            else System.out.println("N");
            stop = System.currentTimeMillis();
            if(stop-start > 23990) break;
        }
    }
}

class Sudoku{
    private final int[][] Board;

    public Sudoku(int[][] board) {
        Board = board;
    }

    //marking existing numbers
    private boolean[][] markExisting(){
        boolean[][] exists = new boolean[9][9];

        for(int i=0; i < Board.length; i++)
            for(int j=0; j < Board.length; j++)
                exists[i][j] = Board[i][j] != 0;

        return exists;
    }

    public boolean solve(){
        boolean[][] existing = markExisting();
        int row, col, id = 0;
        boolean backtracking = false;

        while( id >= 0 && id < 81){

            // Row and col
            row = id/9;
            col = id%9;

            // Check only empty spaces
            if(!existing[row][col]){
                Board[row][col]++;

                // Search for next value
                while(!isSafe(Board[row][col], row, col))
                    Board[row][col]++;

                if(Board[row][col] > 9){

                    // No value found. We do back-tracking.
                    Board[row][col] = 0;
                    backtracking = true;


                } else{

                    // Go forward.
                    backtracking = false;
                }
            }

            // If back-tracking is on, we go back one step
            id += backtracking ? -1:1;
        }

        // If id == 81 - Done else, it is unsolvable
        return id == 81;
    }

    private boolean isSafe(int num, int row, int col) {
        return !this.isInRow(num, row, col)
                && !this.isInColumn(num,row, col)
                && !this.isInBox(num, row, col);
    }

    private boolean isInRow(int num, int row, int col){
        for(int i = 0; i < 9; i++){
            if(i != col && Board[row][i] == num) return true;
        }
        return false;
    }

    private boolean isInColumn(int num, int row, int column){
        for(int i = 0; i < 9; i++){
            if(i != row && Board[i][column] == num) return true;
        }
        return false;
    }

    private boolean isInBox(int num, int row, int col){
        int srow = row - row%3;
        int scol = col - col%3;
        for(int i = srow; i < srow + 3; i++) {
            for (int j = scol; j < scol + 3; j++) {
                if (i == row && j == col) continue;
                if (Board[i][j] == num) return true;
            }
        }
        return false;
    }

    public void print(){
        System.out.println("Y");
        for (int[] ints : Board) {
            for (int j = 0; j < Board.length; j++) {
                System.out.print(ints[j]);
            }
        }
        System.out.println();
    }

    public void printForPeople(){
        for (int[] ints : Board) {
            for (int j = 0; j < Board.length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}