import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
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

/**
 * Space
 */
public class Space extends JPanel implements  MouseListener, MouseMotionListener,MouseWheelListener {

    //constants

    private static final int sunDiam = 100;
    private static final double coefTemp = 100;

    // timer
    private int tempsJours = 0, tempsAnnées = 0, dt = 40;
    private boolean pause;

    // variables de zoom
    private double zoomFactor = 1;

    // retiens les 3 dernieres valeurs du zoom pour déterminer le sens de rotation
    // et fluidifier le mouvement imprécis de la molette (voir testRotation())
    private double prevZoom;
    private double pprevZoom;
    private double ppprevZoom;
    private int rotation; // 0 no rotation, 1 zoom in, -1 zoom out

    // coordonnées d'un point 'fixe' ici le point reel 0,0 sur la map
    private double xOffset;
    private double yOffset;

    // retiens les coordonnées sur la map de la souris
    private double mouseXReel;
    private double mouseYReel;

    // variables pour drag pour le zoom
    private int startX = 0, startY = 0;
    private double xDiff = 0, yDiff = 0;

    // hud courant pour pouvoir l'afficher dans la fenetre
    private HUD hudCourant;
    private int bx, by, ax, ay;
    private ObjetCeleste objSelected;

    // variable de modes en fonction des actions de l'utilisateur mode 0 normal,
    // mode 1 nouvelle planete
    private int mode = 0;
    private boolean mouseIn = false;
    private int mouseX = 0, mouseY = 0;
    private int newPlanetX = 0, newPlanetY = 0, newPlanetRadius = 20;
    private int newPlanetXReel = 0, newPlanetYReel = 0;

    // Image pour l'affichage sans scintillements
    private BufferedImage monBuf; // buffer d’affichage
    private Image spaceStars;
    private Image planetImage;
    private Image sunImage;
    private Image resizedPlanet;
    private Image resizedSun;

    // explosion
    private Image explosion, resizedExplosion;
    private int explosionX, explosionY, explosionCounter, explosionR;
    private boolean explosing;

    // objects

    private LinkedList<ObjetCeleste> objets;
    private LinkedList<ObjetCeleste> objetsTrajectoire;
    private LinkedList<ObjetCeleste> objetsDetruits;
    private LinkedList<ObjetCeleste> objetsDestructeurs;
    private int size;

    private Soleil sun;

    // ChoixAtome
    private boolean atome[] = new boolean[4];
    private int masse = 0;

    public Space(int xPos, int yPos, int x, int y, int bx, int by, int ax, int ay) throws AWTException {

        System.out.println("Create the universe");

        this.setLayout(null);
        this.setBounds(xPos, yPos, x, y);

        pause = true;

        this.bx = bx;
        this.by = by;
        this.ax = ax;
        this.ay = ay;

        // creation d'un HUD vide pour l'affichage initial
        hudCourant = new HUD();
        objSelected = null;

        // on récupère la taille de space dans une variable dim
        Dimension dim = getSize();

        // les offsets initiaux sont nuls
        xOffset = 0;
        yOffset = 0;

        // buffered image pour l'affichage sans scintillements
        monBuf = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);

        // images

        try {

            // chemins de fichier

            File pathToSpace = new File("space_with_stars.png");
            File pathToPlanet = new File("earth.png");
            File pathToSun = new File("sun.png");

            // transformation en objet image des fichier
            spaceStars = ImageIO.read(pathToSpace);
            planetImage = ImageIO.read(pathToPlanet);
            sunImage = ImageIO.read(pathToSun);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // le gif de l'explosion est chargé differement
        try {
            explosion = new ImageIcon("explosion.gif").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // les image redimensionnées des images initiales
        // on repart toujours de l'image initiale pour éviter les dégradations lié à un
        // nombre trop grand de redimensionnement
        resizedExplosion = explosion.getScaledInstance(newPlanetRadius * 2, newPlanetRadius * 2, Image.SCALE_FAST);
        resizedPlanet = planetImage.getScaledInstance((int) (newPlanetRadius * 2 * zoomFactor),
                (int) (newPlanetRadius * 2 * zoomFactor), Image.SCALE_FAST);

        // space stars ne sera rescale qu'une seule fois on peut le faire directement
        spaceStars = spaceStars.getScaledInstance((int) dim.getWidth(), (int) dim.getHeight(), Image.SCALE_DEFAULT);

        // initialisation de toutes les listes d'objets

        objets = new LinkedList<ObjetCeleste>();
        objetsTrajectoire = new LinkedList<ObjetCeleste>();
        objetsDetruits = new LinkedList<ObjetCeleste>();
        objetsDestructeurs = new LinkedList<ObjetCeleste>();

        // ajout des listeners

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);

        // ajout du soleil au milieu de la fenetre

        resizedSun = sunImage.getScaledInstance(sunDiam, sunDiam, Image.SCALE_FAST);
        HUD h = new HUD(bx, by, ax, ay, "l'étoile");

        sun = new Soleil(100000, (int)dim.getWidth() / 2, (int)dim.getHeight() / 2, resizedSun, sunDiam / 2, h);
        objets.add(sun);

        size = objets.size();

        repaint();

    }

