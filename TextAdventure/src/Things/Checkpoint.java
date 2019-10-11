package Things;

/**
 * To be used when certain rooms, monsters, or actions may only become available
 *  after reaching a specific point in the game.
 *   For instance: The randomly generated monster that terrorizes a town can 
 *     only be a Leviathan after the client has "unlocked"/played through enough
 *      of the Season 7 storyline.
 * @author mardisaa
 */
public class Checkpoint extends PickUp {
    
    public Checkpoint (String name) {
        this.name = name;
    }   
}

