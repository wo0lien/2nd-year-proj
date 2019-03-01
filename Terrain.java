import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class Terrain extends JPanel implements ActionListener, MouseListener {

    //const
    private static final int GREYRATIO = 200;
    private static final Color backgroundColor = new Color(GREYRATIO, GREYRATIO, GREYRATIO);

    private static final Color rangeColor = new Color(255, 0, 0, 120);

    //variable

    private Mouton mout;
    private BufferedImage monBuf; // buffer d’affichage
    private Timer t;


    //gestion des déplacements de la map

    private int xOffset=0, yOffset=0, mouseX, mouseY;
    private Robot rob;
    public boolean echap;

    public Terrain(int xPos,int yPos, int x, int y) throws AWTException{
	
        this.setLayout(null);
        this.setBounds(xPos, yPos, x, y);

        //ajout des Listeners

        this.addMouseListener(this);

        //timer

        t = new Timer(10, this);

        //robot

        rob = new Robot();
        echap = true;

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
        //empty pour l'instant plus d'utilité au timer
    }

    @Override
    public void mouseExited(MouseEvent e) {

        // Pour empecher la souris de sortir et déplacer le terrain sous la souris
        if (!echap) {
            // en x
            if (e.getX() <= 0) {
                xOffset += Math.abs((double) e.getX());
                this.repaint();
                mouseX = 1 + this.getLocationOnScreen().x;
            } else if (e.getX() >= this.getWidth()) {
                xOffset += this.getWidth() - Math.abs((double) e.getX());
                this.repaint();
                mouseX = this.getWidth() + this.getLocationOnScreen().x - 1;
            } else {
                mouseX = e.getX() + this.getLocationOnScreen().x;
            }
            // en y
            if (e.getY() <= 0) {
                yOffset += Math.abs((double) e.getY());
                this.repaint();
                mouseY = this.getLocationOnScreen().y + 1;
            } else if (e.getY() >= this.getHeight()) {
                yOffset += this.getHeight() - Math.abs((double) e.getY());
                this.repaint();
                mouseY = this.getLocationOnScreen().y + this.getHeight() - 1;
            } else {
                mouseY = this.getLocationOnScreen().y + e.getY();
            }

            //déplacement de la souris 

            rob.mouseMove(mouseX, mouseY);
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
        echap = false;
    }

}