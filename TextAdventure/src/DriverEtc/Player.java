package DriverEtc;

//import Things.Item;
import Settings.Location;
import Things.Medicine;
import Things.Monster;
import Things.PickUp;
import Things.Weapon;
import Things.RevealerMedicine;
import Things.RevealerWeapon;

/*
* Over time, I would like to move away from "Player" and use the types 
*(Winchester, Angel, D, ARKangel, Baby (Impala) and civilian). 
 */
public class Player {

    private String name;
    protected Weapon equip;
    private RevealerWeapon equip2;
    protected Weapon hand2hand;
    protected int health;
    private String pronoun;
    private int levelPoints;
    private Inventory inv;
    private Inventory storyProgress;
    private Location curLoc;

    public Player(String n) {
        name = n;
        if (n.equalsIgnoreCase("mary") || n.equalsIgnoreCase("jo")
                || n.equalsIgnoreCase("ellen") || n.equalsIgnoreCase("anna")
                || n.equalsIgnoreCase("ruby") || n.equalsIgnoreCase("pamela")) {
            this.pronoun = "her";
        } else {
            this.pronoun = "his";
        }
        equip = new Weapon("fists", "Your all-natural weapons.", 45);
        inv = new Inventory();
        storyProgress = new Inventory();
    }

    public int getHealth() {
        return health;
    }

    public int getPoints() {
        return levelPoints;
    }

    public void addPoints(int points) {
        this.levelPoints += points;
        if (this.levelPoints <= 0) {
            this.levelPoints = 0;
        }
    }

    public void attackedBy(Monster m) {
        if (health - m.getAttack() > 0) {
            if (m.isAlive()) {
                health -= m.getAttack();
                System.out.println(m.getName() + " attacks and does " + m.getAttack() + " damage!");
                System.out.println("Your health is now " + health + ".");
            }
        } else if (!name.equalsIgnoreCase("mary")) {
            health -= m.getAttack();
            System.out.println(m.getName() + " has killed you.");
        } else {
            health -= m.getAttack();
        }
    }

    public void healBy(Medicine m) {
        if (health + m.getHealing() >= 100) {
            health = 100;
            System.out.println("Your health is full.");
            inv.usedItem(m);
        } else {
            health += m.getHealing();
            System.out.println("Your health is now " + health + ".");
            inv.usedItem(m);
        }
    }

    public void healBy(RevealerMedicine m) {
        if (health + m.getHealing() >= 100) {
            health = 100;
            System.out.println("Your health is full.");
            inv.usedItem(m);
        } else {
            health += m.getHealing();
            System.out.println("Your health is now " + health + ".");
            inv.usedItem(m);
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean hasItem(String s) {
        return inv.hasItem(s);
    }

    public PickUp getItem(String item) {
        return inv.getItem(item);
    }

    public PickUp dropItem(String item) {
        return inv.dropItem(item);
    }

    public PickUp giveItem(String item, String friend) {
        return inv.giveItem(item, friend);
    }

    public void pickUp(PickUp item) {
        inv.addItem(item, this);
    }

    public void equipWeapon(Weapon equip) {
        if (inv.hasItem(equip.getName())) {
            this.equip = equip;
            System.out.println("You are now carrying your " + equip.getName() + ".");
        } else {
            System.out.println("You do not have a(n) " + equip.getName() + "!");
        }
    }

    public void equipWeapon(RevealerWeapon equip) {
        if (inv.hasItem(equip.getName())) {
            this.equip2 = equip;
            System.out.println("You are now carrying your " + equip.getName() + ".");
        } else {
            System.out.println("You do not have a(n) " + equip.getName() + "!");
        }
    }

    public Weapon getEquip() {
        return equip;
    }

    public RevealerWeapon getEquip2() {
        return equip2;
    }
    
    public String getName() {
        return name;
    }

    public String getPronoun() {
        return pronoun;
    }

    public void getInventory() {
        inv.printInventory();
    }

    public void checkPoint(PickUp token) {
        storyProgress.addItem(token, this);
    }

    public boolean getProgress(String item) {
        return storyProgress.hasItem(item);
    }
    
    public Location getLoc() {
        return curLoc;
    }
    
    public void changeLoc(Location loc) {
        curLoc = loc;
    }
}
