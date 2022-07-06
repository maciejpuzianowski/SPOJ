public class Picture {
    private int Width;
    private int Height;
    private String[] Data;

    Picture(int w, int h, String[] d){
        Width = w;
        Height = h;
        Data = d;
    }

    public void info(){
        System.out.println("width: " + Width);
        System.out.println("height: " + Height);
        for (String x: Data){
            System.out.println(x);
        }
    }

    public String whatAmI(){
        return "";
    }
}
