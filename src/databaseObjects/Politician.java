package databaseObjects;

public class Politician {
	private String name;
	private String party;
	
	public Politician() {
	}
	
	public Politician(String name, String party) {
		this.name = name;
		this.party = party;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getParty() {
		return party;
	}
	
	public void setParty(String party) {
		this.party = party;
	}
	
	public String toString() {
		return "{" + name + ", " + party + "}";
	}
}