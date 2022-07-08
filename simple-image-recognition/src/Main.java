import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = 0;

        if(scanner.hasNextLine()){
            testCases = Integer.parseInt(scanner.nextLine());
        }

        for (int i = 0; i < testCases; i++){
            if(scanner.hasNextLine()) scanner.nextLine();

            int images = 0;
            if(scanner.hasNextLine()){
                images = Integer.parseInt(scanner.nextLine());
            }

            if(scanner.hasNextLine()) scanner.nextLine();

            for(int k = 0; k < images; k++){
                int width = 0, height = 0;
                if(scanner.hasNextLine()){
                    String[] dim = scanner.nextLine().split(" ");
                    height = Integer.parseInt(dim[0]);
                    width = Integer.parseInt(dim[1]);
                }
                String[] data = new String[height];
                for (int l = 0; l < height; l++){
                    data[l] = scanner.nextLine();
                }
                var picture = new Picture(width, height, data);
                System.out.print(picture.whatAmI());
            }
            System.out.println();
        }
    }
}