import bagel.DrawOptions;
import bagel.Image;
import bagel.Keys;
import bagel.Input;

public class Player extends GameUnit {
    private final static Image playerOpenMouth = new Image("res/pacOpen.png");
    private final static Image playerCloseMouth = new Image("res/pac.png");
    private final int Frequency_Modulation = 15;
    private final DrawOptions drop = new DrawOptions();
    private double radians = Math.PI / 2;
    private int currentFrame;
    private int currentStatus = 1;
    private Keys lastPressedKey = Keys.RIGHT;

    public Player(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY);
        currentFrame = 0;

    }

    private boolean isValidPosition(double X, double Y) {
        return X >= 0 && !(X >= ShadowPac.getWindowWidth() - playerCloseMouth.getWidth())
                && Y >= 0 && !(Y >= ShadowPac.getWindowHeight() - playerCloseMouth.getHeight());
    }

    public void move(Keys key) {
        switch (key) {
            case LEFT:
                if (isValidPosition(coordinateX - ShadowPac.STEP_SIZE, coordinateY)) coordinateX -= ShadowPac.STEP_SIZE;
            case RIGHT:
                if (isValidPosition(coordinateX + ShadowPac.STEP_SIZE, coordinateY)) coordinateX += ShadowPac.STEP_SIZE;
            case UP:
                if (isValidPosition(coordinateX, coordinateY - ShadowPac.STEP_SIZE)) coordinateY -= ShadowPac.STEP_SIZE;
            case DOWN:
                if (isValidPosition(coordinateX, coordinateX - ShadowPac.STEP_SIZE)) coordinateY -= ShadowPac.STEP_SIZE;
        }
    }

    @Override
    public void Draw(Input input) {
        int pressKeyNum = 0;
        if (input.wasReleased(lastPressedKey)) {
            if (input.wasPressed(Keys.LEFT)) {
                radians = -Math.PI / 2;
                lastPressedKey = Keys.LEFT;
            }
            if (input.wasPressed(Keys.RIGHT)) {
                radians = Math.PI / 2;
                lastPressedKey = Keys.RIGHT;
            }
            if (input.wasPressed(Keys.UP)) {
                radians = 0.00;
                lastPressedKey = Keys.UP;
            }
            if (input.wasPressed(Keys.DOWN)) {
                radians = Math.PI;
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
}
