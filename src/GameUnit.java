import bagel.*;

/**
 * GameUnit Class
 * Implement basic game unit behaviours
 * @YongchunLi
 */
public abstract class GameUnit {
    protected int coordinateX;
    protected int coordinateY;

    public GameUnit(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    protected GameUnit() {
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public void Draw(Input input){};
    protected void DrawFixUnit(){};

}
