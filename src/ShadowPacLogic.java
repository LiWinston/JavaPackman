/**
 * The ShadowPacLogic class implements the GameLogic interface and provides
 * custom logic for the ShadowPac game. This class handles the game state when
 * the game is either failed or succeeded, and provides access to the lists
 * of Ghosts, Dots and Walls in the game.
 * Notice that setPlayer() MUST BE DONE RIGHT AFTER AN player HAS BEEN INITIALIZED
 * @YongchunLi
*/
public class ShadowPacLogic implements GameLogic {
    private final ShadowPac game;
    private Player player;

    /**
     * Constructor for ShadowPacLogic class that takes in a ShadowPac game instance
     * and assigns it to the private variable 'game'.
     *
     * @param game the ShadowPac game instance
     */
    public ShadowPacLogic(ShadowPac game) {
        this.game = game;
    }

    /**
     * Notice that setPlayer() MUST BE DONE RIGHT AFTER AN player HAS BEEN INITIALIZED
     * @param player Player Instance for initialization
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setPlayer(int x, int y,ShadowPacLogic logic){
        this.player = new Player(x,y,logic);
    }
    /*
     * Method to retrieve the list of Ghosts in the ShadowPac game.
     *
     * @return an array of Ghost objects representing the Ghosts in the game
     */
    @Override
    public Ghost[] getGhostList() {
        return game.getGhostList();
    }
    /**
     * Method to retrieve the list of Dots in the ShadowPac game.
     *
     * @return an array of Dot objects representing the Dots in the game
     */
    @Override
    public Dot[] getDotList() {
        return game.getDotList();
    }
    /**
     * Method to retrieve the list of Walls in the ShadowPac game.
     *
     * @return an array of Wall objects representing the Walls in the game
     */
    @Override
    public Wall[] getWallList() {
        return game.getWallList();
    }

    /**
     * Method to set the game state to 'Failed'.
     */
    @Override
    public void gameFailed() {
        game.setGameStageLOSE();
    }
    /**
     * Method to set the game state to 'Success'.
     */
    @Override
    public void gameSucceeded() {
        game.setGameStageWIN();
    }
    /**
     * Method to call Player to checkAround without receiving a Game reference.
     */
    @Override
    public void letPlayerCheckAround() {
        if(null == this.player) System.err.println("Need Set player for ShadowPacLogic object!");
        player.checkAround();
    }

    /*
     * Method to get the game DotNum.
     */
    public int getSupposedDotNum() {
        return game.getSupposedDotNum();
    }
    /*
     * Method to get the game StepSize.
     */
    public int getSTEP_SIZE() {
        return ShadowPac.getSTEP_SIZE();
    }
    /*
     * Method to get the player instance reference.
     */
    public Player getPlayer() {
        return player;
    }
}
