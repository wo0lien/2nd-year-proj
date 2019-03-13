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
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.net.URL;

/**
 * Space
 */
public class Space extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    // timer
    Timer t;
    private int dt = 40, temps = 0;
    private boolean pause;

    // variable de modes en fonction des actions de l'utilisateur mode 0 normal,
    // mode 1 nouvelle planete

    private int mode = 0;
    private boolean mouseIn = false;
    private int mouseX = 0, mouseY = 0;
    private int newPlanetX = 0, newPlanetY = 0, newPlanetRadius = 20;

    // Image pour l'affichage sans scintillements
    private BufferedImage monBuf; // buffer d’affichage

    private BufferedImage spaceStars;
    private Image planetImage;
    private Image resizedPlanet;

    // explosion
    private Image explosion, resizedExplosion;
    private int explosionX, explosionY, explosionCounter, explosionR;
    private boolean explosing;

    // objects

    private LinkedList<ObjetCeleste> objets;

    public Space(int xPos, int yPos, int x, int y) {

        System.out.println("Create the universe");

        this.setLayout(null);
        this.setBounds(xPos, yPos, x, y);

        t = new Timer(dt, this);
        pause = true;
        t.start();

        Dimension dim = getSize();

        // buffered image
        monBuf = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);

        // images

        try {

            // chemins de fichier

            File pathToSpace = new File("space_with_stars.png");
            File pathToPlanet = new File("earth.png");

            // transformation en objet image des fichier
            spaceStars = ImageIO.read(pathToSpace);
            planetImage = ImageIO.read(pathToPlanet);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            explosion = new ImageIcon("explosion.gif").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // resize explosion
        resizedExplosion = explosion.getScaledInstance(newPlanetRadius * 2, newPlanetRadius * 2, Image.SCALE_FAST);
        resizedPlanet = planetImage.getScaledInstance(newPlanetRadius * 2, newPlanetRadius * 2, Image.SCALE_FAST);

        objets = new LinkedList<ObjetCeleste>();

        // ajout des listeners

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == t) {

            // si l'animation tourne on peut update la position des planetes

            if (!pause) {
                for (ObjetCeleste objet : objets) {
                    double Force, angle, r;
                    int dx, dy;
                    for (ObjetCeleste obj : objets) {
                        // int Distx;
                        // int Disty;
                        dx = obj.GetX() - objet.GetX();
                        dy = obj.GetY() - objet.GetY();
                        r = dx * dx + dy * dy;
                        if (r != 0) {

                            Force = obj.GetMasse() / r;
                            angle = Math.atan2(dy, dx);
                            objet.vx += Force * Math.cos(angle);
                            objet.vy += Force * Math.sin(angle);

                            if (Math.sqrt(r) + 3 < objet.r + obj.r && obj.masse < objet.masse) {
                                System.out.println("Collision");
                                objet.r += obj.r;
                                objet.masse += obj.masse;

                                // animation de l'explosion

                                explosionX = obj.GetX();
                                explosionY = obj.GetY();
                                explosionCounter = 0;

                                explosionR = obj.r;

                                // resize Image
                                resizedExplosion = explosion.getScaledInstance(explosionR * 2, explosionR * 2,
                                        Image.SCALE_FAST);

                                // active l'affichage
                                explosing = true;

                                // on supprime l'objet
                                objets.remove(obj);
                                /*
                                 * if (objet.r < objet.r + explosionR) { objet.r += 1; }
                                 */
                                objet.resize();

                            }
                        }

                    }
                    objet.update(dt);
                }
            }

            repaint();
        }
    }

    /**
     * Methode pour entrer dans le mode de création d'une planete
     */
    public void NewPlanet() {
        System.out.println("Passage en mode ajout de planete");

        // reinitialisation des variables necessaires

        newPlanetRadius = 20;
        newPlanetX = 0;
        newPlanetY = 0;
        resizedPlanet = planetImage.getScaledInstance(newPlanetRadius * 2, newPlanetRadius * 2, Image.SCALE_FAST);

        mode = 1;
    }

    public void TimerButton() {

        // changement d'etat de la variable pause

        pause = !pause;
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

        // affichage de la liste des objets

        for (ObjetCeleste obj : objets) {
            obj.paint(g);
        }

        // prise en compte du mode

        switch (mode) {
        case 0:
            // affichage classique de l'animation
            break;
        case 1:
            // affichage de la planete sur le curseur de l'utilisateur
            if (mouseIn) {
                g.drawImage(resizedPlanet, mouseX - newPlanetRadius, mouseY - newPlanetRadius, null);
            }
            break;
        case 2:
            // position de la nouvelle planete fixee => fixage de la taille
            if (mouseIn) {
                g.drawImage(resizedPlanet, newPlanetX - newPlanetRadius, newPlanetY - newPlanetRadius, null);
            }
            break;
        case 3:
            if (mouseIn) {
                g.setColor(Color.red);
                g.drawLine(newPlanetX, newPlanetY, mouseX, mouseY);
            }
            break;
        default:
            break;
        }

        // explosion
        if (explosionCounter > 25) {
            explosing = false;
        }
        if (explosing) {
            explosionCounter++;

            g.drawImage(resizedExplosion, explosionX - explosionR, explosionY - explosionR, null);

        }

    }

    // mouse motion methods

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
            // mode fixage de la taille de la planete
            newPlanetRadius = (int) Math.sqrt(Math.pow(mouseX - newPlanetX, 2) + Math.pow(mouseY - newPlanetY, 2));

            // mise a l'echelle de la planete
            resizedPlanet = planetImage.getScaledInstance(newPlanetRadius * 2, newPlanetRadius * 2, Image.SCALE_FAST);

            break;

        default:
            break;
        }
    }

    // mouse methods

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (mode) {
        case 0:
            // chose qui se passe quand le jeu est en mode normal
            break;
        case 1:
            // fixage de la position de la nouvelle planete
            newPlanetX = e.getX();
            newPlanetY = e.getY();
            // passage au mode suivant
            System.out.println("Passe au mode selection taille");
            mode = 2;
            break;

        case 2:
            // sauvegarde de la planete dans la liste des objets
            // remplacer le 2 par un coef en fonction des materiaux
            Planete newp = new Planete((double) 750 * newPlanetRadius, 0, 0, newPlanetX, newPlanetY, resizedPlanet,
                    newPlanetRadius);
            objets.add(newp);

            // repassage en mode 3
            System.out.println("Passage au mode selection vitesse");
            mode = 3;
            break;
        case 3:

            // on assigne a la planete la vitesse en x et en y en fonction de la position du
            // curseur

            objets.get(objets.size() - 1).setVitesseX((double) e.getX() - newPlanetX);
            objets.get(objets.size() - 1).setVitesseY((double) e.getY() - newPlanetY);

            // retour au mode 0

            mode = 0;

            break;
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