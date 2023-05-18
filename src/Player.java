import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.List;
import java.util.Objects;

/**
 * Represents the main player character in the game.
 * The player can move, collide with objects, and perform various actions.
 * It keeps track of the player's score, life, angle, collision box, and can blink its mouth open and closed.
 * The player can independently detect collisions with different objects and perform specific actions based on the collision type,
 * such as pre-judging collision with walls, and checking completed collisions with ghosts and dots.
 * It also keeps track of the origin position and can return to the origin when necessary.
 *
 * This class extends the GameUnit class.
 *
 * The player class is an important component of the game and is used in different levels of the game logic.
 * It provides methods for movement, collision detection, and updating the game state.
 *
 * @author YongchunLi
 */
public class Player extends GameUnit {
    private final static Image playerOpenMouth = new Image("res/pacOpen.png"); // image of the player with open mouth
    private final static Image playerCloseMouth = new Image("res/pac.png");// image of the player with closed mouth
    private static final double STEPSIZE_L1 = 4;
    private static final double STEPSIZEFRENZY_L1 = 3;
    private static int AIMSCORE;// target score -- Not set Final for Scalability(Maybe required to change half way)
    private final DrawOptions drop = new DrawOptions();// draw options for the player
    private Ghost lastCollision = null;
    private int Life; // number of lives the player has left
    private double radians = 0;// angle of player movement, same as direction of drawing
    private int currentFrame; // current frame counter, for converting image
    private int currentStatus = 1;// current status of player mouth (1 for open, 0 for closed)
    private int score;// current score of the player


    /**
     * Constructor for the Player class level 0.
     *
     * @param coordinateX the X coordinate of the player
     * @param coordinateY the Y coordinate of the player
     * @param logic0      the instance of the gameLogic0
     */
    public Player(double coordinateX, double coordinateY, ShadowPacLogic_L0 logic0) {
        // Call the superclass constructor with the player coordinates and game logic instance
        super(coordinateX, coordinateY, logic0);

        // Set the game logic instance for level 0
        this.setLogicL0(logic0);

        // Set the current frame to 0
        setCurrentFrame(0);

        // Set the origin position using a Point object
        setOriginPos(new Point(coordinateX, coordinateY));

        // Set the player's initial life count to 3
        this.setLife(3);

        // Set the player's initial score to 0
        this.setScore(0);

        // Set the AIMED score target based on the supposed number of dots in level 0 (10 points per dot)
        setAIMSCORE(getLogicL0().getSupposedDotNum() * 10);

        // Set the player's hitbox using a Rectangle object, based on the dimensions of the playerCloseMouth image
        setHitBox(new Rectangle(coordinateX, coordinateY, getPlayerCloseMouth().getWidth(), getPlayerCloseMouth().getHeight()));
    }

    /**
     * Constructor for the Player class level 1.
     *
     * @param coordinateX the X coordinate of the player
     * @param coordinateY the Y coordinate of the player
     * @param logic1      the instance of the gameLogic1
     */
    public Player(double coordinateX, double coordinateY, ShadowPacLogic_L1 logic1) {
        // Call the superclass constructor with the player coordinates and game logic instance
        super(coordinateX, coordinateY, logic1);

        // Set the current frame to 0
        setCurrentFrame(0);

        // Set the origin position using a Point object
        setOriginPos(new Point(coordinateX, coordinateY));

        // Set the player's initial life count to 3
        this.setLife(3);

        // Set the player's initial score to 0
        this.setScore(0);

        // Set a fixed AIM score target of 800 for level 1
        setAIMSCORE(800);

        // Set the player's hitbox using a Rectangle object, based on the dimensions of the playerOpenMouth image
        setHitBox(new Rectangle(coordinateX, coordinateY, getPlayerOpenMouth().getWidth(), getPlayerCloseMouth().getHeight()));
    }

    /**
     * Returns the image of the player with an open mouth.
     *
     * @return the image of the player with an open mouth
     */
    protected static Image getPlayerOpenMouth() {
        return playerOpenMouth;
    }

