
/**
 * Delegation Interface for decoupling Player and ShadowPac to some extent
 */
public interface GameLogic {

    Ghost[] getGhostList();
    Dot[] getDotList();
    Wall[] getWallList();
    void gameFailed();
    void gameSucceeded();

    void letPlayerCheckAround();
}
