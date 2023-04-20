import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Player extends GameUnit {
    private final static Image playerOpenMouth = new Image("res/pacOpen.png"); // image of the player with open mouth
    private final static Image playerCloseMouth = new Image("res/pac.png");// image of the player with closed mouth
    private static int AIMSCORE;// target score -- Not set Final for Scalability(Maybe required to change half way)
    private final Point originPos;// initial position of the player
    private final int Frequency_Modulation = 15;// frequency of mouth opening and closing
    private final DrawOptions drop = new DrawOptions();// draw options for the player
    private final ShadowPacLogic logic;
    private int Life; // number of lives the player has left
    private double radians = 0;// angle of player movement, same as direction of drawing
    private int currentFrame; // current frame counter, for converting image
    private int currentStatus = 1;// current status of player mouth (1 for open, 0 for closed)
    private int score;// current score of the player
    private static final double TOLEFT = -Math.PI;
    private static final double TORIGHT = 0.0;
    private static final double TOUP = -Math.PI/2;
    private static final double TODOWN = Math.PI/2;


    /**
     * Constructor for the player class.
     *
     * @param coordinateX the X coordinate of the player
     * @param coordinateY the Y coordinate of the player
     * @param logic       the instance of the gameLogic (Delegation interface applied)
     */
    public Player(int coordinateX, int coordinateY, ShadowPacLogic logic) {
        super(coordinateX, coordinateY);
        this.logic = logic;
        currentFrame = 0;
        originPos = new Point(coordinateX, coordinateY);
        this.Life = 3;
        this.score = 0;
        AIMSCORE = logic.getSupposedDotNum() * 10;
        setHitBox(new Rectangle(coordinateX, coordinateY, playerCloseMouth.getWidth(), playerCloseMouth.getHeight()));
    }

    public int getLife() {
        return Life;
    }

    /**
     * Checks whether the player has won and updates the game stage accordingly.
     */
    public void checkWin() {
        if (this.score >= AIMSCORE) {
            logic.gameSucceeded();
        }
    }

    /**
     * Checks whether the player has collided with a ghost or eaten a dot.
     * executed based on current position(or rather after move)
     * Ver 2.0 : Only check if two unit are close enough
     */
    public void checkAround() {
        for (Ghost gst : logic.getGhostList()) {
            if (this.isAround(gst)) {
                if (checkCollideWithGhost(gst)) break;
            }
        }
        for (Dot dt : logic.getDotList()) {
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
    private boolean checkCollideWithGhost(Ghost gst) {
        if (this.getHitBox().intersects(gst.getHitBox())) {
            dieAndReset();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Eats a dot if the player has collided with it.
     *
     * @param dt the dot being checked for collision
     */
    private void EatDot(Dot dt) {
        if (dt.isExist && this.getHitBox().intersects(dt.getHitBox())) {
            dt.isExist = false;
            this.score += 10;
            checkWin();
        }
    }

    /**
     * check whether the attempt step is valid move for limiting the actual move of Player.
     *
     * @param x     attemptX
     * @param y     attemptY
     * @param logic the ShadowPacLogic instance used for delegation.
     * @return true for invalid due to Wall
     */
    private boolean isToCollideWithWall(int x, int y, ShadowPacLogic logic) {
        Player newPl = new Player(x, y, logic);
        Rectangle try_hit = new Rectangle(new Point(x, y), playerCloseMouth.getWidth(), playerCloseMouth.getHeight());
        for (Wall wl : logic.getWallList()) {
            if (newPl.isAround(wl)) {
                if (try_hit.intersects(wl.getHitBox())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidPosition(int X, int Y) {
        return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight() && !(isToCollideWithWall(X, Y, logic)));
    }

    public void move(Keys key) {
        int STEP_SIZE = logic.getSTEP_SIZE();
        int X = getCoordinateX(), Y = getCoordinateY();
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
     * @param input The input received from the user.TODO：Consider replace by interface
     */
    @Override
    public void Draw(Input input) {
        if (input.isDown(Keys.LEFT)) {
            radians = TOLEFT;
            move(Keys.LEFT);
        } else if (input.isDown(Keys.RIGHT)) {
            radians = TORIGHT;
            move(Keys.RIGHT);
        } else if (input.isDown(Keys.UP)) {
            radians = TOUP;
            move(Keys.UP);
        } else if (input.isDown(Keys.DOWN)) {
            radians = TODOWN;
            move(Keys.DOWN);
        }
        this.getHitBox().moveTo(new Point(getCoordinateX(), getCoordinateY()));// Move the hitbox to the new position
        ++currentFrame;
        if (currentFrame == Frequency_Modulation) {
            currentStatus = (currentStatus == 1 ? 0 : 1);
            currentFrame = 0;
        }
        switch (currentStatus) {
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
    public int getImageSize() {
        return (int) playerCloseMouth.getHeight();
    }

    private void DrawOpenMouth() {
        playerOpenMouth.drawFromTopLeft(getCoordinateX(), getCoordinateY(), drop.setRotation(radians));
    }

    private void DrawCloseMouth() {
        playerCloseMouth.drawFromTopLeft(getCoordinateX(), getCoordinateY(), drop.setRotation(radians));
    }

    /**
     * Resets the player's position to the original position and reduces the player's number of lives by 1.
     */
    public void dieAndReset() {
        --Life;
        if (Life == 0) {
            logic.gameFailed();
        }
        setCoordinateX((int) originPos.x);
        setCoordinateY((int) originPos.y) ;
        radians = TORIGHT;
    }

    public int getScore() {
        return score;
    }
}
