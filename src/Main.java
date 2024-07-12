import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Fighter f1 = new Fighter();
        Goblin g1 = new Goblin();

        System.out.println("What is your name?");
        f1.setName(sc.nextLine());

        while(f1.getIsAlive()) {
            String answer = yesOrNo("Does " + f1.getName() + " want to descend the dungeon?");
            if (answer.equalsIgnoreCase("no")) {
                System.out.println("Ok.");
                break;
            } else {
                answer = yesOrNo(g1.getName() + " appears! Does " + f1.getName() + " want to fight?");
                if (answer.equalsIgnoreCase("Yes"))
                {
                    while (f1.getIsAlive() && g1.getIsAlive()) {
                        if (f1.attack() >= g1.getArmor()) {
                            g1.setHp(f1.damage());
                            System.out.println(f1.getName() + " hit the goblin! It has " + g1.getHp() + " HP.");
                        } else {
                            System.out.println(f1.getName() + " missed. Womp womp.");
                        }
                        if (g1.attack() >= f1.getArmor()) {
                            f1.setHp(g1.damage());
                            System.out.println(f1.getName() + " was hit by the goblin! " + f1.getName() + " has " + f1.getHp() + " HP.");
                        } else {
                            System.out.println(g1.getName() + " missed.");
                        }
                        if (g1.getHp() <= 0)
                        {
                            g1.dead();
                            System.out.println(f1.getName() + " killed " + g1.getName() + "!");
                        }
                        if (f1.getHp() <= 0)
                        {
                            f1.dead();

                            System.out.println(g1.getName() + " killed " + f1.getName() + "! Game over :(");
                        }

                        if(f1.getIsAlive() && g1.getIsAlive()) {
                            answer = yesOrNo("The round is over but " + g1.getName() + " lives. Do you want to continue to fight?");
                            if (answer.equalsIgnoreCase("no")) {
                                break;
                            }
                        }
                    }
                }
                if(answer.equalsIgnoreCase("no")) {
                    System.out.println("You attempt to flee the " + g1.getName() + ".");
                    if(g1.attack() >= f1.getArmor()) {
                        f1.setHp(g1.damage());
                        System.out.println(f1.getName() + " is hit by an opportunity attack by " + g1.getName() + ". Now " + f1.getName() + " has " + f1.getHp() + " HP.");

                        if(f1.getHp() <= 0) {
                            f1.dead();
                            System.out.println();
                            System.out.println(g1.getName() + " killed " + f1.getName() + "! Game over :(");
                        }
                    } else {
                        System.out.println(g1.getName() + " failed to hit " + f1.getName() + " with an opportunity attack.");
                    }
                }
            }
            if(f1.getIsAlive()) {
                f1.rest();
                g1.resetHP();
            }
        }
    }

    public static String yesOrNo(String question) {
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
