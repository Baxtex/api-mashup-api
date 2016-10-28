package api;

import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import controller.Controller;

/**
 * Endpoint for the API. The URI for accessing these resources is:
 * http://localhost:8080/api-mashup-api/api/v1/<resource>
 */

@Path("/v1/")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes({ MediaType.TEXT_PLAIN + ";charset=utf-8" })
public class ApiV1 {

	private Controller controller = new Controller();
	private final String MSG = "message";
	private final String ERR_MSG = "Server was not able to process your request";
	private final String ACAO = "Access-Control-Allow-Origin";
	private final Response ERR_RESPONSE = Response.status(500).header(ACAO, "*")
			.entity(ERR_MSG).build();

	/**
	 * Default resource that tells the user to specify their request.
	 * 
	 * @return - response with a json success message.
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response defaultMessage() {
		return Response.ok("Please specify your request.").header(ACAO, "*").build();
	}

	/**
	 * Return posts from all politicans from the current date.
	 * 
	 * @return - response with a JSONObject with posts.
	 */
	@GET
	@Path("posts/{date}")
	public Response getAllPosts(@PathParam("date") String dateStr) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getAllPosts(dateStr);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No posts found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * 
	 * Return posts from all politicans in specific party on a specific.
	 * 
	 * @param party - the short name of the party, like 's' or 'kd'
	 * @return - response with a JSONObject with posts.
	 * @throws ParseException
	 */
	@GET
	@Path("posts/{party}/{date}")
	public Response getPartyPosts(@PathParam("party") String party, @PathParam("date") String dateStr)
			throws ParseException {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getAllPostsParty(party, dateStr);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No posts found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Return posts from specific politician.
	 * 
	 * @param party - the short name of the party, like 's' or 'kd'
	 * @param id - the id of the politician stored in our database.
	 * @return - response with a JSONObject with posts.
	 */
	@GET
	@Path("posts/politician/{politicianID}/{date}")
	public Response getPostsByPolitican(@PathParam("politicianID") String id, @PathParam("date") String dateStr) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getPostsPolitician(id, dateStr);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No posts found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Returns a response with post from specific id. TODO NEW
	 * 
	 * @param id
	 * @param dateStr
	 * @return
	 */
	@GET
	@Path("posts/specific/{postID}")
	public Response getSpecificPost(@PathParam("postID") int postID) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getSpecificPost(postID);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No posts found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Returns all politician objects.
	 *
	 * @return - response with a JSONObject with politicians.
	 */
	@GET
	@Path("politicians")
	public Response getPoliticians() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getAllPoliticians();
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No politician found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Return all politican objects from specific party.
	 * 
	 * @param party - the short name of the party, like 's' or 'kd'
	 * @return - response with a JSONObject with politicians.
	 */
	@GET
	@Path("politicians/{party}")
	public Response getPoliticiansByParty(@PathParam("party") String party) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getPoliticiansParty(party);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No politicians found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Return specific politican object.
	 * 
	 * @param politician - the politician id.
	 * @return - response with a JSONObject with politician.
	 */
	@GET
	@Path("politicians/id/{politician}")
	public Response getSpecificPoliticianByParty(@PathParam("politician") String id) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getPolitician(id);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No politician found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Return all parties as objects.
	 * 
	 * @return - response with a JSONObject with parties.
	 */
	@GET
	@Path("parties")
	public Response getPartyInformation() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getParties();
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No parties found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Return a specific party object.
	 * 
	 * @return - response with a JSONObject with parties.
	 */
	@GET
	@Path("parties/{party}")
	public Response getSpecificPartyInformation(@PathParam("party") String party) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getParty(party);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No party found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Methods that retrives comments for a specific post.
	 * 
	 * @param postID - the id for the post
	 * @return - response with a JSONObject with comments.
	 */
	@GET
	@Path("comment/{postID}")
	public Response getComment(@PathParam("postID") int postID) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getComments(postID);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No comments found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Puts a new comment into the database.
	 * 
	 * @param postID - id for the post.
	 * @param text - the actual comment.
	 * @param email - email of the comment author.
	 * @return - response with a json success message.
	 */
	@PUT
	@Path("comment/{postID}/{text}/{ip}")
	public Response postComment(@PathParam("postID") int postID, @PathParam("text") String text,
			@PathParam("ip") String ip) {
		JSONObject jsonObject = new JSONObject();
		try {
			controller.postComment(postID, text, ip);
			jsonObject.put(MSG, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Puts a new like for a post in the database.
	 * 
	 * @param postID - id for the post.
	 * @param email - email of the user liking a post.
	 * @return - response with a json success message.
	 */
	@PUT
	@Path("like/{postID}/{ip}")
	public Response postLike(@PathParam("postID") int postID, @PathParam("ip") String ip) {
		JSONObject jsonObject = new JSONObject();
		try {
			if (controller.postLike(postID, ip)) {
				jsonObject.put(MSG, "success");
			} else {
				jsonObject.put(MSG, "Already liked");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Puts a new dislike for a post in the database.
	 * 
	 * @param postID - id for the post.
	 * @param email - email of the user liking a post.
	 * @return - response with a json success message.
	 */
	@PUT
	@Path("dislike/{postID}/{ip}")
	public Response postDislike(@PathParam("postID") int postID, @PathParam("ip") String ip) {

		JSONObject jsonObject = new JSONObject();
		try {
			if (controller.postDislike(postID, ip)) {
				jsonObject.put(MSG, "success");
			} else {
				jsonObject.put(MSG, "Already disliked.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Delets a previously added like in the datbase.
	 * 
	 * @param postID - id for the post.
	 * @param email - email of the user liking a post.
	 * @return - response with a json success message.
	 */
	@DELETE
	@Path("like/{postID}/{ip}")
	public Response postRevertLike(@PathParam("postID") int postID, @PathParam("ip") String ip) {
		JSONObject jsonObject = new JSONObject();
		try {

			if (controller.postRevertLike(postID, ip)) {
				jsonObject.put(MSG, "success");
			} else {
				jsonObject.put(MSG, "Failed to revert like.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Delets a previously added dislike in the datbase.
	 * 
	 * @param postID - id for the post.
	 * @param email - email of the user liking a post.
	 * @return - response with a json success message.
	 */
	@DELETE
	@Path("dislike/{postID}/{ip}")
	public Response postRevertDislike(@PathParam("postID") int postID, @PathParam("ip") String ip) {

		JSONObject jsonObject = new JSONObject();
		try {
			if (controller.postRevertDislike(postID, ip)) {
				jsonObject.put(MSG, "success");
			} else {
				jsonObject.put(MSG, "Failed to revert dislike.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Send and ip to check what posts they have liked. TODO Change URL
	 */
	@GET
	@Path("like/ips/{ip}")
	public Response checkLikes(@PathParam("ip") String ip) {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.checkIPs(ip, true);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No comments found");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Send api to check what posts they disliked. TODO: Change URL
	 * 
	 * @param ip
	 * @return
	 */
	@GET
	@Path("dislike/ips/{ip}")
	public Response checkdisLikes(@PathParam("ip") String ip) {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.checkIPs(ip, false);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No comments found");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}
}