    /**
     * Returns the image of the player with a closed mouth.
     *
     * @return the image of the player with a closed mouth
     */
    protected static Image getPlayerCloseMouth() {
        return playerCloseMouth;
    }

    /**
     * Returns the score aim.
     *
     * @return the score value as the new score aim for player
     */
    protected static int getAIMSCORE() {
        return AIMSCORE;
    }

    /**
     * Sets the score aim.
     *
     * @param AIMSCORE the new score aim for player
     */
    protected static void setAIMSCORE(int AIMSCORE) {
        Player.AIMSCORE = AIMSCORE;
    }

    /**
     * Returns the current number of remaining lives for the player.
     *
     * @return the current number of remaining lives for the player
     */
    public int getLife() {
        return Life;
    }

    /**
     * Sets the number of remaining lives for the player.
     *
     * @param life the new number of remaining lives for the player
     */
    public void setLife(int life) {
        Life = life;
    }


    /**
     * Checks whether the player has won and updates the game stage accordingly.
     */
    public void checkWin() {
        if (this.getScore() >= getAIMSCORE()) {
            if (inL0()) getLogicL0().level_completed();
            if (inL1()) getLogicL1().gameSucceeded();
        }
    }

    /**
     * Checks whether the player has lost.
     * Shared between L0 and L1
     */

    public void checkLose() {
        if (this.getLife() <= 0) {
            getLogicL1().gameFailed();
        }
    }

