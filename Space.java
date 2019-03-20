import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
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
public class Space extends JPanel implements  MouseListener, MouseMotionListener {

    // timer
    private int tempsJours = 0, tempsAnnées=0, dt=40;
    private boolean pause;

    //variables de zoom
    private int zoomFactor=1, prevZoomFactor=1;
    private boolean zoom;
    private boolean dragger;
    private boolean released;
    private double xOffset = 0;
    private double yOffset = 0;
    private int xDiff;
    private int yDiff;
    private int startX;
    private int startY;

    // variable de modes en fonction des actions de l'utilisateur mode 0 normal,
    // mode 1 nouvelle planete
    private int mode = 0;
    private boolean mouseIn = false;
    private int mouseX = 0, mouseY = 0;
    private int newPlanetX =  0, newPlanetY = 0, newPlanetRadius = 20;

    // Image pour l'affichage sans scintillements
    private BufferedImage monBuf; // buffer d’affichage
    private Image spaceStars;
    private Image planetImage;
    private Image resizedPlanet;

    //explosion
    private Image explosion, resizedExplosion; 
    private int explosionX, explosionY, explosionCounter, explosionR;
    private boolean explosing;

    private boolean frags;

    //objects

    private LinkedList<ObjetCeleste> objets; 
    private LinkedList<ObjetCeleste> fragments;
    private int size;

