package externalAPIs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import databaseObjects.Politician;

public class GSHandler {
	private static final String URL_GOOGLE = "http://www.google.com/search?q=";
	private final String CHARSET = "UTF-8";
	private final String USERAGENT = "API-MASHUP-API";
	private Elements links;

	public GSHandler() {

	}

	public String getPolitician_Facebook(Politician politician) {
		String returnUrl = null;
		String search = politician.getName() + " " + politician.getParty() + " facebook";
		try {
			links = Jsoup.connect(URL_GOOGLE + URLEncoder.encode(search, CHARSET)).userAgent(USERAGENT).get()
					.select(".g>.r>a");

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (Element link : links) {
			String title = link.text();
			String url = link.absUrl("href"); // Google returns URLs in format
												// "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
			try {
				url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (!url.startsWith("http")) {
				continue; // Ads/news/etc.
			}

			if (title.equals(politician.getName() + " |" + " Facebook")) {
				returnUrl = url;
				break;
			}
		}
		return returnUrl;
	}

	public LinkedList<Politician> getPoliticians_SocialMedia(LinkedList<Politician> politicians) {
		
		LinkedList<Politician> returnList = politicians;
		System.out.println("Getting politicians social media...");
		for (int i = 0; i < returnList.size(); i++) {
			System.out.println("Getting social media for: " + returnList.get(i).getName());
			
			returnList.get(i).setFacebook_URL(getPolitician_Facebook(returnList.get(i)));
			returnList.get(i).setTwitter_URL(getPolitician_Twitter(returnList.get(i)));
			
			System.out.println(returnList.get(i).getFacebook_URL());
			System.out.println(returnList.get(i).getTwitter_URL());
			
		}
		return returnList;
	}

	public String getPolitician_Twitter(Politician politician) {
		String returnUrl = null;
		String search = politician.getName() + " " + politician.getParty() + " Twitter";
		String[] splitSearch = (politician.getName() + " " + politician.getParty() + " Twitter").split(" ");
		String[] splitTitle;
		try {
			links = Jsoup.connect(URL_GOOGLE + URLEncoder.encode(search, CHARSET)).userAgent(USERAGENT).get()
					.select(".g>.r>a");

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (Element link : links) {
			String title = link.text();
			String url = link.absUrl("href"); // Google returns URLs in format
												// "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
			try {
				url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (!url.startsWith("http")) {
				continue; // Ads/news/etc.
			}
			splitTitle = title.split(" ");
			// for(int i = 0; i < splitSearch.length; i++){
			// System.out.print(i + " : " + splitSearch[i] + " ");
			// }
			// System.out.println(" ");
			if (splitTitle[0].equals(splitSearch[0]) && splitTitle[1].equals(splitSearch[1])
					&& splitTitle[splitTitle.length - 1].equals(splitSearch[splitSearch.length - 1])) {
				returnUrl = url;
				break;
			}
			// if(splitTitle[0].equals(splitSearch[0])
			// && splitTitle[1].equals(splitSearch[1])
			// && splitTitle[3].equals(splitSearch[4])){
			// returnUrl = url;
			// break;
			// }

			// System.out.println("Title: " + title);
			// System.out.println("URL: " + url);
		}
		return returnUrl;
	}

//	public LinkedList<Politician> getPoliticians_Twitter(LinkedList<Politician> politicians) {
//		LinkedList<Politician> returnList = politicians;
//		System.out.println("Getting politicians social media...");
//		for (int i = 0; i < returnList.size(); i++) {
//			System.out.println("Getting social media for: " + returnList.get(i).getName());
////			System.out.println(getPolitician_Facebook(politicians.get(i)));
//			returnList.get(i).setFacebook_URL(getPolitician_Facebook(returnList.get(i)));
//			returnList.get(i).setTwitter_URL(getPolitician_Twitter(returnList.get(i)));
//			// getPolitician_Facebook(politicians.get(i));
//		}
//		return returnList;
//	}

	// public static void main( String[] args ){

	// GoogleSearchHandler googleHandler = new GoogleSearchHandler();
	//
	// String search = "Stefan Löfven S Twitter";
	// String charset = "UTF-8";
	// String userAgent = "API-MASHUP-API"; // Change this to your company's
	// name and bot homepage!
	//
	// Elements links = null;
	// try {
	// links = Jsoup.connect(URL_GOOGLE + URLEncoder.encode(search,
	// charset)).userAgent(userAgent).get().select(".g>.r>a");
	//
	// } catch (UnsupportedEncodingException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// for (Element link : links) {
	// String title = link.text();
	// String url = link.absUrl("href"); // Google returns URLs in format
	// "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
	// try {
	// url = URLDecoder.decode(url.substring(url.indexOf('=') + 1,
	// url.indexOf('&')), "UTF-8");
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// if (!url.startsWith("http")) {
	// continue; // Ads/news/etc.
	// }
	//
	// System.out.println("Title: " + title);
	// System.out.println("URL: " + url);
	// }
	//// // Show title and URL of 1st result.
	//
	// }
}
