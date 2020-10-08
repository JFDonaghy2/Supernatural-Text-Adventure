package Things;

import DriverEtc.Player;

public class Monster {

    private final String name;
    private final String info;
    private final String deathMessage;
    private final int attStat;
    private int health;
    private PickUp itemDrop;
    private final int levelPoints;
    private boolean drops;

    public Monster(String name, String info, int attStat, int health,
            String deathMessage, PickUp itemDrop, int levelPoints) {
        this.name = name;
        this.info = info;
        this.attStat = attStat;
        this.health = health;
        this.deathMessage = deathMessage;
        drops = true;
        this.itemDrop = itemDrop;
        this.levelPoints = levelPoints;
    }

    public Monster(String name, String info, int attStat, int health,
            String deathMessage, int levelPoints) {
        this.name = name;
        this.info = info;
        this.attStat = attStat;
        this.health = health;
        drops = false;
        this.deathMessage = deathMessage;
        this.levelPoints = levelPoints;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getDeathMessage() {
        return deathMessage;
    }

    public int getAttack() {
        return attStat;
    }

    public int getHealth() {
        return health;
    }

    public boolean dropsItem() {
        return drops;
    }

    public void attackMonster(Weapon w, Player p) {
        if (name.equalsIgnoreCase("fire")) {
            System.out.println("You can only fight fire with fire, smokey.");
            return;
        }
        System.out.println(p.getName() + " attacks " + name
                + " with " + p.getPronoun() + " " + w.getName() + "."
                + " It caused " + w.getAttack() + " damage!");
        health -= w.getAttack();
        if (!isAlive()) {
            System.out.println(deathMessage);
            if (dropsItem()) {
                if (!itemDrop.getClass().toString().equalsIgnoreCase(
                        "class Things.Checkpoint")) {
                    System.out.println(name + " dropped " + itemDrop.getName() + ".");
                    System.out.println("This is in Monster.java if you wish to change it.");
                }
                p.pickUp(itemDrop);
                p.addPoints(levelPoints);
            }

        }
    }
    
    /**
     *
     * @param w
     * @param p
     */
    public void attackMonster(RevealerWeapon w, Player p) {
        System.out.println(p.getName() + " attacks " + name
                + " with " + p.getPronoun() + " " + w.getName() + "."
                + " It caused " + w.getAttack() + " damage!");
        health -= w.getAttack();
        if (!isAlive()) {
            System.out.println(deathMessage);
            if (dropsItem()) {
                if (!itemDrop.getClass().toString().equalsIgnoreCase(
                        "class Things.Checkpoint")) {
                    System.out.println(name + " dropped " + itemDrop.getName() + ".");
                    System.out.println("This is in Monster.java if you wish to change it.");
                }
                p.pickUp(itemDrop);
                p.addPoints(levelPoints);
            }

        }
    }

    public boolean isAlive() {
        return (health > 0);
    }
}
