package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Friendly;
//import Things.Key;
import Things.Medicine;
import Things.Monster;
import Things.PickUp;
import Things.Checkpoint;
import java.util.Random;

public abstract class Location {

    protected String location;
    protected String intoLoc;
    protected boolean locked = false;
    protected String info;
    protected Location n;
    protected Location s;
    protected Location e;
    protected Location w;
    protected boolean playerStanding = true;
    protected Object[] trueLockStatus = {this.n, this.s, this.e, this.w};
    protected ArrayList<PickUp> items = null;
    protected ArrayList<Monster> enemies = null;
    protected ArrayList<Friendly> friendlies = null;
    protected int waits = 0;
    //	protected boolean goodChildhood = false;

    public boolean isLocked() {
        return locked;
    }

    /*
        * see unlock for the reason behind keeping this function/method
     */
    public void setLock(Boolean tf) {
        this.locked = tf;
    }

    public ArrayList<Monster> getEnemies() {
        return enemies;
    }

    public Location getN() {
        return n;
    }

    public Location getS() {
        return s;
    }

    public Location getE() {
        return e;
    }

    public Location getW() {
        return w;
    }

    public PickUp getItem(String item) {
        for (PickUp p : items) {
            if (p.getName().equalsIgnoreCase(item)) {
                return p;
            }
        }
        return null;
    }

    public boolean hasItem(String item) {
        return (getItem(item) != null);
    }

    public Monster getMonster(String monster) {
        for (Monster m : enemies) {
            if (m.getName().equalsIgnoreCase(monster)
                    || m.getName().equalsIgnoreCase("the " + monster)) {
                return m;
            }
        }
        return null;
    }

    public boolean hasMonster(String item) {
        return (getMonster(item) != null);
    }

    public Friendly getFriendly(String friendly) {
        for (Friendly f : friendlies) {
            if (f.getName().equalsIgnoreCase(friendly)
                    || f.getNickname().equalsIgnoreCase(friendly)) {
                return f;
            }
        }
        return null;
    }

    public boolean hasFriendly(String item) {
        return (getFriendly(item) != null);
    }

    public void removeItem(PickUp item) {
        items.remove(item);
    }

    public void droppedItem(PickUp item) {
        if (item != null) {
            items.add(item);
        }
    }

    public String getInfo() {
        return info;
    }

    public void changeInfo(String s) {
        this.info = s;
    }

    public Location goTo(char c) {
        switch (c) {
            case 'n':
                return n;
            case 's':
                return s;
            case 'e':
                return e;
            case 'w':
                return w;
            default:
                throw new IllegalArgumentException();
        }
    }

    public abstract void waitTurn(Player p);

    /*
        * If this seems redundant due to changes in trigger, then you might
        * be forgetting that there are actual keys that change the state of
        * rooms, so the player can choose whether the room is locked or unlocked
        * independently of the room's trigger (which will need to be updated to 
        * recognize the player's inventory so that it doesn't change the state
        * of the room against the player's wishes.
     */
    public abstract void unlock(Player p);
    
    public void triggerMonster(Player p, int playerPoints) {
            Random r = new Random();
            /*
            * Recheck your math when you get a chance.
            */
            int monsType = (r.nextInt() % playerPoints);
            
            /*
            * Produce a level 1 monster
            */
            if (monsType < 200) {
                System.out.println("Level 1 monster attacks!");
                    //enemies.add(new Monster()) 
                    
            /*
            * Produce a level 1 OR level 2 monster
            */        
            } else if (monsType < 300) {
                System.out.println("Level 1 or 2 monster attacks!");                
            /*
            * Produce a level 1, 2, OR 3 monster
            */    
            } else if (monsType < 400) {
                System.out.println("Level 1, 2, or 3 monster attacks!");                
            }

    }

    public abstract void talkTo(String s, Player p);

