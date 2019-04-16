import java.awt.Graphics;
import java.awt.Image;

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
    protected double volume;
    public float atome[] = new float [4];


    public ObjetCeleste(double m,double vitx, double vity, double ax, double ay, Image i, int rayon, HUD hud, float [] a) {
        masse=m;
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
        volume=4/3 * Math.PI * Math.pow(r,3);
        this.hud=hud;
        for (int j =0 ; j<a.length ; j++ ) {
            atome[j] = a[j];
        }
    }

    // déclatation des méthodes abstract

    public abstract void paint(Graphics g);

    public abstract void update(int dt);
    
    public abstract void resize();

    //accesseurs

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getMasse(){
        return this.masse;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public int getR() {
        return r;
    }

    public double getTemp() {
        return hud.getTemp();
    }

    public HUD getHUD() {
        return hud;
    }

    public float[] getAtome() {
        return atome;
    }

    // permet de déterminer des coordonnées sur l'écran en fonction du zoom et des coordonnées réelles
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

    //envoie les informations au HUD sur la température
    public void initializeTemp(double t){
        hud.initializeTemp(t);
    }

    public void setTemp(double t) {
        hud.setTemp(t);
    }

    //fait le calcul de la masse totale
    public void updateMasse() {
        masse=0;
        for(int i=0;i<atome.length;i++) {
            masse+=atome[i];
        }
        hud.setMasse((float)(masse));
    }
}