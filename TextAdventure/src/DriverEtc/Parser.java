package DriverEtc;

import Settings.Location;
import Things.Friendly;
import Things.Item;
import Things.Key;
import Things.Medicine;
import Things.Monster;
import Things.PickUp;
import Things.Revealer;
import Things.RevealerMedicine;
import Things.RevealerWeapon;
import Things.Weapon;
import java.util.ArrayList;

public class Parser {

    private Location lastLoc;
    private Location curLoc;
    private ArrayList<Player> teamFreeWill = new ArrayList<>(5);

    /*
	 * The ever-changing list of commands that a player can give to the current 
	 * character.
     */
    String commands = "wait\ngo/move\nnorth/south/east/west\nreturn (to the "
            + "last room visited)\ntalk to (friend)\npick up/grab\nuse/equip\n"
            + "fight\nlook at (room or item)\nlook around (for a type)"
            + "\ngive (item) to (character)\ncurrent room/player\ncharacter update";

    /*
	 * This is a fairly new addition (20 November 2017), so if any problems
	 * 		(like with null check), then they began then.
     */
    public Parser(Location lastLoc, Location curLoc) {
        this.lastLoc = lastLoc;
        this.curLoc = curLoc;
    }

    public void commands(String s, Player player) {
        s = s.toLowerCase().trim();
        /*
		 * Calls the wait function so that the character waits a turn.
         */
        if (s.equals("wait")) {
            waitThere(player);
            /*
			 * Remind the user of their current player.
             */
        } else if (s.equals("current player") || s.equals("player name")) {
            String name = player.getName();
            if (name.equals("John") || name.equals("Mary") || name.equals("Sam") || name.equals("Dean")) {
                System.out.println("You are playing as " + name + " Winchester.");
            } else {
                System.out.println("You are playing as " + name + ".");
            }
            /*
			 * Remind the user of their current room.
             */
        } else if (s.equals("current room") || s.equals("room name")) {
            System.out.println("You are in the "
                    + player.getLoc() + ".");
            /*
			 * Character status
             */
        } else if (s.equalsIgnoreCase("character update") || s.equalsIgnoreCase("update")
                || s.equalsIgnoreCase("status")) {
            System.out.println("You are " + player.getName() + ", in the " + curLoc + ".\n"
                    + "Your health is at " + player.getHealth() + ".");

            /*
	* Make the player move.
             */
        } else if (((s.startsWith("go ") && !s.equalsIgnoreCase("go back"))
                || (s.startsWith("goes ") && !s.equalsIgnoreCase("goes back"))
                || s.startsWith("move ")) || s.startsWith("moves ")
                || s.startsWith("walk ") || s.startsWith("walks ")) {
            goTo(s.substring(s.indexOf(" ")).trim(), player);

            /*
            * Want to account for
                > enter nursery
                and 
                > enter into nursery
             */
        } else if (s.startsWith("enter ") || s.startsWith("enters ")) {
            String theDragon = s.substring(s.indexOf(" ")).trim();
            if (theDragon.contains("to ") || theDragon.contains("into ")) {
                goTo(theDragon, player);
            } else {
                goTo("to " + theDragon, player);
            }
        } else if (s.startsWith("north") || s.startsWith("south")
                || s.startsWith("east") || s.startsWith("west")) {
            goTo(s.trim(), player);
            /*
        * Return/go back to the previous room visited (if still unlocked).
             */
        } else if (s.equalsIgnoreCase("return")
                || s.equalsIgnoreCase("returns")
                || s.equalsIgnoreCase("go back")
                || s.equalsIgnoreCase("goes back")) {
            if (lastLoc != null) {
                TextAdventure.changeRoom(lastLoc, player);
            } else {
                System.out.println("There is nowhere to return to.");
            }
            // direction typos
        } else if (s.contains("notr") || s.contains("norht")
                || s.contains("noth") || s.contains("north")) {
            System.out.println("Perhaps you meant \'north\'?");
            // Come back to this when you figure out how to get a SECOND input 
            // to immediately follow so that this can really be a yes/no answer.
        } else if (s.contains("sotu") || s.contains("sout") || s.contains("suot")
                || s.contains("sour") || s.contains("south")) {
            System.out.println("Perhaps you meant \'south\'?");
        } else if (s.contains("est") || s.contains("esra") || s.contains("et")
                || s.contains("easr") || s.contains("east")) {
            System.out.println("Perhaps you meant \'east\'?");
        } else if (s.contains("wet") || s.contains("wre") || s.contains("wt")
                || s.contains("wte") || s.contains("wst") || s.contains("swt")) {
            System.out.println("Perhaps you meant \'west\'?");
            // unnecessary Spongebob reference
        } else if (s.startsWith("weast")) {
            System.out.println("Patrick, you're fired.");
            // getting ahead of themselves
        } else if (s.contains("castiel") && !player.getProgress("PulledFromPerdition")) {
            System.out.println("Hold your horses. We're not there yet.");
            // a mistyping
        } else if (s.startsWith("moove")) {
            System.out.println("Mooooooo!");
            //		} else if (s.startsWith("leave") || s.startsWith("back")
            //				|| s.startsWith("return")) {
            //			System.out.println("Calling goBack(lastDirec, player) with: lastDirec = " + lastDirec + " and player = " +  player.getName() + "." );
            //			goBack(lastDirec, player);
            /*
                    * Talk to a Friendly (or Monster).
                    * Eventually has to account for switching characters.
             */
        } else if (s.startsWith("talk to ") || s.startsWith("converse with ")
                || s.startsWith("chat with ") || s.startsWith("share gossip with")
                || s.startsWith("speak with ") || s.startsWith("speak to ")
                || s.startsWith("call to")) {
            String str = s.substring(s.indexOf(" ")).trim();
            talkTo(str.substring(str.indexOf(" ")).trim(), player);
        } else if (s.startsWith("converse in a lengthy discussion with ")) {
            talkTo(s.substring(43), player);
        } else if (s.startsWith("skip")) {
            talkTo("skip", player);
            /*
                    * Pick up an object.
             */
        } else if (s.startsWith("pick up ")) {
            pickUp(s.substring(s.indexOf(" ")).substring(s.indexOf(" ")), player);
        } else if (s.startsWith("grab ")) {
            pickUp(s.substring(s.indexOf(" ")).trim(), player);
            /*
                    * Use an object.
             */
        } else if (s.startsWith("use ") || s.startsWith("equip ")) {
            useItem(s.substring(s.indexOf(" ")), player, curLoc);
        } else if (s.startsWith("eat ")) {
            System.out.println("That was delicious.");
            useItem(s.substring(s.indexOf(" ")), player, curLoc);
            /*
                    * Change the current weapon.
             */
        } else if (s.startsWith("switch weapon ")) {
            useItem(s.substring(14), player, curLoc);
        } else if (s.startsWith("switch weapon to ")) {
            useItem(s.substring(17), player, curLoc);
            /*
                    * Attack a monster (or a Friendly).
             */
        } else if (s.startsWith("attack ") || s.startsWith("fight ")
                || s.startsWith("hurt ") || s.startsWith("gank ")) {
            attack(s.substring(s.indexOf(" ")).trim(), player);
            /*
                    * Examine an object.
             */
        } else if (s.startsWith("look at ")) {
            lookAt(s.substring(8), player);
        } else if (s.startsWith("examine ")
                || s.startsWith("inspect ")) {
            lookAt(s.substring(s.indexOf(" ")).trim(), player);
            /*
                    * Examine a room.
             */
        } else if (s.startsWith("look around")) {
            lookAround(s, curLoc);
        } else if (s.startsWith("explore ") || s.startsWith("around ")) {
            lookAround(s.substring(s.indexOf(" ")), curLoc);
            /*
                    * List the possible commands for the character.
             */
        } else if (s.equalsIgnoreCase("commands") || s.equalsIgnoreCase("help")) {
            System.out.println("Some valid commands are:");
            System.out.println(commands);
            
            /*
                    * Release an object from the character's inventory.
            *  What about set ITEM down, release ITEM, and put ITEM down?
             */
        } else if (s.startsWith("give ") || s.startsWith("drop ")
                || s.startsWith("release ")) {
            releaseItem(s.substring(s.indexOf(" ") + 1), player, curLoc);
            
            /*
                    * Accept an item from a Friendly.
             */
        } else if (s.startsWith("take ")) {
            takeItem(s.substring(5), player, curLoc);
            /*
                    * Return to the last room visited.
             */
        } else if (s.startsWith("return")) {
            goTo(s, player);
            /*
                    * Some minor healing caused by breathing a sigh of relief.
             */
        } else if (s.startsWith("breathe") || s.startsWith("sigh")) {
            sigh(player);
                /*
    * As the game goes on, friends come and go. I would like to have them lumped
    * together so that the client can use the special skills of one player if 
    * they have unlocked that character.
    */
        } else if ((s.startsWith("add ") || 
                s.startsWith("join ") || 
                s.startsWith("include ")) && 
                (s.endsWith(" to army") || s.endsWith(" to the army") || 
                s.endsWith(" to party") || s.endsWith(" to the party") ||
                s.endsWith(" in the fight"))) {
            ourHeroes(s.substring(s.indexOf(" ") + 1));
//            String localFriendBeg = s.substring(s.indexOf(" " + 1));
//            String localFriend = localFriendBeg.substring(0, localFriendBeg.indexOf(" " - 1));
//            if (curLoc.hasFriendly(s.))
//            teamFreeWill.add(player)
            /*
                    * Unrecognized command.
             */
        } else {
            System.out.println("I didn't understand that. Please input a valid command.");
        }
    }