    public void trigger(Player p) {
        /* Once the client has obtained a certain level with the player (here, 
        * at 100 points), and there have not already been Monsters added to this
        * location, then a monster will randomly be chosen based on the player's
        * point level. For instance, 100 - 199 is a demon, 200 - 299 is a ghost,
        * 300 - 399 is TWO demons,
        */
        if (p.getPoints() >= 100 && enemies.isEmpty()) {
            triggerMonster(p, p.getPoints());
        }
        if (this.locked == true) {
            System.out.println("Seems like the room is locked.");
        }
    }

    public String getLoc() {
        return location;
    }

    public String intoLoc() {
        return intoLoc;
    }

    public int waits() {
        return waits;
    }

    public void lookItems() {
        if (items.isEmpty()) {
            System.out.println("You see that there are no items in this room.");
        } else if (items.size() == 1) {
            System.out.println("You see " + items.get(0).getName() + ".");
        } else {
            System.out.print("You see " + items.get(0).getName());
            int size = items.size() - 1;
            for (int i = 1; i < size; i++) {
                System.out.print(", " + items.get(i).getName());
            }
            System.out.println(", and " + items.get(size).getName());
        }
    }

    public void lookFriendly() {
        if (friendlies.isEmpty()) {
            System.out.println("You see that there are no friends in this room.");
        } else if (friendlies.size() == 1) {
            System.out.println("You see " + friendlies.get(0).getName() + ".");
        } else {
            System.out.print("You see " + friendlies.get(0).getName());
            int size = friendlies.size() - 1;
            for (int i = 1; i < size - 1; i++) {
                System.out.print(", " + friendlies.get(i).getName());
            }
            System.out.println(", and " + friendlies.get(size).getName());
        }
    }

    public void lookMonsters() {
        if (enemies.isEmpty()) {
            System.out.println("You see that there are no monsters in this room.");
            System.out.println("You can breathe a sigh of relief.");
        } else if (enemies.size() == 1) {
            System.out.println("You see " + enemies.get(0).getName() + ".");
        } else {
            System.out.print("You see " + enemies.get(0).getName());
            int size = enemies.size() - 1;
            for (int i = 1; i < size - 1; i++) {
                System.out.print(", " + enemies.get(i).getName());
            }
            System.out.println(", and " + enemies.get(size).getName());
        }
    }

    public void lookHelp() {
        if (items.isEmpty() && friendlies.isEmpty()) {
            System.out.println("You see that there is no help in this room.");
            System.out.println("Kinda sad, isn't it?.");
        } else {
            lookItems();
            lookFriendly();
        }
    }

    public void lookMedicine() {
        if (items.isEmpty()) {
            System.out.println("You see that there is no medicine in this room.");
        } else {
            ArrayList<Medicine> medicine = new ArrayList<Medicine>();
            for (PickUp p : items) {
                if (p instanceof Medicine) {
                    medicine.add((Medicine) p);
                }
            }
            if (medicine.isEmpty()) {
                System.out.println("You see that there is no medicine in this room.");
            } else if (medicine.size() == 1) {
                System.out.print("You see " + medicine.get(0).getName());
            } else {
                System.out.print("You see " + medicine.get(0).getName());
                int size = medicine.size() - 1;
                for (int i = 1; i < size - 1; i++) {
                    System.out.print(", " + medicine.get(i).getName());
                }
                System.out.println(", and " + medicine.get(size).getName());
            }
        }
    }
    
    public boolean hasFriendlies() {
        return friendlies.isEmpty();
    }
    
    public boolean hasItems() {
        return items.isEmpty();
    }
    
    public boolean hasMonsters() {
        return enemies.isEmpty();
    }
    //	public void fallDown() {
    //		this.playerStanding = false;
    //		Room[] surrounding = {this.n, this.s, this.e, this.w};
    //		for(int i = 0; i < surrounding.length; i++) {
    //			if(surrounding[i] != null) {
    //				surrounding[i].setLock(true);
    //			}
    //		}
    //	}
    //
    //	public void standUp() {
    //		this.playerStanding = true;
    //		Room[] surrounding = {this.n, this.s, this.e, this.w};
    //		for(int i = 0; i < surrounding.length; i++) {
    //			if(surrounding[i] != null) {
    //				surrounding[i].setLock((boolean) trueLockStatus[i]);
    //			}
    //		}
    //	}	

}
