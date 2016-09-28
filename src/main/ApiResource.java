package main;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/foo")
public class ApiResource {

	@GET
	@Path("/bar")
	@Produces(MediaType.TEXT_HTML)
	public String getUsers() {
		return "Testing";
	}
	
	@GET
	@Path("/bar2")
	@Produces(MediaType.TEXT_HTML)
	public String getUsers2() {
		return "Testing";
	}
}
