import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;

public class Ghost extends GameUnit{
    private static Image ghostIMG = new Image("res/ghostRed.png");
    private final static Image ghostFrenzy = new Image("res/ghostFrenzy.png");
    private final static double WIDTH = ghostIMG.getWidth();
    private final static double HEIGHT = ghostIMG.getHeight();
    private double stepSize = 0;
    protected int score = 30;
    private double direction;


    public Ghost(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY, (ShadowPacLogic_L0) null);
        setHitBox(new Rectangle(coordinateX, coordinateY, ghostIMG.getWidth(), ghostIMG.getHeight()));
    }

    public Ghost(double coordinateX, double coordinateY, ShadowPacLogic_L1 lg1, String str) {
        super(coordinateX, coordinateY,lg1);
        if (str.equals("GhostRed")) {
            ghostIMG = new Image("res/ghostRed.png");
            stepSize = 1;
            direction = TORIGHT;
        } else if (str.equals("GhostBlue")) {
            ghostIMG = new Image("res/ghostBlue.png");
            stepSize = 2;
            direction = TODOWN;
        } else if (str.equals("GhostGreen")) {
            ghostIMG = new Image("res/ghostGreen.png");
            stepSize = 4;
            direction = getRandomDirectionPositive();
        } else if (str.equals("GhostPink")) {
            ghostIMG = new Image("res/ghostPink.png");
            stepSize = 3;
            direction = getRandomDirection();
        } else {
            System.err.println("Given Wrong Ghost Type! " + this + " \n");
            System.err.println("Default ghost is constructed. \n");
        }
        setHitBox(new Rectangle(coordinateX, coordinateY, ghostIMG.getWidth(), ghostIMG.getHeight()));
    }

    private double getRandomDirection() {
        double[] directions = new double[]{TORIGHT,TODOWN,TOLEFT,TOUP};
        Random rand = new Random();
        int index = rand.nextInt(directions.length);
        return directions[index];
    }
    private double getRandomDirectionPositive() {
        double[] directions = new double[]{TORIGHT,TODOWN};
        Random rand = new Random();
        int index = rand.nextInt(directions.length);
        return directions[index];
    }


    public void Draw() {
        if(this.logicL1 != null) move();
        DrawFixUnit();
    }

//    @Override
    public void DrawFixUnit() {
        ghostIMG.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
    }

    @Override
    public int getImageSize() {
        return (int) ghostIMG.getHeight();
    }

    private boolean isToCollideWithWall(double x, double y, ShadowPacLogic_L1 logic) {
        Ghost newGst = new Ghost(x, y);
        Rectangle try_hit = new Rectangle(new Point(x, y), WIDTH, HEIGHT);
        for (Wall wl : logic.getWallList()) {
            if (newGst.isAround(wl)) {
                if (try_hit.intersects(wl.getHitBox())) {
                    direction *= -1;
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isValidPosition(double X, double Y) {
        return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight() && !(isToCollideWithWall(X, Y, logicL1)));
    }

    public void move() {
        double X = getCoordinateX(), Y = getCoordinateY();
        double STEP_SIZE = logicL1.getisFrenzy()? getSTEP_SIZE()-0.5 : getSTEP_SIZE();
        if (direction == TOLEFT) {
            if (isValidPosition(X - STEP_SIZE, Y)) setCoordinateX(X - STEP_SIZE);
        } else if (direction == TORIGHT) {
            if (isValidPosition(X + STEP_SIZE, Y)) setCoordinateX(X + STEP_SIZE);
        } else if (direction == TOUP) {
            if (isValidPosition(X, Y - STEP_SIZE)) setCoordinateY(Y - STEP_SIZE);
        } else if (direction == TODOWN) {
            if (isValidPosition(X, Y + STEP_SIZE)) setCoordinateY(Y + STEP_SIZE);
        }

    }

    public double getSTEP_SIZE() {
        return stepSize;
    }

    public int getScore() {
        return score;
    }
}
