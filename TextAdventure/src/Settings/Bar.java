package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Checkpoint;
import Things.Friendly;
import Things.Item;
import Things.Medicine;
import Things.Monster;
import Things.PickUp;

public class Bar extends Location {

    public Bar(Player p, Location DormRoom) {
        this.location = "bar near the Stanford campus";
        this.intoLoc = "across campus to the bar";
        this.locked = false;
        this.info = "Someone truly wanted to get into the full Halloween spirit tonight.\n"
                + "The campus bar that is decorated with skeletons, jack o'lanterns, and cobwebs.\n"
                + "Everyone is dressed in costumes. Well, everyone except for you.\n"
                + "Your friend Luis is saving a table for you and Jessica near the bar.";
        this.n = null;
        this.s = null;
        this.e = DormRoom;
        this.w = null;
        this.items = new ArrayList<PickUp>();
        this.friendlies = new ArrayList<Friendly>();
        friendlies.add(new Friendly("Jess", "Party organizer. Here to make sure that you"
                + " celebrate tonight and actually think about the bright future that"
                + " lies ahead of you. "
                + "Make sure that you can't have a Jess in DormRoom AND in the Bar."));
        friendlies.add(new Friendly("Luis", "This guy played Gerald on \"Hey Arnold!\""
                + " No, seriously, look it up."));
        this.enemies = new ArrayList<Monster>();
        /*
		 * Come on. I HAVE to add a Brady easter egg. We know that he had to be around at
		 * Stanford. Why not have him watching his handy work from afar at the bar?
		 * Especially because we know that he is going to kill Jessica soon after this.
		 * His strength should be whatever is normal for his kind and his dropItem should
		 * point at what is to come or help to unlock a FUTURE easter egg.
         */
        enemies.add(new Monster("Brady", "Introduced you to Jess and will soon "
                + "be off to start working at Niveus Pharmaceuticals.",
                100, 25, "Brady: \"Oh, I won't be"
                + " going down so easily. Today is not the day that I die.\"\n"
                + "And, with that, Brady turns and runs away.",
                new Item("Business Card", "Who is Dr. Green?"), 25));
        waits = 0;
    }

    int jessResponse = 0;
    int samResponse = 0;

