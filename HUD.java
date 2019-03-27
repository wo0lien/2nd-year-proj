
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class HUD extends JPanel {

    String planetName;

    //variables Louise

   /* private JPanel Contour;
    private JLabel Titre; 
    private JLabel Dist;
    private JLabel affDist;
    private JLabel temp ;
    private JLabel tempMoy;
    private JLabel tempMin;
    private JLabel tempMax;
    private JLabel  affMoy;
    private JLabel  affMin;
    private JLabel  affMax;*/

    // taille de la fenetre

    /**
     *
     */

    private static final int _200 = 200;
    private int h, w;

    public HUD() {
        h = this.getHeight();
        w = this.getWidth();
    }

    public HUD(int x, int y, int ax, int ay) {

        String[] c = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z" };

        planetName = c[(int) (Math.random() * 26)].toUpperCase() + c[(int) (Math.random() * 26)].toUpperCase()
                + "-" + ((int) (Math.random() * 9999)) + "-" + c[(int) (Math.random() * 26)].toUpperCase()
                + c[(int) (Math.random() * 26)].toUpperCase();

        this.setBounds(x, y, ax, ay);
        this.setBackground(Color.black);

       /* temp = new JLabel();
        temp.setBounds(20, 140, 200, 25);
        temp.setLayout(null);
        this.add(temp);
        temp.setText("Températures : ");

        Dist = new JLabel("Distance au Soleil : ");
        Dist.setBounds(20, 100, 200, 25);
        Dist.setLayout(null);
        this.add(Dist);

        affDist = new JLabel("val d" + " km");
        affDist.setBounds(20, 20, 20, 50);
        affDist.setLayout(null);
        affDist.setBorder(BorderFactory.createLineBorder(Color.magenta, 2));
        this.add(affDist);

        tempMoy = new JLabel("moyenne :");
        tempMoy.setBounds(20, 170, 100, 50);
        tempMoy.setLayout(null);
        this.add(tempMoy);

        affMoy = new JLabel("val" + " °C", SwingConstants.CENTER);
        affMoy.setBounds(20, 210, 75, 25);
        affMoy.setLayout(null);
        affMoy.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        this.add(affMoy);

        tempMin = new JLabel("minimale :", SwingConstants.CENTER);
        tempMin.setBounds(w / 2 - 50, 170, 75, 50);
        tempMin.setLayout(null);
        this.add(tempMin);

        affMin = new JLabel("val" + " °C", SwingConstants.CENTER);
        affMin.setBounds(w / 2 - 50, 210, 75, 25);
        affMin.setLayout(null);
        affMin.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
        this.add(affMin);

        tempMax = new JLabel("maximale :");
        tempMax.setBounds(w - 100, 170, 100, 50);
        tempMax.setLayout(null);
        this.add(tempMax);

        affMax = new JLabel("val" + " °C", SwingConstants.CENTER);
        affMax.setBounds(w - 100, 210, 75, 25);
        affMax.setLayout(null);
        affMax.setBorder(BorderFactory.createLineBorder(Color.orange, 2));
        this.add(affMax); */

        // this.setLayout(null);

        h = this.getHeight();
        w = this.getWidth();

        // repaint();

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.white);

        // rectangle du contour
        g.drawRect(20, 20, w - 40, h - 40);
        Font F = new Font("F", 1, 20);
        g.setFont(F);
        g.drawString("Nom de la Planète : ", 40, 100);
        g.drawString(planetName, 40, 130);
		g.drawString("Températures :", 40, 280);
        Font G = new Font("H", 1, 15);
        g.setFont(G);
        g.drawString("Distance au Soleil : ", 40, _200);
        g.drawString("val" + " km", 200, _200);
        g.drawString("moyenne :", 50,320 );
        g.drawString("min :", 50,360 );
        g.drawString("max :", 50,400 );
        g.setColor(Color.GREEN);
        g.drawString("val" + " °C", 200,320 );
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("val" + " °C", 200,360 );
        g.setColor(Color.RED);
        g.drawString("val" + " °C", 200,400 );



        
        


    }
}