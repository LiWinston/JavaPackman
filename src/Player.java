import bagel.DrawOptions;
import bagel.Image;
import bagel.Keys;
import bagel.Input;
import bagel.util.Point;

public class Player extends GameUnit {
    private final Point originPos;
    private int Life;
    private final static Image playerOpenMouth = new Image("res/pacOpen.png");
    private final static Image playerCloseMouth = new Image("res/pac.png");
    private final int Frequency_Modulation = 15;
    private final DrawOptions drop = new DrawOptions();
    private double radians = 0;
    private int currentFrame;
    private int currentStatus = 1;//1 = Openmouth and 0 = Closed
    private Keys lastPressedKey = Keys.RIGHT;

    public Player(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        currentFrame = 0;
        originPos = new Point(coordinateX, coordinateY);
        this.Life = 3;
    }
    private boolean isToCollideWithGhost(ShadowPac game){
        // TODO:Collide detect
        //  How to implement more resource-saving detection?
        //  Consider Spatial split trees, grids, scan lines, and more
        for(Ghost gh : game.ghostList){

        }
        return false;
    }
    private boolean isToCollideWithWall(ShadowPac game){
        for(Wall wl : game.wallList){
            // TODO
        }
        return false;
    }
    private boolean isValidPosition(int X, int Y) {
        return X >= 0 && !(X >= ShadowPac.getWindowWidth() - playerCloseMouth.getWidth())
                && Y >= 0 && !(Y >= ShadowPac.getWindowHeight() - playerCloseMouth.getHeight());
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
                if (isValidPosition(coordinateX, coordinateX - ShadowPac.STEP_SIZE)) coordinateY += ShadowPac.STEP_SIZE;
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
        playerOpenMouth.draw(coordinateX, coordinateY, drop.setRotation(radians));
    }

    private void DrawCloseMouth() {
        playerCloseMouth.draw(coordinateX, coordinateY, drop.setRotation(radians));
    }

    public void dieAndReset(){
        --Life;
        coordinateX = (int) originPos.x;
        coordinateY = (int) originPos.y;
    }
}
