import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * Space
 */
public class Space extends JPanel implements ActionListener {

    //objets
    private Soleil s;
    private Planete p;

    //const
    private static final int GREYRATIO = 200;
    private static final Color backgroundColor = new Color(GREYRATIO, GREYRATIO, GREYRATIO);

    Timer t;
    private int dt = 40, temps = 0;

    //Image pour l'affichage sans scintillements
    private BufferedImage monBuf; // buffer d’affichage

    public Space(int xPos, int yPos, int x, int y) {

        System.out.println("Create the universe");
	
        this.setLayout(null);
        this.setBounds(xPos, yPos, x, y);

        t = new Timer(dt, this);
        t.start();

        //objets

        s = new Soleil(100, 300, 300);
        p = new Planete(100, 0.005, 450, 300, s);

        Dimension dim = getSize();
        //buffered image
        monBuf = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        temps += dt;
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

        //background painting (pas forcement très opti)
        g.setColor(backgroundColor);    
        g.fillRect(0, 0, this.getWidth(), this.getHeight() );

        //paint objets
        s.paint(g, temps);
        p.paint(g, temps);

    }

}