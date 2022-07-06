import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Recognition recognition = new Recognition();
        while(scanner.hasNextLine()){
            recognition.addInput(scanner.nextLine());
        }
        recognition.dataToPictures();
        recognition.recognize();
    }
}