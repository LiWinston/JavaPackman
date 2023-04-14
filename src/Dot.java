import bagel.Image;

public class Dot extends GameUnit{
    boolean isExist;
    private final static Image dot = new Image("res/dot.png");

    public Dot(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY);
        this.isExist = true;
    }

    @Override
    public void Draw() {
        if(this.isExist){

        }
    }
}
