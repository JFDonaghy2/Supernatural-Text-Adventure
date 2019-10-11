package Things;

public class Medicine extends PickUp {

	private int healing;
	
	public Medicine(String name, String info, int healing) {
		this.name = name;
		this.info = info;
		this.healing = healing;
	}
	
	public int getHealing() {
		return healing;
	}
	
}
