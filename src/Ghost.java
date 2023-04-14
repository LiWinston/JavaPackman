import bagel.*;
import bagel.util.Rectangle;

public class Ghost extends GameUnit{
    private final static Image ghostRed = new Image("res/ghostRed.png");

    public Ghost(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        hitBox = new Rectangle(coordinateX,coordinateY,ghostRed.getWidth(),ghostRed.getHeight());
    }

    @Override
    public void Draw(Input input) {
        DrawFixUnit();
    }

    @Override
    public void DrawFixUnit() {
        ghostRed.drawFromTopLeft(coordinateX,coordinateY);
    }
}
