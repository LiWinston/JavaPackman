import bagel.*;

public class Wall extends GameUnit{
    private final static Image wall = new Image("res/wall.png");
    @Override
    public void DrawFixUnit() {
        wall.draw(coordinateX,coordinateY);
    }
}
