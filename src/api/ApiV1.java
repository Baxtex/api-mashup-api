package api;

import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import controller.Controller;

/**
 * End point of the API. The URI for accessing these resources is:
 * http://localhost:8080/api-mashup-api/v1/<resource>
 */

@Path("/v1/")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes({ MediaType.TEXT_PLAIN + ";charset=utf-8" })
public class ApiV1 {

	private Controller controller = new Controller();
	private final String MSG = "message";
	private final String ERR_MSG = "Server was not able to process your request";
	private final String ACAO = "Access-Control-Allow-Origin";
	private final Response ERR_RESPONSE = Response.status(500).header(ACAO, "*").entity(ERR_MSG).build();

	/**
	 * Default resource that tells the user to specify their request.
	 * @return - response with a JSON success message.
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response defaultMessage() {
		return Response.ok("Please specify your request.").header(ACAO, "*").build();
	}

	/**
	 * Returns posts from all politicians and the specified date.
	 * @param dateStr - a date formatted as yyyy-mm-dd
	 * @return - response with a JSONObject with posts from the specified date.
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
	 * Returns posts from all politicians in the specified party and from the specified date.
	 * @param party - the short name of the party, like 's' or 'kd'.
	 * @param dateStr - a date formatted as yyyy-mm-dd
	 * @return - response with a JSONObject with posts from the specified party and date.
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
	 * Returns all posts from the specified politician, regardless of date.
	 * @param politicianID - id of a certain politician.
	 * @return - response with a JSONObject with posts from the specified politician.
	 */
	@GET
	@Path("posts/politician/{politicianID}")
	public Response getPostsByPolitican(@PathParam("politicianID") String id) {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject = controller.getPostsPolitician(id);
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
	 * Returns posts from the specified politician from the specified date.
	 * @param politicianID - id of a certain politician.
	 * @param date - a date formatted as yyyy-mm-dd
	 * @return - response with a JSONObject with posts from the specified politician and date.
	 */
	@GET
	@Path("posts/politician/{politicianID}/{date}")
	public Response getPostsByPoliticanDate(@PathParam("politicianID") String id, @PathParam("date") String dateStr) {
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
	 * Returns the 20 most up voted posts.
	 * @return - response with a JSONObject with the 20 most up voted posts.
	 */
	
	@GET
	@Path("posts/upvoted")
	public Response getPostsMostUpvoted() {
		JSONObject jsonObject = new JSONObject();
		try{
			jsonObject = controller.getPostsMostUpvoted();
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}
	
	/**
	 * Returns the 20 most down voted posts.
	 * @return - response with a JSONObject with the 20 most down voted posts.
	 */
	
	@GET
	@Path("posts/downvoted")
	public Response getPostsMostDownvoted() {
		JSONObject jsonObject = new JSONObject();
		try{
			jsonObject = controller.getPostsMostDownvoted();
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}


	/**
	 * Returns a response with the post related to the specified post id.
	 * @param postID - id of a certain post.
	 * @return - response with a JSONObject with a post.
	 */
	
	@GET
	@Path("posts/id/{postID}")
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
	 * Returns a response with data about all politicians.
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
	 * Returns a response with data about all politicians of the specified party.
	 * @param party - the short name of the party, like 's' or 'kd'.
	 * @return - response with a JSONObject with politicians.
	 */
	@GET
	@Path("politicians/party/{party}")
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
	 * Returns a response with data about the specified politician.
	 * @param politician - politician id of a certain politician.
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
	 * Returns a response with data about all parties.
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
	 * Returns a response with data about the specified party.
	 * @param party - the short name of the party, like 's' or 'kd'.
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
	 * Returns a response with comments related to the specified post id.
	 * @param postID - id of a certain post.
	 * @return - response with a JSONObject with comments.
	 */
	@GET
	@Path("comments/{postID}")
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
	 * Returns a response containing a list of all likes related to the specified IP address.
	 * @param ip - IP address of the user liking a post.
	 * @return - a list of all likes related to the specified IP address.
	 */
	
	@GET
	@Path("likes/ips/{ip}")
	public Response checkLikes(@PathParam("ip") String ip) {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.checkIPs(ip, true);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No likes found");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Returns a response containing a list of all dislikes related to the specified IP address.
	 * @param ip - IP address of the user disliking a post.
	 * @return - a list of all dislikes related to the specified IP address.
	 */
	
	@GET
	@Path("dislikes/ips/{ip}")
	public Response checkdisLikes(@PathParam("ip") String ip) {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.checkIPs(ip, false);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put(MSG, "No dislikes found");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Posts a new comment into the database.
	 * @param postID - id for the post.
	 * @param text - the actual comment.
	 * @param ip - IP address of the comment author.
	 * @return - response with a JSON success message.
	 */
	@POST
	@Path("comment/{postID}/{text}/{alias}")
	public Response postComment(@PathParam("postID") int postID, @PathParam("text") String text,
			@PathParam("alias") String alias) {
		JSONObject jsonObject = new JSONObject();
		try {
			controller.postComment(postID, text, alias);
			jsonObject.put(MSG, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return ERR_RESPONSE;
		}
		return Response.ok(jsonObject.toString()).header(ACAO, "*").build();
	}

	/**
	 * Posts a new like for a post in the database.
	 * @param postID - id for the post.
	 * @param ip - IP address of the user liking a post.
	 * @return - response with a JSON success message.
	 */
	@POST
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
	 * Posts a new dislike for a post in the database.
	 * @param postID - id for the post.
	 * @param ip - IP address of the user liking a post.
	 * @return - response with a JSON success message.
	 */
	@POST
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
	 * Deletes a previously added like from the database.
	 * @param postID - id for the post.
	 * @param ip - IP address of the user liking a post.
	 * @return - response with a JSON success message.
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
	 * Deletes a previously added dislike from the database.
	 * @param postID - id for the post.
	 * @param ip - IP address of the user liking a post.
	 * @return - response with a JSON success message.
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

}
