import bagel.Keys;

public interface Moveable {
    private boolean isValidPosition(double X, double Y,ShadowPacLogic_L0 lg0) {
        return X >= 0 && (X < ShadowPac.getWindowWidth()) && Y >= 0 && (Y < ShadowPac.getWindowHeight() &&
                !(isToCollideWithWall(X, Y, lg0)));
    }

    abstract boolean isToCollideWithWall(double X, double Y, ShadowPacLogic_L0 lg0);
    abstract boolean isToCollideWithWall(double X, double Y, ShadowPacLogic_L1 lg1);
    void move(Keys key);

    double getCoordinateY();

    void setCoordinateY(double v);

    double getCoordinateX();

    void setCoordinateX(double v);

    double getSTEP_SIZE();

}
