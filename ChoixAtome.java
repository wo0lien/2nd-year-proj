
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.AWTException;


public class Fenetre extends JFrame implements ActionListener, KeyListener {

   private JPanel pan;
private JButton Azote, Carbone, Hydrogene, Oxygene;
private JLabel Choix;

public Fenetre(String nom) throws AWTException{
    super("ChoixComposition");

    this.setSize(1400, 850);

    //Jpanel principal (content pane)
    pan = new JPanel();
    pan.setLayout(null);
    pan.setBackground(Color.gray);

    Azote = new JButton("Azote");
    Azote.setBounds(75, 395, 200, 60);
    Azote.addActionListener(this);


    Carbone = new JButton("Carbone");
    Carbone.setBounds(350, 395, 200, 60);
    Carbone.addActionListener(this);

    Hydrogene = new JButton("Hydrogene");
    Hydrogene.setBounds(700, 395, 200, 60);
    Hydrogene.addActionListener(this);

    Oxygene = new JButton("Oxygene");
    Oxygene.setBounds(1050, 395, 200, 60);
    Oxygene.addActionListener(this);

    Choix = new JLabel ("Choisis de quoi ta planète sera constituée !");
    Choix.setBounds(600, 600, 200, 50);

    public void actionPerformed(ActionEvent e) {
        //reaction aux clics sur les boutons
        switch (e.getSource()) {
            case Carbone :

            break;

            case Hydrogene :
            
            break;

            case Oxygene :
            
            break;
            
            case Azote: 
            break;
        }
    }
}
}
