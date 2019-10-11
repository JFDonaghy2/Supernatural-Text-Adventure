package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
//import Things.Item;
import Things.Medicine;
import Things.PickUp;
import Things.Friendly;
import Things.Monster;
import Things.Checkpoint;
import Things.Item;
import Things.Weapon;

public class DormRoom extends Location {

    public DormRoom(Player p) {
        this.location = "dorm room";
        this.intoLoc = "across campus to your apartment";
        this.locked = false;
        this.info = "The apartment is a nice one bedroom, one bath.\n"
                + "The fridge is stocked with healthy snacks for you "
                + "and there's ice cream in the freezer for Jess.";
        this.n = null;
        this.s = null;
        this.e = new Stairs(p, this);
        this.w = new Bar(p, this);
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        items.add(new Medicine("Kale Smoothie", "Sadly, it is exactly"
                + " what it sounds like.", 15));
        items.add(new Medicine("Ice cream",
                "It's Strawberry Cheesecake ice cream!\n"
                + "Exactly what you need after a hard day.", -30));
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
    }

    int jessResponse = 0;
    int samResponse = 0;
    int deanResponse = 0;
    int waitOption = 0;
    Monster figure = new Monster("the figure", "A broad shouldered"
            + " menace who looks oddly familiar...", 35, 40,
            "However, the figure manages to pin you down.\n"
            + "Figure: \"Whoa! Easy, tiger.\"\n"
            + "Wait. Is this your brother DEAN?",
            new Checkpoint("BrothersReunited"), 1);
    Friendly jess = new Friendly("Jessica Moore", "Your smokin' hot girlfriend.\n"
            + "Get it? You will...");
    Friendly dean = new Friendly("Dean Winchester", "DEAN INFO");

    public void talkTo(String s, Player p) {
        if (s.equalsIgnoreCase(p.getName())) {
            System.out.println("While perfectly acceptable, talking to yourself "
                    + "is not going to help you here.");
        } else if (s.equals("jess") || s.equals("jessica")) {
            if (friendlies.isEmpty()) {
                System.out.println("Jessica is waiting for you outside.");
            } else if (samResponse == 0) {
                System.out.println("\"Do I have to?\"");
                System.out.println("Jess: \"Yes! It'll be fun. "
                        + "And where is your costume?\"");
                samResponse++;
            } else if (samResponse == 1) {
                System.out.println("\"You know how I feel about Halloween.\"");
                System.out.println("Jess: \"And that is why we are going out to the bar "
                        + "tonight! Come on. Follow me.\"");
                System.out.println("Jessica moves through the door to the west.");
                friendlies.remove(0);
                samResponse++;
            }
        } else if (s.equalsIgnoreCase("dean")
                || s.equalsIgnoreCase("dean winchester")) {
            if (!friendlies.contains(dean)) {
                    friendlies.add(dean);
                    System.out.println("Is that...your brother DEAN?");
            } else if (!p.getProgress("BrothersReunited")) {
                System.out.println("\"Dean?\"");
            } else {
                System.out.println("You are talking to your brother and it is "
                        + "really emotional.");
            }
        } else if (s.equalsIgnoreCase("figure")
                || s.equalsIgnoreCase("the figure")) {
            if (!p.getProgress("BrothersReunited")) {
                System.out.println("Don't be too loud! You still have the "
                        + "element of surprise.");
            } else {
                System.out.println("You can refer to the figure as \'Dean\' "
                        + "now.");
            }
        } else if (s.equalsIgnoreCase("skip")) {
            if (!p.getProgress("BarReturn")) {
                System.out.println("Jessica moves through the door to the "
                        + "west.");
                if (!friendlies.isEmpty()) {
                    friendlies.remove(0);
                }
                samResponse = 2;
            } else {
                /*
                        Skip through the Sam&Dean dialogue.
                 */
                p.checkPoint(new Checkpoint("BrothersReunited"));
                p.checkPoint(new Checkpoint("DadsOnAHuntingTrip"));
            }
        } else {
            System.out.println("That person is not here, " + p.getName());
        }
    }

    @Override
    public void waitTurn(Player p) {
        if (waitOption == 0) {
            jessResponse++;
            switch (jessResponse) {
                case 1:
                    System.out.println("Jess: \"We were supposed to be there, like, "
                            + "15 minutes ago.\"");
                    info = "The apartment is a nice one bedroom, one bath.\n"
                            + "The fridge is stocked with healthy snacks for you "
                            + "and there's ice cream in the freezer for Jess.\n"
                            + "Jess is ready to hit the town.\n"
                            + "Tonight IS Halloween after all...";
                    break;
                default:
                    System.out.println("Jess:\"Sam! You comming or what?\"");
                    break;
            }
        } else if (enemies.isEmpty() && !friendlies.contains(dean)) {
                enemies.add(figure);
                System.out.println("A figure moves past your line of sight"
                    + " from one side of the living room to the other.\n"
                    + "You spring into action, sneaking up on the figure.\n"
                    + "You stand behind the figure, waiting to act.");
        } else {
            System.out.println("What are you waiting for?\nTalk to Dean!");
        }

            //if ()
     
    }

    @Override
    public void unlock(Player p) {
        locked = false;
    }

    public void lock() {
        locked = true;
    }

    @Override
    public void trigger(Player p) {
        if (friendlies.isEmpty()) {
            friendlies.add(jess);
        }
        if (p.getProgress("BarReturn")) {
            info = "You and Jess continue to \"celebrate\" a little more "
                    + "in the bedroom.\nA few hours later, you awaken to a noise"
                    + " that sounded like someone bumping into your living room"
                    + " table.\nJumping out of bed, you notice that the window "
                    + "is open.\nYou stand at the doorframe, staring into the"
                    + " living room.\nIf you wait here, you might be able to "
                    + "see the figure go by.";
            waitOption++;
        }
    }
    // Lock the dorm behind you as you begin "Don't you want to hear Dean out?"
}
