package DriverEtc;

import java.util.Scanner;

//import Rooms.DeanRoom;
//import Rooms.Bar;
import Settings.DormRoom;
import Settings.LivingRoom;
import Settings.MasterBedroom;
import Settings.Location;
//import Things.Key;
//import Things.Monster;
public class TextAdventure {

    /*
	 * How do you want to level up the character? Munchkin-style? where you level up after beating
	 * a monster? Or perhaps the traditional, up to a certain XP (use switch statements for each level,
	 * so level 1 is case 1 [because level 0/case 0 is before you choose to go with Dean and level 1 
	 * starts when you're out on the road, so you'll be given something to level you us?] and each 
	 * level/case is done by 
	 * How can you change from one room to another as a "CUT TO"? Do you just prompt the
	 * player to move there?
	 * Changes to make:
	 * Trying to get it so that when you give something to a friend, you also talk to
	 * 	   them. So, where is the command actually going? Where does "Sam" come from?
	 * Once Dean steps out into the hallway, his room should remain open.
	 * If you try to pick up a character, you are told that it's a little too heavy 
	 *     for you.
	 * Can switch between Sam and Dean with a phrase (maybe "Go Team Winchester!" 
	 *     Perhaps "Jerk" and "Snitch")
	 *     	I like that. Go Team Winchester would switch regardless of current, while,
	 *     	if you say the others as the wrong brother, it just says, "Hey! That's MY
	 *     	line!"
         *      OOh, oh and Dean has "PrayToCastiel" checkpoint that allows him to call upon Castiel
                    while Sam has "InBedWithRuby" that allows him to do the same with Ruby/his powers.
	 *     I would like for the whole beginning nursery scene to be triggered when John looks around.
	 * Fix: distinction between "the" as in "the" television vs. "the Colt" and, maybe
	 * 		"the Car Keys" 
	 * 		Probably best to just make it "a television" and then treat "the Colt" and
	 * 		"the Car Keys" like you would "Cas' trenchcoat" or "Bobby's Flask"
	 * 		Maybe tell the player that the only need to type in "television" not "a
	 * 		television" or "the television"
	 *      Player immediately picks up whatever monster dropped (in Monster.java)
	 * Check points and serialization (I want this to be a long game. You play through
	 * 		13+ seasons.) When you deserialize a saved game, you are reminded of 
	 * 		the check points that you passed in "The Road so Far."
	 * 		This would (at least) have to save:
	 * 		the last/current room
	 * 		current character
	 * 		items (both being carried and in the room)
	 * 		health status (so, all player information)
	 * 		the level (which, to some degree would include the strength of and previous
	 * 			monsters fought)
	 * 		Hide authorship in game.  (Like 3 easter eggs)
	 * 		When adding dialogue, you can use a switch statement in the talkTo method of
	 * 	the room that itself calls upon the waitTurn method (using this.waitTurn(p).
	 * 		Brady's dialogue in Bar.java
	 * 		Ability to skip talking if there is a lot of dialogue (set samResponse to max)
	 * 				send it through as a name and then be a condition
	 * 					like if (s.equalsIgnoreCase("skip") {
	 * 						system.out.println("Last line of dialogue? All dialogue? None? Whatever.")
	 * 					}
     */
 /*
	 * The actual Room stored in curRoom changes as the character moves, but there are
	 * special features specific to a room, so we monitor where we are to account for 
	 * that.
     */
    private static Location curRoom;

    private static Location lastRoom;

    /*
	 * First and foremost, I thought that it would be cool if you shift perspective
	 * between the Winchesters at the beginning (Mary->John->Sam)->Sam/Dean). Then,
	 * I adapted this to allow savvy users the chance to leave Azazel alone in order to
	 * to live long enough to have a normal life with her family. That is, until Sam is 
	 * zapped away to fight the other psychic kids with absolutely NO training at all.
	 * Hmm. How would that play out? Without Dean to trade his soul, would Mary do it?
     */
    public static boolean naturalEnding(Player p) {
        if (p.getName().equalsIgnoreCase("Mary")) {
            return (p.getItem("a good childhood for your boys.") != null);
        } else if (p.getName().equalsIgnoreCase("John")) {
            return (p.getProgress("AnOriginStory"));
        } else if (p.getName().equalsIgnoreCase("Sam")) {
            return (p.getItem("A healthy, independent brotherly relationship") != null);
        } else {
            return false;
        }
    }

