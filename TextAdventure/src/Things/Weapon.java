package Things;

public class Weapon extends PickUp {
	
	private int attStat;
	
	public Weapon (String name, String info, int attStat) {
		this.name = name;
		this.info = info;
		this.attStat = attStat;
	}
	
	public int getAttack() {
		return attStat;
	}
	
}
