package challanges;

import java.util.Scanner;

public class SUD {

    public static void main(String[] args) {
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

        }
    }
}

class Sudoku{
    private int[][] Board;

    public Sudoku(int[][] board) {
        Board = board;
    }

    private boolean[][] findSolved(){
        boolean[][] isSolved = new boolean[9][9];

        for(int i=0; i<9; i++)
            for(int j=0; j<9; j++)
                isSolved[i][j] = Board[i][j] != 0;

        return isSolved;
    }

    public boolean solve(){
        boolean[][] isSolved = findSolved();
        int row, col, k = 0;
        boolean backtracking = false;

        while( k >= 0 && k < 81){
            // Find row and col
            row = k/9;
            col = k%9;

            // Only handle the unsolved cells
            if(!isSolved[row][col]){
                Board[row][col]++;

                // Find next valid value to try, if one exists
                while(!isSafe(Board[row][col]+1, row, col) && Board[row][col] < 9)
                    Board[row][col]++;

                if(Board[row][col] >= 9){
                    // no valid value exists. Reset cell and backtrack
                    Board[row][col] = 0;
                    backtracking = true;
                } else{
                    // a valid value exists, move forward
                    backtracking = false;
                }
            }

            // if backtracking move back one, otherwise move forward 1.
            k += backtracking ? -1:1;
        }

        // k will either equal 81 if done or -1 if there was no solution.
        return k == 81;
    }

    private boolean isSafe(int num, int row, int col) {
        return !this.usedInRow(num, row)
                && !this.usedInColumn(num, col)
                && !this.usedInBox(num, row, col);
    }

    private boolean usedInRow(int num, int row){
        for(int i = 0; i < 9; i++){
            if(Board[row][i] == num) return true;
        }
        return false;
    }

    private boolean usedInColumn(int num, int column){
        for(int i = 0; i < 9; i++){
            if(Board[i][column] == num) return true;
        }
        return false;
    }

    private boolean usedInBox(int num, int row, int col){
        row = row/3;
        col = col/3;
        for(int i = 0; i < 3; i++){
            if(Board[row][i] == num) return true;
        }
        return false;
    }

    public void print(){
        for (int[] ints : Board) {
            for (int j = 0; j < Board.length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }
}