package databaseObjects;

/**
 * Party class reåresenting a single party. Only getters and setters.
 * 
 * @author Anton Gustafsson
 *
 */
public class Party {
	
	private String name;
	private String nameShort;
	private String party_url;
	

	public Party(){
	}
	
	public String getParty_url() {
		return party_url;
	}

	public void setParty_url(String party_url) {
		this.party_url = party_url;
	}
	public Party(String name, String nameShort) {
		this.name = name;
		this.nameShort = nameShort;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
	}
	
	public String getName() {
		return name;
	}
	
	public String getNameShort() {
		return nameShort;
	}
	
	public String toString() {
		return "{" + name + ", " + nameShort + "}";
	}
}
