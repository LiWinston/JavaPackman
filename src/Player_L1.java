import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Player_L1 extends Player_L0 {
    private final static Image playerOpenMouth = new Image("res/pacOpen.png"); // image of the player with open mouth
    private final static Image playerCloseMouth = new Image("res/pac.png");// image of the player with closed mouth
//    private final ShadowPacLogic_L1 logicL1;


    /**
     * Creates a new GameUnit object with the specified coordinates.
     *
     * @param coordinateX the x-coordinate of the player
     * @param coordinateY the y-coordinate of the player
     */
    public Player_L1(int coordinateX, int coordinateY, ShadowPacLogic_L1 logic1) {
        super(coordinateX, coordinateY, logic1);
        currentFrame = 0;
        originPos = new Point(coordinateX, coordinateY);
        this.Life = 3;
        this.score = 0;
        AIMSCORE = 800;
        setHitBox(new Rectangle(coordinateX, coordinateY, playerOpenMouth.getWidth(), playerCloseMouth.getHeight()));
    }
    /**
     * Checks whether the player has won and updates the game stage accordingly.
     */
    public void checkWin() {
        if (this.score >= AIMSCORE) {
            logicL1.gameSucceeded();
        }
    }
    /**
     * Checks whether the player has collided with a ghost or eaten a dot(inludes dot-like units).
     * executed based on current position(or rather after move)
     */

    public void checkAround() {
        for (Ghost gst : logicL1.getGhostList()) {
            if (this.isAround(gst)) {
                if (checkCollideWithGhost(gst)) break;
            }
        }
        for (Dot dt : logicL1.getDotList()) {
            if (this.isAround(dt)) {
                EatDot(dt);
            }
        }
    }

}


