import bagel.*;

public class Ghost extends GameUnit{
    private final static Image ghostRed = new Image("res/ghostRed.png");

    @Override
    public void Draw(Input input) {
        DrawFixUnit();
    }

    @Override
    public void DrawFixUnit() {
        ghostRed.draw(coordinateX,coordinateY);
    }
}
