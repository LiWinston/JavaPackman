import bagel.Image;
import bagel.Keys;
import bagel.Input;
public class Player extends GameUnit {
    private final static Image playerOpenMouth = new Image("res/pacOpen.png");
    private final static Image playerCloseMouth = new Image("res/pac.png");
    double angle = 90.0;
    public Player(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY);
    }
    /*Implement monitoring of keyboard input at the level of the player class*/

    @Override
    public void Draw() {

    }

}
