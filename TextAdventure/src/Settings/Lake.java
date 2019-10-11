package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Friendly;
import Things.Monster;
import Things.PickUp;

public class Lake extends Location {

    public Lake(Street s) {
        this.location = "";
        this.intoLoc = "Lake";
        this.locked = false;
        this.info = "This is the end of the street. "
                + "There is a beautiful lake. To the east there seems "
                + "to be something, but the woods are too thick. "
                + "You might get lost without help.";
        this.n = null;
        this.s = null;
        this.e = new HouseExt(this);
        this.w = s;
        this.items = new ArrayList<PickUp>();;
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    @Override
    public void waitTurn(Player p) {
        waits++;
        System.out.println(info);
    }

    @Override
    public void unlock(Player p) {
        locked = false;
    }

    @Override
    public void talkTo(String s, Player p) {
        System.out.println("There is nobody here to talk to.");
    }
}
