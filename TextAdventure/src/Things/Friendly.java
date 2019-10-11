package Things;

import DriverEtc.Inventory;

public class Friendly {

	private String name;
        private String nickname;
	private String dialogue;
	private Inventory inv;
	
	public Friendly(String name, String dialogue) {
		this.name = name;
                nickname = name.substring(0, 3);
		this.dialogue = dialogue;
		inv = new Inventory();
	}
	
	public String getName() {
		return name;
	}
        
        public String getNickname() {
            return nickname;
        }
	
	public String getDialogue() {
		return dialogue;
	}
	
	
	public void changeDialogue(String s) {
		dialogue = s;
	}
	
	public boolean hasItem(String s) {
		return inv.hasItem(s);
	}

	public PickUp getItem(String item) {
		return inv.getItem(item);
	}

	public PickUp dropItem(String item) {
		return inv.dropItem(item);
	}
	
//	public void addItem(PickUp item) {
//		if (item != null) {
//			inv.addItem(item, name);
//		}
//	}

	public void givenItem(PickUp item) {
		if (item != null) {
			inv.givenItem(item, name);
		}
	}
	
	public void printInventory() {
		inv.printInventory();
	}
}
