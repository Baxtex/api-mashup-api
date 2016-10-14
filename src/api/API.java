package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import controller.Controller;

/**
 * This class acts as a simple API. The URI for accessing these resources is:
 * localhost:8080/api-mashup-api/api/v1/
 * 
 * @author Anton Gustafsson
 *
 */

@Path("/v1")
public class API {
	private Controller controller = new Controller();
	
	
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
		JSONObject jsonObject;
		try {
			jsonObject = controller.getSocialPosts(fbId, tId);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*")
					.entity("Server was not able to process your request").build();
		}
		return Response.ok(jsonObject).header("Access-Control-Allow-Origin", "*").build();
	}
}