    /**
     * Checks whether the player has collided with a ghost or eaten a dot(includes dot-like units).
     * This method is executed based on the player's current position after making a move.
     * Added optimization to only check for collisions if two units are close enough.
     */
    public void checkAround() {
        if (inL0()) {
            for (Ghost gst : getLogicL0().getGhostList()) {
                if (this.isAround(gst)) {
                    if (checkCollideWithGhost(gst)) break;
                }
            }
            for (Dot dt : getLogicL0().getDotList()) {
                if (this.isAround(dt)) {
                    EatDot(dt);
                }
            }
        } else if (inL1()) {
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
    }

    /**
     * Checks whether the player has collided with a ghost.
     * Introduce collision target recording to prevent possible respawn point overlap (although it did not happen in the
     * given CSV) and immediate redeath after respawn, which may cause repeated deduction of life in a single collision.
     * Typical scenario: Walking towards the pink ghost in opposite directions at the midpoint of the bottom line.
     *
     * @param gst the ghost being checked for collision
     * @return true if the player has collided with the ghost, and call dieAndReset
     */
    protected boolean checkCollideWithGhost(Ghost gst) {
        if (gst.getHidden()) return false;
        //check for spcial case for L1
        if (inL1()) {
            // Check for special case in Level 1
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
        }
        if (inL0()) {
            // Check for collision in Level 0
            if (this.getHitBox().intersects(gst.getHitBox())) {
                dieAndReset();
                return true; // Collision detected, call dieAndReset
            } else {
                return false; // No collision
            }
        }

        if (inL1()) {
            // Check for collision in Level 1
            if (this.getHitBox().intersects(gst.getHitBox())) {
                if (getLogicL1().isFrenzyMode()) {
                    setScore(getScore() + gst.getScore());
                    gst.setHidden();
                } else {
                    dieAndReset();
                    gst.reset();
                    setLastCollision(gst);
                }
                return true; // Collision detected, call dieAndReset or update score
            } else {
                return false; // No collision
            }
        }

        return false; // No collision
    }

    /**
     * Eats a dot if the player has collided with it.
     * and set gamemode to frenzy if the dot is a Pellet(Only in L1).
     * after eating turn the Dot existence to false
     * Once a dot is eaten check the win condition.
     *
     * @param dt the dot being checked for collision
     */
    protected void EatDot(Dot dt) {
        if (dt.isExist() && this.getHitBox().intersects(dt.getHitBox())) {
            dt.setExist(false);
            this.setScore(this.getScore() + dt.getScore());
            if (inL1()) {
                if (Objects.equals(dt.getType(), "Pellet")) {
                    getLogicL1().setFrenzyMode();
                }
            }
            checkWin();
        }
    }

    /**
     * check whether the attempt step is valid move for limiting the actual move of both Player and Player_L1.
     * Third parameter roughly defined as Object to react accordingly on game logic type.
     * If none of ShadowPacLogic_L0 and ShadowPacLogic_L1 matches the given logic, do nothing with a false return.
     *
     * @param x     attemptX
     * @param y     attemptY
     * @param logic the ShadowPacLogic instance used for delegation.
     * @return true for invalid due to Wall
     */
    public boolean isToCollideWithWall(double x, double y, Object logic) {
        Player newPl = null;
        if (logic instanceof ShadowPacLogic_L0) {
            newPl = new Player(x, y, (ShadowPacLogic_L0) logic);
        } else if (logic instanceof ShadowPacLogic_L1) {
            newPl = new Player(x, y, (ShadowPacLogic_L1) logic);
        }
        Rectangle try_hit = new Rectangle(new Point(x, y), getPlayerCloseMouth().getWidth(), getPlayerCloseMouth().getHeight());
        if (logic instanceof ShadowPacLogic_L0) {
            Wall[] walls = ((ShadowPacLogic_L0) logic).getWallList();
            for (Wall wl : walls) {
                if (newPl.isAround(wl)) {
                    if (try_hit.intersects(wl.getHitBox())) {
                        return true;
                    }
                }
            }
        } else if (logic instanceof ShadowPacLogic_L1) {
            List<Wall> walls = ((ShadowPacLogic_L1) logic).getWallList();
            for (Wall wl : walls) {
                if (newPl.isAround(wl)) {
                    if (try_hit.intersects(wl.getHitBox())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check whether it is within bounds at the edge of the map, and check whether it touches a wall
     *
     * @param X position x to be checked Z
     * @param Y position y to be checked Z
     * @return true if the given position is Valid Position and vice versa
     */
    protected boolean isValidPosition(double X, double Y) {
        if (inL0()) {
            return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight() &&
                    !(isToCollideWithWall(X, Y, getLogicL0())));
        }
        if (inL1()) {
            return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight() &&
                    !(isToCollideWithWall(X, Y, getLogicL1())));
        }
        return false;
    }

    /**
     * Move the player according to the input after performing a feasibility check
     *
     * @param key keyboard and mouse input
     */
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

    double getSTEP_SIZE() {
        if (getLogicL0() != null) return getLogicL0().getSTEP_SIZE();
        if (getLogicL1() != null) return getLogicL1().isFrenzyMode() ? STEPSIZE_L1 : STEPSIZEFRENZY_L1;
        return 0;
    }

    /**
     * Draws the player on the screen based on the input received from the user.
     *
     * @param input The input received from the user.
     */
    public void Draw(Input input) {
        if (inL1()) {
            checkWin();
            checkLose();
        }
        if (input.isDown(Keys.LEFT)) {
            setRadians(getTOLEFT());
            move(Keys.LEFT);
        } else if (input.isDown(Keys.RIGHT)) {
            setRadians(getTORIGHT());
            move(Keys.RIGHT);
        } else if (input.isDown(Keys.UP)) {
            setRadians(getTOUP());
            move(Keys.UP);
        } else if (input.isDown(Keys.DOWN)) {
            setRadians(getTODOWN());
            move(Keys.DOWN);
        }
        this.getHitBox().moveTo(new Point(getCoordinateX(), getCoordinateY()));// Move the hitbox to the new position
        setCurrentFrame(getCurrentFrame() + 1);
        if (getCurrentFrame() == getFrequency_Modulation()) {
            setCurrentStatus((getCurrentStatus() == 1 ? 0 : 1));
            setCurrentFrame(0);
        }
        switch (getCurrentStatus()) {
            case 0:
                DrawCloseMouth();
                break;
            case 1:
                DrawOpenMouth();
                break;
            default:
                throw new RuntimeException("Failed currentStatus!");
        }
    }

    /**
     * Retrieves the image size of the player when the mouth is closed.
     *
     * @return the image size of the player when the mouth is closed
     */
    @Override
    public double getImageSize() {
        return getPlayerCloseMouth().getHeight();
    }

    /**
     * Draws the player with an open mouth.
     */
    private void DrawOpenMouth() {
        getPlayerOpenMouth().drawFromTopLeft(getCoordinateX(), getCoordinateY(), getDrop().setRotation(getRadians()));
    }

    /**
     * Draws the player with a closed mouth.
     */
    private void DrawCloseMouth() {
        getPlayerCloseMouth().drawFromTopLeft(getCoordinateX(), getCoordinateY(), getDrop().setRotation(getRadians()));
    }

    /**
     * Resets the player's position to the original position and reduces the player's number of lives by 1.
     * If the player has no remaining lives, it triggers a game failure in the respective game logic level.
     */
    public void dieAndReset() {
        setLife(getLife() - 1);
        if (getLife() == 0) {
            if (inL0()) {
                getLogicL0().gameFailed();
            } else if (inL1()) {
                getLogicL1().gameFailed();
            }
        }
        setPosition(getOriginPos());
        setRadians(getTORIGHT());
    }

    /**
     * Sets the player's position to the specified position.
     *
     * @param Pos the position to set
     */
    protected void setPosition(Point Pos) {
        setCoordinateX((int) Pos.x);
        setCoordinateY((int) Pos.y);
        setHitBox(new Rectangle(Pos, getPlayerCloseMouth().getWidth(), getPlayerCloseMouth().getHeight()));
    }

    /**
     * Retrieves the player's score.
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score.
     *
     * @param score the score to set
     */
    protected void setScore(int score) {
        this.score = score;
    }

    /**
     * Retrieves the frequency modulation value.
     *
     * @return the frequency modulation value
     */
    protected int getFrequency_Modulation() {
        // frequency of mouth opening and closing
        return 15;
    }

    /**
     * Retrieves the draw options for the player.
     *
     * @return the draw options for the player
     */
    protected DrawOptions getDrop() {
        return drop;
    }

    /**
     * Retrieves the rotation angle of the player.
     *
     * @return the rotation angle of the player
     */
    protected double getRadians() {
        return radians;
    }

    /**
     * Sets the rotation angle of the player.
     *
     * @param radians the rotation angle to set
     */
    protected void setRadians(double radians) {
        this.radians = radians;
    }

    /**
     * Retrieves the current frame of the player.
     *
     * @return the current frame of the player
     */
    protected int getCurrentFrame() {
        return currentFrame;
    }

    /**
     * Sets the current frame of the player.
     *
     * @param currentFrame the current frame to set
     */
    protected void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Retrieves the current status of the player.
     *
     * @return the current status of the player
     */
    protected int getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Sets the current status of the player.
     *
     * @param currentStatus the current status to set
     */
    protected void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * Retrieves the last ghost the player collided with.
     *
     * @return the last ghost the player collided with
     */
    protected Ghost getLastCollision() {
        return lastCollision;
    }

    /**
     * Sets the last ghost the player collided with.
     *
     * @param lastCollision the last ghost to set
     */
    protected void setLastCollision(Ghost lastCollision) {
        this.lastCollision = lastCollision;
    }

    /**
     * Checks if the player is in logic level 0.
     *
     * @return true if the player is singly in logic level 0, false otherwise
     */
    private boolean inL0() {
        return null != getLogicL0() && null == getLogicL1();
    }

    /**
     * Checks if the player is in logic level 1.
     *
     * @return true if the player is singly in logic level 1, false otherwise
     */
    private boolean inL1() {
        return null != getLogicL1() && null == getLogicL0();
    }
}
