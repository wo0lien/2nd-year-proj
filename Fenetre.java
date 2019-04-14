import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.AWTException;

/**
 * Fenetre
 */
public class Fenetre extends JFrame implements ActionListener,KeyListener {

    //variables

    private JPanel mainPanel, footerPanel, hud;
    private JButton newPlanet, timerButton;
    private JTextArea timeCountJours, timeCountYears;
    private Space space;

    //timer
    Timer t;
    private int dt = 40;

    public Fenetre(String nom) throws AWTException{
        super(nom);

        this.setSize(1400, 850);

        //Jpanel principal (content pane)
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.black);

        //création du JPanel Space a la taille de la fenetre
        space = new Space(this.getInsets().left , this.getInsets().top, this.getWidth() - this.getInsets().left - this.getInsets().right - 400, this.getHeight() - this.getInsets().top - this.getInsets().bottom - 150,this.getWidth() - this.getInsets().left - this.getInsets().right - 400, this.getInsets().top, 400, this.getHeight() - this.getInsets().top - this.getInsets().bottom - 150);
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

        timeCountJours = new JTextArea("Jours : " + space.getTempsJours());
        timeCountJours.setBounds(480,40,200,60);

        timeCountYears = new JTextArea("Années : " + space.getTempsAnnées());
        timeCountYears.setBounds(480,60,200,60);

        footerPanel.add(newPlanet);
        footerPanel.add(timerButton);
        footerPanel.add(timeCountJours);
        footerPanel.add(timeCountYears);

        mainPanel.add(footerPanel);

        //side panel

        hud = new HUD();
        mainPanel.add(hud);

        //ajout du panel à la fenetre principale
        this.setContentPane(mainPanel);

        //ajout des listeners
        newPlanet.addActionListener(this);
        timerButton.addActionListener(this);
        addKeyListener(this);

        //affichage de la fenetre et arret a la fermeture
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //consutrction du timer
        t = new Timer(dt, this);
        t.start();
    }
    public void changeAnimationState() {
        if (timerButton.getText() == "Start animation") {
            timerButton.setText("Stop animation");
        } else {
            timerButton.setText("Start animation");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //reaction aux clics sur les boutons
        if (e.getSource() == newPlanet) {
            space.NewPlanet();
            space.TimerButton(true);
        } else if (e.getSource() == timerButton){
            changeAnimationState();
            space.TimerButton();
            space.cancelPLanet();
        } else if (e.getSource()==t) {
            timeCountJours.setText("Jours : " + space.getTempsJours());
            timeCountYears.setText("Années : " + space.getTempsAnnées());
            space.timerPerformed();
            hud=space.getHUD();
            hud.repaint();
            mainPanel.add(hud);

        }
    }

    public void keyPressed(KeyEvent e) { 
        int code = e.getKeyCode();
        System.out.print("Code clavier "+code+" appuye. ");
        if (code == KeyEvent.VK_SPACE) {
            System.out.print("C’est la barre d’espace. ");
        } else if (code == KeyEvent.VK_DOWN) {
            System.out.print("C’est la fleche du bas. ");
        } else {
        System.out.print("Ce n’est ni espace, ni bas. ");
        }
        System.out.println("keyTyped : " + e.getKeyCode());
    }
    public void keyReleased(KeyEvent e) {

    }
       // méthode exécutée à chaque fois qu’une touche unicode est utilisée (donc pas CTRL, SHIFT ou ALT par exemple)
    public void keyTyped(KeyEvent e) { 
        System.out.println("keyTyped");
        /*if (changeName) {
            System.out.println(e.getKeyChar());
            newName+=e.getKeyChar();
            objectName=newName;
            repaint();
        }*/
    }
}