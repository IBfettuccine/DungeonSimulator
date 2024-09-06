public class Fighter extends Hero{

    @Override
    public int attack() {
        return (int) (Math.random() * 20) + 6;
    }

    @Override
    public int damage() {
        return (int) (Math.random() * 10) + 4;
    }

    public void rest() throws Exception {
        this.hp += (int) (Math.random() * 10) + 1;
        if(this.hp > 12) {
            this.hp = 12;
        }
        ChatGPTRequest.askChatGPT(this.name + " has taken a short rest and recovered some HP. " + this.name + " now has " + this.hp + " HP.");
    }

    public Fighter()
    {
        hp = 12;
        armor = 15;
        name = "";
        isAlive = true;
    }
}







