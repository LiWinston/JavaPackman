import bagel.Image;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Objects;

/**
 * Player_L1 extends Player, All methods and properties that should be inherited and shared are encapsulated in the
 * parent class, and only the parts related to game logic 2 are rewritten or added
 *
 * @author YongchunLi
 */
public class Player_L1 extends Player {
    private static final double STEPSIZE = 4;
    private static final double STEPSIZEFRENZY = 3;
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

    /**
     * Eats a dot if the player has collided with it and dot is still existing.
     * and set gamemode to frenzy if the dot is a Pellet.
     * after eating turn the Dot existence to false.
     * Once a dot is eaten check the win condition.
     *
     * @param dt the dot being checked for collision
     */

    @Override
    protected void EatDot(Dot dt) {
        if (dt.isExist() && this.getHitBox().intersects(dt.getHitBox())) {
            dt.setExist(false);
            this.setScore(this.getScore() + dt.getScore());
            if (Objects.equals(dt.getType(), "Pellet")) {
                getLogicL1().setFrenzy();
            }
            checkWin();
        }
    }

    /**
     * Introduce collision target recording to prevent possible respawn point overlap (although it did not happen in the
     * given CSV) and immediate redeath after respawn, which may cause repeated deduction of life in a single collision.
     * Typical scenario: Walking towards the pink ghost in opposite directions at the midpoint of the bottom line.
     *
     * @param gst the ghost being checked for collision
     * @return T for Collide, F for not
     */
    protected boolean checkCollideWithGhost(Ghost gst) {
        if (gst.getHidden()) return false;// do not consider hidden ghost
        if (gst.equals(getLastCollision())) {
            if (this.getHitBox().intersects(gst.getHitBox())) {
                //The target to be detected is the target of the last collision, and the two have not yet separated,
                // so it is not considered a valid collision
                return false;
            }
            //The target to be detected is the one of the last collision, but the two have separated,
            // so the record is reset to zero.
            setLastCollision(null);
        }
        if (this.getHitBox().intersects(gst.getHitBox())) {
            if (getLogicL1().getisFrenzy()) {
                setScore(getScore() + gst.getScore());
                gst.setHidden();
            } else {
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

    //Repeated codes here, however can not be streamlined due to local method invoking
    //otherwise, such as super.move(key) will lead to invoking of the parent isValidPosition().
    public void move(Keys key) {
        double STEP_SIZE = getSTEP_SIZE();
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
        setPosition(getOriginPos());
        setRadians(getTORIGHT());
    }

    protected Ghost getLastCollision() {
        return lastCollision;
    }

    protected void setLastCollision(Ghost lastCollision) {
        this.lastCollision = lastCollision;
    }

    public double getSTEP_SIZE() {
        return getLogicL1().getisFrenzy() ? STEPSIZE : STEPSIZEFRENZY;
    }
}