    public void timerPerformed() {
        // si l'animation tourne on peut update la position des planetes

        if (!pause) {

            // appel de la fonction pour toutes les interractions
            interractions(objets, false);

            // incrémentation du timer
            tempsJours++;
            if (tempsJours == 365) {
                tempsJours = 0;
                tempsAnnées++;
            }

            // setup les variables de températures

            for (ObjetCeleste obj : objets) {

                // distance au soleil
                //on verifie que ce ne soit pas le soleil lui-meme
                if (obj.getType() != "sun") {
                    SetTemp(obj);
                }
            }
        }
        repaint();
    }

    private void SetTemp(ObjetCeleste obj) {
        double dist = SunDistance(obj);
        obj.SetTemp(sun.GetTemp() / dist * coefTemp);
    }

    private double SunDistance(ObjetCeleste obj) {
        double dist = Math
                .sqrt(Math.abs((double) (sun.GetX() - obj.GetX())) * Math.abs((double) (sun.GetX() - obj.GetX()))
                        + Math.abs((double) (sun.GetY() - obj.GetY())) * Math.abs((double) (sun.GetY() - obj.GetY())));
        return dist;
    }

    /**
     * Methode pour entrer dans le mode de création d'une planete
     * Appel depuis la fenetre et le bouton d'ajout de planete
     */
    public void NewPlanet() {

        System.out.println("Passage en mode ajout de planete");

        // reinitialisation des variables necessaires

        newPlanetRadius = 20;

        resizedPlanet = planetImage.getScaledInstance(newPlanetRadius * 2, newPlanetRadius * 2, Image.SCALE_SMOOTH);

        mode = 1;
    }

    /**
     * Methode appelée depuis la fenetre par l'appui du bouton start/stop animation
     */
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

