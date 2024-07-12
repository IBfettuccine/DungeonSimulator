public class Goblin extends Monster{
    public Goblin() {
        name = "goblin";
        hp = 7;
        armor = 14;
        isAlive = true;
    }

    @Override
    public int attack() {
        return (int) (Math.random() * 20) + 1 + 4;
    }

    @Override
    public int damage() {
        return (int) (Math.random() * 6) + 1 + 2;
    }

    @Override
    public void resetHP() {
        this.hp = 7;
        this.isAlive = true;
    }

}
