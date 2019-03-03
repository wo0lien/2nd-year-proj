import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.AWTException;

/**
 * Fenetre
 */
public class Fenetre extends JFrame implements KeyListener{

    //variables

    private JPanel mainPanel;
    private Space space;

    public Fenetre(String nom) throws AWTException{
        super(nom);

        this.setSize(800, 800);

        //Jpanel principal (content pane)
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.black);

        //attention il faudrait ici des valeurs en fonction de la taille de la fenetre et des insets()
        space = new Space(50, 50, 700, 700);

        mainPanel.add(space);

        //ajout du panel à la fenetre principale
        this.setContentPane(mainPanel);

        //ajout des listeners
        this.addKeyListener(this);

        //affichage de la fenetre et arret a la fermeture
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //keyEvents pour la suite
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}