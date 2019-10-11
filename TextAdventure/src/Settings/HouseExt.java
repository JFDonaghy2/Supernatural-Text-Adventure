package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Friendly;
import Things.Item;
import Things.Monster;
import Things.PickUp;

public class HouseExt extends Location {

    public HouseExt(Lake l) {
        this.location = "inside of SOMEBODY's house";
        this.intoLoc = "into SOMEBDOY's house";
        this.locked = true;
        this.info = "There is a house to the north. It looks very run down, but the door is still locked. There is a fang lying on the ground. ";
        this.n = new HouseInt(this);
        this.s = null;
        this.e = null;
        this.w = l;
        this.items = new ArrayList<PickUp>();
        items.add(new Item("Fang", "It is an extremely sharp fang. Better show this to Bobby once you get more clues!"));
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public void unlock(Player p) {
        if (p.hasItem("Directions")) {
//		if (k.getName().equalsIgnoreCase("Directions")) {
            locked = false;
            System.out.println("The directions help you find your way.");
        } else {
            System.out.println("There is no road here! You could get lost!");
        }
    }

    @Override
    public void waitTurn(Player p) {
        waits++;
        System.out.println(info);
    }

    @Override
    public void talkTo(String s, Player p) {
        System.out.println("There is nobody here to talk to.");
    }
}
