import bagel.DrawOptions;
import bagel.Image;
import bagel.Keys;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Player extends GameUnit {
    private final ShadowPac game;
    private final Point originPos;
    private int Life;
    final static Image playerOpenMouth = new Image("res/pacOpen.png");
    private final static Image playerCloseMouth = new Image("res/pac.png");
    private final int Frequency_Modulation = 15;
    private final DrawOptions drop = new DrawOptions();
    private double radians = 0;
    private int currentFrame;
    private int currentStatus = 1;//1 = Openmouth and 0 = Closed
    private Keys lastPressedKey = Keys.RIGHT;
    private int score;
    private static final int AIMSCORE = ShadowPac.supposedDotNum * 10;

    public Player(int coordinateX, int coordinateY,ShadowPac game) {
        super(coordinateX, coordinateY);
        this.game = game;
        currentFrame = 0;
        originPos = new Point(coordinateX, coordinateY);
        this.Life = 3;
        this.score = 0;
        hitBox = new Rectangle(coordinateX,coordinateY,playerCloseMouth.getWidth(),playerCloseMouth.getHeight());
    }

    public void checkWin(){
        if(this.score >= AIMSCORE){
            //todo TELL SHADOWPAC by somehow communication bwtween class
            //maybe observation design
            game.gs = ShadowPac.gameStage.Success;
        };
    }
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
    private boolean checkCollideWithGhost(Ghost gst){
        if(this.hitBox.intersects(gst.hitBox)){
            dieAndReset();
            return true;
        }else{
            return false;
        }
    }
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
                if (isValidPosition(coordinateX, coordinateY - ShadowPac.STEP_SIZE)) coordinateY += ShadowPac.STEP_SIZE;
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
        hitBox.moveTo(new Point(coordinateX,coordinateY));
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
