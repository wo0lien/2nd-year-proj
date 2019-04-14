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
    protected double x, y;
    protected Image disp;
    protected Image imageObjet;
    protected HUD hud;
    protected double zoomCoeff;
    protected int rZoom;
    protected double xZ,yZ;
    public boolean atome[] = new boolean [4];


    public ObjetCeleste(double m, double vitx, double vity, double ax, double ay, Image i, int rayon, HUD hud, boolean [] a) {
        
        masse = m;
        x = ax;
        y = ay;
        xZ=x;
        yZ=y;
        disp = i;
        vx = vitx;
        vy = vity;
        r = rayon;
        zoomCoeff=1;
        rZoom=r;
        this.hud=hud;
        for (int j =0 ; j<a.length ; j++ ) {
            atome[j] = a[j];
        }

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

    //accesseurs

    public double GetX() {
        return this.x;
    }

    public double GetY() {
        return this.y;
    }

    public double GetMasse(){
        return this.masse;
    }

    public double GetVx() {
        return vx;
    }

    public double GetVy() {
        return vy;
    }

    public int GetR() {
        return r;
    }

    public double GetTemp() {
        return hud.GetTemp();
    }

    public HUD getHUD() {
        return hud;
    }

    public boolean[] getatome() {
        return atome;
    }
    public void zoomUpdate(double zoom,double xOffset, double yOffset) {
        xZ = (int)(-(xOffset-x)*zoom);
        yZ = (int)(-(yOffset-y)*zoom);
        rZoom=(int)(r*zoom);
    }
    public abstract void setVitesseX(double vx);

    public abstract void setVitesseY(double vy);

    public String getType() {
        return "";
    }

    public void InitializeTemp(double t){
        hud.InitializeTemp(t);
    }

    public void SetTemp(double t) {
        hud.SetTemp(t);
    }
}