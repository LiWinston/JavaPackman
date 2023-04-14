import bagel.DrawOptions;
import bagel.Image;
import bagel.Keys;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Player extends GameUnit {
    private final ShadowPac game;// instance of the game
    private final Point originPos;// initial position of the player
    private int Life; // number of lives the player has left
    final static Image playerOpenMouth = new Image("res/pacOpen.png"); // image of the player with open mouth
    private final static Image playerCloseMouth = new Image("res/pac.png");// image of the player with closed mouth
    private final int Frequency_Modulation = 15;// frequency of mouth opening and closing
    private final DrawOptions drop = new DrawOptions();// draw options for the player
    private double radians = 0;// angle of player movement, same as direction of drawing
    private int currentFrame; // current frame counter, for converting image
    private int currentStatus = 1;// current status of player mouth (1 for open, 0 for closed)
    private Keys lastPressedKey = Keys.RIGHT;// last key pressed by the player
    private int score;// current score of the player
    private static final int AIMSCORE = ShadowPac.supposedDotNum * 10;// target score

    /**

     Constructor for the player class.
     @param coordinateX the X coordinate of the player
     @param coordinateY the Y coordinate of the player
     @param game the instance of the game
     */
    public Player(int coordinateX, int coordinateY,ShadowPac game) {
        super(coordinateX, coordinateY);
        this.game = game;
        currentFrame = 0;
        originPos = new Point(coordinateX, coordinateY);
        this.Life = 3;
        this.score = 0;
        hitBox = new Rectangle(coordinateX,coordinateY,playerCloseMouth.getWidth(),playerCloseMouth.getHeight());
    }

    public int getLife() {
        return Life;
    }

    /**
     * Checks whether the player has won and updates the game stage accordingly.
     */
    public void checkWin(){
        if(this.score >= AIMSCORE){
            //todo: Improve encapsulation by TELLing SHADOWPAC with
            // somehow communication bwtween class like : observation design mode
            game.gs = ShadowPac.gameStage.Success;
        };
    }
    /**
     * Checks whether the player has collided with a ghost or eaten a dot.
     * @param game the instance of the game
     */
    public void checkAround(ShadowPac game){
        for(Ghost gst : game.ghostList){
            if(checkCollideWithGhost(gst)){
                dieAndReset();
                break;
            }
        }
        for(Dot dt : game.dotList){
            EatDot(dt);
        }
    }
    /**
     * Checks whether the player has collided with a ghost.
     * @param gst the ghost being checked for collision
     * @return true if the player has collided with the ghost, and call dieAndReset
     */
    private boolean checkCollideWithGhost(Ghost gst){
        if(this.hitBox.intersects(gst.hitBox)){
            dieAndReset();
            return true;
        }else{
            return false;
        }
    }
    /**
     *  Eats a dot if the player has collided with it.
     *  @param dt the dot being checked for collision
     */
    private void EatDot(Dot dt){
        if(dt.isExist && this.hitBox.intersects(dt.hitBox)){
            dt.isExist = false;
            this.score += 10;
            checkWin();
        }
    }
    private boolean isToCollideWithWall(int x, int y, ShadowPac game){
        Rectangle try_hit = new Rectangle(new Point(x, y), playerCloseMouth.getWidth(), playerCloseMouth.getHeight());
        for(Wall wl : game.wallList) {
            if(try_hit.intersects(wl.hitBox)){
                return true;
            }
        }
        return false;
    }
    private boolean isValidPosition(int X, int Y) {
//        return X >= 0 && (X < ShadowPac.getWindowWidth() - playerCloseMouth.getWidth()) && Y >= 0
//                && (Y < ShadowPac.getWindowHeight() - playerCloseMouth.getHeight() &&!(isToCollideWithWall(X,Y,game)));
        return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight() &&!(isToCollideWithWall(X,Y,game)));

    }

    public void move(Keys key) {
        switch (key) {
            case LEFT:
                if (isValidPosition(coordinateX - ShadowPac.STEP_SIZE, coordinateY)) coordinateX -= ShadowPac.STEP_SIZE;
                break;
            case RIGHT:
                if (isValidPosition(coordinateX + ShadowPac.STEP_SIZE, coordinateY)) coordinateX += ShadowPac.STEP_SIZE;
                break;
            case UP:
                if (isValidPosition(coordinateX, coordinateY - ShadowPac.STEP_SIZE)) coordinateY -= ShadowPac.STEP_SIZE;
                break;
            case DOWN:
                if (isValidPosition(coordinateX, coordinateY + ShadowPac.STEP_SIZE)) coordinateY += ShadowPac.STEP_SIZE;
                break;
        }
    }

    @Override
    public void Draw(Input input) {
//        int pressKeyNum = 0;
        if (true) {//input.wasReleased(lastPressedKey)
            if (input.wasPressed(Keys.LEFT)) {
                radians = -Math.PI;
                move(Keys.LEFT);
                lastPressedKey = Keys.LEFT;
            }
            else if (input.wasPressed(Keys.RIGHT)) {
                radians = 0;
                move(Keys.RIGHT);
                lastPressedKey = Keys.RIGHT;
            }
            else if (input.wasPressed(Keys.UP)) {
                radians = -Math.PI / 2;
                move(Keys.UP);
                lastPressedKey = Keys.UP;
            }
            else if (input.wasPressed(Keys.DOWN)) {
                radians = Math.PI / 2;
                move(Keys.DOWN);
                lastPressedKey = Keys.DOWN;
            }
        }
        hitBox.moveTo(new Point(coordinateX,coordinateY));// Move the hitbox to the new position
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

    private void DrawOpenMouth() {
        playerOpenMouth.drawFromTopLeft(coordinateX, coordinateY, drop.setRotation(radians));
    }

    private void DrawCloseMouth() {
        playerCloseMouth.drawFromTopLeft(coordinateX, coordinateY, drop.setRotation(radians));
    }

    /**
     * Resets the player's position to the original position and reduces the player's number of lives by 1.
     */
    public void dieAndReset(){
        --Life;
        if(Life == 0){
            game.gs = ShadowPac.gameStage.Lose;
        }
        coordinateX = (int) originPos.x;
        coordinateY = (int) originPos.y;
    }

    public int getScore() {
        return score;
    }
}
