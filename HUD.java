import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import javax.swing.JPanel;

public class HUD extends JPanel {
    String objectName;
    String typeObj;

    //variable pour n'afficher qu'un seul HUD
    private boolean selected=false;

    //tableau de masse par élément
    private float[] atome;

    //masse totale
    private float masse;
    private double distanceSoleil;

    //variables liées aux temperatures
    private double moyTemp, minTemp, maxTemp, deltaTemp;

    //retiens les 1000 dernières températures pour les calculs
    private LinkedList<Double> temps;

    // taille de la fenetre
    private int h, w;

    public HUD() {
        h = this.getHeight();
        w = this.getWidth();
    }

    public HUD(int x, int y, int ax, int ay, String name) {
        //initialisation variable temp
        temps = new LinkedList<Double>();

        //création d'un nom aléatoire
        String[] c = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z" };

        objectName = c[(int) (Math.random() * 26)].toUpperCase() + c[(int) (Math.random() * 26)].toUpperCase()
                + "-" + ((int) (Math.random() * 9999)) + "-" + c[(int) (Math.random() * 26)].toUpperCase()
                + c[(int) (Math.random() * 26)].toUpperCase();

        //bounds et background
        this.setBounds(x, y, ax, ay);
        this.setBackground(Color.black);
        h = this.getHeight();
        w = this.getWidth();

        typeObj=name;

        atome = new float[4];
    }

    @Override
    public void paint(Graphics g) {
        if (selected) { //selected pour éviter de paint plusieurs hud
            //fond noir
            g.setColor(Color.black);
            g.fillRect(0, 0, w, h);

            // rectangle du contour
            g.setColor(Color.white);
            g.drawRect(20, 20, w - 40, h - 40);

            //nom de l'objet
            Font F = new Font("F", 1, 20);
            g.setFont(F);
            g.drawString("Nom de " + typeObj + " : ", 40, 100);
            g.drawString(objectName, 40, 130);

            //informations supplémentaires pour les planètes
            if (typeObj.equals("la planète")) {
                g.drawString("Températures :", 40, 280);
                g.drawString("Elements Chimiques :", 40, 440);
                Font G = new Font("G", 1, 15);
                g.setFont(G);
                g.drawString("Distance au Soleil : ", 40, 200);
                g.drawString((int)(distanceSoleil) + " x10^6 km", 200, 200);
                g.drawString("actuelle :", 50,320 );
                g.drawString("moyenne :", 50,360 );
                g.drawString("delta :", 50,400 );
                g.setColor(Color.GREEN);
                g.drawString((int)getTemp() + " K", 200,320 );
                g.setColor(Color.LIGHT_GRAY);
                g.drawString((int)moyTemp + " K", 200,360 );
                g.setColor(Color.RED);
                g.drawString((int)deltaTemp + " K", 200,400 );
                g.setColor(Color.WHITE);

                //pourcentage de chaque élément
                g.drawString("Azote         : " + (int)(atome[0]/masse*100) + "%", 40,480 );
                g.drawString("Carbone     : " + (int)(atome[1]/masse*100) + "%", 40,520 );
                g.drawString("Oxygène     : " + (int)(atome[2]/masse*100) + "%", 40,560 );
                g.drawString("Hydrogène : " + (int)(atome[3]/masse*100) + "%", 40,600 );
            }
        }
    }
    public void initializeTemp(double a){
        temps.add(a);
    }
    public void setTemp(double a) {
        temps.add(a);
    }
    
    //retourne la dernière température
    public double getTemp() {
        return temps.getLast();
    }

    //pour changer la masse totale de chaque élément
    public void setAtome(float[] atomeMasses) {
        for (int i=0;i<4;i++) {
            atome[i]=atomeMasses[i];
        }
    }
    public void setSelected(boolean a) {
        selected=a;
    }
    public void setMasse(float a) {
        masse=a;
    }

    //ajoute la température actuelle à la liste
    public void addTemp(double t) {
        temps.add(t);
    }
    
    //calcule les valeurs des différentes variables liées à la température
    public void updateTemp() {
        while (temps.size()>1000) {
            temps.removeFirst();
        }
        maxTemp=temps.getFirst();
        minTemp=temps.getFirst();
        moyTemp=0;
        int i=0;
        for (double t : temps) {
            if (t>maxTemp) {
                maxTemp=t;
            }
            if (t<minTemp) {
                minTemp=t;
            }
            moyTemp+=t;
            i++;
        }
        moyTemp/=i;
        deltaTemp=maxTemp-minTemp;
    }
    
    public void setDistance(double d) {
        distanceSoleil=d;
    }
}