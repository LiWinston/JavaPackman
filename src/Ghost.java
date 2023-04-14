import bagel.*;

public class Ghost extends GameUnit{
    private final static Image ghostRed = new Image("res/ghostRed.png");

    public Ghost(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
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
