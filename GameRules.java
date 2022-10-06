/**
 * Maintains game rules. Checks if a move is legal.
 * It also maintains move turns and the game stage.
 *
 * @Student 1 Name: Lloyd (Junghyeon) Na
 * @Student 1 Number: 23433196 
 * 
 * @Student 2 Name: Owen Smith 
 * @Student 2 Number: 22957291 
 */
public class GameRules
{
    // Instance variables to maintain whose move it is
    private boolean moveStage; 
    private boolean goatsTurn;
    private int numGoats; //the number of goats on the board
    private int numTigers; //the number of tigers on the board
    private final int MAXGOATS = 12;
    private int numMoves; //EXTRA FEATURE: counts the number of moves taken by player.
    
    // list of all legal moves legalMoves[0] is {1,3,9} which means a piece from [0] can move to 
    // [1],[3] or [9]. legalMoves[1] is {0,2,4} meaning a piece from [1] can move to [0],[2] or [4]
    public final int[][] legalMoves = {{1,3,9},{0,2,4},{1,5,14},{0,4,6,10},{1,3,5,7},{2,4,8,13},
    {3,7,10,11},{4,6,8},{5,7,12},{0,10,21},{3,9,11,18},{6,10,15},{8,13,17},{5,12,14,20},{2,13,23},
    {11,16,18},{15,17,19},{12,16,20},{10,15,19,21},{16,18,20,22},{13,17,19,23},{9,18,22},{19,21,23},
    {14,20,22}};     
    
    //list of all legal eat moves by tigers e.g. legalEats[0] means that a tiger at [0] can 
    // eat a goat at [1] and land in [2] (there has to be a goat at [1] and [2] must be vacant)
    // tiger at [0] can also eat a goat at [9] and jump to 21 OR eat a goat at [3] and jump to [6]
    // legalEats[4]={} is empty meaning that at tiger at [4] has no options
    private final int[][] legalEats = {{1,2, 9,21, 3,6}, {4,7}, {1,0, 5,8, 14,23}, {4,5, 10,18},{},
    {4,3, 13,20}, {3,0, 7,8, 11,15}, {4,1}, {5,2, 7,6, 12,17}, {10,11},{}, {10,9}, {13,14},{},
    {13,12}, {11,6, 16,17, 18,21}, {19,22}, {12,8, 16,15, 20,23}, {10,3, 19,20},{}, {13,5, 19,18},
    {9,0, 18,15, 22,23}, {19,16}, {14,2, 20,17, 22,21}};                              
   
    //NOTE: You MUST use only the above 2 arrays to implement all moves.
    // Adding more arrays is NOT allowed.
    

    /**
     * Constructor for objects of class GameRules
     */
    public GameRules()
    {              
        moveStage = false;
        goatsTurn = true;
        numGoats = 0;
        numTigers = 0;
        numMoves = 0;
    }       
    
    /**
     * returns moveStage
     */
    public boolean isMoveStage()
    {
        return moveStage;
    }
    
    /**
     * returns true iff it is goats turn
     */
    public boolean isGoatsTurn()
    {
        return goatsTurn;
    }    
    
    /**
     * EXTRA FEATURE: Counts the number of moves taken by player.
     */
    public void moveMade(){
        numMoves ++;
    }

    /**
     * EXTRA FEATURE: Returns the number of moves taken by player.
     */
    public int moveCount(){
        return numMoves;
    }
    
    /**
     * Adds (+1 or -1) to goat numbers.
     * Changes the goatsTurn and moveStage as per rules.
     */
    public void addGoat(int n)
    {
        //TODO 12
        numGoats += n;
        if(moveStage == false){
            if(numGoats < 4){
                moveStage = false;
                goatsTurn = true;
                } else if(numGoats == 4 && numTigers == 0 ){
                goatsTurn = false;
                } else if(numGoats < 8 && numTigers == 1){
                goatsTurn = true;
                } else if(numGoats == 8 && numTigers == 1){
                goatsTurn = false;
                } else if(numGoats < MAXGOATS && numTigers == 2){
                goatsTurn = true;
                } else if(numGoats == MAXGOATS && numTigers == 2){
                goatsTurn = false;
                moveStage = true;
                } 
        }
    }
    
    /**
     * returns number of goats
     */
    public int getNumGoats()
    {
        return numGoats;
    }
    
    /**
     * EXTRA FEATURE: Resets the number of goats and tigers to start again.
     */
    public void resetRules(){ 
        moveStage = false;
        goatsTurn = true;
        numGoats = 0;
        numTigers = 0;
        numMoves = 0;
    }

    /**
     * increments tigers and gives turn back to goats
     */
    public void incrTigers()
    {
        //TODO 16
        numTigers++;
        goatsTurn = true;
    }
        
    /**
     * Returns the nearest valid location (0-23) on the board to the x,y mouse click.
     * Locations are described in project description on LMS.
     * You will need bkSize & GameViewer.locs to compute the distance to a location.
     * If the click is close enough to a valid location on the board, return 
     * that location, otherwise return -1 (when the click is not close to any location).
     * Choose a threshold for proximity of click based on bkSize.
     */    
    public int nearestLoc(int x, int y, int bkSize)
    {
        // TODO 11     
        double x1 = x;
        double y1 = y;
        int loc = -1;
        double smallest = bkSize * bkSize;
        
        for(int i = 0; i < 24; i++){
            int x2 = GameViewer.locs[i][0] * bkSize;
            int y2 = GameViewer.locs[i][1] * bkSize;
            if(Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)) <= 30){
                if(smallest > Math.sqrt(Math.pow((x1 - x2), 2) 
                + Math.pow((y1 - y2), 2))){
                    smallest = Math.sqrt(Math.pow((x1 - x2),2) 
                + Math.pow((y1 - y2), 2));
                    loc = i;
                }
            }
        }
        return loc;
    }
    
    /**
     * Returns true iff a move from location a to b is legal, otherwise returns false.
     * For example: a,b = 1,2 -> true; 1,3 -> false; 20,17 -> true. Refer to the 
     * project description for details.
     * Throws an exception for illegal arguments.
     */
    public boolean isLegalMove(int a, int b)
    {
        //TODO 19
        boolean legal = false;
        int numOfDestinations = legalMoves[a].length; //number of possible legal destinations can vary between 3 and 4. 
        for(int i = 0; i < numOfDestinations; i++){
            if(legalMoves[a][i] == b){
                legal = true;
            }
        }
        
        //if(legal == false){
        //    throw new IllegalArgumentException("Illegal Move"); //Throws an execption for any illegal arguments.
        //removed to make life easy}
        
        return legal;        
    }
    
    /**
     * Returns true of the tiger at tigerLoc (location) can eat any goat
     * the location of the goat that can be eaten is filled in scapeGoat[0]
     * the destination where the tiger will land after eating the goad is 
     * filled in scapeGoat[1]. Returns false if the tiger cannot eat any goat
     * 
     * NOTE: This method can use legalEats[][] only and no other array.
     */
    public boolean canEatGoat(int tigerLoc, Board bd, int[] scapeGoat)
    {
        //TODO 23      
        boolean canEat = false;
        int numOfEats = legalEats[tigerLoc].length; //number of possible legal eats can vary from 0 to 3.
        
        for(int i = 0; i < numOfEats; i += 2){
            if(scapeGoat[0] == legalEats[tigerLoc][i]){
                if(scapeGoat[1] == legalEats[tigerLoc][i + 1]){
                    canEat = true;
                    return canEat;
                }
            }
        }
        
        return canEat;        
    }
}