    /*
	 * You can only move to an unlocked room, so this is a check to make sure that the
	 * move is valid. If it is, the current room of the character is changed. if it is
	 * not already unlocked,...
	 * COME BACK TO THIS
     */
    public static void changeRoom(Location room, Player p) {
        room.trigger(p);
        if (!room.isLocked()) {
            lastRoom = curRoom;
            curRoom = room;
            p.changeLoc(room);
            System.out.println("You move " + curRoom.intoLoc() + ". ");
            System.out.println(curRoom.getInfo());
        }
    }



    
    /*
	 * Here it is, the actual driver. Below you can change the text adventure info, 
	 * how to switch between the characters, and how to switch between sections:
	 * The Winchester Home, Stanford, Jericho, etc. 
     */
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the fan-made, Supernatural text adventure!\n");
        System.out.println("Type 'help' to display a list of valid commands.\n");
        //try {
            Thread.sleep(3000);
        //} catch (InterruptedException ex) {
        //    Logger.getLogger(TextAdventure.class.getName()).log(Level.SEVERE, null, ex);
        //}
        String info = "Lawrence, Kansas\n      1983\n\n\t\tMary Winchester\n"
                + "\t\t---------------\n"
                + "In her bedroom, "
                + "Mary Winchester,\nwife and mother "
                + "of two young boys (Sam and Dean) slowly wakes.";
        System.out.println(info);
        Thread.sleep(2500);
        curRoom = new MasterBedroom();
        Winchester mary = new Winchester("Mary");
        Winchester john = new Winchester("John");
        Winchester sam = new Winchester("Sam");


        System.out.println(curRoom.getInfo());

        /*
	 * As long as the player does not trigger Mary's natural ending or death,
	 * she is the current character.
         */
        while (!naturalEnding(mary) && mary.isAlive()) {
            System.out.println("------------------------------------------"
                    + "-----------------------------");
            System.out.print("Mary: ");            
            String input = in.nextLine();
            Parser parse = new Parser(lastRoom, curRoom);
            System.out.println("------------------------------------------"
                    + "-----------------------------");
            parse.commands(input, mary);
        }

        /*
		 * Mary has died, so we switch to John's perspective.
         */
        if (!naturalEnding(mary)) {
            System.out.println("------------------------------------------"
                + "-------------"
                + "\n\n\t\tJohn Winchester\n"
                + "\t\t---------------\n"
                +"Downstairs in the living room, John Winchester "
                + "awakens.\nThe sound of his wife's scream echoes throughout "
                + "the house.");

            lastRoom = null;
            curRoom = new LivingRoom(john);

            /*
	    * The player uses John until getting the boys out of the house (the 
	    * natural ending).
            */
            while (!naturalEnding(john)) {
                System.out.println("------------------------------------------"
                    + "-----------------------------");          
                System.out.print("John: ");     
                String input = in.nextLine();
                Parser parse = new Parser(lastRoom, curRoom);
                System.out.println("------------------------------------------"
                    + "-----------------------------");            
                parse.commands(input, john);

            /*
	     * Alter the Living Room info for John.
            */
//	    if (curRoom instanceof Hallway && !john.hasItem("Dean")) {
//	        if (curRoom.getS() != null) {
//	            curRoom.getS().changeInfo("Your beloved lounge chair/bed sits "
//	            + "in the middle of the living room"
//	            + "not far from the television.");
//	        }
//	    }	

                /*
				 * Alter the Hallway info for John now that Sam is out of the Nursery.
                 */
//				if (curRoom instanceof Nursery && john.hasItem("Sam")) {
//					if (curRoom.getW() != null) {
//						curRoom.getW().changeInfo("The second floor hallway of your "
//								+ "home.\nTo the west lies your bedroom and Sam's "
//								+ "nursery lies to the east.\nTo the north, Dean's "
//								+ "room is open.\n"
//								+ "Stairs to the first floor are to the south.");
//					}
//					curRoom.setLock(true);
//				}
            }
        }

        /*
		 * Now we begin to play as Sam at Stanford.
         */
        lastRoom = null;
        curRoom = new DormRoom(sam);
        
        System.out.println("------------------------------------------"
                + "-------------"
                + "\n\n\t\tSam Winchester\n"
                + "\t\t--------------\n");
        System.out.println("  Stanford University\nTwenty-two years later...\n");
        System.out.println("Jess: \"Sam, get a move on would ya?\"");

        /*
		 * As long as Sam is alive and hasn't reached his natural ending (soon to be
		 * changed to a character swap), he is your current character.
         */
        while (!naturalEnding(sam) && sam.isAlive()) {
            System.out.println("------------------------------------------"
                    + "-----------------------------");      
            System.out.print("You: ");     
            String input = in.nextLine();
            Parser parse = new Parser(lastRoom, curRoom);
            System.out.println("------------------------------------------"
                    + "-----------------------------");            
            parse.commands(input, sam);

        }

        /*
		 * Out of characters, out of luck. Game over.
         */
        if (!sam.isAlive()) {
            System.out.println("\nYou have died. Game over!\n");
        } else if (naturalEnding(sam)) {
            System.out.println("\nCongratulations! You win! "
                    + "This is actually a pretty rare thing to do.\n");
        }
        in.close();
    }

}
