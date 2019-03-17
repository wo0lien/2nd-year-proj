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
    private JButton newPlanet, timerButton;
    private JTextArea timeCount;
    private Space space;

    //timer
    Timer t;
    private int dt = 40;

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

        timerButton = new JButton("Start animation");
        timerButton.setBounds(250, 20, 200, 60);

        timeCount = new JTextArea("Jours : " + space.getTemps());
        timeCount.setBounds(480,40,200,60);

        footerPanel.add(newPlanet);
        footerPanel.add(timerButton);
        footerPanel.add(timeCount);

        mainPanel.add(footerPanel);

        //ajout du panel à la fenetre principale
        this.setContentPane(mainPanel);

        //ajout des listeners
        this.addKeyListener(this);
        newPlanet.addActionListener(this);
        timerButton.addActionListener(this);

        //affichage de la fenetre et arret a la fermeture
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //consutrction du timer
        t = new Timer(dt, this);
        t.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //reaction aux clics sur les boutons
        if (e.getSource() == newPlanet) {
            space.NewPlanet();
        } else if (e.getSource() == timerButton){
            space.TimerButton();
            if (timerButton.getText() == "Start animation") {
                timerButton.setText("Stop animation");
            } else {
                timerButton.setText("Start animation");
            }
        } else if (e.getSource()==t) {
            timeCount.setText("Jours : " + space.getTemps());
            space.timerPerformed();
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