    public void waitThere(Player p) {
        curLoc.waitTurn(p);
    }

    public void goTo(String s, Player p) {
        if ((s.endsWith("north") || s.endsWith("upstairs")) && curLoc.getN() != null) {
            TextAdventure.changeRoom(curLoc.getN(), p);
        } else if ((s.endsWith("south") || s.endsWith("downstairs")) && curLoc.getS() != null) {
            TextAdventure.changeRoom(curLoc.getS(), p);
        } else if (s.endsWith("east") && curLoc.getE() != null) {
            TextAdventure.changeRoom(curLoc.getE(), p);
        } else if (s.endsWith("west") && curLoc.getW() != null) {
            TextAdventure.changeRoom(curLoc.getW(), p);

            /*
            * As in "go (in)to...," "move (in)to...," "walk INto...," 
            * "enter (in)to...," etc. 
             */
        } else if (s.contains("to ") || s.contains("into ")) {
            String room = s.substring(s.indexOf(" ")).trim();
            if (curLoc.getN() != null
                    && curLoc.getN().intoLoc().contains(room)) {
                TextAdventure.changeRoom(curLoc.getN(), p);
            } else if (curLoc.getS() != null
                    && curLoc.getS().intoLoc().contains(room)) {
                TextAdventure.changeRoom(curLoc.getS(), p);
            } else if (curLoc.getE() != null
                    && curLoc.getE().intoLoc().contains(room)) {
                TextAdventure.changeRoom(curLoc.getE(), p);
            } else if (curLoc.getW() != null
                    && curLoc.getW().intoLoc().contains(room)) {
                TextAdventure.changeRoom(curLoc.getW(), p);

            } else if (s.contains("the opposite direction of ")
                    || s.contains("the opposite of ")) {
                System.out.println("Why must you make me do this extra work?");
            } else if (s.contains("neither north, south, or east")
                    || s.contains("neither north, south, or west")
                    || s.contains("neither south, east, or west")
                    || s.contains("neither north, east, or west")) {
                System.out.println("Coming soon to a very angry language "
                        + "parser near you.");
            }
        } else {
            System.out.println("You cannot go that way.");
        }
    }


