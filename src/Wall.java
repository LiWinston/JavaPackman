import bagel.*;

public class Wall extends GameUnit{
    private final static Image wall = new Image("res/wall.png");

    public Wall(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
    }

    @Override
    public void DrawFixUnit() {
        wall.drawFromTopLeft(coordinateX,coordinateY);
    }
}
