import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Planete
 */
public class Planete extends ObjetCeleste{

    public Planete(double m, double vx, double vy, int ax, int ay, Image i) {
        super(m, vx, vy, ax, ay, i); //on appelle le constructeur de la class parente ObjCelestes
    }

    public void paint(Graphics g) {
        
        //afficahge de trajectoire

        g.setColor(Color.red);
        g.drawImage(disp, this.x - disp.getWidth(null) / 2, this.y - disp.getHeight(null) / 2, null);
    }

    @Override
    public void setVitesseX(double vx) {
        dx = vx;
    }

    @Override
    public void setVitesseY(double vy) {
        dy = vy;
    }

    @Override
    public void update(int dt) {
        this.x += dt * dx / 1000;
        this.y += dt * dy / 1000;
    }
}