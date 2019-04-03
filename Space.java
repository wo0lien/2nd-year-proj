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
import java.awt.event.MouseWheelListener;
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
public class Space extends JPanel implements  MouseListener, MouseMotionListener,MouseWheelListener {

    // timer
    private int tempsJours = 0, tempsAnnées=0, dt=40;
    private boolean pause;

    //variables de zoom
    private double zoomFactor=1;
    private double prevZoom;
    private double pprevZoom;
    private int rotation; //0 no rotation, 1 zoom in, -1 zoom out
    private double xOffset; //coordonnées d'un point 'fixe'
    private double yOffset;

    //hud courant pour pouvoir l'afficher dans la fenetre
    private HUD hudCourant;
    private int bx,by,ax,ay;
    private ObjetCeleste objSelected;

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
    private Image sunImage;
    private Image resizedPlanet;
    private Image resizedSun;

    //explosion
    private Image explosion, resizedExplosion; 
    private int explosionX, explosionY, explosionCounter, explosionR;
    private boolean explosing;

    //objects

    private LinkedList<ObjetCeleste> objets; 
    private int size;

    private Soleil sun;

    public Space(int xPos, int yPos, int x, int y,int bx, int by, int ax, int ay) {

        System.out.println("Create the universe");

        this.setLayout(null);
        this.setBounds(xPos, yPos, x, y);

        pause = true;

        this.bx=bx;
        this.by=by;
        this.ax=ax;
        this.ay=ay;

        hudCourant=new HUD();
        objSelected=null;

        Dimension dim = getSize();

        xOffset=dim.width/2;
        yOffset=dim.height/2;

        // buffered image
        monBuf = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);

        // images

        try {
            
            //chemins de fichier

            File pathToSpace = new File("space_with_stars.png");
            File pathToPlanet = new File("earth.png");
            File pathToSun = new File("sun.png");
            
            //transformation en objet image des fichier    
            spaceStars = ImageIO.read(pathToSpace);
            planetImage = ImageIO.read(pathToPlanet);
            sunImage = ImageIO.read(pathToSun);
        
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

        // ajout des listeners

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);

        //ajout du soleil au milieu de la fenetre

        int SunDiam = 100;

        resizedSun = sunImage.getScaledInstance(SunDiam, SunDiam, Image.SCALE_FAST);
        HUD h = new HUD();

        sun = new Soleil(100000, 400, 400, resizedSun, SunDiam / 2, h);
        objets.add(sun);

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

                            //préparation de la collision
                            
                            if (Math.sqrt(r) < objet.r + obj.r){
                                
                                if (obj.masse < objet.masse || objet.getType() == "sun") {
                                    
                                    if (objet.getType() != "sun") {
                                        objet.r += obj.r;
                                        objet.masse += obj.masse;
                                    }
                                        
                                    //animation de l'explosion
        
                                    explosionX = obj.GetX();
                                    explosionY = obj.GetY();
                                    explosionCounter = 0;
        
                                    explosionR = obj.r;
                                        
                                    //resize Image
                                    resizedExplosion = explosion.getScaledInstance(explosionR * 2, explosionR * 2, Image.SCALE_FAST);
        
                                    //active l'affichage
                                    explosing = true;
        
                                        //si l'objet sélectionné était l'un des 2, le nouvel objet est sélectionné 
                                    if (objSelected==obj) {
                                        objSelected=objet;
                                    }

                                    //on supprime l'objet 
                                    objets.remove(obj);
                                    objet.resize();
                                }
                            }
                        }
                    }
                    objet.update(dt);
                }
                tempsJours++;
                if (tempsJours==365) {
                    tempsJours=0;
                    tempsAnnées++;
                }
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

        resizedPlanet = planetImage.getScaledInstance(newPlanetRadius * 2, newPlanetRadius * 2, Image.SCALE_SMOOTH);

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

        //mise en valeur de l'objet celeste sélectionné
        if (objSelected!=null) {
            g.setColor(Color.WHITE);
            g.drawRect(objSelected.xZ-objSelected.rZoom-5,objSelected.yZ-objSelected.rZoom-5,objSelected.rZoom*2+10,objSelected.rZoom*2+10);
        }

        // prise en compte du mode
        String str = "xOffset : " +xOffset + " yOffset : " + yOffset;
        g.drawString(str,mouseX,mouseY);
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
    public HUD getHUD() {
        return hudCourant;
    }

    //mouse motion methods

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Drag");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        updateOffset();

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
                    // clicker sur une planete pour afficher son hud
                    //implémentation du zoom : penser à prendre l'offset en compte
                    for (ObjetCeleste obj : objets) {
                        double dx = obj.GetX() - (int)(e.getX());
                        double dy = obj.GetY() - (int)(e.getY());
                        double r = Math.sqrt(dx * dx + dy * dy);
                        if (r < obj.r) {
                            System.out.println("j'affiche le hud");
                            hudCourant=obj.getHUD();
                            objSelected=obj;
                        }
                    }
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
                    HUD hud= new HUD(bx,by,ax,ay);
                    Planete newp = new Planete((double)3000 * newPlanetRadius, 0, 0, newPlanetX, newPlanetY, resizedPlanet, newPlanetRadius,hud);
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
        } else if (e.getButton()==MouseEvent.BUTTON3) {
            cancelPLanet();
        }
    }

    public void cancelPLanet() {
        System.out.println("annulation du placement de la planète");
            mode=0;
            if (size!=objets.size()){
                objets.removeLast();
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

    public void mouseWheelMoved(MouseWheelEvent e){ //gère le zoom
        if (e.getWheelRotation()<0) {
            zoomFactor*=1.1;
            zoomFactor=Math.min(10.0,zoomFactor);  
            testRotation();
            if (zoomFactor<10 && rotation==1) {
                zoom();
                repaint();
            }        
            //scroll vers le haut
        } else {
            zoomFactor/=1.1;
            zoomFactor=Math.max(0.1,zoomFactor);
            testRotation();
            if (zoomFactor>0.1 && rotation==-1) {
                zoom();
                repaint();
            } 
            // scroll vers le bas
        }
        System.out.println("zoom : "  + zoomFactor);
        System.out.println("Rotation : " + rotation);
    }

    public void zoom() {
         //implementation du zoom
        for (ObjetCeleste obj : objets) {
            obj.resize();
            obj.zoomUpdate(zoomFactor, mouseX, mouseY);
        }
        //coordonnées du point central de l'écran
    }

    //déterminer la position réelle de la sourie sur la map
    public void updateOffset() {
        xOffset=-(double)(xOffset-mouseX)/zoomFactor+xOffset;
        yOffset=-(double)(yOffset-mouseY)/zoomFactor+yOffset;
    }

    //fluidifier le mouvement de la molette qui est parfois imprécis
    public void testRotation() {
        if (zoomFactor>prevZoom && prevZoom>pprevZoom) {
            rotation=1;
        } else if (zoomFactor<prevZoom && prevZoom<pprevZoom) {
            rotation=-1;
        } else {
            rotation=0;
        }
        pprevZoom=prevZoom;
        prevZoom=zoomFactor;
    }
}