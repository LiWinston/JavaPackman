import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;

/**
 * Ghost class, representing different colored ghosts in the game.
 * The class is constructed and initialized based on the given category.
 * It can randomly select one of the four or two initial directions to initialize its movement direction as required.
 * The ghost can be drawn differently based on its level, frenzy mode, and hidden status.
 * If the ghost is in level 1, it can move automatically and independently check if it collides with a wall.
 * It can also adjust its step size in response to frenzy mode.
 *
 * Note: The ghost images are assumed to be located at "res/ghostRed.png",
 * "res/ghostBlue.png", "res/ghostGreen.png", and "res/ghostPink.png".
 *
 * Example Usage:
 * Ghost ghost = new Ghost(100, 200);
 * ghost.Draw();
 *
 * @see GameUnit
 * @see ShadowPacLogic_L1
 * @see Wall
 * @see ShadowPac
 * @see <a href="https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/bagel/util/Rectangle.html">bagel.util.Rectangle</a>
 * @see <a href="https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/bagel/Image.html">bagel.Image</a>
 * @see <a href="https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/bagel/util/Point.html">bagel.util.Point</a>
 * @author YongchunLi
 */
public class Ghost extends GameUnit {
    private final static Image ghostFrenzy = new Image("res/ghostFrenzy.png");
    private String type = "Normal";
    private Image ghostIMG = new Image("res/ghostRed.png");
    private final double WIDTH = ghostIMG.getWidth();
    private final double HEIGHT = ghostIMG.getHeight();
    private double stepSize;
    private double direction;
    private boolean hidden = false;

