import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;


public class HUD extends JPanel implements MouseListener,MouseMotionListener, KeyListener {

	private static final int NB_MOYENNE = 1000;

    String objectName;
    String typeObj;
    boolean changerName = false;
    String newName;
    private Image changeName;
    private boolean mouseOnButton = false;
    private int mouseX,mouseY;
    private boolean selected=false;
    private float[] atome;
    private float masse;
    private double distanceSoleil;

    private double moyTemp, minTemp, maxTemp, deltaTemp;
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

        String[] c = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z" };

        objectName = c[(int) (Math.random() * 26)].toUpperCase() + c[(int) (Math.random() * 26)].toUpperCase()
                + "-" + ((int) (Math.random() * 9999)) + "-" + c[(int) (Math.random() * 26)].toUpperCase()
                + c[(int) (Math.random() * 26)].toUpperCase();

        this.setBounds(x, y, ax, ay);
        this.setBackground(Color.black);
        typeObj=name;

        atome = new float[4];

        try {
            File pathToChangeName = new File("iconName.png"); 
            //transformation en objet image des fichier    
            changeName = ImageIO.read(pathToChangeName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        changeName=changeName.getScaledInstance(18,18,Image.SCALE_SMOOTH);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        addKeyListener(this);
        this.addKeyListener(this);

        h = this.getHeight();
        w = this.getWidth();

        // repaint();

    }
    public void buttonPerformed() {
        objectName=newName;
        changerName=!changerName;
        newName="";
        repaint();
    }
    public void keyPressed(KeyEvent e) { 
        int code = e.getKeyCode();
        System.out.print("Code clavier "+code+" appuye. ");
        if (code == KeyEvent.VK_SPACE) {
            System.out.print("C’est la barre d’espace. ");
        } else if (code == KeyEvent.VK_DOWN) {
            System.out.print("C’est la fleche du bas. ");
        } else {
        System.out.print("Ce n’est ni espace, ni bas. ");
        }
        System.out.println("keyTyped : " + e.getKeyCode());
}
        public void keyReleased(KeyEvent e) {

     }
       // méthode exécutée à chaque fois qu’une touche unicode est utilisée (donc pas CTRL, SHIFT ou ALT par exemple)
    public void keyTyped(KeyEvent e) { 
        System.out.println("keyTyped");
        if (changerName) {
            System.out.println(e.getKeyChar());
            newName+=e.getKeyChar();
            objectName=newName;
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        if (selected) {
            //fond noir
            g.setColor(Color.black);
            g.fillRect(0, 0, w, h);

            // rectangle du contour
            g.setColor(Color.white);
            g.drawRect(20, 20, w - 40, h - 40);
            Font F = new Font("F", 1, 20);
            g.setFont(F);
            g.drawString("Nom de " + typeObj + " : ", 40, 100);
            g.drawString(objectName, 40, 130);
            g.drawImage(changeName,220,112,null);
            if (mouseOnButton) {
                g.drawRect(218, 110, 22, 22);
            }
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
                g.drawString("Azote         : " + (int)(atome[0]/masse*100) + "%", 40,480 );
                g.drawString("Carbone     : " + (int)(atome[1]/masse*100) + "%", 40,520 );
                g.drawString("Oxygène     : " + (int)(atome[2]/masse*100) + "%", 40,560 );
                g.drawString("Hydrogène : " + (int)(atome[3]/masse*100) + "%", 40,600 );
            }
        }
    }
    public void mouseDragged(MouseEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
        if (mouseOnButton) {
            changerName=!changerName;
            repaint();
        }
    }
    public void mouseMoved(MouseEvent e) {
        mouseX=e.getX();
        mouseY=e.getY();
        if (mouseX>=220 && mouseX<= 238 && mouseY>=112 && mouseY<=130 && selected==true) {
            mouseOnButton=true;
            repaint();
        } else {
            mouseOnButton=false;
            //repaint();
        }
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void initializeTemp(double a){
        temps.add(a);
    }
    public void setTemp(double a) {
        temps.add(a);
    }
    public double getTemp() {
        return temps.getLast();
    }
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
    public void addTemp(double t) {
        temps.add(t);
    }
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