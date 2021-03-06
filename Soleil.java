import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Soleil
 */
public class Soleil extends ObjetCeleste {

    public Soleil(double m, double ax, double ay, Image i, int rayon, HUD hud) {
        super(m, 0, 0, ax, ay, i, rayon, hud, new float[4]); //un soleil est une étoile fixe avec vitesse = 0
        
        hud.initializeTemp(1000);

        try {
            File pathToSun = new File("sun.png"); 
            //transformation en objet image des fichier    
            imageObjet = ImageIO.read(pathToSun);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g){
        g.drawImage(disp, (int)this.xZ - disp.getWidth(null) / 2, (int)this.yZ - disp.getHeight(null) / 2, null);
    }

    @Override
    public void setVitesseX(double vx) {}

    @Override
    public void setVitesseY(double vy) {}

    @Override
    public void update(int dt) {}

    @Override
    public void resize(){
        disp = imageObjet.getScaledInstance(2*rZoom,2*rZoom, Image.SCALE_SMOOTH);
    }


    public String getType() {
        return "sun";
    }
}