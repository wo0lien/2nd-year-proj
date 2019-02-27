import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Terrain
 */
public class Terrain extends JPanel {

    //const

    private static final Color backgroundColor = new Color(50, 50, 50);

    //Objects des especes

    public Terrain(int xPos,int yPos, int x, int y){
	
		this.setLayout(null);
        this.setBounds(xPos, yPos, x, y);
        // this.setBackground(Color.black);
        //utilisation de la librairie graphics pour les graphiques du plateau
        repaint();
    }
    
    @Override
    public void paint(Graphics g) {

        //background painting
        g.setColor(backgroundColor);
        g.fillRect(0, 0, 600, 400);
    }
}