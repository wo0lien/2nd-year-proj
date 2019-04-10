import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.File;
import javax.imageio.ImageIO;


public class HUD extends JPanel implements ActionListener, KeyListener {

    String objectName;
    String typeObj;
    private JButton changeName;
    private JPanel mainPanel;
    private JTextField TF;
    boolean changerName = false;
    String newName;


    //variables Louise

   /* private JPanel Contour;
    private JLabel Titre; 
    private JLabel Dist;
    private JLabel affDist;
    private JLabel temp ;
    private JLabel tempMoy;
    private JLabel tempMin;
    private JLabel tempMax;
    private JLabel  affMoy;
    private JLabel  affMin;
    private JLabel  affMax;*/

    // taille de la fenetre

    /**
     *
     */

    private static final int _200 = 200;
    private int h, w;

    public HUD() {
        h = this.getHeight();
        w = this.getWidth();
    }

    public HUD(int x, int y, int ax, int ay, String name) {
        mainPanel=new JPanel();
        mainPanel.setBounds(20, 20, w - 40, h - 40);
        mainPanel.setVisible(true);
        //ImageIcon iconName = ImageIcon.createImageIcon("iconChange.jpg");

        String[] c = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z" };

        objectName = c[(int) (Math.random() * 26)].toUpperCase() + c[(int) (Math.random() * 26)].toUpperCase()
                + "-" + ((int) (Math.random() * 9999)) + "-" + c[(int) (Math.random() * 26)].toUpperCase()
                + c[(int) (Math.random() * 26)].toUpperCase();

        this.setBounds(x, y, ax, ay);
        this.setBackground(Color.black);
        typeObj=name;

        /*changeName= new JButton(iconName);
        changeName.setBounds(150,100,180,130);
        changeName.setLayout(null);
        changeName.addActionListener(this);*/
        //mainPanel.add(changeName);

        //mainPanel.addKeyListener(this);
        //this.setContentPane(mainPanel);
        
        //TF=new JTextField(objectName);
        //TF.setBounds

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
    public void actionPerformed(ActionEvent e) {
        objectName=newName;
        changerName=!changerName;
        newName="";
        repaint();
    }
    public void keyPressed(KeyEvent e) { 

     }
    public void keyReleased(KeyEvent e) {

     }
       // méthode exécutée à chaque fois qu’une touche unicode est utilisée (donc pas CTRL, SHIFT ou ALT par exemple)
    public void keyTyped(KeyEvent e) { 
        System.out.println(e.getSource());
        System.out.println(e.getKeyCode());
        System.out.println(e.getKeyChar());
        newName+=e.getKeyChar();
        objectName=newName;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);

        // rectangle du contour
        g.drawRect(20, 20, w - 40, h - 40);
        Font F = new Font("F", 1, 20);
        g.setFont(F);
        g.drawString("Nom de " + typeObj + " : ", 40, 100);
        g.drawString(objectName, 40, 130);
        if (typeObj.equals("la planète")) {
            g.drawString("Températures :", 40, 280);
            g.drawString("Elements Chimiques :", 40, 440);
            Font G = new Font("G", 1, 15);
            g.setFont(G);
            g.drawString("Distance au Soleil : ", 40, _200);
            g.drawString("val" + " km", 200, _200);
            g.drawString("moyenne :", 50,320 );
            g.drawString("min :", 50,360 );
            g.drawString("max :", 50,400 );
            g.setColor(Color.GREEN);
            g.drawString("val" + " °C", 200,320 );
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("val" + " °C", 200,360 );
            g.setColor(Color.RED);
            g.drawString("val" + " °C", 200,400 );
            g.setColor(Color.WHITE);
            g.drawString("Azote", 40,480 );
            g.drawString("Carbone", 40,520 );
            g.drawString("Oxygène", 40,560 );
            g.drawString("Hydrogène", 40,600 );
        }
    }
}