    public Space(int xPos, int yPos, int x, int y) {

        System.out.println("Create the universe");

        this.setLayout(null);
        this.setBounds(xPos, yPos, x, y);

        pause = true;

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
        try {
            explosion = new ImageIcon("explosion.gif").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //resize explosion
        resizedExplosion = explosion.getScaledInstance(newPlanetRadius * 2, newPlanetRadius * 2, Image.SCALE_FAST);
        resizedPlanet = planetImage.getScaledInstance(newPlanetRadius * 2, newPlanetRadius * 2, Image.SCALE_FAST);

        objets = new LinkedList<ObjetCeleste>();
        fragments = new LinkedList<ObjetCeleste>();

        frags = false;

        // ajout des listeners

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        repaint();

    }

    public void timerPerformed() {
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

                            //difference de vitesse entre les 2 astres
                            double deltaV = Math.abs(objet.vx - obj.vx) + Math.abs(objet.vy - obj.vy);
                            //préparation de la collision
                            
                            if (Math.sqrt(r) < objet.r + obj.r && obj.masse < objet.masse){
                                //si elles vont vite l'une par rapport a l'autreS
                                if (deltaV > 500) {
                                    //explosion en petits sattelites
                                    System.out.println("Explosionnn");

                                    //généation des satellites
                                    double n = 0;
                                    int max = (int)(Math.random() * 6) + 5;
                                    double m = (objet.GetMasse() + obj.GetMasse()) / max;
                                    int ray = (objet.r + obj.r) / max;
                                    int ax = (obj.GetX() + objet.GetX()) / 2;
                                    int ay = (obj.GetY() + objet.GetY()) / 2;

                                    Image im = planetImage.getScaledInstance(ray * 2, ray * 2, Image.SCALE_FAST);

                                    
                                    for (int i = 0; i < max; i++) {

                                        double vitx = 30 * Math.cos(n);
                                        double vity = 30 * Math.sin(n);

                                        System.out.println("masse " + m + " vitx " + vitx + " vity " + vity + " ax " + ax + " ay " + ay + " ray " + ray);

                                        Planete pl = new Planete(m, vitx, vity, ax + (int)(vitx * 3), ay + (int)(vity * 3), im, ray);

                                        fragments.push(pl);
                                        //on veut des satellites dans toutes les directions
                                        n += 6.28 / max;
                                    }

                                    frags = true;

                                    objets.remove(obj);

                                } else {
                                    objet.r += obj.r;
                                    objet.masse += obj.masse;
                                    
                                    //animation de l'explosion
    
                                    explosionX = obj.GetX();
                                    explosionY = obj.GetY();
                                    explosionCounter = 0;
    
                                    explosionR = obj.r;
                                    
                                    //resize Image
                                    resizedExplosion = explosion.getScaledInstance(explosionR * 2, explosionR * 2, Image.SCALE_FAST);
    
                                    //active l'affichage
                                    explosing = true;
    
                                    //on supprime l'objet 
                                    objets.remove(obj);
                                    objet.resize();
                                }
                            }
                        }
                    }
                    if (!frags) {
                        objet.update(dt);
                    } else {
                        objets.remove(objet);
                        System.out.println("remove");
                    }
                }  
                tempsJours++;
                if (tempsJours==365) {
                    tempsJours=0;
                    tempsAnnées++;
                }
            }
            
            if (frags) {
                frags = false;
                for (ObjetCeleste fragm : fragments) {
                    System.out.println("pushed");
                    objets.push(fragm);
                }
                fragments.clear();
            }

            repaint();
    }

    /**
     * Methode pour entrer dans le mode de création d'une planete
     */
    public void NewPlanet() {
        System.out.println("Passage en mode ajout de planete");

        //reinitialisation des variables necessaires

        newPlanetRadius = 20;
        newPlanetX = 0;
        newPlanetY = 0;
        resizedPlanet = planetImage.getScaledInstance(newPlanetRadius * 2, newPlanetRadius * 2, Image.SCALE_FAST);

        mode = 1;
    }


    public void TimerButton() {
        
        //changement d'etat de la variable pause
        
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
        
        //affichage de la liste des objets

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
                g.drawImage(resizedPlanet , mouseX - newPlanetRadius, mouseY - newPlanetRadius, null);
            }
            break;
        case 2:
            //position de la nouvelle planete fixee => fixage de la taille
            if (mouseIn) {
                g.drawImage(resizedPlanet , newPlanetX - newPlanetRadius, newPlanetY - newPlanetRadius, null);
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

        //explosion
        if (explosionCounter > 25){
            explosing = false;
        }
        if (explosing) {
            explosionCounter++;

            g.drawImage(resizedExplosion, explosionX - explosionR , explosionY - explosionR, null);

        } 
    }

    public int getTempsJours() {
        return tempsJours;
    }
    public int getTempsAnnées() {
        return tempsAnnées;
    }

    //mouse motion methods

    @Override
    public void mouseDragged(MouseEvent e) {
        xDiff = e.getLocationOnScreen().getX() - startX;
        yDiff = e.getLocationOnScreen().getY() - startY;

        dragger = true;
        //repaint();

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
                resizedPlanet = planetImage.getScaledInstance(newPlanetRadius * 2, newPlanetRadius * 2, Image.SCALE_FAST);

                break;
            
            default:
                break;
        }
    }

    //mouse methods

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton()==MouseEvent.BUTTON1) {
            size=objets.size();
            switch (mode) {
                case 0:
                    //chose qui se passe quand le jeu est en mode normal
                    break;
                case 1:
                    //fixage de la position de la nouvelle planete
                    newPlanetX = e.getX();
                    newPlanetY = e.getY();
                    //passage au mode suivant
                    System.out.println("Passe au mode selection taille");
                    mode = 2;
                    break;

                case 2:
                    //sauvegarde de la planete dans la liste des objets
                    //remplacer le 2 par un coef en fonction des materiaux
                    Planete newp = new Planete((double)3000 * newPlanetRadius, 0, 0, newPlanetX, newPlanetY, resizedPlanet, newPlanetRadius);
                    objets.add(newp);

                    //repassage en mode 3
                    System.out.println("Passage au mode selection vitesse");
                    mode = 3;
                    break;
                case 3:

                    // on assigne a la planete la vitesse en x et en y en fonction de la position du curseur

                    objets.get(objets.size() - 1).setVitesseX((double)e.getX() - newPlanetX);
                    objets.get(objets.size() - 1).setVitesseY((double)e.getY() - newPlanetY);
                    
                    //retour au mode 0

                    mode = 0;

                    break;
                default:
                    break;
            }
        } else {
            System.out.println("annulation du placement de la planète");
            mode=0;
            if (size!=objets.size()){
                objets.removeLast();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        released = false;
        startX = e.getX();
        startY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        released = true;
        //repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseIn = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseIn = false;
    }

    public void mouseWheelMoved(MouseWheelEvent e){ //gère bientot le zoom
        if (e.getWheelRotation()<0) {
            zoomFactor*=1.1;
            zoomFactor=max(2,zoomFactor);
            //repaint();
            //scroll vers le haut
        } else {
            zoomFactor/=1.1;
            zoomFactor=max(1,zoomFactor);
            //repaint();
            // scroll vers le bas
        }
    }
    public void zoom(Graphics g) {
         //implementation du zoom
         Graphics g2= (Graphics) g;
         if (zoom) {
             double xRel = mouseX - getLocationOnScreen().getX();
             double yRel = mouseY - getLocationOnScreen().getY();
 
             double zoomDiv = zoomFactor / prevZoomFactor;
 
             xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
             yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;
 
             g2.translate((int)xOffset,(int)yOffset);
             g2.scale(zoomFactor, zoomFactor);
             prevZoomFactor = zoomFactor;
             zoom = false;
         }
         if (dragger) {
             g2.translate((int)(xOffset + xDiff),(int)( yOffset + yDiff);
             g2.scale(zoomFactor, zoomFactor);
             if (released) {
                 xOffset += xDiff;
                 yOffset += yDiff;
                 dragger = false;
             }
         } 
         g=g2;
    }
}