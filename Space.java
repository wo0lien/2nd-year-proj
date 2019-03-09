import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Space
 */
public class Space extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    // timer
    Timer t;
    private int dt = 40, temps = 0;

    // variable de modes en fonction des actions de l'utilisateur mode 0 normal,
    // mode 1 nouvelle planete

    private int mode = 0;
    private boolean mouseIn = false;
    private int mouseX = 0, mouseY = 0;
    private int newPlanetX =  0, newPlanetY = 0, newPlanetRadius = 20;

    // Image pour l'affichage sans scintillements
    private BufferedImage monBuf; // buffer d’affichage

    private BufferedImage spaceStars;
    private Image planetImage;
    private Image resizedPlanet;

    public Space(int xPos, int yPos, int x, int y) {

        System.out.println("Create the universe");

        this.setLayout(null);
        this.setBounds(xPos, yPos, x, y);

        t = new Timer(dt, this);
        t.start();

        Dimension dim = getSize();

        // buffered image
        monBuf = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);

        // images

        try {
            
            //chemins de fichier

            File pathToSpace = new File("space_with_stars.png");
            File pathToPlanet = new File("earth.png");
            
            //transformation en objet image des fichier    
            spaceStars = ImageIO.read(pathToSpace);
            planetImage = ImageIO.read(pathToPlanet);
        
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ajout des listeners

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == t) {
            temps += dt;
            repaint();
        }
    }

    /**
     * Methode pour entrer dans le mode de création d'une planete
     */
    public void NewPlanet() {
        System.out.println("Passage en mode ajout de planete");
        mode = 1;
    }

    /**
     * Methode d'affichage des elements visuels
     * 
     * @params graphics g
     */
    @Override
    public void paint(Graphics g) {

        Prepaint(monBuf.getGraphics());
        g.drawImage(monBuf, getInsets().left, getInsets().top, null);

    }

    /**
     * Méthode pour précharger l'affichage et éviter les scintillements
     */
    public void Prepaint(Graphics g) {

        // draw background image

        g.drawImage(spaceStars, 0, 0, null);

        // prise en compte du mode

        switch (mode) {
        case 0:
            // affichage classique de l'animation
            break;
        case 1:
            // affichage de la planete sur le curseur de l'utilisateur
            if (mouseIn) {
                g.setColor(Color.green);
                //g.fillOval(mouseX - newPlanetRadius, mouseY - newPlanetRadius, newPlanetRadius * 2, newPlanetRadius * 2);

                g.drawImage(resizedPlanet , mouseX - newPlanetRadius, mouseY - newPlanetRadius, null);
            }
        case 2:
            //position de la nouvelle planete fixee => fixage de la taille
            if (mouseIn) {
                g.setColor(Color.green);
                // g.fillOval(newPlanetX - newPlanetRadius, newPlanetY - newPlanetRadius, newPlanetRadius * 2, newPlanetRadius * 2);

                g.drawImage(resizedPlanet , newPlanetX - newPlanetRadius, newPlanetY - newPlanetRadius, null);
            }
            break;
        default:
            break;
        }

    }

    //mouse motion methods

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        switch (mode) {
            case 0:
                
                break;
        
            case 1:
                
                break;
            case 2:
                //mode fixage de la taille de la planete
                newPlanetRadius = (int)Math.sqrt(Math.pow(mouseX - newPlanetX, 2) + Math.pow(mouseY - newPlanetY, 2));

                //mise a l'echelle de la planete

                resizedPlanet = planetImage.getScaledInstance(newPlanetRadius * 2, newPlanetRadius * 2, Image.SCALE_DEFAULT);

                break;
            
            default:
                break;
        }
    }

    //mouse methods

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (mode) {
            case 0:
                //chose qui se passe quand le jeu est en mode normal
                break;
            case 1:
                //fixage de la position de la nouvelle planete
                newPlanetX = mouseX;
                newPlanetY = mouseY;
                //passage au mode suivant
                mode = 2;
                break;

            case 2:
                //sauvegarde de la planete dans la liste des objets

                //repassage en mode 0 et réinitialisation

                mode = 0;

                newPlanetRadius = 20;
                newPlanetX = 0;
                newPlanetY = 0;

            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseIn = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseIn = false;
    }

}