import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Planete
 */
public class Planete extends ObjetCeleste{

    public Planete(double m, double vx, double vy, int ax, int ay, Image i, int rayon, HUD hud) {
        super(m, vx, vy, ax, ay, i,rayon,hud); //on appelle le constructeur de la class parente ObjCelestes
    }

    public void paint(Graphics g) {
        
        //afficahge de trajectoire

        g.setColor(Color.red);
        g.drawImage(disp, this.x - disp.getWidth(null) / 2, this.y - disp.getHeight(null) / 2, null);
    }

    @Override
    public void setVitesseX(double vitx) {
        vx = vitx;
    }

    @Override
    public void setVitesseY(double vity) {
        vy = vity;
    }

    @Override
    public void update(int dt) {
        this.x += dt * vx / 1000;
        this.y += dt * vy / 1000;
    }

    @Override
    public void resize(){
        disp = disp.getScaledInstance(r * 2, r * 2, Image.SCALE_FAST);
    }
}