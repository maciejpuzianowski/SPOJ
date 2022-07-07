import java.awt.*;

class LinearFunction {
    private final double A;
    private final double B;
    private final double ConstantX;
    private final double ConstantY;

    private int AmountOfPoints;

    LinearFunction(Point p1, Point p2){
        double[] ab = calculateAB(p1, p2);
        A = ab[0];
        B = ab[1];
        ConstantX = ab[2];
        ConstantY = ab[3];
        AmountOfPoints = 2;
    }

    public void incrementAmount(){
        AmountOfPoints++;
    }
    public int getAmountOfPoints(){
        return AmountOfPoints;
    }
    public double getA(){
        return A;
    }
    public double getConstantX(){ return ConstantX; }
    public double getConstantY(){
        return ConstantY;
    }

    private double[] calculateAB(Point p1, Point p2){
        double[] result = new double[4];
        if(p1.x == p2.x) {
            result[0] = 0;
            result[1] = 0;
            result[2] = p1.x;
        } else if (p1.y == p2.y) {
            result[0] = 0;
            result[1] = 0;
            result[3] = p1.y;
        }else {
            result[0] = (p2.y - p1.y) / (p2.x - p1.x);
            result[1] = p1.y - (A * p1.x);
            result[2] = 0;
            result[3] = 0;
        }
        return result;
    }

    public boolean belongs(Point p){
        if(A != 0 && B != 0 && p.y == (A*p.x) + B) return true;
        if(ConstantX != 0 && p.x == ConstantX) return true;
        return ConstantY != 0 && p.y == ConstantY;
    }
}
