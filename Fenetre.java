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

        this.setSize(1000, 700);

        //Jpanel principal (content pane)
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.black);

        //création du JPanel Space a la taille de la fenetre
        space = new Space(this.getInsets().left , this.getInsets().top, this.getWidth() - this.getInsets().left - this.getInsets().right, this.getHeight() - this.getInsets().top - this.getInsets().bottom);
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