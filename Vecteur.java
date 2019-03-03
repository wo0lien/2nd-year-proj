/**
 * Vecteur
 */
public class Vecteur {

    private double x,y,n;

    public Vecteur(double ax, double ay) {
        x = ax;
        y = ay;
        n = Math.sqrt(x * x + y * y);
    }

    //accesseurs

    public double GetX(){
        return x;
    }
    public double GetY() {
        return y;
    }
    public double GetNorme() {
        return n;
    }
}