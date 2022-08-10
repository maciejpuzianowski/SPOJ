package challanges;

import java.math.BigInteger;
import java.util.Scanner;

public class FIB64 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < 2550; i++) {

            var split = sc.nextLine().split(" ");

            var fib = split[0];
            var mod = split[1];

            if(fib.length() > 10 || mod.length() > 10) {
                System.out.println();
                continue;
            }

            var fibint = new BigInteger(fib);
            var modint = new BigInteger(mod);

            System.out.println(fibMod(fibint, modint));

        }
    }

    private static BigInteger fibMod(BigInteger n, BigInteger m){
        return fib(n,m)[0];
    }

    //https://math.stackexchange.com/questions/1124590/need-help-understanding-fibonacci-fast-doubling-proof

    private static BigInteger[] fib(BigInteger n, BigInteger m){
        if(n.equals(BigInteger.ZERO)){
            return new BigInteger[]{BigInteger.ZERO,BigInteger.ONE};
        }

        else{
            BigInteger[] ab = fib(n.divide(BigInteger.valueOf(2)), m);
            BigInteger c = ab[0].multiply(ab[1].multiply(BigInteger.valueOf(2)).subtract(ab[0])).mod(m);
            BigInteger d = (ab[0].multiply(ab[0]).add(ab[1].multiply(ab[1]))).mod(m);

            if(n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
                return new BigInteger[]{c,d};

            else
                return new BigInteger[]{d, c.add(d).mod(m)};
        }
    }

}
