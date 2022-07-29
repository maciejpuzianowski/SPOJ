package problems;

import java.math.BigInteger;
import java.util.Scanner;

public class ARITH {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] operators = {"+", "-", "*"};

        int cases = 0;
        if(scanner.hasNextLine()){
            cases = Integer.parseInt(scanner.nextLine());
        }

        for(int i = 0; i < cases; i++){

            String input = scanner.nextLine();
            String operator = "";

            //checking operation
            for (var op: operators) {
                if(input.contains(op)){
                    operator = op;
                    break;
                }
            }

            //getting numbers
            var split = input.split("\\".concat(operator));
            BigInteger num1 = new BigInteger(split[0]);
            BigInteger num2 = new BigInteger(split[1]);

            switch(operator){

                case "+":{
                    //getting result and max length of line
                    BigInteger result = num1.add(num2);
                    int maxLen = Math.max(result.toString().length(), split[1].length()+1);

                    //printing whitespaces and first number
                    System.out.print(" ".repeat( maxLen - split[0].length()));
                    System.out.println(num1);

                    //printing whitespaces and second number with operator
                    System.out.print(" ".repeat(maxLen - split[1].length() - 1));
                    System.out.println(operator+num2);

                    //dashes
                    System.out.print("-".repeat(maxLen));
                    System.out.println();

                    //result
                    System.out.print(" ".repeat(maxLen - result.toString().length()));
                    System.out.println(result);

                    break;
                }

                case "-":{
                    //getting result and max length of line
                    BigInteger result = num1.subtract(num2);
                    int maxLen = Math.max(result.toString().length(), Math.max(split[0].length(), split[1].length()+1));

                    //printing whitespaces and first number
                    System.out.print(" ".repeat(Math.max(0, maxLen  - split[0].length())));
                    System.out.println(num1);

                    //printing whitespaces and second number with operator
                    System.out.print(" ".repeat( maxLen - split[1].length() - 1));
                    System.out.println(operator+num2);

                    //dashes
                    System.out.print(" ".repeat(Math.min(maxLen - result.toString().length(), maxLen-split[1].length()-1)));
                    System.out.print("-".repeat(Math.max(result.toString().length(), split[1].length()+1)));
                    System.out.println();

                    //result and whitespaces before
                    System.out.print(" ".repeat(maxLen - result.toString().length()));
                    System.out.println(result);

                    break;
                }

                case "*":{
                    //getting result and max length of line
                    BigInteger result = num1.multiply(num2);
                    int maxLen = Math.max(result.toString().length(), split[1].length() + 1);

                    //printing whitespaces and first number
                    System.out.print(" ".repeat(maxLen - split[0].length()));
                    System.out.println(num1);

                    //printing whitespaces and second number with operator (-1 for operator sign)
                    System.out.print(" ".repeat(maxLen - split[1].length() - 1));
                    System.out.println(operator+num2);


                    //flag to move dashes and result in case last multiplied number is digit
                    boolean flag = false;

                    //printing process only when second number is longer then 1 digit
                    if(split[1].length() > 1) {

                        //for each digit in num2
                        for (int s = split[1].length() - 1; s >= 0; s--) {

                            //getting partial result
                            BigInteger partialResult = num1.multiply(new BigInteger(String.valueOf(split[1].charAt(s))));

                            //first dashes and spaces before (+1 for operator sign)
                            if(s == split[1].length()-1){
                                System.out.print(" ".repeat(maxLen - Math.max(split[1].length() + 1, partialResult.toString().length())));
                                System.out.print("-".repeat(Math.max(split[1].length()+1, partialResult.toString().length())));
                                System.out.println();
                            }

                            //last digit flag
                            if(s == 0 && partialResult.toString().length() == 1){
                                flag = true;
                            }

                            System.out.print(" ".repeat(Math.max(0, maxLen - (split[1].length() - 1 - s) - partialResult.toString().length())));
                            System.out.println(partialResult);

                        }
                    }

                    //final dashes
                    if(flag) System.out.print(" ");
                    System.out.print("-".repeat(result.toString().length()));
                    System.out.println();

                    //result
                    if(flag) System.out.print(" ");
                    System.out.println(result);

                    break;
                }
                default: return;
            }
            System.out.println();
        }
    }
}
