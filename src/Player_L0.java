import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.List;

public class Player_L0 extends GameUnit {
    private final static Image playerOpenMouth = new Image("res/pacOpen.png"); // image of the player with open mouth
    private final static Image playerCloseMouth = new Image("res/pac.png");// image of the player with closed mouth
    private static int AIMSCORE;// target score -- Not set Final for Scalability(Maybe required to change half way)
    private final DrawOptions drop = new DrawOptions();// draw options for the player

    private int Life; // number of lives the player has left
    private double radians = 0;// angle of player movement, same as direction of drawing
    private int currentFrame; // current frame counter, for converting image
    private int currentStatus = 1;// current status of player mouth (1 for open, 0 for closed)
    private int score;// current score of the player

    /**
     * Constructor for the player0 class.
     *
     * @param coordinateX the X coordinate of the player
     * @param coordinateY the Y coordinate of the player
     * @param logic0      the instance of the gameLogic0
     */
    public Player_L0(double coordinateX, double coordinateY, ShadowPacLogic_L0 logic0) {
        super(coordinateX, coordinateY, logic0);
        this.setLogicL0(logic0);
        setCurrentFrame(0);
        setOriginPos(new Point(coordinateX, coordinateY));
        this.setLife(3);
        this.setScore(0);
        setAIMSCORE(getLogicL0().getSupposedDotNum() * 10);
        setHitBox(new Rectangle(coordinateX, coordinateY, getPlayerCloseMouth().getWidth(), getPlayerCloseMouth().getHeight()));
    }

    /**
     * super Constructor for the player class for subclass to invoke.
     *
     * @param coordinateX the X coordinate of the player
     * @param coordinateY the Y coordinate of the player
     * @param logic1      the instance of the gameLogic1
     */

    protected Player_L0(double coordinateX, double coordinateY, ShadowPacLogic_L1 logic1) {
        super(coordinateX, coordinateY, logic1);
    }

    protected static Image getPlayerOpenMouth() {
        return playerOpenMouth;
    }

    protected static Image getPlayerCloseMouth() {
        return playerCloseMouth;
    }

    protected static int getAIMSCORE() {
        return AIMSCORE;
    }

    protected static void setAIMSCORE(int AIMSCORE) {
        Player_L0.AIMSCORE = AIMSCORE;
    }

    public int getLife() {
        return Life;
    }

    public void setLife(int life) {
        Life = life;
    }


    /**
     * Checks whether the player has won and updates the game stage accordingly.
     */
    public void checkWin() {
        if (this.getScore() >= getAIMSCORE()) {
            getLogicL0().level_completed();
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
     * Checks whether the player has collided with a ghost or eaten a dot.
     * executed based on current position(or rather after move)
     * Ver 2.0 : Only check if two unit are close enough
     */
    public void checkAround() {
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
    }

    /**
     * Checks whether the player has collided with a ghost.
     *
     * @param gst the ghost being checked for collision
     * @return true if the player has collided with the ghost, and call dieAndReset
     */
    protected boolean checkCollideWithGhost(Ghost gst) {
        if (this.getHitBox().intersects(gst.getHitBox())) {
            dieAndReset();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Eats a dot if the player has collided with it.
     * after eating turn the Dot existence to false
     *
     * @param dt the dot being checked for collision
     */
    protected void EatDot(Dot dt) {
        if (dt.isExist() && this.getHitBox().intersects(dt.getHitBox())) {
            dt.setExist(false);
            this.setScore(this.getScore() + dt.getScore());
            checkWin();
        }
    }

    /**
     * check whether the attempt step is valid move for limiting the actual move of both Player_L0 and Player_L1.
     * Third parameter roughly defined as Object to react accordingly on game logic type.
     * If none of ShadowPacLogic_L0 and ShadowPacLogic_L1 matches the given logic, do nothing with a false return.
     *
     * @param x     attemptX
     * @param y     attemptY
     * @param logic the ShadowPacLogic instance used for delegation.
     * @return true for invalid due to Wall
     */
    public boolean isToCollideWithWall(double x, double y, Object logic) {
        Player_L0 newPl = null;
        if (logic instanceof ShadowPacLogic_L0) {
            newPl = new Player_L0(x, y, (ShadowPacLogic_L0) logic);
        } else if (logic instanceof ShadowPacLogic_L1) {
            newPl = new Player_L1(x, y, (ShadowPacLogic_L1) logic);
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
    private boolean isValidPosition(double X, double Y) {
        return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight() &&
                !(isToCollideWithWall(X, Y, getLogicL0())));
    }

    /**
     * Move the player according to the input after performing a feasibility check
     *
     * @param key keyboard and mouse input
     */
    public void move(Keys key) {
        int STEP_SIZE = getLogicL0().getSTEP_SIZE();
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


    /**
     * Draws the player on the screen based on the input received from the user.
     *
     * @param input The input received from the user.
     */
    public void Draw(Input input) {
        if (getLogicL1() != null) {
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

    @Override
    public double getImageSize() {
        return getPlayerCloseMouth().getHeight();
    }

    private void DrawOpenMouth() {
        getPlayerOpenMouth().drawFromTopLeft(getCoordinateX(), getCoordinateY(), getDrop().setRotation(getRadians()));
    }

    private void DrawCloseMouth() {
        getPlayerCloseMouth().drawFromTopLeft(getCoordinateX(), getCoordinateY(), getDrop().setRotation(getRadians()));
    }

    /**
     * Resets the player's position to the original position and reduces the player's number of lives by 1.
     */
    public void dieAndReset() {
        setLife(getLife() - 1);
        if (getLife() == 0) {
            getLogicL0().gameFailed();
        }
        setPosition(getOriginPos());
        setRadians(getTORIGHT());
    }

    protected void setPosition(Point Pos) {
        setCoordinateX((int) Pos.x);
        setCoordinateY((int) Pos.y);
        setHitBox(new Rectangle(Pos, getPlayerCloseMouth().getWidth(), getPlayerCloseMouth().getHeight()));
    }

    public int getScore() {
        return score;
    }

    protected void setScore(int score) {
        this.score = score;
    }

    protected int getFrequency_Modulation() {
        // frequency of mouth opening and closing
        return 15;
    }

    protected DrawOptions getDrop() {
        return drop;
    }

    protected double getRadians() {
        return radians;
    }

    protected void setRadians(double radians) {
        this.radians = radians;
    }

    protected int getCurrentFrame() {
        return currentFrame;
    }

    protected void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    protected int getCurrentStatus() {
        return currentStatus;
    }

    protected void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }
}
