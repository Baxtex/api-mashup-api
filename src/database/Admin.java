package database;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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
		admin.createPostsFromAPIs();
		// admin.getSpecificPostMashup("690252680", "@aliesbati");
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
	 * Loops through all our politicans and retrieves their latest posts and
	 * puts them in the database.
	 * 
	 * @return
	 */
	private void createPostsFromAPIs() {
		boolean postOrNot;
		int counter = 0;
		try {
			LinkedList<Politician> politicians = dbHandler.getPoliticians();
			Iterator<Politician> iter = politicians.iterator();
			// Loop through all politicans
			while (iter.hasNext()) {
				postOrNot = false;
				counter++;
				Politician p = (Politician) iter.next();
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

							// TODO Fix how to get the number of likes for a
							// post.
							System.out.println("fbPost");
							System.out.println(String.valueOf(fbPost.getLikes().getSummary().getTotalCount()));
							// System.out.println(dbPost.toString());
							// dbHandler.addPost(dbPost);
						}
					}
				} else {
					postOrNot = true;
				}
				// if TW exist, create new posts from that data.
				if (twID != null) {
					List<Status> statuses = twHandler.getPostsList(1, p.getTwitterId());
					databaseObjects.Post dbPost = null;
					for (Status twPost : statuses) {
						if (twPost != null) {
							dbPost = new databaseObjects.Post(twPost.getText(), p.getId(), "fb", twPost.getCreatedAt(),
									twPost.getRetweetCount());
							System.out.println("twPost");
							// dbHandler.addPost(dbPost);
						}
					}
				} else {
					postOrNot = false;
				}

				System.out.println("counter " + counter);
				if (postOrNot) {
					System.out.println("No Post created and added.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Loops through all our politicans and adds their data to a JSONObject.
	 * 
	 * @return
	 */
	// private JSONObject getPostText() {
	// System.out.println("CreatePost:");
	// JSONObject resultObject = new JSONObject();
	// JSONArray pArray = new JSONArray();
	//
	// try {
	// resultObject.put("HTTP_CODE", "200");
	// resultObject.put("MSG", "jsonArray successfully retrieved, v1");
	//
	// LinkedList<Politician> politicians = dbHandler.getPoliticians();
	// Iterator<Politician> iter = politicians.iterator();
	// while (iter.hasNext()) {
	// Politician p = (Politician) iter.next();
	// JSONObject tempObj1 = new JSONObject();
	//
	// long fbID = p.getFacebookId();
	// String twID = p.getTwitterId();
	//
	// tempObj1.put("name", p.getName());
	// // If FB
	// if (fbID != 0) {
	// tempObj1.put("fbPosts", fbHandler.getPosts(1,
	// String.valueOf(p.getFacebookId()).toString()));
	// } else {
	// tempObj1.put("FBpost", "None");
	// }
	//
	// // if TW
	// if (twID != null) {
	// tempObj1.put("twPosts", twHandler.getPosts(1,
	// p.getTwitterId()).toString());
	// } else {
	// tempObj1.put("TWpost", "None");
	// }
	//
	// pArray.put(new JSONObject().put("politican", tempObj1));
	// System.out.println("...");
	//
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// try {
	// resultObject.put("politicians", pArray);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// System.out.println(resultObject.toString());
	// return resultObject;
	// }

	/**
	 * Retrieves facebook and twitter post for a specific politician and mashes
	 * them up into a new JSONObject.
	 * 
	 * @return
	 */
	public void getSpecificPostMashup(String fbId, String tId) {
		JSONArray jsonArrayFB = new JSONArray();
		JSONArray jsonArrayT = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {

			jsonArrayFB = fbHandler.getPostsJSONArray(1, fbId);
			jsonArrayT = twHandler.getPostsJSONArray(1, tId);
			jsonObject.put("fbposts", jsonArrayFB);
			jsonObject.put("twposts", jsonArrayT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(jsonObject.toString());

	}

	// /**
	// * Retrieves post from a specificparty
	// *
	// * @param party
	// * @return
	// */
	// public JSONObject test2(String party) {
	// JSONObject resultObject = new JSONObject();
	// JSONArray pArray = new JSONArray();
	// try {
	// resultObject.put("HTTP_CODE", "200");
	// resultObject.put("MSG", "jsonArray successfully retrieved, v1");
	//
	// LinkedList<Politician> politicians = dbHandler.getPoliticians(party);
	// Iterator<Politician> iter = politicians.iterator();
	// while (iter.hasNext()) {
	// Politician p = (Politician) iter.next();
	// pArray.put(new JSONObject().put("name", p.getName()));
	//
	// if (p.getFacebookId() != 0 && p.getTwitterId() != null) {
	// pArray.put(
	// new JSONObject().put("fbPosts", fbHandler.getPosts(3,
	// String.valueOf(p.getFacebookId()))));
	// pArray.put(new JSONObject().put("twPosts", twHandler.getPosts(1,
	// p.getTwitterId())));
	// }
	//
	// if (p.getFacebookId() == 0 && p.getTwitterId() == null) {
	// pArray.put(new JSONObject().put("fbPosts", "None"));
	// pArray.put(new JSONObject().put("twPosts", "None"));
	// }
	//
	// if (p.getFacebookId() != 0 && p.getTwitterId() == null) {
	// pArray.put(new JSONObject().put("fbPosts",
	// fbHandler.getPosts(3, String.valueOf(p.getFacebookId()).toString())));
	//
	// }
	// if (p.getFacebookId() == 0 && p.getTwitterId() != null) {
	// pArray.put(new JSONObject().put("twPosts", twHandler.getPosts(1,
	// p.getTwitterId()).toString()));
	// }
	// resultObject.put("politicians", pArray);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return resultObject;
	// }

}
