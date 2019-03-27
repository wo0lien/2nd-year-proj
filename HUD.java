
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HUD extends JPanel {

    //variables Louise

    private JPanel Contour;
    private JLabel Titre; 
    private JLabel Temp ;
    private JLabel DTemp;
    private JLabel Chimie;
    private JLabel Criteres;

    // taille de la fenetre

    private int h,w;
    
    public HUD(int x, int y, int ax, int ay){
        
        this.setBounds(x, y, ax, ay);
        this.setBackground(Color.black);
        //this.setLayout(null);

        h = this.getHeight();
        w = this.getWidth();

        repaint();

        /*
        Titre = new JLabel();
        Titre.setBounds(2,2,6,4);
        Titre.setText("Nom de la planète");
        Titre.setBackground(Color.YELLOW);
        Temp = new JLabel();
        Temp.setBounds(1,8,5,2);
        Temp.setText("Température moyenne : ");
        //ajouter les cases avec les températures moyennes min et max
        DTemp = new JLabel();
        DTemp.setBounds(1,12,5,2);
        DTemp.setText("Températures min et max : ");
        Chimie = new JLabel();
        Chimie.setBounds(1,16,5,2);
        Chimie.setText("Eléments chimques présents : ");
        Contour.setVisible(true);*/

    }
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);

        //rectangle du contour
        g.drawRect(20, 20, w-40,h-40);
        Font F = new Font( "F" , 1, 20);
        g.setFont(F);
        g.drawString("Nom de la Planète : ", 40, 100);
        String[] c = {"a", "b", "c", "d", "e", "f" ,"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        String planetName = c[(int)(Math.random()*26)].toUpperCase() + c[(int)(Math.random()*26)].toUpperCase() + "-" + ((int)(Math.random()*9999)) + "-" + c[(int)(Math.random()*26)].toUpperCase() + c[(int)(Math.random()*26)].toUpperCase();
        g.drawString(planetName, 40, 130);
    }

}