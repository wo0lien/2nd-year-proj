import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.security.auth.callback.NameCallback;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Terrain
 */
public class Terrain extends JPanel implements MouseMotionListener, MouseListener, ActionListener{

    //const
    private static final int GREYRATIO = 200;
    private static final Color backgroundColor = new Color(GREYRATIO, GREYRATIO, GREYRATIO);

    private static final Color rangeColor = new Color(255, 0, 0, 120);

    //variable

    private boolean b;

    private Mouton mout;

    private BufferedImage monBuf; // buffer d’affichage

    private Timer t;

    //gestion des déplacements de la map

    private int xOffset=0, yOffset=0, mouseX, mouseY;

    public Terrain(int xPos,int yPos, int x, int y){
	
        this.setLayout(null);
        this.setBounds(xPos, yPos, x, y);

        //ajout des Listeners
        
        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        //timer

        t = new Timer(10, this);

        // this.setBackground(Color.black);
        //utilisation de la librairie graphics pour les graphiques du plateau
        mout = new Mouton(200, 200);

        Dimension dim = getSize();
        //buffered image
        monBuf = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
        repaint();
    }
    
    /**
     * Methode d'affichage des elements visuels
     * @params graphics g
     */
    @Override
    public void paint(Graphics g) {

        Prepaint(monBuf.getGraphics());
        g.drawImage(monBuf, getInsets().left, getInsets().top, null);
        
    }

    /**
     * Méthode pour précharger l'affichage et éviter les scintillements
     */
    public void Prepaint(Graphics g){   

        //background painting
        g.setColor(backgroundColor);    
        g.fillRect(0, 0, 600, 400);

        //affichage des objets

        g.setColor(rangeColor);
        g.fillOval(xOffset + mout.x - (mout.GetRange() / 2), yOffset + mout.y - (mout.GetRange() / 2), mout.GetRange(), mout.GetRange());

        g.setColor(Color.blue);
        g.fillRect(xOffset + mout.x - (mout.width / 2),yOffset + mout.y - (mout.height / 2), mout.width, mout.height);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (b) {
            xOffset += mouseX;
            this.repaint();    
        } else {
            xOffset -= 600 - mouseX;
            this.repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        //détermination de la face par laquelle la souris est sortie
        if (mouseX < 20) {
            b = true;
            xOffset += mouseX;
            System.out.println("repaint now");
            t.start();
            this.repaint();
        } else if(mouseX > 580) {
            b = false;
            xOffset -= 600 - mouseX;
            System.out.println("repaint now");
            t.start();
            this.repaint();
        }
    }

    //déclaration des methodes abstract

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {
        t.stop();
    }
    @Override
    public void mouseDragged(MouseEvent e){}

    @Override
    public void mouseMoved(MouseEvent e){
        System.out.println(e.getX() +" : "+e.getY());
        mouseX = e.getX();
        mouseY = e.getY();
    }

}