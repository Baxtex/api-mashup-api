package databaseObjects;

public class Party {
	
	private String name;
	private String nameShort;
	
	public Party(){
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
