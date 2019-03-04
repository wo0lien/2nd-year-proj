import java.awt.Graphics;

/**
 * ObjetCeleste
 * Premiere instance abstract du composant de base objet celeste
 */
public abstract class ObjetCeleste {

    //
    protected double masse, vAng;
    protected int x, y;

    public ObjetCeleste(double m, double v, int ax, int ay) {
        masse = m;
        x = ax;
        y = ay;
        vAng = v;
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