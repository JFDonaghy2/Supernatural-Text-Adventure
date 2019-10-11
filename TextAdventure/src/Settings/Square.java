package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Friendly;
//import Things.Key;
import Things.Monster;
import Things.PickUp;

public class Square extends Location {

    public Square(Street s) {
        this.location = "town square";
        this.intoLoc = "square";
        this.locked = false;
        this.info = "";
        this.n = null;
        this.s = new Store(this);
        this.e = s;
        this.w = null;
        this.items = new ArrayList<PickUp>();
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public void talkTo(String s, Player p) {

    }

    @Override
    public void waitTurn(Player p) {

    }

    @Override
    public void unlock(Player p) {
        locked = false;
    }

}
