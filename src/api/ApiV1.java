package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import controller.Controller;

/**
 * This class acts as the endpoint for the API. The URI for accessing these
 * resources is: http://localhost:8080/api-mashup-api/api/v1/<resource>
 * 
 * @author Anton Gustafsson
 *
 */

@Path("/v1/")
public class ApiV1 {

	private final String ERR_MSG = "Server was not able to process your request";
	private Controller controller = new Controller();

	/**
	 * Prints if we don't specify any resource
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response print1() {
		return Response.ok("Please specify your resource.").header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Return posts from all politicans
	 * 
	 * @return
	 */
	@GET
	@Path("posts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getAllPosts() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getAllPosts();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * 
	 * Return posts from all politicans in specific party.
	 * 
	 * @param party the short name of the party, f.e. "s"
	 * @return
	 */
	@GET
	@Path("posts/{party}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPartyPosts(@PathParam("party") String party) {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getAllPostsParty(party);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}

		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Return posts from specific politician
	 * 
	 * @param party the short name of the party, f.e. "s"
	 * @param id the id of the politician stored in our database
	 * @return
	 */

	@GET
	@Path("posts/id/{politicianID}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPostsByPolitican(@PathParam("politicianID") String id) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getPostsPolitician(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * TODO DOES NOT WORK! Return specified number of comments for a polititan.
	 * 
	 * @param party
	 * @param id
	 * @param nbrComments
	 * @return
	 */
	@GET
	@Path("posts/id/{politician}/{nbrComments}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPostsCommentsByPolitican(@PathParam("party") String party, @PathParam("politician") String id,
			@PathParam("nbrComments") String nbrComments) {
		JSONObject jsonObject = new JSONObject();
		try {
			// TODO DOES NOT DO ANYTHING YET!!!
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Return all politician objects.
	 * 
	 * @param party
	 * @return
	 */
	@GET
	@Path("politicians")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPoliticians() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getAllPoliticians();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Return all politican objects from specific party.
	 * 
	 * @param party the short name of the party, f.e. "s"
	 * @return
	 */

	@GET
	@Path("politicians/{party}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPoliticiansByParty(@PathParam("party") String party) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getPoliticiansParty(party);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Return specific politican object
	 * 
	 * @return
	 */

	@GET
	@Path("politicians/id/{politician}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getSpecificPoliticianByParty(@PathParam("politician") String id) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getPolitician(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Return all parties as objects.
	 * 
	 * @return
	 */
	@GET
	@Path("parties")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPartyInformation() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getParties();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}


	/**
	 * Return a specific party object.
	 * 
	 * @return
	 */
	@GET
	@Path("parties/{party}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getSpecificPartyInformation(@PathParam("party") String party) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getParty(party);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}


	/**
	 * 
	 * This method is super cereal and it's for testing purposes only.
	 * 
	 * @return
	 */
	@GET
	@Path("testPosts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPostsTest() {

		String fbId = "1064287767";
		String tId = "@ingemarnilsson_";
		JSONObject jsonObject = new JSONObject();
		try {
			// jsonObject = controller.getSocialPosts(fbId, tId);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}

		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}
}
