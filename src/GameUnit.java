/**
 * GameUnit Class
 * Implement basic game unit behaviours
 * @YongchunLi
 */
public abstract class GameUnit {
    private double coordinateX;
    private double coordinateY;

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
    public abstract void Draw();
}
