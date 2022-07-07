import java.awt.*;
import java.util.ArrayList;

class Picture {
    private final int Width;
    private final int Height;
    private final String[] Data;
    private final ArrayList<Point> Points;

    Picture(int w, int h, String[] d){
        Width = w;
        Height = h;
        Data = d;
        Points = new ArrayList<>();
        for(int i = 0; i < Data.length; i++){
            for (int k = 0; k < Width; k++) {
                if(Data[i].charAt(k) == 'x') Points.add(new Point(k+1, i+1));
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
}
