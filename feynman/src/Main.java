import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while((s = r.readLine()) != null){
            int result =feynman(Integer.parseInt(s));
            System.out.println(result == 0 ? "" : result);
        }
    }

    public static int feynman(int i){
        if(i == 0) return 0;
        if(i == 1) return 1;
        return feynman(i-1) + (i*i);
    }

}