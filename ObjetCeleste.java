import java.awt.Graphics;
import java.awt.Image;

/**
 * ObjetCeleste
 * Premiere instance abstract du composant de base objet celeste
 */
public abstract class ObjetCeleste {

    //
    protected double masse, dx, dy;
    protected int x, y;
    protected Image disp;

    public ObjetCeleste(double m, double vx, double vy, int ax, int ay, Image i) {
        masse = m;
        x = ax;
        y = ay;
        dx = vx;
        dy = vy;
        disp = i;
    }

    //déclatation des méthodes abstract

    public abstract void paint(Graphics g);

    public abstract void update(int dt);

    public int GetX(){
        return this.x;
    }

    public int GetY() {
        return this.y;
    }

    public abstract void setVitesseX(double vx);
    public abstract void setVitesseY(double vy);
}