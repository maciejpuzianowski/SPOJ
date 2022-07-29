package problems;

import java.util.Arrays;
import java.util.Scanner;

public class SBANK {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int testCases = 0;
        if (scanner.hasNextLine()) {
            testCases = Integer.parseInt(scanner.nextLine());
        }

        for (int i = 0; i < testCases; i++) {
            int banksAmount = 0;
            if (scanner.hasNextLine()) {
                banksAmount = Integer.parseInt(scanner.nextLine());
            }

            String[] banks = new String[banksAmount];

            //getting account numbers to array
            for (int k = 0; k < banksAmount; k++) {
                banks[k] = scanner.nextLine();
            }

            //sorting strings
            Arrays.sort(banks);

            //checking if numbers appear more than once
            for (int j = 0; j < banks.length; j++) {
                int flag = 1;
                var ref = banks[j];
                while (j < banks.length - 1 && ref.equals(banks[j + 1])) {
                    flag++;
                    j++;
                }
                System.out.println(ref + " " + flag);
            }

            if(scanner.hasNextLine()) {
                //empty line
                scanner.nextLine();
            }
        }
    }
}