import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Fenetre
 */
public class Fenetre extends JFrame {

    //variables

    private JPanel mainPanel;
    private Terrain world;

    public Fenetre(String nom) {
        super(nom);

        this.setSize(800, 800);

        world = new Terrain(100, 50, 600, 400);

        //Jpanel principal (content pane)
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.black);
        mainPanel.add(world);
        //ajout du panel à la fenetre principale
        this.setContentPane(mainPanel);

        //affichage de la fenetre et arret a la fermeture
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}