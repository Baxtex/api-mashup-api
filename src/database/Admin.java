package database;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import databaseObjects.Comment;
import databaseObjects.Politician;
import externalAPIs.FBHandler;
import externalAPIs.TWHandler;
import facebook4j.Post;
import facebook4j.ResponseList;
import twitter4j.Status;

public class Admin {
	private DBHandler dbHandler = new DBHandler();
	private FBHandler fbHandler = new FBHandler();
	private TWHandler twHandler = new TWHandler();

	public static void main(String[] args) {

		Admin admin = new Admin();
		// admin;
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
					dbHandler.addDataDB("UPDATE politicians SET fID = ? WHERE fb_url = ?", newString,
							p.getFacebook_URL());
				}
			} catch (IOException e) {
				System.out.println("Socket timeout");
			}
		}
	}

	/**
	 * This method loops over the politicians and uses their twitter URL to find
	 * their Twitter ID's.
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

				dbHandler.addDataDB("UPDATE politicians SET tID = ? WHERE twitter_url = ?", twitterUrlSub, twitterUrl);
			}
		}
	}


	/**
	 * Loops through all our politicans and retrieves their latest posts and
	 * puts them in the database.
	 * 
	 * @return
	 */
	private void createPostsFromAPIs() {

		try {
			int i = 325;
			LinkedList<Politician> politicians = dbHandler.getPoliticians();
			while (i < politicians.size()) {
				i++;
				System.out.println(i);
				Politician p = (Politician) politicians.get(i);
				long fbID = p.getFacebookId();
				String twID = p.getTwitterId();

				// If FBID exists, create new posts from that data.
				if (fbID != 0) {
					ResponseList<Post> feed = fbHandler.getPostsList(1, String.valueOf(p.getFacebookId()).toString());
					databaseObjects.Post dbPost = null;
					for (Post fbPost : feed) {
						if (fbPost != null) {
							dbPost = new databaseObjects.Post(fbPost.getMessage(), p.getId(), "fb",
									fbPost.getCreatedTime(), 0);
							System.out.println("fbPost");
							// System.out.println(dbPost.toString());
							dbHandler.addPost(dbPost);
						}
					}
				} else {
					System.out.println("No Post created and added.");
				}
				// if TWID exist, create new posts from that data.
				if (twID != null) {
					List<Status> statuses = twHandler.getPostsList(1, p.getTwitterId());
					databaseObjects.Post dbPost = null;
					for (Status twPost : statuses) {
						if (twPost != null) {
							dbPost = new databaseObjects.Post(twPost.getText(), p.getId(), "tw", twPost.getCreatedAt(),
									twPost.getRetweetCount());
							System.out.println("twPost");
							// System.out.println(twPost.toString());
							dbHandler.addPost(dbPost);
						}
					}
				} else {
					System.out.println("No Post created and added.");
				}

				System.out.println("counter " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addImageURL(){
		int counter = 0;
		try {
			LinkedList<Politician> politicians = dbHandler.getPoliticians();
			Iterator<Politician> iter = politicians.iterator();
			// Loop through all politicans
			while (iter.hasNext()) {
				counter++;
				System.out.println("counter " + counter);
				Politician p = (Politician) iter.next();
				String twID = p.getTwitterId();
				int pID = p.getId();
				String res = null;

				if (twID != null) {
					res = twHandler.getProfileURL(twID);
					dbHandler.addDataDB("UPDATE politicians set profile_url = ? WHERE id = ? ",  res, String.valueOf(pID));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Just an example method for adding comments.
	 */
	private void addSpoofCommentsExample(){
		System.out.println("Adding spoofcomment called.");
		Date date = new Date();
		Comment comment = new Comment();
		comment.setDate(date);
		comment.setEmail("foo@bar.com");
		comment.setText("Vad snackar han om?");
		comment.setPost(565);// post id
		dbHandler.addComment(comment);
		
	}
}
