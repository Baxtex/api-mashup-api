package api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import externalAPIs.FBHandler;
import externalAPIs.RDHandler;
import externalAPIs.TWHandler;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
//import facebook4j.conf.ConfigurationBuilder;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
//import twitter4j.conf.ConfigurationBuilder;;

/**
 * This class acts as a simple API. The URI for accessing these resorces is:
 * localhost:8080/api-mashup-api/api/v1/
 * 
 * @author Anton Gustafsson
 *
 */

@Path("/v1/")
public class API {
	private FBHandler fbHandler;
	private TWHandler twHandler;
	private RDHandler rdHandler;
	
	public API (){
		fbHandler = new FBHandler();
		twHandler = new TWHandler();
	}
	

	/**
	 * Prints to the screen if we are in /v1/foo
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String print1() {
		return "Please specify what resource you need.";
	}

	/**
	 * This method is serious and it's purpose is to return a JSON Array
	 * containing posts from both twitter and facebook. The question is how we
	 * are going to retrieve the ids.
	 * 
	 * @return
	 */
	@GET
	@Path("/posts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPosts() {
		String fbId = "118943054880945"; // Annie löfs fb id.
		String tId = "@annieloof"; // Annie Löfs Twitter id.
		JSONArray jsonArrayFB = new JSONArray();
		JSONArray jsonArrayT = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("HTTP_CODE", "200");
			jsonObject.put("MSG", "jsonArray successfully retrieved, v1");
			jsonArrayFB = fbHandler.getPosts(5, fbId);
			jsonArrayT = twHandler.getPosts(5, tId);
			jsonObject.put("fbposts", jsonArrayFB);
			jsonObject.put("twposts", jsonArrayT);

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*")
					.entity("Server was not able to process your request").build();
		}
		return Response.ok(jsonObject).header("Access-Control-Allow-Origin", "*").build();
	}




}
