import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

    
public class GameViewer implements MouseListener
{
	public static void main(String []args){
        GameViewer gameviewer = new GameViewer();
    }

    private int bkSize; 
    private int brdSize; 
    private SimpleCanvas sc; 
    private GameRules rules; 
    private Board bd; 
    private AIplayer ai; 
    private boolean preGame;
    private boolean midGame;
                                 
    public static final int[][] locs = {{1,1},                  {4,1},                  {7,1},
    
                                                {2,2},          {4,2},          {6,2},
                                                
                                                        {3,3},  {4,3},  {5,3}, 
                                                        
                                        {1,4},  {2,4},  {3,4},          {5,4},  {6,4},  {7,4},
                                        
                                                        {3,5},  {4,5},  {5,5},
                                                        
                                                {2,6},          {4,6},          {6,6},        
                                        
                                        {1,7},                  {4,7},                  {7,7} };
                                                             
    private int[] mov = {-1,-1}; 

    public GameViewer(int bkSize)
    {        
        this.bkSize = bkSize;
        brdSize = bkSize*8;
        sc = new SimpleCanvas("Tigers and Goats", brdSize, brdSize, Color.BLUE);
        sc.addMouseListener(this);           
        rules = new GameRules();
        bd = new Board();
        ai = new AIplayer();
        preGameSetup();             
                              
    }
    
    public void preGameSetup(){
        preGame = true;
        midGame = false;
        drawInstructions();
    }
    public void drawInstructions(){
        sc.drawRectangle(0,0,brdSize,brdSize, Color.LIGHT_GRAY);
        sc.drawRectangle(5, 5, bkSize + 10, bkSize/2, Color.WHITE);
        sc.drawString("Start Game", 12, 27, Color.BLACK);
        sc.drawString("Instructions:", 55, 60, Color.BLACK);
        sc.drawString("--> Place 12 goats (only where lines intersect)", 60, 80, Color.BLACK);
        sc.drawString("--> Try & trap the tigers so they cannot move ", 60, 100, Color.BLACK);
        sc.drawString("--> You can only move to an empty space that is next to you! ", 60, 120, Color.BLACK);
        sc.drawString("--> Tigers can eat a goat by jumping over it and landing in a vacant spot ", 60, 140, Color.BLACK);
        sc.drawString("--> If you lose more than half of your goats you lose.", 60, 160, Color.BLACK);
        sc.drawString("Good Luck! Try to win in as little moves as possible!", 80, 200, Color.BLACK);
        
    }
    public boolean startGame(int x, int y){ 
        
        boolean hit = false;
        int rx = 5;
        int ry = 5;
        int rx2 = bkSize + 10;
        int ry2 = bkSize/2;
        if(x >= rx && x <= rx2 && y >= ry && y <= ry2){ 
            hit = true;
        }
        return hit;
    }
    public void start(){
        preGame = false;
        midGame = true;
        drawBoard();
    }

    public boolean viewInstructions(int x, int y){
        boolean hit = false;
        int rx = 5;
        int ry = 40;
        int rx2 = bkSize -25;
        int ry2 = bkSize - 20;
        if(x >= rx && x <= rx2 && y >= ry && y <= ry2){ 
            hit = true;
        }
        return hit;
    }

    public void drawInstructionsV2(){
        sc.drawRectangle(0,0,brdSize,brdSize, Color.LIGHT_GRAY);
        sc.drawRectangle(5, 5, bkSize + 10, bkSize/2, Color.WHITE);
        sc.drawString("Resume", 16, 27, Color.BLACK);
        sc.drawString("Instructions:", 55, 60, Color.BLACK);
        sc.drawString("--> Place 12 goats (only where lines intersect)", 60, 80, Color.BLACK);
        sc.drawString("--> Try & trap the tigers so they cannot move ", 60, 100, Color.BLACK);
        sc.drawString("--> You can only move to an empty space that is next to you! ", 60, 120, Color.BLACK);
        sc.drawString("--> Tigers can eat a goat by jumping over it and landing in a vacant spot ", 60, 140, Color.BLACK);
        sc.drawString("--> If you lose more than half of your goats you lose.", 60, 160, Color.BLACK);
    }
    
    public boolean resumeGame(int x, int y){ 
        boolean hit = false;
        int rx = 5;
        int ry = 5;
        int rx2 = bkSize;
        int ry2 = bkSize/2;
        if(x >= rx && x <= rx2 && y >= ry && y <= ry2){
            hit = true;
        }
        return hit;
    }
    public void resume(){
        //Need to store values and locations of goats & tigers before resetting to resume from.
        drawBoard();
    }
    
    public GameViewer( )
    {
        this(80);
    }
    