    public void talkTo(String s, Player p) {
        String name = p.getName();
        if (s.equalsIgnoreCase(name)) {
            System.out.println("Perhaps you'd like to talk to everyone else, " + name);
        }
        if (name.equalsIgnoreCase("Sam")) {
            if (s.equals("jess") || s.equals("jessica") || s.contains("luis")) {
                if (jessResponse == 0) {
                    System.out.println("Jess: \"Go wait for me over there with Luis "
                            + "while I go grab us some drinks.\n"
                            + "We're celebrating YOU tonight, so I'm buying.\"");
                } else {
                    switch (samResponse) {
                        case 0:
                            System.out.println("\"Alright, alright. It's not that big a deal.\"");
                            System.out.println("Jess: \"He acts all humble, but he scored a 174.\"");
                            System.out.println("Luis: \"Mmm. Is that good?\"");
                            System.out.println("Jess: \"Scary good.\"");
                            System.out.println("Luis: \"So there you go! You're a first round draft pick."
                                    + "\n You can go to any law school you want.\"");
                            samResponse++;
                            break;
                        case 1:
                            System.out.println("\"Actually, I got an interview here.\"\n"
                                    + "\"Monday.\"\n"
                                    + "\"If it goes okay, I think that I got a shot at a full ride next year."
                                    + "\"");
                            System.out.println("Jess: \"Hey! It's gonna go great.\"");
                            samResponse++;
                            break;
                        case 2:
                            System.out.println("\"It better.\"");
                            System.out.println("Luis: \"How does it feel to be the golden boy of your "
                                    + "family?\"");
                            samResponse++;
                            break;
                        case 3:
                            System.out.println("\"Nah, they don't know.\"");
                            System.out.println("Luis: \"Don't know? I would be gloating!\nWhy not?\"");
                            samResponse++;
                            break;
                        case 4:
                            System.out.println("\"'Cause we're not exactly the Brady's\"");
                            System.out.println("Luis: \"And I'm not exactly the Huxtables.\nMore shots!\"");
                            samResponse++;
                            break;
                        case 5:
                            System.out.println("\"No. NO. NO!\"");
                            System.out.println("Jess: \"Seriously, I'm proud of you.\n"
                                    + "You're gonna knock 'em dead on Monday and you're gonna get that "
                                    + "full ride.\nI know it.\"");
                            samResponse++;
                            break;
                        case 6:
                            System.out.println("\"What would I do without you?\"");
                            System.out.println("Jess: \"Crash and burn.\"");
                            System.out.println("And, with that, Jess leans across the table and gives you"
                                    + " a kiss.");
                            samResponse++;
                            if (!p.getProgress("BarReturn")) {
                                p.checkPoint(new Checkpoint("BarReturn"));
                                p.addPoints(15);
                            }
                            break;
                        default:
                            System.out.println("\"This has really been a great night, but I really think"
                                    + " it's time to turn in.\"\n"
                                    + "\"Don't you, Jess?\"");
                            if (friendlies.size() == 2) {
                                System.out.println("Luis:\"Yeah, I've got to get going myself anyway.\"");
                                info = "Someone truly wanted to get into the full Halloween spirit tonight.\n"
                                        + "The campus bar that is decorated with skeletons, jack o'lanterns, and cobwebs.\n"
                                        + "Everyone is dressed in costumes. Well, everyone except for you.";
                                friendlies.remove(1);
                            }
                            /*
					The first time that this is triggered, send Luis home.
					if (friendlies.contains("luis")){
					System.out.println("Luis:\"Yeah, I've got to get going myself anyway.\"");
					friendlies.remove(1);
					}
					Give the player something that will let them leave the bar/go back to the dorm.
                             */
                            break;
                    }
                }
            } else if (s.equalsIgnoreCase("brady")) {
                System.out.println("Brady: \"Hey, Sam. How are you doing?\n"
                        + "Sorry that I can't stay for the party. I have my first day at Niveus tomorrow.\n"
                        + "Man, Jessica sure looks smokin' hot tonight, doesn't she?\"");
            } else if (s.equalsIgnoreCase("skip")) {
                System.out.println("\"This has really been a great night, but I really think"
                        + " it's time to turn in.\"\n"
                        + "\"Don't you, Jess?\"");
                if (!p.getProgress("BarReturn")) {
                    p.checkPoint(new Checkpoint("BarReturn"));
                    p.addPoints(15);
                }
                if (friendlies.size() == 2) {
                    System.out.println("Luis:\"Yeah, I've got to get going myself anyway.\"");
                    info = "Someone truly wanted to get into the full Halloween spirit tonight.\n"
                            + "The campus bar that is decorated with skeletons, jack o'lanterns, and "
                            + "cobwebs.\n"
                            + "Everyone is dressed in costumes. Well, everyone except for you.";
                    friendlies.remove(1);
                    samResponse = 7;
                }
            } else {
                System.out.println(s + " is not here, " + p.getName() + ".");
            }
        }
    }

    @Override
    public void waitTurn(Player p) {
        waits++;
        if (jessResponse == 0) {
            jessResponse++;
            System.out.println("Jessica returns with several (rounds) of shots.");
            items.add(new Medicine("tequila", "It looks delicious, and will heal you if you "
                    + "drink it.", 40));
            items.add(new Medicine("whiskey", "It looks delicious, and will heal you if you "
                    + "drink it.", 40));
            System.out.println("Jess: \"So here's to Sam and his awesome LSAT victory!\"");
        } else {
            System.out.println("The conversation comes to a halt as you come up with something "
                    + "to say.");
        }
    }

    @Override
    public void unlock(Player p) {
        locked = false;
    }

    @Override
    public void trigger(Player p) {
        if (p.getProgress("BarReturn")) {
            this.locked = true;
        }
    }
}
