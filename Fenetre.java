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
    private Terrain world;
    private boolean echap = false;

    public Fenetre(String nom) throws AWTException{
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

        //ajout des listeners
        this.addKeyListener(this);

        //affichage de la fenetre et arret a la fermeture
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println("Pointeur libéré");
        if (code == KeyEvent.VK_ESCAPE ){
            world.echap = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}