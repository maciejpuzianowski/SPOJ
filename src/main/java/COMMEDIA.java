import java.util.Arrays;
import java.util.Scanner;

public class COMMEDIA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int cases = 0;
        if(scanner.hasNextLine()){
            cases = Integer.parseInt(scanner.nextLine());
        }

        for (int i = 0; i < cases; i++){
            //getting dimension
            int dim = 0;
            if(scanner.hasNextLine()){
                dim = Integer.parseInt(scanner.nextLine());
            }

            //storing input
            String[] input = new String[dim];
            for (int k = 0; k < dim; k++){
                input[k] = scanner.nextLine();
            }

            //finding how many moves is needed to get blank tile into right place
            int moves = 0;
            //collecting values to an array
            int[] values = new int[dim*dim*dim];
            int valuesIndex = 0;
            for(int k = 0; k < dim; k++){

                //obtaining values for array and looking for 0 to calculate moves
                var splitString = input[k].split(" ");

                for(int j = 0; j < splitString.length; valuesIndex++ , j++){
                    //values to array
                    values[valuesIndex] = Integer.parseInt(splitString[j]);

                    //looking for 0
                    if(splitString[j].equals("0")){
                        //adding move from left to right
                        moves += (dim - (j+1) % dim);
                        //adding move from rear to front
                        moves += dim - Math.ceil((double)(j+1)/dim);
                        //adding move from up to down
                        moves += (dim - (k + 1));
                    }
                }

            }

            //creating and filing array with numbers from 1 to dim*dim*dim
            int[] initial = new int[dim*dim*dim];
            Arrays.setAll(initial, o -> o+1);

            //counting number of transpositions needed to achieve the goal
            int transpositions = 0;
            for(int k = 0; k < values.length; k++){

                //transposition needed
                if(initial[k] != values[k] && values[k] != 0){
                    transpositions++;

                    //swapping tiles - transposition
                    for (int j = k; j < values.length; j++){
                        if(values[j] == initial[k]){
                            var temp = values[k];
                            values[k] = values[j];
                            values[j] = temp;
                            break;
                        }
                    }
                }
            }

            //getting outcome
            boolean isMovesOdd = moves % 2 == 0;
            if(isMovesOdd){
                System.out.println(transpositions % 2 == 0 ? "Puzzle can be solved." : "Puzzle is unsolvable.");
            } else{
                System.out.println(transpositions % 2 == 1 ? "Puzzle can be solved." : "Puzzle is unsolvable.");
            }
        }
    }
}
