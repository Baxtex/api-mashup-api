package databaseObjects;

public class Politician {
	private String name;
	private String party;
	private String facebook_URL;
	private String twitter_URL;

	public Politician() {
		
	}
	
	public Politician(String name, String party) {
		this.name = name;
		this.party = party;
	}

	public Politician(String name, String party, String facebook_URL, String twitter_URL) {
		this.name = name;
		this.party = party;
		this.facebook_URL = facebook_URL;
		this.twitter_URL = twitter_URL;
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
	
	public String getFacebook_URL(){
		return facebook_URL;
	}
	
	public void setFacebook_URL(String url){
		this.facebook_URL = url;
	}
	
	public String getTwitter_URL(){
		return twitter_URL;
	}
	
	public void setTwitter_URL(String url){
		this.twitter_URL = url;
	}

	public String toString() {
		return "{" + name + ", " + party + "}";
	}
	
}
