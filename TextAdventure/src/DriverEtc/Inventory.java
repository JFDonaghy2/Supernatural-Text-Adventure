package DriverEtc;

import Things.Checkpoint;
import java.util.ArrayList;
import Things.Revealer;
import Things.PickUp;

public class Inventory {

    private ArrayList<PickUp> inventory;

    public Inventory() {
        inventory = new ArrayList<PickUp>();
    }

    public PickUp getItem(String item) {
        for (PickUp p : inventory) {
            if (p.getName().equalsIgnoreCase(item)) {
                return p;
            }
        }
        return null;
    }

    public boolean hasItem(String item) {
        for (PickUp p : inventory) {
            if (p.getName().equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }

    public PickUp dropItem(String item) {
        int index = 0;
        for (PickUp p : inventory) {
            if (p.getName().equalsIgnoreCase(item)) {
                System.out.println("You have dropped " + item + ".");
                return inventory.remove(index);
            }
            index++;
        }
        System.out.println("You don't have " + item);
        return null;
    }

    public PickUp giveItem(String item, String friend) {
        int index = 0;
        for (PickUp p : inventory) {
            if (p.getName().equalsIgnoreCase(item)) {
                System.out.println("You have given " + item + " to " + friend);
                return inventory.remove(index);
            }
            index++;
        }
        System.out.println("You don't have " + item);
        return null;
    }

    /*
    * Current desire: For Revealers to "drop" an item into a room.
     */
    public void addItem(PickUp item, Player player) {
        inventory.add(item);
        /*
        * Not all checkpoints are tied to big aspects of the game.
        * Only certain ones should reward the character with points to progress,
        * so those points will be awarded SEPARATELY from the checkpoint, like,
        * on the next line.
        *
        * The if statement is written like this in case other THINGS should not
        * require a statement be printed.
         */
        if (item instanceof Revealer) {
            ((Revealer) item).printMessage();
            PickUp p = ((Revealer) item).getItem();
            if (p != null) {
                if (((Revealer) item).addToLocation() == true) {
                    player.getLoc().droppedItem(p);
                } else {
                    player.pickUp(p);
                }
            }
        } else if (item instanceof Checkpoint) {

        } else {
            System.out.println(player.getName()
                    + " has picked up "
                    + item.getName() + ".");
        }
    }

    public void usedItem(PickUp item) {
        inventory.remove((PickUp) item);
    }

    public void givenItem(PickUp item, String grabber) {
        inventory.add(item);
        if (item.getName().equalsIgnoreCase(grabber)) {
            System.out.println("Noice! I'm hiding in Inventory.java");
        } else {
            System.out.println(item.getName());
        }
    }

    //	public Boolean vowelCheck(String word) {
    //		if (word.startsWith("a") || word.startsWith("A") ||
    //				word.startsWith("e") || word.startsWith("E") ||
    //				word.startsWith("i") || word.startsWith("I") ||
    //				word.startsWith("o") || word.startsWith("O") ||
    //				word.startsWith("u") ||	word.startsWith("U")) {
    //			return true;
    //		} else {
    //			return false;
    //		}
    //	}
    //	
    public void printInventory() {
        if (inventory.size() == 1) {
            System.out.println("You have " + inventory.get(0).getName() + ".");
        } else if (inventory.size() > 1) {
            System.out.print("You have " + inventory.get(0).getName());
            int size = inventory.size() - 1;
            for (int i = 1; i < size; i++) {
                System.out.print(",\n " + inventory.get(i).getName());
            }
            System.out.println(",\n and "
                    + inventory.get(size).getName() + ".");
        } else if (inventory.isEmpty()) {
            System.out.println("You do not have any items.");
        }
    }

    public void printInventoryVowelCheck() {
        if (inventory.size() == 1) {
            if (inventory.get(0).getName().startsWith("A")
                    || inventory.get(0).getName().startsWith("E")
                    || inventory.get(0).getName().startsWith("I")
                    || inventory.get(0).getName().startsWith("O")
                    || inventory.get(0).getName().startsWith("U")) {
                System.out.println("You have an "
                        + inventory.get(0).getName() + ".");
            } else {
                System.out.println("You have a "
                        + inventory.get(0).getName() + ".");
            }
        } else if (inventory.size() > 1) {
            if (inventory.get(0).getName().startsWith("A")
                    || inventory.get(0).getName().startsWith("E")
                    || inventory.get(0).getName().startsWith("I")
                    || inventory.get(0).getName().startsWith("O")
                    || inventory.get(0).getName().startsWith("U")) {
                System.out.println("You have an "
                        + inventory.get(0).getName() + ".");
            } else {
                System.out.println("You have a "
                        + inventory.get(0).getName() + ".");
            }
            int size = inventory.size() - 1;
            for (int i = 1; i < size; i++) {
                if (inventory.get(i).getName().startsWith("A")
                        || inventory.get(i).getName().startsWith("E")
                        || inventory.get(i).getName().startsWith("I")
                        || inventory.get(i).getName().startsWith("O")
                        || inventory.get(i).getName().startsWith("U")) {
                    System.out.print(", an " + inventory.get(i).getName());
                } else {
                    System.out.print(", a " + inventory.get(i).getName());
                }
            }
            if (inventory.get(size).getName().startsWith("A")
                    || inventory.get(size).getName().startsWith("E")
                    || inventory.get(size).getName().startsWith("I")
                    || inventory.get(size).getName().startsWith("O")
                    || inventory.get(size).getName().startsWith("U")) {
                System.out.print(", and " + inventory.get(size).getName());
            } else {
                System.out.println(", and " + inventory.get(size).getName());
            }
        } else if (inventory.isEmpty()) {
            System.out.println("You do not have any items.");
        }
    }

}
