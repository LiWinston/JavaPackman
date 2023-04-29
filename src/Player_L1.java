import bagel.DrawOptions;
import bagel.Image;
import bagel.Keys;
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
    public Player_L1(double coordinateX, double coordinateY, ShadowPacLogic_L1 logic1) {
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
    protected boolean checkCollideWithGhost(Ghost gst) {
        if (this.getHitBox().intersects(gst.getHitBox())) {
            if(logicL1.getisFrenzy()){
                score+=gst.getScore();
            }else{
                dieAndReset();
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidPosition(double X, double Y) {
        return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight() &&
                !(isToCollideWithWall(X, Y, logicL1)));
    }

    public void move(Keys key) {
        int STEP_SIZE = logicL1.getisFrenzy() ? 4 : 3;
        double X = getCoordinateX(), Y = getCoordinateY();
        switch (key) {
            case LEFT:
                if (isValidPosition(X - STEP_SIZE, Y)) setCoordinateX(X - STEP_SIZE);
                break;
            case RIGHT:
                if (isValidPosition(X + STEP_SIZE, Y)) setCoordinateX(X + STEP_SIZE);
                break;
            case UP:
                if (isValidPosition(X, Y - STEP_SIZE)) setCoordinateY(Y - STEP_SIZE);
                break;
            case DOWN:
                if (isValidPosition(X, Y + STEP_SIZE)) setCoordinateY(Y + STEP_SIZE);
                break;
        }
    }
    public void dieAndReset() {
        --Life;
        if (Life == 0) {
            logicL1.gameFailed();
        }
        setCoordinateX((int) originPos.x);
        setCoordinateY((int) originPos.y) ;
        radians = TORIGHT;
    }

}


