package problems;

import java.util.*;

public class ONP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int expressions = 0;
        if(scanner.hasNextLine()){
            expressions = Integer.parseInt(scanner.nextLine());
        }

        for(int i = 0; i < expressions; i++){
            if(scanner.hasNextLine()) {
                String expression = scanner.nextLine();
                onpExpression(expression);
            }
        }
    }

    public static void onpExpression(String s){
        Stack<Character> stack = new Stack<>();
        Queue<Character> queue = new LinkedList<>();
        Character[] sym = {'^', '*', '%', '/', '+', '-', ')', '('};
        int[] priority = {3, 2, 2, 2, 1, 1, 1, 0};
        List<Character> symbols = Arrays.asList(sym);

        for(int i = 0; i < s.length(); i++){
            var currChar = s.charAt(i);

            //if current char is a letter
            if(Character.isAlphabetic(currChar)){
                queue.add(currChar);
            }

            else if(currChar == '('){
                stack.add(currChar);
            }

            else if(currChar == ')'){
                while(stack.peek() != '('){
                    queue.add(stack.pop());
                }
                stack.pop();
            }

            //if current char is in symbols list except "(", ")"
            else if (symbols.contains(currChar)){
                char top = stack.peek();

                //checking if the priority of the top of the stack has higher or equal priority then current char
                if(priority[symbols.indexOf(top)] >= priority[symbols.indexOf(currChar)]){
                    queue.add(stack.pop());
                }
                //if not
                else {
                    stack.add(currChar);
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for(Character c: queue) result.append(c);

        System.out.println(result);
    }
}
