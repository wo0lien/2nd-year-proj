
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ChoixAtome extends JFrame implements ActionListener {

	//constantes
	private static final int h = 450;
    private static final int w = 300;
    
    private JPanel pan;
    private JButton Azote, Carbone, Hydrogene, Oxygene;
    private JLabel Choix;
    boolean atome[] = new boolean [4];
    private JFrame fen;
    private boolean enCours=true;

    private Dimension screenSize;

    public ChoixAtome (String ChoixComposition, Space s, int ax, int ay){
        super(ChoixComposition);

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //on fait attention a ne pas sortir de l'écran

        if ((int)screenSize.getWidth() <= w + ax) {
            ax = (int)screenSize.getWidth() - w;
        }

        if ((int)screenSize.getHeight() <= h + ay) {
            ay = (int)screenSize.getHeight() - h;
        }

        //Jpanel principal (content pane)
        fen = new JFrame ();
       // fen.setLocationRelativeTo(null);
        fen.setBounds(ax, ay, w, h);
        pan = new JPanel();
        pan.setSize(w, h);
        pan.setLayout(null);
        pan.setBackground(Color.gray);

        //utilisation du html pour centrer et mettre à la ligne

        Choix = new JLabel ("<html><div style=\"text-align: center;\">Choisis de quoi ta planète sera <br> constituée !</div></html>");
        Choix.setBounds(25, 25, 250, 50);

        Azote = new JButton("Azote");
        Azote.setBounds(50, 100, 200, 50);
        Azote.addActionListener(this);

        Carbone = new JButton("Carbone");
        Carbone.setBounds(50, 175, 200, 50);
        Carbone.addActionListener(this);

        Hydrogene = new JButton("Hydrogene");
        Hydrogene.setBounds(50, 250, 200, 50);
        Hydrogene.addActionListener(this);

        Oxygene = new JButton("Oxygene");
        Oxygene.setBounds(50, 325, 200, 50);
        Oxygene.addActionListener(this);

        pan.add(Oxygene);
        pan.add(Azote);
        pan.add(Carbone);
        pan.add(Hydrogene);
        pan.add(Choix);
        fen.add(pan);

       fen.setContentPane(pan);
      
        fen.setVisible(true);
    }
    
    @Override
    public void actionPerformed (ActionEvent e) {
        //reaction aux clics sur les boutons
        if (e.getSource()== Azote) {
            atome [0] = true;
            end();
        }
        else if (e.getSource() == Carbone) {
            atome [1] = true;
            end();
        }
        else if (e.getSource()== Oxygene) {
            atome [2] = true;
            end();
        }
        else if (e.getSource() == Hydrogene){
            atome [3] = true;
            end();
        }
    }  
    
    public boolean[] getAtome () {
        return atome;
    }
    public void end() {
        enCours=false;
        fen.dispose();
    }
    public boolean getEnCours() {
        return enCours;
    }
}
