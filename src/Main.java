import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Fighter f1 = new Fighter();
        Goblin g1 = new Goblin();

        System.out.println("What is your name?");
        f1.setName(sc.nextLine());

        while(f1.getIsAlive()) {
            ChatGPTRequest.askChatGPT(f1.getName() + " stands at a tunnel descending deeper into the dungeon");
            String answer = yesOrNo("Does " + f1.getName() + " want to descend the dungeon?");
            if (answer.equalsIgnoreCase("no")) {
                System.out.println("Ok.");
                break;
            } else {
                ChatGPTRequest.askChatGPT("A goblin appears wielding a sword!");
                answer = yesOrNo("Does " + f1.getName() + " want to fight? Say 'yes' to attack say 'no' to try and flee");
                if (answer.equalsIgnoreCase("yes"))
                {
                    while (f1.getIsAlive() && g1.getIsAlive()) {
                        if (f1.attack() >= g1.getArmor()) {
                            int dmg = f1.damage();
                            g1.setHp(dmg);
                            ChatGPTRequest.askChatGPT(f1.getName() + " hit the goblin with a sword for " + dmg + " damage! The goblin's has a maximum 7 HP but now it has " + g1.getHp() + " HP.");
                        } else {
                            ChatGPTRequest.askChatGPT(f1.getName() + " missed the goblin with a sword.");
                        }
                        if (g1.getHp() > 0 && g1.attack() >= f1.getArmor()) {
                            f1.setHp(g1.damage());
                            ChatGPTRequest.askChatGPT(f1.getName() + " was hit by the goblin's sword! " + f1.getName() + " has a maximum of 12 HP but now has " + f1.getHp() + " HP.");
                            System.out.println(f1.getName() + " now has a total of " + f1.getHp() + " HP.");
                        } else if (g1.getHp() > 0) {
                            ChatGPTRequest.askChatGPT(g1.getName() + " missed " + f1.getName() + " with it's sword.");
                        }
                        if (g1.getHp() <= 0) {
                            g1.dead();
                            System.out.println();
                            ChatGPTRequest.askChatGPT("The goblin is dead having been slain by " + f1.getName());
                        }
                        if (f1.getHp() <= 0) {
                            f1.dead();

                            ChatGPTRequest.askChatGPT(g1.getName() + " used a sword to kill " + f1.getName() + "! The game is over.");
                        }

                        if(f1.getIsAlive() && g1.getIsAlive()) {
                            ChatGPTRequest.askChatGPT("The round is over but " + g1.getName() + " still lives with " + g1.getHp() + " HP. The goblin has a maximum of 7HP. " + f1.getName() + " has " + f1.getHp() + " and a maximum of 12 HP.");
                            answer = yesOrNo("Do you want to continue to fight?");
                            if (answer.equalsIgnoreCase("no")) {
                                break;
                            }
                        }
                    }
                }
                if(answer.equalsIgnoreCase("no")) {
                    System.out.println();
                    ChatGPTRequest.askChatGPT(f1.getName() + " attempts to flee the from the " + g1.getName() + ".");
                    if(g1.attack() >= f1.getArmor()) {
                        f1.setHp(g1.damage());
                        ChatGPTRequest.askChatGPT(f1.getName() + " is hit by an opportunity attack by " + g1.getName() + " with a sword. " + f1.getName() + " has a maximum of 12 HP but now " + f1.getName() + " has " + f1.getHp() + " HP.");
                        System.out.println(f1.getName() + " now has a total of " + f1.getHp() + " HP.");

                        if(f1.getHp() <= 0) {
                            f1.dead();
                            System.out.println();
                            ChatGPTRequest.askChatGPT(g1.getName() + " used a sword to kill " + f1.getName() + "! The game is over.");
                        }
                    } else {
                        System.out.println();
                        ChatGPTRequest.askChatGPT(g1.getName() + " tried to hit " + f1.getName() + " with an opportunity attack using it's sword but failed.");
                    }
                }
            }
            if(f1.getIsAlive()) {
                f1.rest();
                g1.resetHP();
            }
        }
    }

    public static String yesOrNo(String question) throws Exception {
        Scanner sc = new Scanner(System.in);
        String answer;
        System.out.println(question);
        answer = sc.nextLine();
        while (!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("no")) {
            System.out.println(question);
            answer = sc.nextLine();
        }
        return answer;
    }
}