    private void drawBoard()
    {
         sc.drawRectangle(0,0,brdSize,brdSize, new Color(0, 128, 128)); //wipe the canvas
        
        for(int i=1; i<9; i++)
        {
            if(i<4)
                sc.drawLine(locs[i-1][0]*bkSize, locs[i-1][1]*bkSize,
                        locs[i+5][0]*bkSize, locs[i+5][1]*bkSize, Color.WHITE);
            else if(i==4)
                sc.drawLine(locs[i+5][0]*bkSize, locs[i+5][1]*bkSize,
                        locs[i+7][0]*bkSize, locs[i+7][1]*bkSize, Color.WHITE);
            else if(i==5)
                sc.drawLine(locs[i+7][0]*bkSize, locs[i+7][1]*bkSize,
                        locs[i+9][0]*bkSize, locs[i+9][1]*bkSize, Color.WHITE);              
            else
                sc.drawLine(locs[i+9][0]*bkSize, locs[i+9][1]*bkSize,
                        locs[i+15][0]*bkSize, locs[i+15][1]*bkSize, Color.WHITE);              
           
            if(i==4 || i==8) continue; 
       
            sc.drawLine(i*bkSize, i*bkSize,
                        i*bkSize, brdSize-i*bkSize,Color.white);            
       
            sc.drawLine(i*bkSize,         i*bkSize,
                        brdSize-i*bkSize, i*bkSize, Color.white);      
        }
        

        sc.drawRectangle(5, 5, bkSize, bkSize/3, Color.WHITE);
        sc.drawString("Restart", bkSize/4, bkSize/4, Color.BLACK); 
        
        sc.drawRectangle(5, 40, bkSize -25, bkSize - 20, Color.WHITE);
        sc.drawString("Rules", 10, 55, Color.BLACK);
   
        for(int i = 0; i < 24; i++){
            if(bd.isVacant(i) == false){
                if(bd.isGoat(i) == true){
                    sc.drawDisc(locs[i][0] * bkSize + bkSize/14, locs[i][1] * bkSize + bkSize/14, 20, Color.BLACK);
                    sc.drawDisc(locs[i][0] * bkSize, locs[i][1] * bkSize, 20, new Color(98, 176, 245));
                } else if(bd.isGoat(i) == false){
                    sc.drawCircle(locs[i][0] * bkSize + bkSize/14, locs[i][1] * bkSize + bkSize/14, 20, Color.BLACK);
                    sc.drawCircle(locs[i][0] * bkSize, locs[i][1] * bkSize, 20, new Color(250, 71, 71));
                }
            }
        }
        
        sc.drawString("Number of Goats: " + rules.getNumGoats(), brdSize/2 - bkSize/2, brdSize/20, Color.WHITE);
        sc.drawString("Moves Made: " + rules.moveCount(), brdSize/2 - bkSize/2, brdSize/13, Color.WHITE);
    }
    
        public void restart(){ 
        bd.setAllVacant();
        rules.resetRules();
        ai.resetAIclass();
        drawBoard();
    }

    public boolean restartLocation(int x, int y){ 
        boolean hit = false;
        int rx = 5;
        int ry = 5;
        int rx2 = bkSize;
        int ry2 = bkSize/3;
        if(x >= rx && x <= rx2 && y >= ry && y <= ry2){ 
            hit = true;
        }
        return hit;
    }
    
    public void placeGoat(int loc) 
    {   
        if(rules.isMoveStage() == false){
            if(bd.isVacant(loc) && rules.getNumGoats() <= 11){
                bd.setGoat(loc);
                rules.addGoat(1);
                drawBoard();
                }
        }
    }
    
    public void placeTiger() 
    {   
        ai.placeTiger(bd);
        rules.incrTigers();
        drawBoard();
    }
    
    public void selectGoatMove(int loc) 
    {   
        if(mov[0] == -1 && mov[1] == -1){
            if(bd.isGoat(loc)){
                mov[0] = loc;
                sc.drawDisc(locs[loc][0] * bkSize, locs[loc][1] * bkSize, 20, Color.ORANGE);
            }
        } else if(mov[0] == loc && mov[1] == -1){
                mov[0] = -1;
                sc.drawDisc(locs[loc][0] * bkSize, locs[loc][1] * bkSize, 20, new Color(98, 176, 245));
        }

        if(mov[0] != -1 && mov[1] == -1){
            if(bd.isGoat(loc)){
                sc.drawDisc(locs[mov[0]][0] * bkSize, locs[mov[0]][1] * bkSize, 20, new Color(98, 176, 245));
                mov[0] = loc;
                sc.drawDisc(locs[loc][0] * bkSize, locs[loc][1] * bkSize, 20, Color.ORANGE);
            } else if(bd.isVacant(loc)){
                mov[1] = loc;
                moveGoat();
            }
        }
    }
    
    public void moveGoat() 
    {   
        if(rules.isLegalMove(mov[0], mov[1])){
            bd.swap(mov[0], mov[1]);
            drawBoard();    
            mov[1] = -1;
            mov[0] = -1;
            rules.moveMade();
            tigersMove();
        } else{
            mov[1] = -1;
        }
    }
 
    public void tigersMove()
    { 
        int makeAmoveResult = ai.makeAmove(bd);
        
        if(makeAmoveResult == 1){
            rules.addGoat(-1); 
        }
        
        drawBoard();
        
        if(makeAmoveResult == -1){
            sc.drawString("Goats Win!", brdSize/2 - bkSize/2, brdSize/2, Color.WHITE);
            sc.drawString(" In " +rules.moveCount() + " moves!", brdSize/2 - bkSize/2, (brdSize/2) + 20, Color.WHITE);
        }
        
        if(rules.getNumGoats() < 6){
            sc.drawString("Tigers Win!", brdSize/2 - bkSize/2, brdSize/2, Color.WHITE);
        }
        
    }   
    
    public void mousePressed(MouseEvent e) 
    {
        int x = e.getX();
        int y = e.getY();
        
        if(rules.isMoveStage() == false && preGame == false){
            if(rules.isGoatsTurn()){
                if(rules.nearestLoc(x, y, bkSize) != -1){
                    placeGoat(rules.nearestLoc(x, y, bkSize));
                    if(rules.isGoatsTurn() == false){
                        placeTiger();
                    }
                }
            }
        } else{
            if(rules.nearestLoc(x, y, bkSize) != -1 && preGame == false){
                selectGoatMove(rules.nearestLoc(x, y, bkSize));
            }
        }
        
        if(midGame == true && restartLocation(x, y) && preGame == false){
            restart();
        }

        if(preGame == true && startGame(x, y)){
            start();
        }

        if(midGame == true && viewInstructions(x, y)){
            drawInstructionsV2();
        }

        if(midGame == true && resumeGame(x, y)){
            resume();
        }
    }
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
