package database;

import java.io.IOException;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import databaseObjects.Politician;

public class Admin {
	private DBHandler dbHandler = new DBHandler();

	public static void main(String[] args) {
		Admin admin = new Admin();
		admin.createPost();
	}

	/**
	 * This method loops over the politicians and converts their facebook URL to
	 * facebook ID's and inserts them into the database.
	 */
	private void setPoliticiansFacebookIds() {
		LinkedList<Politician> politicians = dbHandler.getPoliticians();
		int i = 0;
		while (i < politicians.size()) {
			i++;
			Politician p = (Politician) politicians.get(i);
			System.out.println("Counter: " + i + " url " + p.getFacebook_URL());
			try {
				if (p.getFacebook_URL() != null) {
					Document doc;
					doc = Jsoup.connect(p.getFacebook_URL()).get();
					Elements docElement = doc.select("a[href*=profile_id]");
					System.out.println("Printing docElement...");
					System.out.println(docElement);
					String newString = null;
					if (!docElement.isEmpty()) {
						String subStr = "";
						if (p.getFacebook_URL().startsWith("https://m.facebook.com/")) {
							System.out.println("Starts with m.facebook.");
							subStr = docElement.get(0).toString().substring(5, 110);
						} else {
							subStr = docElement.get(0).toString().substring(60, 110);
						}
						System.out.println("Printing subStr...");
						System.out.println(subStr);
						char[] subArray = subStr.toCharArray();
						int pos0 = 0;
						int pos1 = 0;
						for (int j = 0; j < subArray.length; j++) {
							if (subArray[j] == '=') {
								pos0 = j + 1;
							}
							if (subArray[j] == '&') {
								pos1 = j--;
								break;
							}
						}
						System.out.println(pos0 + "   och  " + pos1);
						newString = subStr.substring(pos0, pos1);
						System.out.println("FBId is: " + newString);
					}
					dbHandler.addIdDB("UPDATE politicians SET fID = ? WHERE fb_url = ?", newString,
							p.getFacebook_URL());
				}
			} catch (IOException e) {
				System.out.println("Socket timeout");
			}
		}
	}

	/**
	 * This method loops over the politicians and uses their twitter URL to find
	 * their Twitter ID's-
	 */
	private void setPoliticiansTwitterIds() {
		LinkedList<Politician> politicians = dbHandler.getPoliticians();
		int i = 0;
		while (i < politicians.size()) {
			i++;
			System.out.println(i);
			Politician p = (Politician) politicians.get(i);
			String twitterUrl = p.getTwitter_URL();
			if (twitterUrl != null) {
				String twitterUrlSub = "@" + twitterUrl.substring(20);

				char[] subArray = twitterUrlSub.toCharArray();
				int pos1 = subArray.length;
				for (int j = 0; j < subArray.length; j++) {
					if (subArray[j] == '?') {
						pos1 = j;
					}
				}
				twitterUrlSub = twitterUrlSub.substring(0, pos1);
				System.out.println("twitter urlsub " + twitterUrlSub);

				dbHandler.addIdDB("UPDATE politicians SET tID = ? WHERE twitter_url = ?", twitterUrlSub, twitterUrl);
			}
		}
	}




	/**
	 * Creates a POST object and sends it to the dbHandler for insertion in the
	 * db.
	 */
	private void createPost() {
		// Date date = new Date();
		// Post post = new Post("Nytt inlägg4", 1067, "fb", date, 9);
		// dbHandler.addPost(post);
	}

}
