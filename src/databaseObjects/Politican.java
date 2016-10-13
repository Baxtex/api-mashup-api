package databaseObjects;

public class Politican {
	
	private int id;
	private String name;
	private String party;
	
	public Politican() {
	}
	
	public Politican(int id, String name, String party) {
		this.id = id;
		this.name = name;
		this.party = party;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setParty(String party) {
		this.party = party;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getParty() {
		return party;
	}
	
	public String toString() {
		return "{" + id + ", " + name + ", " + party + "}";
	}
}
