/**
 * Mouton
 * premiere implémentation sans POO 
 */
public class Mouton {

    //variables

    private int range;
    public int x,y,width,height;

    public Mouton(int xPos, int yPos){

        range = 200;
        x = xPos;
        y = yPos;
        width = 20;
        height = 20;

    }

    //accessseurs

    public int GetRange(){
        return range;
    }
}