    /* 
	 * I feel like the null check is redundant since you are moving BACK to a 
	 * previously established room, but I will keep them in for now. 
	 * 
	 * Actually, it is necessary for situations where the user inputs an
	 * inaccessible direction before trying to go back. 
     */
    public void goBack(String s, Player p) {
        if (s.endsWith("north") && curLoc.getS() != null) {
            TextAdventure.changeRoom(curLoc.getS(), p);
        } else if (s.endsWith("south") && curLoc.getN() != null) {
            TextAdventure.changeRoom(curLoc.getN(), p);
        } else if (s.endsWith("east") && curLoc.getW() != null) {
            TextAdventure.changeRoom(curLoc.getW(), p);
        } else if (s.endsWith("west") && curLoc.getE() != null) {
            TextAdventure.changeRoom(curLoc.getE(), p);
        } else {
            System.out.println("You cannot go that way.");
        }
    }

    public void talkTo(String s, Player p) {
        curLoc.talkTo(s, p);
    }

    public void pickUp(String s, Player player) {

        /*
		 * Affix the appropriate article
         */
 /* 
		 * I will add this in later. I just want to finish the game first.
		if ( s.contains("'") || s.startsWith("the") || s.equalsIgnoreCase("Sam")){
			
		} else if (s.endsWith("s")) {
				s = "some " + s;
		} else if (s.startsWith("a") || s.startsWith("e") || 
					s.startsWith("i") || s.startsWith("o") || s.startsWith("u")){
				s = "an " + s;
		} else if (s.equalsIgnoreCase("pie")){
				s = "a slice of " + s;
		} else {
				s = "a " + s;
		}
         */
        PickUp p = curLoc.getItem(s);
        if (p != null) {
            player.pickUp(p);
            curLoc.removeItem(p);
        } else {
            System.out.println("That item is not here.");
        }
    }

