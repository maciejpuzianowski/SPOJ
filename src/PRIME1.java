import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class PRIME1 {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while((s = r.readLine()) != null){
            primes(s);
        }
    }

    //https://pl.wikipedia.org/wiki/Sito_Eratostenesa#/media/Plik:Sieve_of_Eratosthenes_animation.gif

    public static void primes(String input){
        var splitedInput = input.split(" ");

        if(splitedInput.length == 2){
            int start = Integer.parseInt(splitedInput[0]), stop = Integer.parseInt(splitedInput[1]);
            //looking for primes up to sqrt of stop
            int j = 0;
            int sqrt = (int) Math.sqrt(stop);
            boolean[] isPrime = new boolean[sqrt+1];
            int[] primes = new int[sqrt+1];
            Arrays.fill(isPrime, true);
            for (int i = 2; i <= sqrt; i++){
                if(isPrime[i]){
                    primes[j] = i;
                    j++;
                    for(int k = i+i; k<= sqrt; k+=i){
                        isPrime[k] = false;
                    }
                }
            }

            //looking for primes in range
            int size = stop - start + 1;
            isPrime = new boolean[size];
            Arrays.fill(isPrime, true);
            for(int k = 0; k < j; k++){
                int closestInt = start / primes[k];
                closestInt *= primes[k];
                while(closestInt <= stop){
                    if(closestInt >= start && primes[k] != closestInt) isPrime[closestInt-start] = false;
                    closestInt += primes[k];
                }
            }
            for(int i = 0; i < size; i++){
                if(isPrime[i] && (i+start) != 1){
                    System.out.println(i+start);
                }
            }
            System.out.println();
        }
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
