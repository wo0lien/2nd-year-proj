import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.AWTException;

/**
 * Fenetre
 */
public class Fenetre extends JFrame implements ActionListener, KeyListener {

    //variables

    private JPanel mainPanel, footerPanel;
    private JButton newPlanet;
    private Space space;

    public Fenetre(String nom) throws AWTException{
        super(nom);

        this.setSize(1000, 850);

        //Jpanel principal (content pane)
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.black);

        //création du JPanel Space a la taille de la fenetre
        space = new Space(this.getInsets().left , this.getInsets().top, this.getWidth() - this.getInsets().left - this.getInsets().right, this.getHeight() - this.getInsets().top - this.getInsets().bottom - 150);
        mainPanel.add(space);

        //panel de bas de page

        footerPanel = new JPanel();
        footerPanel.setLayout(null);
        footerPanel.setBounds(this.getInsets().left, this.getHeight() - this.getInsets().top - this.getInsets().bottom - 150, this.getWidth() - this.getInsets().left - this.getInsets().right, 150);
        footerPanel.setBackground(Color.WHITE);

        newPlanet = new JButton("Nouvelle planete");
        newPlanet.setBounds(20, 20, 200, 60);

        footerPanel.add(newPlanet);

        mainPanel.add(footerPanel);

        //ajout du panel à la fenetre principale
        this.setContentPane(mainPanel);

        //ajout des listeners
        this.addKeyListener(this);
        newPlanet.addActionListener(this);

        //affichage de la fenetre et arret a la fermeture
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newPlanet) {
            //on lance la methode newPlanet
            space.NewPlanet();
        }
    }

    // keyEvents pour la suite
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

}