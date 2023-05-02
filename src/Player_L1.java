import bagel.Image;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Objects;

/**
 * Player_L1 extends Player_L0, All methods and properties that should be inherited and shared are encapsulated in the
 * parent class, and only the parts related to game logic 2 are rewritten or added
 */
public class Player_L1 extends Player_L0 {
    private final static Image playerOpenMouth = new Image("res/pacOpen.png"); // image of the player with open mouth
    private final static Image playerCloseMouth = new Image("res/pac.png");// image of the player with closed mouth
    private Ghost lastCollision = null;


    /**
     * Creates a new GameUnit object with the specified coordinates.
     *
     * @param coordinateX the x-coordinate of the player
     * @param coordinateY the y-coordinate of the player
     */
    public Player_L1(double coordinateX, double coordinateY, ShadowPacLogic_L1 logic1) {
        super(coordinateX, coordinateY, logic1);
        setCurrentFrame(0);
        setOriginPos(new Point(coordinateX, coordinateY));
        this.setLife(3);
        this.setScore(0);
        setAIMSCORE(800);
        setHitBox(new Rectangle(coordinateX, coordinateY, getPlayerOpenMouth().getWidth(), getPlayerCloseMouth().getHeight()));
    }

    protected static Image getPlayerOpenMouth() {
        return playerOpenMouth;
    }

    protected static Image getPlayerCloseMouth() {
        return playerCloseMouth;
    }

    /**
     * Checks whether the player has won and updates the game stage accordingly.
     */
    public void checkWin() {
        if (this.getScore() >= getAIMSCORE()) {
            getLogicL1().gameSucceeded();
        }
    }

    /**
     * Checks whether the player has collided with a ghost or eaten a dot(includes dot-like units).
     * executed based on current position(or rather after move)
     */

    public void checkAround() {
        for (Ghost gst : getLogicL1().getGhostList()) {
            if (this.isAround(gst)) {
                if (checkCollideWithGhost(gst)) break;
            }
        }
        for (Dot dt : getLogicL1().getDotList()) {
            if (this.isAround(dt)) {
                EatDot(dt);
            }
        }
    }
    @Override
    protected void EatDot(Dot dt) {
        if (dt.isExist() && this.getHitBox().intersects(dt.getHitBox())) {
            dt.setExist(false);
            this.setScore(this.getScore() + dt.getScore());
            if(Objects.equals(dt.getType(), "Pellet")) {
                getLogicL1().setFrenzy();
            }
            checkWin();
        }
    }

    /**
     * Introduce lastCollision to record ghost in case it keeps to deduce Life
     * while a collision happens and ghost has not left the player hitbox area
     * @param gst the ghost being checked for collision
     * @return T/F
     */
    protected boolean checkCollideWithGhost(Ghost gst) {
        if(gst.getHidden()) return false;
        if(gst == getLastCollision()){
            if (this.getHitBox().intersects(gst.getHitBox())){
                return false;
            }
            setLastCollision(null);
        }
        if (this.getHitBox().intersects(gst.getHitBox())) {
            if(getLogicL1().getisFrenzy()){
                setScore(getScore() + gst.getScore());
                gst.setHidden();
            }else{
                dieAndReset();
                gst.reset();
                setLastCollision(gst);
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidPosition(double X, double Y) {
        return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight() &&
                !(isToCollideWithWall(X, Y, getLogicL1())));
    }

    //Repeated codes here can not be once again streamline due to local method invoke
    public void move(Keys key) {
        int STEP_SIZE = getLogicL1().getisFrenzy() ? 4 : 3;
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
        setLife(getLife() - 1);
        if (getLife() == 0) {
            getLogicL1().gameFailed();
        }
        setCoordinateX((int) getOriginPos().x);
        setCoordinateY((int) getOriginPos().y) ;
        setRadians(getTORIGHT());
    }

    protected Ghost getLastCollision() {
        return lastCollision;
    }

    protected void setLastCollision(Ghost lastCollision) {
        this.lastCollision = lastCollision;
    }

    public double getSTEP_SIZE(){
        return getLogicL1().getisFrenzy() ? 4 : 3;
    }
}


