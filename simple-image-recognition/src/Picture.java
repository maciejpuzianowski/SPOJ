import java.awt.*;
import java.util.ArrayList;

class Picture {
    private final int Width;
    private final int Height;
    private final String[] Data;
    private final char[][] Chars;
    private final ArrayList<Point> Points;

    Picture(int w, int h, String[] d){
        Width = w;
        Height = h;
        Data = d;
        Chars = new char[Height][Width];
        Points = new ArrayList<>();
        for(int i = 0; i < Data.length; i++) {
            for (int k = 0; k < Width; k++) {
                if (Data[i].charAt(k) == 'x') {
                    Chars[i][k] = 'x';
                    Points.add(new Point(k + 1, i + 1));
                } else Chars[i][k] = '.';
            }
        }
    }

    public void info(){
        System.out.println("width: " + Width);
        System.out.println("height: " + Height);
        for (String x: Data){
            System.out.println(x);
        }
    }

    public String whatAmI(){
        String outcome = "x";
        ArrayList<LinearFunction> lines = new ArrayList<>();
        for(int i = 0; i < Points.size()-1;){
            var line = new LinearFunction(Points.get(i), Points.get(i+1));
            if(i == Points.size() - 2) {
                lines.add(line);
                break;
            }
            for(int k = i+2; k < Points.size(); k++){
                if(!line.belongs(Points.get(k))){
                    i=k;
                    break;
                }
                line.incrementAmount();
                i=k;
            }
            lines.add(line);
        }
        int parallelcounter = 0;
        for(int i = 0 ; i < lines.size(); i++){
            var iline = lines.get(i);
            if(iline.getAmountOfPoints() == 2) continue;
            for(int k = 0; k < lines.size(); k++){
                var kline = lines.get(k);
                if(iline.getAmountOfPoints() > 2 && kline.getAmountOfPoints() > 2){
                    if(iline.getA() != 0 && iline.getA() == kline.getA()){
                        parallelcounter++;
                        lines.remove(kline);
                    } else if((iline.getConstantX() != 0 && iline.getConstantX() == kline.getConstantX()) ||
                            iline.getConstantY() != 0 && iline.getConstantY() == kline.getConstantY()){
                        parallelcounter++;
                        lines.remove(kline);
                    }
                }
            }
        }
        if(parallelcounter > 1) outcome = "0";
        return outcome;
    }

    public String lessScoreWhatAmI(){
        char[][] pat1 = new char[3][3];
        pat1[0][0] = 'x';
        pat1[0][1] = 'x';
        pat1[0][2] = 'x';
        pat1[1][0] = 'x';
        pat1[1][1] = '.';
        pat1[1][2] = '.';
        pat1[2][0] = 'x';
        pat1[2][1] = '.';
        pat1[2][2] = '.';
        char[][] pat2 = new char[3][3];
        pat2[0][0] = '.';
        pat2[0][1] = 'x';
        pat2[0][2] = 'x';
        pat2[1][0] = 'x';
        pat2[1][1] = '.';
        pat2[1][2] = '.';
        pat2[2][0] = 'x';
        pat2[2][1] = '.';
        pat2[2][2] = '.';
        for (int k = 0; k < Height - 3; k++) {
            for (int i = 0; i < Width - 3; i++) {
                if ((Chars[k][i] == pat1[0][0] && Chars[k][i + 1] == pat1[0][1] && Chars[k][i + 2] == pat1[0][2]
                        && Chars[k + 1][i] == pat1[1][0] && Chars[k + 1][i + 1] == pat1[1][1]
                        && Chars[k + 2][i] == pat1[2][0]) ||
                        (Chars[k][i] == pat2[0][0] && Chars[k][i + 1] == pat2[0][1] && Chars[k][i + 2] == pat2[0][2]
                                && Chars[k + 1][i] == pat2[1][0] && Chars[k + 1][i + 1] == pat2[1][1] && Chars[k + 2][i] == pat2[2][0])) {
                    if(i > 0 && k > 0){
                        if(Chars[k-1][i] == 'x' || Chars[k][i-1] == 'x') return "x";
                    }
                    return "0";
                }
            }
        }
        return "x";
    }
}
