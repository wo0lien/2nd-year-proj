import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

/**
 * ObjetCeleste Premiere instance abstract du composant de base objet celeste
 */
public abstract class ObjetCeleste {

    //
    public double masse, vx, vy;
    public int r;
    protected int x, y;
    protected Image disp;
    protected int temp;
    private HUD hud;

    public ObjetCeleste(double m, double vitx, double vity, int ax, int ay, Image i, int rayon, HUD hud) {
        masse = m;
        x = ax;
        y = ay;
        disp = i;
        vx = vitx;
        vy = vity;
        r = rayon;
        this.hud=hud;


        //bug : la linked list n'est pas dans le constructeur on ne peut pas y avoir acces depuis ici il faut faire en sort de créer la methode update dans space.java
        //pour avoir acces a tous les objets, voir aussi au niveau du foreach plus simple et propre pour parcourir une linked list
        /*
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
        } */
    }

    // déclatation des méthodes abstract

    public abstract void paint(Graphics g);

    public abstract void update(int dt);
    
    public abstract void resize();

    public int GetX() {
        return this.x;
    }

    public int GetY() {
        return this.y;
    }

    public double GetMasse(){
        return this.masse;
    }

    public HUD getHUD() {
        return hud;
    }

    public abstract void setVitesseX(double vx);

    public abstract void setVitesseY(double vy);

    private LinkedList<ObjetCeleste> objets;
}