import bagel.*;

/**
 * GameUnit Class
 * Implement basic game unit behaviours
 * @YongchunLi
 */
public abstract class GameUnit {
    protected double coordinateX;
    protected double coordinateY;

    public GameUnit(double coordinateX, double coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    protected GameUnit() {
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public void Draw(Input input){};
    protected void DrawFixUnit(){};

}
