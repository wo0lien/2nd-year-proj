import java.awt.Graphics;

/**
 * ObjetCeleste
 * Premiere instance abstract du composant de base objet celeste
 */
public abstract class ObjetCeleste {

    //
    protected double masse;
    protected int x, y;
    protected Vecteur vitesse;

    public ObjetCeleste(double m, double vx, double vy, int ax, int ay) {
        masse = m;
        x = ax;
        y = ay;
        vitesse = new Vecteur(vx, vy);
    }

    //déclatation des méthodes abstract

    public abstract void paint(Graphics g);
}