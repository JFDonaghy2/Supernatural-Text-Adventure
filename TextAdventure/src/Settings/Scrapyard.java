package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Friendly;
//import Things.Key;
import Things.Monster;
import Things.PickUp;

public class Scrapyard extends Location {

    public Scrapyard(BobbyHouse b) {
        this.location = "Bobby's scrapyard";
        this.intoLoc = "Bobby's scrapyard";
        this.locked = false;
        this.info = "The old scrapyard outside of Bobby's place. "
                + "Your Impala is parked to the south, and you can "
                + "drive to Springfield with it.";
        this.n = b;
        this.s = new Street(this);
        this.e = null;
        this.w = null;
        this.items = new ArrayList<PickUp>();
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
