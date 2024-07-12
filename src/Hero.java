public abstract class Hero
{
    String name;
    int hp;
    int armor;
    boolean isAlive = true;

    public abstract int attack();
    public abstract int damage();

    public abstract void rest();

    public int getHp()
    {
        return this.hp;
    }
    public int getArmor()
    {
        return this.armor;
    }
    public String getName()
    {
        return this.name;
    }
    public boolean getIsAlive()
    {
        return this.isAlive;
    }

    public void setHp(int hurt)
    {
        this.hp -= hurt;
    }

    public void dead()
    {
        this.isAlive = false;
    }
    public void setName(String newName)
    {
        this.name = newName;
    }

}
