import bagel.Image;
import bagel.Input;

public class Dot extends GameUnit{
    boolean isExist;
    private final static Image dot = new Image("res/dot.png");

    public Dot(int coordinateX, int coordinateY) {
        super(coordinateX, coordinateY);
        this.isExist = true;
    }

    @Override
    public void Draw(Input input) {
        DrawFixUnit();
    }

    @Override
    public void DrawFixUnit() {
        if(this.isExist) dot.draw(coordinateX,coordinateY);
    }
}
