
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HUD extends JPanel {
    JPanel Contour;
    JLabel Titre; 
    JLabel Temp ;
    JLabel DTemp;
    JLabel Chimie;
    JLabel Critères;
    public HUD(){
        Contour= new JPanel() ;
        Contour.setBounds(20,5,10,20);
        Contour.setBackground(Color.BLACK);
        Titre = new JLabel();
        Titre.setBounds(2,2,6,4);
        Titre.setText("Nom de la planète");
        Titre.setBackground(Color.YELLOW);
        Temp = new JLabel();
        Temp.setBounds(1,8,5,2);
        Temp.setText("Température moyenne : ");
        //ajouter les cases avec les températures moyennes min et max
        DTemp = new JLabel();
        DTemp.setBounds(1,12,5,2);
        DTemp.setText("Températures min et max : ");
        Chimie = new JLabel();
        Chimie.setBounds(1,16,5,2);
        Chimie.setText("Eléments chimques présents : ");
        Contour.setVisible(true);

    }

}