import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
/**
 * Space
 */
public class Space extends JPanel {

    //const
    private static final int GREYRATIO = 200;
    private static final Color backgroundColor = new Color(GREYRATIO, GREYRATIO, GREYRATIO);

    //Image pour l'affichage sans scintillements

    private BufferedImage monBuf; // buffer d’affichage

    public Space(int xPos, int yPos, int x, int y) {
	
        this.setLayout(null);
        this.setBounds(xPos, yPos, x, y);

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

        //background painting (pas forcement très opti)
        g.setColor(backgroundColor);    
        g.fillRect(0, 0, this.getWidth(), this.getHeight() );

    }

}