    public void useItem(String s, Player player, Location room) {
        /*
		 * Affix the appropriate article?
         */

        PickUp p = player.getItem(s.trim());
        if (p != null) {
            if (p instanceof Medicine) {
                player.healBy((Medicine) p);
            } else if (p instanceof RevealerMedicine) {
                player.healBy((RevealerMedicine) p);
            } else if (p instanceof Item) {
                lookAt(p.getName(), player);
            } else if (p instanceof Revealer) {
                lookAt(p.getName(), player);
            } else if (p instanceof Weapon) {
                player.equipWeapon((Weapon) p);
            } else if (p instanceof RevealerWeapon) {
                player.equipWeapon((RevealerWeapon) p);
            } else if (p instanceof Key) {
                if (curLoc.getN() != null && curLoc.getN().isLocked()) {
                    curLoc.getN().unlock(player);
                    goTo("north", player);
                } else if (curLoc.getS() != null && curLoc.getS().isLocked()) {
                    curLoc.getS().unlock(player);
                    goTo("south", player);
                } else if (curLoc.getE() != null && curLoc.getE().isLocked()) {
                    curLoc.getE().unlock(player);
                    goTo("east", player);
                } else if (curLoc.getW() != null && curLoc.getW().isLocked()) {
                    curLoc.getW().unlock(player);
                    goTo("west", player);
                }
            } else {
                System.out.println("You cannot use that here.");
            }
        } else {
            System.out.println("You do not have the " + s + ".");
        }
    }

    public void attack(String s, Player p) {
        if (s == null) {
            System.out.println("Please make sure to state your intended victim.");
        } else {
            Monster m = curLoc.getMonster(s);
            Friendly f = curLoc.getFriendly(s);
            if (m != null) {
                if (p.getEquip2() != null) {
                    m.attackMonster(p.getEquip2(), p);
                    p.attackedBy(m);
                } else {
                    m.attackMonster(p.getEquip(), p);
                    p.attackedBy(m);
                }
            } else if (f != null) {
                System.out.println("So that's how you would treat "
                        + s + "?");
            } else {
                System.out.println("Calm down. "
                        + "There's nothing here "
                        + "to attack.");
            }
        }
    }

