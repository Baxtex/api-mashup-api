package api.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;

/**
 * Class acts as a client that uses our API.
 * @author Anton Gustafsson
 *
 */

public class TestClient {
	   private Client client;
	   private String REST_SERVICE_URL = "http://localhost:8080/api-mashup-api/api";
	   private static final String SUCCESS_RESULT="<result>success</result>";
	   private static final String PASS = "pass";
	   private static final String FAIL = "fail";

	   private void init(){
	      this.client = ClientBuilder.newClient();
	   }

	   public static void main(String[] args){
		   TestClient tester = new TestClient();
	      tester.init();
	      tester.testPrint1();
	   }

	   /**
	    * Tries connecting and using our resource located at "http://localhost:8080/api-mashup-api/api/v1/foo"
	    */
	   private void testPrint1(){
		   String callResult = client
			         .target(REST_SERVICE_URL)
			         .path("/v1/foo")
			         .request(MediaType.TEXT_HTML)
			         .get(String.class);
			      	 System.out.println(callResult);
	   }
}
