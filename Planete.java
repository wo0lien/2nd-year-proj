import java.awt.Graphics;

/**
 * Planete
 */
public class Planete extends ObjetCeleste{

    public Planete(double m, double vx, double vy, int ax, int ay) {
        super(m, vx, vy, ay, ax); //on appelle le constructeur de la class parente ObjCelestes
    }

    public void paint(Graphics g) {
        
    }
}