import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Planete
 */
public class Planete extends ObjetCeleste{

    public Planete(double m, double v, int ax, int ay, Image i) {
        super(m, v, ax, ay, i); //on appelle le constructeur de la class parente ObjCelestes
    }

    public void paint(Graphics g, double t) {
        
        //afficahge de trajectoire

        g.setColor(Color.red);
        g.drawImage(disp, this.x - disp.getWidth(null) / 2, this.y - disp.getHeight(null) / 2, null);
    }
}