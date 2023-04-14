import bagel.Image;
import bagel.Input;

public class Dot extends GameUnit{
    boolean isExist;
    private final static Image dot = new Image("res/dot.png");

    public Dot(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY);
        this.isExist = true;
    }

    @Override
    public void DrawFixUnit() {
        if(this.isExist) dot.draw(coordinateX,coordinateY);
    }
}
