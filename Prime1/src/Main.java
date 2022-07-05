import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while((s = r.readLine()) != null){
            primes(s);

        }
    }

    public static void primes(String input){

    }


    //
    //
    //
    public static void slowWayPrimes(String input){
        var splitedInput = input.split(" ");

        if(splitedInput.length == 2) {
            int start = Integer.parseInt(splitedInput[0]), stop = Integer.parseInt(splitedInput[1]);
            for(int i = start; i <= stop; i++){
                if(i > 3 && (i%2 == 0 || i%3 == 0)) continue;
                int counter = 0;
                for (int k = 2; k<=i; k++){
                    if (i%k == 0) counter++;
                    if(counter > 1) break;
                }
                if (counter == 1) System.out.println(i);
            }
            System.out.println();
        }
    }
}