public abstract class Monster {
    String name;
    int hp;
    int armor;
    boolean isAlive = true;

    public  abstract int attack();
    public abstract int damage();

    public abstract void resetHP();


    public int getHp() { return this.hp;}
    public int getArmor() { return this.armor; }
    public String getName() { return this.name; }
    public boolean getIsAlive() { return this.isAlive; }

    public void setHp(int damage) { this.hp -= damage; }
    public void dead() { this.isAlive = false; }
}