    /*
        * The player can get a closer look at an item, friend, monster, the 
        * room, or (Coming Soon), in the directions of north, south, east, and 
        * west.
     */
    public void lookAt(String s, Player p) {
        PickUp item = curLoc.getItem(s);
        PickUp item2 = p.getItem(s);
        Friendly friend = curLoc.getFriendly(s);
        Monster monster = curLoc.getMonster(s);
        if (item != null) {
            System.out.println(item.getInfo());
        } else if (item2 != null) {
            System.out.println(item2.getInfo());
        } else if (friend != null) {
            System.out.println(friend.getDialogue());
        } else if (monster != null) {
            System.out.println(monster.getInfo());
        } else if (s.equals("room")) {
            System.out.println(curLoc.getInfo());
        } else if (s.equals("items")) {
            p.getInventory();
        } else {
            System.out.println("There is nothing to look at.");
        }
    }

    public void lookAround(String s, Location room) {
        if (s.endsWith("items")) {
            room.lookItems();
        } else if (s.endsWith("monsters")) {
            room.lookMonsters();
        } else if (s.endsWith("friends")) {
            room.lookFriendly();
        } else if (s.endsWith("help")) {
            room.lookHelp();
        } else if (s.endsWith("medicine")) {
            room.lookMedicine();
        } else if (s.endsWith("room")) {
            System.out.println(curLoc.getInfo() + "\n");
            room.lookItems();
            room.lookFriendly();
            room.lookMonsters();
        } else {
            System.out.println(curLoc.getInfo());
        }
    }

    /*
	 * Give a specific item to a friendly.
     */
    public void releaseItem(String s, Player player, Location curLoc) {

        String item;
        String friend;

        if (s.contains("to ")) {
            item = s.substring(0, s.indexOf("to ")).trim();
            friend = s.substring(s.indexOf("to ") + 3);
        } else {
            item = s.substring(0);
            friend = null;
        }

        /*
		 * Affix the appropriate article
		if ( s.contains("'") || s.startsWith("the") || s.equalsIgnoreCase("Sam")){
			
		} else if (s.endsWith("s")) {
				s = "some " + s;
		} else if (s.startsWith("a") || s.startsWith("e") || 
					s.startsWith("i") || s.startsWith("o") || s.startsWith("u")){
				s = "an " + s;
		} else if (s.equalsIgnoreCase("pie")){
				s = "a slice of " + s;
		} else {
				s = "a " + s;
		}
         */
        if (item != null) {
            if (friend != null) {
                /*
				 * The player has indicated both an item and a friend where the 
				 * 		friend is in the room.
                 */
                if (curLoc.hasFriendly(friend)) {
                    //curLoc.getFriendly(friend).givenItem(player.giveItem(item, friend));
                    curLoc.talkTo(friend, player);
                    /*
				 * The friend is NOT in the room.
                     */
                } else {
//					System.out.println("Give " + item.trim() + " to whom?");
                    System.out.println("You can't do that.");
                }
                /*
			 * An item was indicated, but no friend was given.
                 */
            } else {
                curLoc.droppedItem(player.dropItem(item));
            }
        }
    }

    public void takeItem(String s, Player player, Location curLoc) {

        String item;
        String friend;

        if (s.contains("from ")) {
            item = s.substring(0, s.indexOf("from "));
            friend = s.substring(s.indexOf("from ") + 5);
            System.out.println(item);
            System.out.println(friend);
        } else {
            item = s.substring(0);
            friend = null;
        }

        if (friend != null) {
            if (item != null) {
                if (curLoc.hasFriendly(friend)
                        && curLoc.getFriendly(friend).hasItem(item)) {
                    player.pickUp(curLoc.getFriendly(friend).dropItem(item));
                } else {
                    System.out.println("Take " + item.trim() + " from whom?");
                }
            } else {
                System.out.println("Take what from whom?");
            }
        } else {
            System.out.println("You can't do that.");
        }
    }

    public void sigh(Player p) {
        p.healBy(new Medicine("A sigh of relief", "You just take"
                + " a moment to appreciate that nothing is wrong.",
                1));
    }
    
    public void ourHeroes(String hero) {
        
        String localFriend = hero.substring(0, hero.indexOf(" ") - 1);
        
        if (!curLoc.hasFriendlies()) {
            System.out.println("There aren't any friends here to join you.");
        } else 
            if (!curLoc.hasFriendly(localFriend)) {
            System.out.println("...does not want to join you.");
            }
    }

}
