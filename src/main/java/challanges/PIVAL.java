package challanges;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PIVAL {
    public static void main(String[] args) {
        System.out.println(PiHelper.calculate(30000));
    }
}

class PiHelper{
    public static String calculate(int decimals){

        //https://en.wikipedia.org/wiki/Gauss%E2%80%93Legendre_algorithm

        BigDecimal a = BigDecimal.ONE;
        BigDecimal b = BigDecimal.ONE.divide(sqrt(new BigDecimal(2), decimals), decimals, RoundingMode.HALF_UP);
        BigDecimal t = BigDecimal.ONE.divide(new BigDecimal(4));
        BigDecimal p = BigDecimal.ONE;
        BigDecimal tempA;

        long start = System.currentTimeMillis();
        long stop = 0;
        while(stop-start < 10000) {
            tempA = a;

            a = tempA.add(b).divide(new BigDecimal(2), decimals, RoundingMode.HALF_UP);
            b = sqrt(tempA.multiply(b), decimals);
            t = t.subtract(p.multiply(tempA.subtract(a).multiply(tempA.subtract(a))));
            p = p.multiply(new BigDecimal(2));
            stop = System.currentTimeMillis();
        }

        return a.add(b).multiply(a.add(b)).divide(new BigDecimal(4).multiply(t), decimals, RoundingMode.HALF_UP).toString();
    }

    private static BigDecimal sqrt(BigDecimal value, int scale) {

        //here we are trying to get scale decimals of sqrt(value)

        BigDecimal startingPoint = new BigDecimal("0");
        BigDecimal sqrtFromDouble = BigDecimal.valueOf(Math.sqrt(value.doubleValue()));

        while (!startingPoint.equals(sqrtFromDouble)) {
            startingPoint = sqrtFromDouble;
            sqrtFromDouble = value.divide(startingPoint, scale, RoundingMode.HALF_UP);
            sqrtFromDouble = sqrtFromDouble.add(startingPoint);
            sqrtFromDouble = sqrtFromDouble.divide(new BigDecimal(2), scale, RoundingMode.HALF_UP);
        }

        return sqrtFromDouble;
    }
}