        //g.drawImage(monBuf, getInsets().left, getInsets().top, null);
        g.drawImage(monBuf, 0, 0, null);

    }

    /**
     * Méthode pour précharger l'affichage et éviter les scintillements
     */
    public void Prepaint(Graphics g) {
        
        // draw background image

        g.drawImage(spaceStars, 0, 0, null);

        //on update les offsets (pourrait changer de place ??)
        updateMouseOffset();
        updateOffset();
        
        g.setColor(Color.GREEN);
        //g.drawString("xZ " + (int)mouseXReel + " yZ " + (int)mouseYReel,mouseX,mouseY);
        //g.drawString("x " + mouseX + " y " + mouseY,mouseX,mouseY+15);
        //g.drawString("xOffset :" + (int)xOffset + " yOffset : " + (int)yOffset,10,10);
        //affichage de la liste des objets

        for (ObjetCeleste obj : objets) {
            obj.zoomUpdate(zoomFactor,xOffset,yOffset);
            obj.paint(g);
        }

        //mise en valeur de l'objet celeste sélectionné
        if (objSelected!=null) {
            g.setColor(Color.WHITE);
            g.drawRect((int)objSelected.xZ - objSelected.rZoom - 5, (int)objSelected.yZ - objSelected.rZoom - 5, objSelected.rZoom * 2 + 10, objSelected.rZoom * 2 + 10);
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
                newPlanetXReel=updateSomeOffsetX(newPlanetX);
                newPlanetYReel=updateSomeOffsetY(newPlanetY);
                g.drawImage(resizedPlanet , newPlanetXReel - (int)(newPlanetRadius*zoomFactor), newPlanetYReel - (int)(newPlanetRadius*zoomFactor), null);
            }
            break;
        case 3:

            //affichage de la trajectoire la nouvelle planete
            if (mouseIn) {
                
                copie();

                objetsTrajectoire.getLast().setVitesseX((double)mouseXReel - newPlanetX);
                objetsTrajectoire.getLast().setVitesseY((double)mouseYReel - newPlanetY);

                calculTraject(objetsTrajectoire.getLast() , g);
            }
        }
        //explosion
        if (explosionCounter > 25){
            explosing = false;
        }
        if (explosing) {
            explosionCounter++;

            g.drawImage(resizedExplosion, updateSomeOffsetX(explosionX) - (int)(explosionR*zoomFactor) , updateSomeOffsetY(explosionY) - (int)(explosionR*zoomFactor), null);

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
        xDiff=(double)(startX-e.getX())/zoomFactor;
        yDiff=(double)(startY-e.getY())/zoomFactor;
        xOffset+=xDiff;
        yOffset+=yDiff;
        updateMouseOffset();
        startX=e.getX();
        startY=e.getY();
        repaint();
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
                newPlanetRadius = Math.max((int)Math.sqrt(Math.pow((int)mouseXReel - newPlanetX, 2) + Math.pow((int)mouseYReel - newPlanetY, 2)), 1);

                //mise a l'echelle de la planete
                resizedPlanet = planetImage.getScaledInstance((int)(newPlanetRadius * 2 * zoomFactor), (int)(newPlanetRadius * 2 * zoomFactor), Image.SCALE_SMOOTH);

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
                    
                    //quand le jeu est en mode normal
                    // clicker sur une planete pour afficher son hud
                    for (ObjetCeleste obj : objets) {
                        double dx = obj.GetX() - (int)mouseXReel;
                        double dy = obj.GetY() - (int)mouseYReel;
                        double r = Math.sqrt(dx * dx + dy * dy);
                        if (r < obj.r) {
                            hudCourant=obj.getHUD();
                            objSelected=obj;
                        }
                    }
                    break;
                case 1:
                    // Choix Atome
                
                    //creation de la fenetre de choix des atomes
                    ChoixAtome choix = new ChoixAtome("choisis ta composition", this, mouseX, mouseY);
                    
                    //on récupère la réponse de l'utilisateur
                    atome = choix.getAtome();
                
                    //fixage de la position de la nouvelle planete
                    newPlanetX = (int)mouseXReel;
                    newPlanetY = (int)mouseYReel;
                    newPlanetXReel=updateSomeOffsetX(newPlanetX);
                    newPlanetYReel=updateSomeOffsetY(newPlanetY);
                    //passage au mode suivant
                    System.out.println("Passe au mode selection taille");
                    mode = 2;
                    break;

                case 2:
                    //sauvegarde de la planete dans la liste des objets
                    //remplacer le 2 par un coef en fonction des materiaux
                    
                    HUD hud= new HUD(bx,by,ax,ay,"la planète"); 
                    if (atome[0] == true){
                        masse = 6000;
                    }
                    if (atome[1] == true){
                        masse = 3000;
                    }
                    if (atome[2] == true){
                        masse = 2000;
                    }
                    if (atome[3] == true){
                        masse = 400;
                    }

                    Planete newp = new Planete((double)masse * newPlanetRadius, 0, 0, newPlanetX, newPlanetY, resizedPlanet, newPlanetRadius,hud, atome);
                    
                    newp.InitializeTemp((double)(sun.GetTemp() / SunDistance(newp) * coefTemp));
                    
                    newp.zoomUpdate(zoomFactor,xOffset,yOffset);
                    objets.add(newp);
                    copie();

                    //repassage en mode 3
                    System.out.println("Passage au mode selection vitesse");
                    mode = 3;
                    break;
                case 3:

                    // on assigne a la planete la vitesse en x et en y en fonction de la position du curseur

                    objets.get(objets.size() - 1).setVitesseX((double)mouseXReel - newPlanetX);
                    objets.get(objets.size() - 1).setVitesseY((double)mouseYReel - newPlanetY);
                    
                    //retour au mode 0

                    mode = 0;
                    newPlanetX=0;
                    newPlanetY=0;
                    newPlanetXReel=0;
                    newPlanetYReel=0;

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
            
            if (mode != 0 && size != objets.size()){
                objets.removeLast();
                mode=0;
            }
            
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startX=mouseX;
        startY=mouseY;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        xDiff=0;
        yDiff=0;
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
            zoomFactor *= 1.1;
            zoomFactor = Math.min(10.0, zoomFactor);  
            testRotation();
            updateOffset();
            updateMouseOffset();
            if (zoomFactor<10 && rotation==1) {
                zoom();
            }  
            //scroll vers le haut
        } else {
            zoomFactor/=1.1;
            zoomFactor=Math.max(0.1,zoomFactor);
            testRotation();
            updateOffset();
            updateMouseOffset();
            if (zoomFactor>0.1 && rotation==-1) {
                zoom();
            } 
            // scroll vers le bas
        }
    }

    public void zoom() {
         //implementation du zoom
        for (ObjetCeleste obj : objets) {
            obj.zoomUpdate(zoomFactor,xOffset,yOffset);
            obj.resize();
        }
        repaint();
    }

    //déterminer la position réelle de la sourie sur la map
    public void updateOffset() {
        xOffset=mouseXReel-mouseX/zoomFactor;
        yOffset=mouseYReel-mouseY/zoomFactor;
    }
    public void updateMouseOffset() {
        mouseXReel=xOffset+mouseX/zoomFactor;
        mouseYReel=yOffset+mouseY/zoomFactor;
    }
    public int updateSomeOffsetX(double xOff) {
        return (int)(-(xOffset-xOff)*zoomFactor);
    }
    public int updateSomeOffsetY(double yOff) {
        return (int)(-(yOffset-yOff)*zoomFactor);
    }

    //fluidifier le mouvement de la molette qui est parfois imprécis
    public void testRotation() {
        if (zoomFactor>prevZoom && prevZoom>pprevZoom && pprevZoom>ppprevZoom) {
            rotation=1;
        } else if (zoomFactor<prevZoom && prevZoom<pprevZoom && pprevZoom<ppprevZoom) {
            rotation=-1;
        } else {
            rotation=0;
        }
        ppprevZoom=pprevZoom;
        pprevZoom=prevZoom;
        prevZoom=zoomFactor;
    }
    public void interractions(LinkedList<ObjetCeleste> listeObj, boolean traj) {

        objetsDetruits.clear();
        objetsDestructeurs.clear();
        
        for (ObjetCeleste objet : listeObj) {
            double Force, angle, r;
            double dx, dy;
            
            for (ObjetCeleste obj : listeObj) {
                // int Distx;
                // int Disty;
                dx = (double)(obj.GetX() - objet.GetX());
                dy = (double)(obj.GetY() - objet.GetY());
                r = dx * dx + dy * dy;
                if (r != 0) {

                    Force = obj.GetMasse() / r;
                    angle = Math.atan2((double)dy, (double)dx);
                    objet.vx += Force * Math.cos(angle);
                    objet.vy += Force * Math.sin(angle);

                    //préparation de la collision
                    
                    if (Math.sqrt(r) < objet.r + obj.r){
                        
                        if (obj.masse < objet.masse || objet.getType() == "sun") {
                            
                            objetsDestructeurs.add(objet);
                            objetsDetruits.add(obj);
                        }
                    }
                }
            }
            objet.update(dt);
        }

        //on s'occupe des destructions

        while(objetsDestructeurs.size() > 0) {

            ObjetCeleste objet = objetsDestructeurs.getLast();
            ObjetCeleste obj = objetsDetruits.getLast();

            if (objet.getType() != "sun") {
                objet.r += obj.r * 0.3; //on augmente la taille
                objet.masse += obj.masse;
                for (int x=0; x<objet.atome.length; x++){
                    if (objet.atome[x]== false && obj.atome[x] == true){
                        objet.atome[x] = true;
                    }
                }
            }
                
            //animation de l'explosion

            explosionX = (int)obj.GetX();
            explosionY = (int)obj.GetY();
            explosionCounter = 0;

            explosionR = obj.r;
                
            //resize Image
            resizedExplosion = explosion.getScaledInstance(explosionR * 2, explosionR * 2, Image.SCALE_FAST);

            //active l'affichage
            if (!traj) {
                explosing = true;
            }
            
                //si l'objet sélectionné était l'un des 2, le nouvel objet est sélectionné 
            if (objSelected==obj) {
                objSelected=objet;
            }

            //on supprime l'objet 
            listeObj.remove(obj);
            objet.resize();

            objetsDetruits.remove(obj);
            objetsDestructeurs.remove(objet);
        }
    }
    /**
     * Créer une copie de la linkedList avec des objets fictifs
     * @param in
     * @param out
     */
    public void copie() {
        
        if (objetsTrajectoire.size() != 0) {
            objetsTrajectoire.clear();
        }
        for (ObjetCeleste obj : objets) {

            //on clone et on ajoute les éléments
            if (obj.getType() == "planet") {
                Planete np = new Planete(obj.GetMasse(), obj.GetVx(), obj.GetVy(), obj.GetX(), obj.GetY(), resizedPlanet,obj.GetR(), obj.getHUD(), obj.getatome());
                objetsTrajectoire.add(np);
            } else {
                Soleil ns = new Soleil(obj.GetMasse(), obj.GetX(), obj.GetY(), resizedPlanet,obj.GetR(), obj.getHUD());
                objetsTrajectoire.add(ns);
            }
        }
    }
    /**
     * Methode pour calculer la trajectoire d'un objet
     * @param obj l'objet dont on calcule les trajectoires
     */
    public void calculTraject(ObjetCeleste obj, Graphics g) {

        int rad = 2;

        //on pose des points sur les 20 prochaines itérations
        for (int i = 0; i < 200; i++) {

            //on check si l'objet n'a pas explosé

            if (objetsTrajectoire.contains(obj)) {
                
                //interractions entre les objets de la liste factice

                interractions(objetsTrajectoire, true);

                //affichage
                if (i%2 == 0) {
                    g.setColor(Color.red);
                    g.fillOval(updateSomeOffsetX(obj.GetX()) - rad, updateSomeOffsetY(obj.GetY()) - rad, 2 * rad, 2 * rad);
                }
            }
        }
    }


}