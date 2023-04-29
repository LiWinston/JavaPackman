import java.util.List;

/**
 * The ShadowPacLogic(Level 0) class provides custom logic for the ShadowPac game.
 * This class handles the game state when the game is either failed or succeeded,
 * and provides access to the lists of Ghosts, Dots and Walls in the game.
 * Notice that setPlayer() MUST BE DONE RIGHT AFTER AN playerL0 HAS BEEN INITIALIZED
 *
 * @YongchunLi
 */
public class ShadowPacLogic_L1 {
    private final short gamePID;//HashCode of game ID
    private final ShadowPac game;
    private Player_L1 playerL1;

    /**
     * Constructor for ShadowPacLogic class that takes in a ShadowPac game instance
     * and assigns it to the private variable 'game'.
     *
     * @param game the ShadowPac game instance
     */
    public ShadowPacLogic_L1(ShadowPac game) {
        this.game = game;
        gamePID = game.getPID();
    }
    public void setPlayer_L1(int x, int y, ShadowPacLogic_L1 logic) {
        this.playerL1 = new Player_L1(x, y, logic);
    }


    /*
     * Method to retrieve the list of Ghosts in the ShadowPac game.
     *
     * @return an array of Ghost objects representing the Ghosts in the game
     */

    public List<Ghost> getGhostList() {
        return game.getGhostList_L1();
    }

    /**
     * Method to retrieve the list of Dots in the ShadowPac game.
     *
     * @return a List of Dot objects representing the Dots in the game
     */

    public List<Dot> getDotList() {
        return game.getDotList_L1();
    }

    /**
     * Method to retrieve the list of Walls in the ShadowPac game.
     *
     * @return an array of Wall objects representing the Walls in the game
     */

    public List<Wall> getWallList() {
        return game.getWallList_L1();
    }

    /**
     * Method to set the game state to 'Failed'.
     */

    public void gameFailed() {
        game.setGameStageLOSE(this);
    }

    /**
     * Method to set the game state to 'Success'.
     */

    public void gameSucceeded() {
        game.setGameStageWIN(this);
    }

    /**
     * Method to call Player_L0 to checkAround without receiving a Game reference.
     */

    public void letPlayerCheckAround() {
        if (null == this.playerL1) {
            System.err.println("Need Set playerL1 for ShadowPacLogic object!");
            System.err.println();
            return;
        }
        playerL1.checkAround();
    }

    /*
     * Method to get the playerL0 instance reference.
     */
    public Player_L1 getPlayer() {
        return playerL1;
    }

    /**
     * Notice that setPlayer() MUST BE DONE RIGHT AFTER AN playerL0 HAS BEEN INITIALIZED
     *
     * @param playerL1 Player_L0 Instance for initialization
     */
    public void setPlayer(Player_L1 playerL1) {
        this.playerL1 = playerL1;
    }

    public short getPID() {
        return gamePID;
    }

    public boolean getisFrenzy() {
        return game.getisFrenzy();
    }
    public void setFrenzy(){
        game.setFrenzy(this);
    }
}
