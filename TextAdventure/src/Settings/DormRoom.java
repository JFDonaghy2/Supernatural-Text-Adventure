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
    int samJessResponse = 0;
    int samDeanResponse = 0;
    int deanResponse = 0;
    int waitOption = 0;
    Monster figure = new Monster("the figure", "A broad shouldered"
            + " menace who looks oddly familiar...", 35, 40,
            "However, the figure manages to pin you down.\n"
            + "Figure: \"Whoa! Easy, tiger.\"\n"
            + "Wait. Is this your brother DEAN?", 1);
    Friendly jessNurse = new Friendly("Jessica Moore", "Your smokin' hot girlfriend.\n"
            + "Get it? You will...");
    Friendly jessSmurf = new Friendly("Jessica Moore", "Your smokin' hot girlfriend.\n"
            + "Get it? You will...");
    Monster deanMon = new Monster("Dean Winchester", "A chesnut-haired, Southern model.\n"
            + "Please try not to stare.", 35, 40, "* Sam wraps his leg around "
                    + "Dean and flips them both over. *\n* Dean smiles *\nDean: \"Or not.\""
                    + "\nDean: \"Get off me.\"", 1);
    Friendly dean = new Friendly("Dean Winchester", "A chesnut-haired, Southern model.\n"
            + "Please try not to stare.");

    public void talkTo(String s, Player p) {
        if (s.equalsIgnoreCase(p.getName())) {
            System.out.println("While perfectly acceptable, talking to yourself "
                    + "is not going to help you here.");
        } else if (s.equals("jess") || s.equals("jessica")) {
            if (friendlies.isEmpty()) {
                System.out.println("Jessica is waiting for you outside.");
            } else if (samJessResponse == 0) {
                System.out.println("\"Do I have to?\"");
                System.out.println("Jess: \"Yes! It'll be fun. "
                        + "And where is your costume?\"");
                samJessResponse++;
            } else if (samJessResponse == 1) {
                System.out.println("\"You know how I feel about Halloween.\"");
                System.out.println("Jess: \"And that is why we are going out to the bar "
                        + "tonight! Come on. Follow me.\"");
                System.out.println("Jessica moves through the door to the west.");
                friendlies.remove(0);
                samJessResponse++;
            }
        } else if (s.equalsIgnoreCase("dean")
                || s.equalsIgnoreCase("dean winchester")) {
            if (!enemies.contains(deanMon) && !p.getProgress("BrothersReunited")) {
                    enemies.add(deanMon);
                    System.out.println("\"Dean?\"");
                    p.checkPoint(new Checkpoint("BrothersReunited"));
                    System.out.println("*The figure, Dean, laughs.*");
                    enemies.remove(0);
                    samDeanResponse++;
            } else if (samDeanResponse == 1) {
                System.out.println("\"You scared the crap outta me.\"");
                System.out.println("Dean: \"Because you're out of practice.\"");
//                Sam fights Dean THEN the deanMon is removed
//                enemies.remove(0);
//                friendlies.add(dean);
                samDeanResponse++;
            } else if (samDeanResponse == 2) {
//            } else if (!friendlies.contains(dean) && !enemies.contains(deanMon)) {
                friendlies.add(dean);
                System.out.println("You help Dean to his feet.");
                System.out.println("\"Dean, what the hell are you doing here?");
                System.out.println("Dean: \"I was looking for a beer.\"");
                samDeanResponse++;
            } else if (samDeanResponse == 3) {
                System.out.println("\"What the hell are you doing here.\"");
                System.out.println("Dean: \"Okay, alright."
                        + "\nWe gotta talk\"");
                samDeanResponse++;
            } else if (samDeanResponse == 4) {
                System.out.println("\"Uh, the phone?\"");
                System.out.println("Dean: \"If I'da called would you have picked up?\"");
                samDeanResponse++;
            } else if (samDeanResponse == 5) {
//                JESS ENTERS
                System.out.println("Before you can speak, a sleepy Jessica enters the room.");
                System.out.println("Jess: \"Sam?\"");
                System.out.println("\"Jess. Hey. Dean, this is my girlfriend Jessica.\"");
                System.out.println("Jess: \"Wait. Your BROTHER Dean?\"");
                System.out.println("Dean: \"I love the smurfs.");
                System.out.println("You know, I gotta tell you: YOU are out of my brother's league.\"");
                System.out.println("Jess: \" Just...let me put something on.\"");
                System.out.println("Dean: \"No, NO, no. I wouldn't dream of it. Seriously.");
                System.out.println("Anyway, I gotta borrow your boyfriend here. "
                        + "\nTalk about some private family business."
                        + "\nBut, uh, nice meeting YOU.\"");
                samDeanResponse++;
            } else if (samDeanResponse == 6) {
                System.out.println("\"No. No, whatever you want to say, you can say it in front of her.\"");
                System.out.println("Dean: \"Okay, um, Dad hasn't been home in a few days.\"");
                samDeanResponse++;
            } else if (samDeanResponse == 7) {
                System.out.println("\"So he's working overtime on a Miller Time shift."
                        + "\nHe'll stumble back in sooner-or-later.\"");
                System.out.println("Dean: \"Dad's on a HUNTING TRIP. And he hasn't been home in a few days.\"");
                samDeanResponse++;
            } else if (samDeanResponse == 8) {
                System.out.println("\"Jess, excuse us. We have to go outside.\"");
                p.checkPoint(new Checkpoint("DadsOnAHuntingTrip"));
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
                    System.out.println("Jess: \"Sam! You comming or what?\"");
                    if (friendlies.isEmpty()) {
                        friendlies.add(jessNurse);                        
                    }
                    break;
            }
        } else if (enemies.isEmpty() && !friendlies.contains(dean)) {
                samJessResponse = 2;
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
//        locked = false;
    }

    public void lock() {
//        locked = true;
    }

    @Override
    public void trigger(Player p) {
        if (!p.getProgress("BarReturn")) {
                this.locked = true;
                System.out.println("You shouldn't leave the bar until Jess is done celebrating.");
        }
        if (p.getProgress("BarReturn")) {
            if (!friendlies.isEmpty()) {
                friendlies.remove(0);
            }
            samJessResponse = 2;
            this.locked = false;
            info = "You and Jess continue to \"celebrate\" a little more "
                    + "in the bedroom.\nA few hours later, you awaken to a noise"
                    + " that sounded like someone bumping into your living room"
                    + " table.\nJumping out of bed, you notice that the window "
                    + "is open.\nYou stand at the doorframe, staring into the"
                    + " living room.\nIf you wait here, you might be able to "
                    + "see the figure go by.";
            friendlies.add(jessSmurf);
            waitOption++;
        }
    }
    // Lock the dorm behind you as you begin "Don't you want to hear Dean out?"
}
