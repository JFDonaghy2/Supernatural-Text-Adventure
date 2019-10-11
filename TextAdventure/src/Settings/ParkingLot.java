package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
//import Things.Item;
import Things.Medicine;
import Things.PickUp;
import Things.Friendly;
import Things.Monster;

public class ParkingLot extends Location {

    public ParkingLot(Player p, Location Stairs) {
        this.location = "Stanford parking lot";
        this.intoLoc = "through the parking lot to Dean's "
                + "'67 Chevy Impala, 'Baby'";
        this.locked = false;
        this.info = "";
        this.n = null;
        this.s = null;
        this.e = null;
        this.w = Stairs;
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public void talkTo(String s, Player p) {
        if (s.equalsIgnoreCase(p.getName())) {
            System.out.println("While perfectly acceptable, talking to yourself "
                    + "is not going to help you here.");
        } else {
            System.out.println("That person is not here, " + p.getName());
        }
    }


    /*
	 * The rooms change their description for each call of wait. Here, Mary waiting 
	 * allows her to live and give her boys the life that she always wanted for them.
	 * However, this good childhood keeps Sam and Dean from being able to save Sam from
	 * the other psychic kids. 
	 * Thus, by waiting 4 times, the player skips straight ahead to Sam at Stanford.
	 * @see Rooms.Room#waitTurn()
     */
    @Override
    public void waitTurn(Player p) {

    }

    @Override
    public void unlock(Player p) {
        locked = false;
    }

    /*
	 * From the parser, if you leave this room, it should lock so that you now HAVE
	 * to face YellowEyes.
     */
    public void lock() {
        locked = true;
    }

    @Override
    public void trigger(Player p) {
        if (!p.getProgress("DeanLecture1")) {
            this.locked = true;
            System.out.println("Where are you going to go? "
                    + "Dean has the keys.");
        }
    }
}
