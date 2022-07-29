package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ADDREV {
    public static void reStr(String input){
        String finalresult;

        if (input.split(" ").length != 2) {
            System.out.println();
            return;
        }
        String in1 = input.split(" ")[0], in2 = input.split(" ")[1];
        int num1 = Integer.parseInt(in1), num2 = Integer.parseInt(in2);

        //reversing
        String num1s = Integer.toString(num1), num2s = Integer.toString(num2);
        StringBuilder sb1 = new StringBuilder(num1s);
        StringBuilder sb2 = new StringBuilder(num2s);
        String num1sr = sb1.reverse().toString();
        String num2sr = sb2.reverse().toString();

        //checking zeros at beginning
        while (num1sr.startsWith("0"))
            num1sr = num1sr.substring(1);
        while (num2sr.startsWith("0"))
            num2sr = num2sr.substring(1);

        int res1 = Integer.parseInt(num1sr), res2 = Integer.parseInt(num2sr), result = res1 + res2;
        StringBuilder resb = new StringBuilder(Integer.toString(result));
        finalresult = resb.reverse().toString();
        while (finalresult.startsWith("0"))
            finalresult = finalresult.substring(1);
        System.out.println(finalresult);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while((s = r.readLine()) != null){
            reStr(s);
        }
    }
}
