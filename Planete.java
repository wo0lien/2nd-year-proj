import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Planete
 */
public class Planete extends ObjetCeleste{

    public Planete(double m, double vx, double vy, double ax, double ay, Image i, int rayon, HUD hud, float[] a) {
        super(m, vx, vy, ax, ay, i,rayon,hud, a); //on appelle le constructeur de la class parente ObjCelestes
        
        try {
            File pathToPlanet = new File("earth.png"); 
            //transformation en objet image des fichier    
            imageObjet = ImageIO.read(pathToPlanet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        
        //affichage de trajectoire

        g.setColor(Color.RED);
        g.drawImage(disp, (int)this.xZ - disp.getWidth(null) / 2, (int)this.yZ - disp.getHeight(null) / 2, null);
        //enlever les commentaires pour débugger : affiche les coordonnées réelles et les coordonnées affichées
        //g.drawString("x " + x + " y " + y,xZ,yZ);
        //g.drawString("xZ " + xZ + " yZ " + yZ,xZ,yZ+15);
    }

    @Override
    public void setVitesseX(double vitx) {
        vx = vitx;
    }

    @Override
    public void setVitesseY(double vity) {
        vy = vity;
    }

    //update les coordonnées de l'objet à l'instant t+1
    @Override
    public void update(int dt) {
        this.x += dt * vx / 1000;
        this.y += dt * vy / 1000;
    }

    @Override
    public void resize(){
        disp = imageObjet.getScaledInstance(2*rZoom,2*rZoom, Image.SCALE_SMOOTH);
    }

    public String getType() {
        return "planet";
    }
}