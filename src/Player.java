import bagel.DrawOptions;
import bagel.Image;
import bagel.Keys;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Player extends GameUnit {
//    private final ShadowPac game;// instance of the game
    private final Point originPos;// initial position of the player
    private int Life; // number of lives the player has left
    private final static Image playerOpenMouth = new Image("res/pacOpen.png"); // image of the player with open mouth
    private final static Image playerCloseMouth = new Image("res/pac.png");// image of the player with closed mouth
    private final int Frequency_Modulation = 15;// frequency of mouth opening and closing
    private final DrawOptions drop = new DrawOptions();// draw options for the player
    private double radians = 0;// angle of player movement, same as direction of drawing
    private int currentFrame; // current frame counter, for converting image
    private int currentStatus = 1;// current status of player mouth (1 for open, 0 for closed)
    private int score;// current score of the player
    private static int AIMSCORE;// target score -- Not set Final for Scalability(Maybe required to change half way)
    private final ShadowPacLogic logic;
    /**
     Constructor for the player class.
     @param coordinateX the X coordinate of the player
     @param coordinateY the Y coordinate of the player
     @param logic the instance of the gameLogic (Delegation interface applied)
     */
    public Player(int coordinateX, int coordinateY,ShadowPacLogic logic) {
        super(coordinateX, coordinateY);
        this.logic = logic;
        currentFrame = 0;
        originPos = new Point(coordinateX, coordinateY);
        this.Life = 3;
        this.score = 0;
        AIMSCORE = logic.getSupposedDotNum() * 10;
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
//            game.gs = ShadowPac.gameStage.Success;
            logic.gameSucceeded();
        }
    }
    /**
     * Checks whether the player has collided with a ghost or eaten a dot.
     * executed based on current position(or rather after move)
     * @param game the instance of the game
     */
    public void checkAround(){
        for(Ghost gst : logic.getGhostList()){
            if(checkCollideWithGhost(gst)){
                break;
            }
        }
        for(Dot dt : logic.getDotList()){
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

    /**
     * check whether the attempt step is valid move for limiting the actual move of Player.
     * @param x attemptX
     * @param y attemptY
     * @param logic the ShadowPacLogic instance used for delegation.
     * @return true for invalid due to Wall
     */
    private boolean isToCollideWithWall(int x, int y, ShadowPacLogic logic){
        Rectangle try_hit = new Rectangle(new Point(x, y), playerCloseMouth.getWidth(), playerCloseMouth.getHeight());
        for(Wall wl : logic.getWallList()) {
            if(try_hit.intersects(wl.hitBox)){
                return true;
            }
        }
        return false;
    }
    private boolean isValidPosition(int X, int Y) {
        return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight() &&!(isToCollideWithWall(X,Y,logic)));
    }

    public void move(Keys key) {
        int STEP_SIZE = logic.getSTEP_SIZE();
        switch (key) {
            case LEFT:
                if (isValidPosition(coordinateX - STEP_SIZE, coordinateY)) coordinateX -= STEP_SIZE;
                break;
            case RIGHT:
                if (isValidPosition(coordinateX + STEP_SIZE, coordinateY)) coordinateX += STEP_SIZE;
                break;
            case UP:
                if (isValidPosition(coordinateX, coordinateY - STEP_SIZE)) coordinateY -= STEP_SIZE;
                break;
            case DOWN:
                if (isValidPosition(coordinateX, coordinateY + STEP_SIZE)) coordinateY += STEP_SIZE;
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
            radians = -Math.PI;
            move(Keys.LEFT);
        }
        else if (input.isDown(Keys.RIGHT)) {
            radians = 0;
            move(Keys.RIGHT);
        }
        else if (input.isDown(Keys.UP)) {
            radians = -Math.PI / 2;
            move(Keys.UP);
        }
        else if (input.isDown(Keys.DOWN)) {
            radians = Math.PI / 2;
            move(Keys.DOWN);
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
//            game.gs = ShadowPac.gameStage.Lose;
            logic.gameFailed();
        }
        coordinateX = (int) originPos.x;
        coordinateY = (int) originPos.y;
    }

    public int getScore() {
        return score;
    }
}
