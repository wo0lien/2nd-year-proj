import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Soleil
 */
public class Soleil extends ObjetCeleste {

    public Soleil(double m, int ax, int ay, Image i) {
        super(m, 0, 0, ax, ay, i); //un soleil est une étoile fixe avec vitesse = 0
    }

    public void paint(Graphics g){
        g.setColor(Color.white);
        g.fillOval(this.x - 15, this.y - 15, 30, 30);
    }

    @Override
    public void setVitesseX(double vx) {}

    @Override
    public void setVitesseY(double vy) {}

    @Override
    public void update(int dt) {}
}