import bagel.Image;
import bagel.Input;
import bagel.util.Rectangle;

public class Ghost extends GameUnit {
    private final static Image ghostRed = new Image("res/ghostRed.png");

    public Ghost(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        setHitBox(new Rectangle(coordinateX, coordinateY, ghostRed.getWidth(), ghostRed.getHeight()));
    }

    @Override
    public void Draw(Input input) {
        DrawFixUnit();
    }

    @Override
    public void DrawFixUnit() {
        ghostRed.drawFromTopLeft(this.getCoordinateX(), this.getCoordinateY());
    }

    @Override
    public int getImageSize() {
        return (int) ghostRed.getHeight();
    }
}
