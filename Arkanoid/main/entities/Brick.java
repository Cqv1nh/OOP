package entities;

public abstract class Brick extends GameObject {
    private int hitPoints; // So lam va cham de gach bi pha huy
    private String type; // Loai gach

    public Brick(double x, double y, double width, double height, int hitPoints, String type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
    }
    
    public int getHitPoints() {
        return hitPoints;
    }

    public String getType() {
        return type;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public abstract void takeHit();
    public abstract boolean isDestroyed();
}
