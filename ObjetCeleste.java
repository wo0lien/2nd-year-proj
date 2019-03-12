import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

/**
 * ObjetCeleste Premiere instance abstract du composant de base objet celeste
 */
public abstract class ObjetCeleste {

    //
    protected double masse, dx, dy;
    protected int x, y;
    protected Image disp;
    protected int temp;

    public ObjetCeleste(double m, double vx, double vy, int ax, int ay, Image i) {
        masse = m;
        x = ax;
        y = ay;
        dx = vx;
        dy = vy;
        disp = i;
        for (int j = 0; j < objets.size(); j++) {
            // int Distx;
            // int Disty;
            double r;
            double Force;
            double angle;
            double dirx;
            double diry;
            dx = objets.get(j).x - x;
            dy = objets.get(j).y - y;
            r = dx * dx + dy * dy;
            if (r != 0) {
                Force = objets.get(j).masse / r;
                angle = Math.atan2(dy, dx);
                dirx = Force * Math.cos(angle);
                diry = Force * Math.sin(angle);
                vx += dirx;
                vy += diry;
            }
        }
    }

    // déclatation des méthodes abstract

    public abstract void paint(Graphics g);

    public abstract void update(int dt);

    public int GetX() {
        return this.x;
    }

    public int GetY() {
        return this.y;
    }

    public abstract void setVitesseX(double vx);

    public abstract void setVitesseY(double vy);

    private LinkedList<ObjetCeleste> objets;
}