    /**
     * Constructs a Ghost object with the given coordinates.
     *
     * @param coordinateX the X coordinate of the Ghost
     * @param coordinateY the Y coordinate of the Ghost
     */
    public Ghost(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY, null);
        setHitBox(new Rectangle(coordinateX, coordinateY, ghostIMG.getWidth(), ghostIMG.getHeight()));
    }

    /**
     * Constructs a specific type of Ghost object based on the given parameters.
     *
     * @param coordinateX the X coordinate of the Ghost
     * @param coordinateY the Y coordinate of the Ghost
     * @param lg1         the instance of ShadowPacLogic_L1
     * @param str         the type of Ghost to be constructed
     */
    public Ghost(double coordinateX, double coordinateY, ShadowPacLogic_L1 lg1, String str) {
        super(coordinateX, coordinateY, lg1);
        this.type = str;
        switch (type) {
            case "GhostRed":
                ghostIMG = new Image("res/ghostRed.png");
                stepSize = 1;
                setDirection(getTORIGHT());
                break;
            case "GhostBlue":
                ghostIMG = new Image("res/ghostBlue.png");
                stepSize = 2;
                setDirection(getTODOWN());
                break;
            case "GhostGreen":
                ghostIMG = new Image("res/ghostGreen.png");
                stepSize = 4;
                setDirection(getRandomDirectionPositive());
                break;
            case "GhostPink":
                ghostIMG = new Image("res/ghostPink.png");
                stepSize = 3;
                setDirection(getRandomDirection());
                break;
            default:
                System.err.println("Given Wrong Ghost Type! " + this + " \n");
                System.err.println("Default ghost is constructed. \n");
                break;
        }
        setHitBox(new Rectangle(coordinateX, coordinateY, ghostIMG.getWidth(), ghostIMG.getHeight()));
    }

    /**
     * Generates a random direction from four possible directions.
     *
     * @return The randomly generated direction.
     */
    private double getRandomDirection() {
        double[] directions = new double[]{getTORIGHT(), getTODOWN(), getTOLEFT(), getTOUP()};
        Random rand = new Random();
        int index = rand.nextInt(4);
        return directions[index];
    }

    /**
     * Generates a random direction from two possible positive directions.
     *
     * @return The randomly generated direction.
     */
    private double getRandomDirectionPositive() {
        double[] directions = new double[]{getTORIGHT(), getTODOWN()};
        Random rand = new Random();
        int index = rand.nextInt(2);
        return directions[index];
    }

    /**
     * If in GAMESTAGE gaming1, ghost moves before drawing
     * Use logicL1 not null for object origin tracking
     */
    public void Draw() {
        if (isHidden()) return;
        if (this.getLogicL1() != null) {
            move();
            if (getLogicL1().isFrenzyMode() && !this.isHidden()) {
                ghostFrenzy.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
            } else ghostIMG.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
        } else {
            ghostIMG.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
        }

    }

    @Override
    public double getImageSize() {
        return ghostIMG.getHeight();
    }

    /**
     * Wall collision Detection and accordingly action defined
     *
     * @param x     received intended X position
     * @param y     received intended Y position
     * @param logic The gameManager, used for ObjList accessing
     * @return T/F with direction converting in required ways
     */
    private boolean isToCollideWithWall(double x, double y, ShadowPacLogic_L1 logic) {
        Ghost newGst = new Ghost(x, y, logic, this.type);
        Rectangle try_hit = new Rectangle(new Point(x, y), WIDTH, HEIGHT);
        for (Wall wl : logic.getWallList()) {
            if (newGst.isAround(wl)) {
                if (try_hit.intersects(wl.getHitBox())) {
                    if (this.type.equals("GhostRed") || this.type.equals("GhostBlue") || this.type.equals("GhostGreen")) {
                        setDirection(getReverseDirection(getDirection()));
                    } else if (this.type.equals("GhostPink")) {
                        setDirection(getRandomDirection());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /*
    Due to confusion of representation 'Rad * -1'
    use this to get Reverse Direction gracefully
     */
    private double getReverseDirection(double direction) {
        if (direction == getTORIGHT()) {
            return getTOLEFT();
        } else if (direction == getTOLEFT()) {
            return getTORIGHT();
        } else if (direction == getTOUP()) {
            return getTODOWN();
        } else if (direction == getTODOWN()) {
            return getTOUP();
        }
        return direction;
    }

    private boolean isValidPosition(double X, double Y) {
        return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight()
                && !(isToCollideWithWall(X, Y, getLogicL1())));
    }

    /**
     * Conduct the automatic movement together with its Hitbox
     */
    public void move() {
        double X = getCoordinateX(), Y = getCoordinateY();
        double STEP_SIZE = getSTEP_SIZE();
        if (getDirection() == getTOLEFT()) {
            if (isValidPosition(X - STEP_SIZE, Y)) setCoordinateX(X - STEP_SIZE);
        } else if (getDirection() == getTORIGHT()) {
            if (isValidPosition(X + STEP_SIZE, Y)) setCoordinateX(X + STEP_SIZE);
        } else if (getDirection() == getTOUP()) {
            if (isValidPosition(X, Y - STEP_SIZE)) setCoordinateY(Y - STEP_SIZE);
        } else if (getDirection() == getTODOWN()) {
            if (isValidPosition(X, Y + STEP_SIZE)) setCoordinateY(Y + STEP_SIZE);
        }
        this.getHitBox().moveTo(new Point(getCoordinateX(), getCoordinateY()));
    }
    /**
     * Resets the Ghost to its original position.
     * The X and Y coordinates and the hitbox are set to the original position.
     */
    public void reset() {
        setCoordinateX(getOriginPos().x);
        setCoordinateY(getOriginPos().y);
        this.getHitBox().moveTo(getOriginPos());
    }

    /**
     * Returns the step size of the Ghost.
     * If the Ghost is in frenzy mode, the step size is reduced by 0.5.
     *
     * @return the step size of the Ghost
     */
    public double getSTEP_SIZE() {
        return getLogicL1().isFrenzyMode() ? stepSize - 0.5 : stepSize;
    }

    /**
     * Returns the score value of the Ghost.
     *
     * @return the score value of the Ghost
     */
    public int getScore() {
        return 30;
    }

    /**
     * Sets the hidden status of the Ghost to true.
     */
    public void setHidden() {
        this.setHidden(true);
    }

    /**
     * Returns the hidden status of the Ghost.
     *
     * @return true if the Ghost is hidden, false otherwise
     */
    public boolean getHidden() {
        return isHidden();
    }

    /**
     * Returns the movement direction of the Ghost.
     *
     * @return the movement direction of the Ghost
     */
    public double getDirection() {
        return direction;
    }

    /**
     * Sets the movement direction of the Ghost.
     *
     * @param direction the new movement direction of the Ghost
     */
    private void setDirection(double direction) {
        this.direction = direction;
    }

    /**
     * Returns the hidden status of the Ghost.
     *
     * @return true if the Ghost is hidden, false otherwise
     */
    private boolean isHidden() {
        return hidden;
    }

    /**
     * Sets the hidden status of the Ghost.
     *
     * @param bl the new hidden status of the Ghost
     */
    public void setHidden(boolean bl) {
        this.hidden = bl;
    }
}
