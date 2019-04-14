import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * Fenetre
 */
public class Fenetre extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
	//variables
	private JPanel mainPanel, footerPanel, hud;
    private JButton newPlanet, timerButton, exitButton;
    private JTextArea timeCountJours, timeCountYears;
    private Space space;

    //variable dimension pour récupérer la taille de l'écran en mode fullscreen
    private Dimension screenSize;

    //timer
    Timer t;
    private int dt = 40;

    public Fenetre(String nom) throws AWTException{
        super(nom);

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //set la taille de la fenetre
        this.setSize(1400, 850);
        
        //make it fullscreen
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setUndecorated(true);

        //Jpanel principal (content pane)
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.black);

        //création du JPanel Space a la taille de la fenetre 
        //space = new Space(this.getInsets().left , this.getInsets().top, this.getWidth() - this.getInsets().left - this.getInsets().right - 400, this.getHeight() - this.getInsets().top - this.getInsets().bottom - 150, this.getWidth() - this.getInsets().left - this.getInsets().right - 400, this.getInsets().top, 400, this.getHeight() - this.getInsets().top - this.getInsets().bottom - 150);
        
        //creation de space à la taille de l'écran 
        space = new Space(0, 0, (int)screenSize.getWidth() - 400, (int)screenSize.getHeight() - 150, (int)screenSize.getWidth() - 400, 0 , 400, (int)screenSize.getHeight() - 150);
        mainPanel.add(space);

        //panel de bas de page

        footerPanel = new JPanel();
        footerPanel.setLayout(null);
        //footerPanel.setBounds(this.getInsets().left, this.getHeight() - this.getInsets().top - this.getInsets().bottom - 150, this.getWidth() - this.getInsets().left - this.getInsets().right, 150);
        
        //la taille du footer panel dépend de la taille de l'écran
        footerPanel.setBounds(0, (int)screenSize.getHeight() - 150, (int)screenSize.getWidth(), 150);
        
        footerPanel.setBackground(Color.WHITE);

        //ajout des boutons dans la barre du bas

        newPlanet = new JButton("Nouvelle planete");
        newPlanet.setBounds(20, 20, 200, 60);

        timerButton = new JButton("Start animation");
        timerButton.setBounds(250, 20, 200, 60);

        exitButton = new JButton("Exit the game");
        exitButton.setBounds((int)screenSize.getWidth() - 250, 20, 200, 60);

        timeCountJours = new JTextArea("Jours : " + space.getTempsJours());
        timeCountJours.setBounds(480, 40,200,60);

        timeCountYears = new JTextArea("Années : " + space.getTempsAnnées());
        timeCountYears.setBounds(480, 60,200,60);
        
        footerPanel.add(newPlanet);
        footerPanel.add(timerButton);
        footerPanel.add(exitButton);
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
        exitButton.addActionListener(this);

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
            space.cancelPLanet();
            if (timerButton.getText() == "Start animation") {
                timerButton.setText("Stop animation");
            } else {
                timerButton.setText("Start animation");
            }
        } else if (e.getSource()==t) {
            
            //refresh le compteur de temps lorsque le déclencheur est le timer 

            timeCountJours.setText("Jours : " + space.getTempsJours());
            timeCountYears.setText("Années : " + space.getTempsAnnées());
            space.timerPerformed();
            hud=space.getHUD();
            hud.repaint();
            mainPanel.add(hud);
        
        } else if (e.getSource() == exitButton) {
            
            //ferme la fenetre et kill le programme
            
            this.dispose();
            System.exit(0);
        }
    }
}