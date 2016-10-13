package databaseObjects;

public class Politician {
	
	private String firstname;
	private String surname;
	private String party;
	
	public Politician() {
		
	}
	
	public Politician(String firstname, String surname, String party) {
//		this.id = id;
		this.firstname = firstname;
		this.surname = surname;
		this.party = party;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setParty(String party) {
		this.party = party;
	}
	
	public String getParty() {
		return party;
	}
	
	public String toString() {
		return "{" + firstname + ", " + surname + ", " + party + "}";
	}
}
