import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;


public class HUD extends JPanel implements MouseListener,MouseMotionListener, KeyListener {

	private static final int NB_MOYENNE = 1000;

    String objectName;
    String typeObj;
    private JTextField TF;
    boolean changerName = false;
    String newName;
    private Image changeName;
    private boolean mouseOnButton = false;
    private int mouseX,mouseY;

    private double temp, moyTemp, minTemp, maxTemp, deltaTemp;
    private LinkedList<Float> temps;

    // taille de la fenetre
    private int h, w;

    public HUD() {
        h = this.getHeight();
        w = this.getWidth();
    }

    public HUD(int x, int y, int ax, int ay, String name) {

        /*mainPanel=new JPanel();
        mainPanel.setBounds(20, 20, w - 40, h - 40);
        mainPanel.setVisible(true);*/

        //initialisation variable temp

        temp = 0;
        temps = new LinkedList<Float>();

        String[] c = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z" };

        objectName = c[(int) (Math.random() * 26)].toUpperCase() + c[(int) (Math.random() * 26)].toUpperCase()
                + "-" + ((int) (Math.random() * 9999)) + "-" + c[(int) (Math.random() * 26)].toUpperCase()
                + c[(int) (Math.random() * 26)].toUpperCase();

        this.setBounds(x, y, ax, ay);
        this.setBackground(Color.black);
        typeObj=name;

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

       /* temp = new JLabel();
        temp.setBounds(20, 140, 200, 25);
        temp.setLayout(null);
        this.add(temp);
        temp.setText("Températures : ");

        Dist = new JLabel("Distance au Soleil : ");
        Dist.setBounds(20, 100, 200, 25);
        Dist.setLayout(null);
        this.add(Dist);

        affDist = new JLabel("val d" + " km");
        affDist.setBounds(20, 20, 20, 50);
        affDist.setLayout(null);
        affDist.setBorder(BorderFactory.createLineBorder(Color.magenta, 2));
        this.add(affDist);

        tempMoy = new JLabel("moyenne :");
        tempMoy.setBounds(20, 170, 100, 50);
        tempMoy.setLayout(null);
        this.add(tempMoy);

        affMoy = new JLabel("val" + " °C", SwingConstants.CENTER);
        affMoy.setBounds(20, 210, 75, 25);
        affMoy.setLayout(null);
        affMoy.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        this.add(affMoy);

        tempMin = new JLabel("minimale :", SwingConstants.CENTER);
        tempMin.setBounds(w / 2 - 50, 170, 75, 50);
        tempMin.setLayout(null);
        this.add(tempMin);

        affMin = new JLabel("val" + " °C", SwingConstants.CENTER);
        affMin.setBounds(w / 2 - 50, 210, 75, 25);
        affMin.setLayout(null);
        affMin.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
        this.add(affMin);

        tempMax = new JLabel("maximale :");
        tempMax.setBounds(w - 100, 170, 100, 50);
        tempMax.setLayout(null);
        this.add(tempMax);

        affMax = new JLabel("val" + " °C", SwingConstants.CENTER);
        affMax.setBounds(w - 100, 210, 75, 25);
        affMax.setLayout(null);
        affMax.setBorder(BorderFactory.createLineBorder(Color.orange, 2));
        this.add(affMax); */

        // this.setLayout(null);

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
        g.drawString("mouseX " + mouseX + " mouseY " + mouseY,mouseX,mouseY);

        //fond noir
        g.setColor(Color.black);

        g.fillRect(0, 0, w, h);

        g.setColor(Color.white);
        // rectangle du contour
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
            g.drawString("val" + " km", 200, 200);
            g.drawString("actuelle :", 50,320 );
            g.drawString("moyenne :", 50,360 );
            g.drawString("delta :", 50,400 );
            g.setColor(Color.GREEN);
            g.drawString((int)temp + " °C", 200,320 );
            g.setColor(Color.LIGHT_GRAY);
            g.drawString((int)moyTemp + " °C", 200,360 );
            g.setColor(Color.RED);
            g.drawString((int)deltaTemp + " °C", 200,400 );
            g.setColor(Color.WHITE);
            g.drawString("Azote", 40,480 );
            g.drawString("Carbone", 40,520 );
            g.drawString("Oxygène", 40,560 );
            g.drawString("Hydrogène", 40,600 );
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
        if (mouseX>=220 && mouseX<= 238 && mouseY>=112 && mouseY<=130) {
            mouseOnButton=true;
        } else {
            mouseOnButton=false;
        }
        repaint();
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
        temp=a;
    }
    public void setTemp(double a) {
        temp=a;
    }
    public double getTemp() {
        return temp;
    }
}