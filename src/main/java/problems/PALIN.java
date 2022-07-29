package problems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;

public class PALIN {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        int cases = Integer.parseInt(r.readLine());

        for(int i = 0; i < cases; i++){
            System.out.println(PalinSearch.search(r.readLine()));
        }
    }

    static class PalinSearch{
        static char[] increment(char[] str)
        {
            Character[] result = new Character[str.length+2];

            int n1 = 1, n2 = str.length;

            //reverse
            char[] str1= {'1'};
            char[] str2 = reverseArray(str);

            int next = 0;
            int i = 0;
            for (; i < n1; i++)
            {
                int sum = ((str1[i] - '0') +(str2[i] - '0') + next);
                result[i] = (char)(sum % 10 + '0');

                //next for next go
                next = sum / 10;
            }

            // rest of num2
            for (i = n1; i < n2; i++)
            {
                int sum = ((str2[i] - '0') + next);
                result[i] = (char)(sum % 10 + '0');
                next = sum / 10;
            }

            // last next
            if (next > 0)
                result[++i] = (char)(next + '0');

            //getting rid of empty elements in array
            Object[] c = Arrays.stream(result).filter(Objects::nonNull).toArray();
            char[] res = new char[c.length];
            int id = 0;
            for(var o: c) {
                res[id] = (char)o;
                id++;
            }

            //reversing and return
            return reverseArray(res);
        }

        private static char[] reverseArray(char[] arr) {
            char[] array = Arrays.copyOf(arr, arr.length);
            int i;
            char temp;
            int size = array.length;
            for (i = 0; i < size / 2; i++) {
                temp = array[i];
                array[i] = array[size - i - 1];
                array[size - i - 1] = temp;
            }
            return array;
        }
        public static String search(String num){
            char[] arr = num.toCharArray();
            char[] result;
            //storing borders in variables
            int len = num.length();
            int lenDiv2 = len / 2;
            int lenMinusLen2 = len - lenDiv2;

            //dividing string into 3 parts
            var left = Arrays.copyOfRange(arr,0, lenDiv2);
            var middle = Arrays.copyOfRange(arr, lenDiv2, lenMinusLen2); //middle can be empty
            var right = Arrays.copyOfRange(arr,lenMinusLen2, len);

            //if right is less than left reversed we are just taking left reversed as right part
            var leftReversed = reverseArray(left);
            if (Arrays.compare(right, leftReversed) < 0) {
                result = new char[left.length+ middle.length + leftReversed.length];
                int k = 0;

                for (char c : left) {
                    result[k] = c;
                    k++;
                }

                for (char c : middle) {
                    result[k] = c;
                    k++;
                }

                for (char c : leftReversed) {
                    result[k] = c;
                    k++;
                }
            }

            //if not
            else {
                var temp = new char[left.length + middle.length];
                int k = 0;
                for (char c : left) {
                    temp[k] = c;
                    k++;
                }

                for (char c : middle) {
                    temp[k] = c;
                    k++;
                }

                //incrementing left (with middle)
                var next = increment(temp);

                //reversing next
                var nextReversed = reverseArray(next);

                //making newLeft by taking first left.length() + middle.length()
                var newLeft = Arrays.copyOfRange(next,0, left.length + middle.length);

                //making newRight by taking the entire reversed (or without first digit in case num.length() is odd)
                var newRight = Arrays.copyOfRange(nextReversed, middle.length, nextReversed.length);

                result = new char[newLeft.length+ newRight.length];
                int j = 0;

                for (char c : newLeft) {
                    result[j] = c;
                    j++;
                }

                for (char c : newRight) {
                    result[j] = c;
                    j++;
                }
            }
            var sb = new StringBuilder();
            for(char c: result) sb.append(c);
            return sb.toString();
        }
    }
}
