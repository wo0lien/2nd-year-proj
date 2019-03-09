import java.awt.Graphics;
import java.awt.Image;

/**
 * ObjetCeleste
 * Premiere instance abstract du composant de base objet celeste
 */
public abstract class ObjetCeleste {

    //
    protected double masse, vAng;
    protected int x, y;
    protected Image disp;

    public ObjetCeleste(double m, double v, int ax, int ay, Image i) {
        masse = m;
        x = ax;
        y = ay;
        vAng = v;
        disp = i;
    }

    //déclatation des méthodes abstract

    public abstract void paint(Graphics g, double t);

    public int GetX(){
        return this.x;
    }

    public int GetY() {
        return this.y;
    }
}