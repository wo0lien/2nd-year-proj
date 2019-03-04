import java.awt.Color;
import java.awt.Graphics;

/**
 * Planete
 */
public class Planete extends ObjetCeleste{

    private Soleil soleil;
    private double rayon;

    public Planete(double m, double v, int ax, int ay, Soleil s) {
        super(m, v, ay, ax); //on appelle le constructeur de la class parente ObjCelestes
        soleil = s; //définition du soleil comme référence

        //calcul du rayon orbital
        rayon = Math.pow(Math.pow(soleil.GetX() - this.x, 2) + Math.pow(soleil.GetY() - this.y, 2), 0.5);

        System.out.println("rayon : "+ rayon );
    }

    public void paint(Graphics g, double t) {
        
        //afficahge de trajectoire

        g.setColor(Color.red);
        g.drawOval(soleil.x - (int)rayon, soleil.y - (int)rayon, (int)rayon * 2, (int)rayon * 2);
        
        //calcul de la position de la planete

        this.x = soleil.GetX() + (int)(rayon * Math.cos(t * vAng));
        this.y = soleil.GetY() + (int)(rayon * Math.sin(t * vAng));

        g.setColor(Color.green );
        g.fillOval(this.x - 15, this.y - 15, 30, 30);
    }
}