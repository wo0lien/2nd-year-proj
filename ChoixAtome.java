
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.AWTException;


public class Choix extends JFrame implements ActionListener, KeyListener {

   private JPanel pan;
private JButton Azote, Carbone, Hydrogene, Oxygene;
private JLabel Choix;

public Choix (String ChoixComposition) throws AWTException{
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

    pan.add(Oxygene);
    pan.add(Azote);
    pan.add(Carbone);
    pan.add(Hydrogene);
    pan.add(Choix);

    public void actionPerformed (ActionEvent e) {
        //reaction aux clics sur les boutons
        if (e.getSource()== Oxygene) {

        }
        else if (e.getSource() == Azote) {

        }
        else if (e.getSource()== Carbone) {

        }
        else if (e.getSource() == Hydrogene){

        }
    }
}
}