package it.unibo.oop.cctan.model;

public abstract class HittableImpl implements Hittable {

    private int hitPoints;
    
    protected HittableImpl() {}
    
    protected HittableImpl(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    @Override
    public int getHP() {
        return this.hitPoints;
    }

    @Override
    public void hit() {
        this.hitPoints--;
        if (this.hitPoints <= 0) {
            this.destroyed();
        }
    }
    
    protected abstract void destroyed();
}
