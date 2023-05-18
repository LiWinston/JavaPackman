import java.util.List;

/**
 * The ShadowPacLogic(Level 1) class provides custom logic for the ShadowPac game.
 * This class handles the game state when the game is either failed or succeeded,
 * and provides access to the lists of Ghosts, Dots and Walls in the game.
 * Notice that setPlayer() MUST BE DONE RIGHT AFTER AN playerL0 HAS BEEN INITIALIZE
 *
 * @author YongchunLi
 */
public class ShadowPacLogic_L1 {
    private final short gamePID;//HashCode of game ID, no need to consider but has been implemented and kind of lazy to delete
    private final ShadowPac game;
    private Player playerL1;

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

    /**
     * Sets the player's position using level 1 logic.
     *
     * @param x     the X coordinate of the player
     * @param y     the Y coordinate of the player
     * @param logic the level 1 logic object
     */
    public void setPlayer_L1(double x, double y, ShadowPacLogic_L1 logic) {
        this.setPlayerL1(new Player(x, y, logic));
    }


    /**
     * Method to retrieve the list of Ghosts in the ShadowPac game.
     *
     * @return a List of Ghost objects representing the Ghosts in the game
     */
    public List<Ghost> getGhostList() {
        return getGame().getGhostList_L1();
    }

    /**
     * Method to retrieve the list of Dots in the ShadowPac game.
     *
     * @return a List of Dot objects representing the Dots in the game
     */
    public List<Dot> getDotList() {
        return getGame().getDotList_L1();
    }

    /**
     * Method to retrieve the list of Walls in the ShadowPac game.
     *
     * @return a List of Wall objects representing the Walls in the game
     */
    public List<Wall> getWallList() {
        return getGame().getWallList_L1();
    }

    /**
     * Method to set the game state to 'Failed'.
     */
    public void gameFailed() {
        getGame().setGameStageLOSE(this);
    }

    /**
     * Method to set the game state to 'Success'.
     */
    public void gameSucceeded() {
        getGame().setGameStageWIN(this);
    }

    /**
     * Method to call Player_L1 to checkAround without receiving a Game reference.
     */
    public void letPlayerCheckAround() {
        if (null == this.getPlayerL1()) {
            System.err.println("Need Set playerL1 for ShadowPacLogic object!");
            System.err.println();
            return;
        }
        getPlayerL1().checkAround();
    }

    /**
     * Returns the reference to the Player instance in level 1.
     *
     * @return the Player instance in level 1
     */
    public Player getPlayer() {
        return getPlayerL1();
    }

    /**
     * Retrieves the gamePID value.
     *
     * @return the gamePID value
     */
    public short getPID() {
        return getGamePID();
    }

    /**
     * Checks if the game is currently in frenzy mode.
     *
     * @return true if the game is in frenzy mode, false otherwise
     */
    public boolean isFrenzyMode() {
        return getGame().getisFrenzy();
    }

    /**
     * Sets the game into frenzy mode.
     */
    public void setFrenzyMode() {
        getGame().setFrenzy(this);
    }

    /**
     * Retrieves the game's process ID.
     *
     * @return the process ID of the game
     */
    private short getGamePID() {
        return gamePID;
    }

    /**
     * Retrieves the ShadowPac game instance.
     *
     * @return the ShadowPac game instance
     */
    private ShadowPac getGame() {
        return game;
    }

    /**
     * Retrieves the Player instance for Level 1.
     *
     * @return the Player instance for Level 1
     */
    private Player getPlayerL1() {
        return playerL1;
    }

    /**
     * Sets the Player instance for Level 1.
     *
     * @param playerL1 the Player instance for Level 1
     */
    private void setPlayerL1(Player playerL1) {
        this.playerL1 = playerL1;
    }
}
