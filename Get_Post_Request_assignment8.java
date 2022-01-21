package restassuredtestcases;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

public class Get_Post_Request_assignment8 {
	
	
// 1. Get Single User	
	@Test(enabled = false)
	public void testGetSingleUserData() {
		
		System.out.println("==============1. Get Single User===========");

		//in api we have URI(Uniform resource identifier) and not url
		baseURI = "https://reqres.in/api";
		
		String validUserId = "2";

		given()
			.get("/users/" + validUserId)
		.then()
			.statusCode(200)
			.body("data.id", equalTo(2))
			.log().all();

	}
	
//	2. Single user not found
	@Test(enabled = false)
	public void testValidateGetUserNotFound() {
		
		System.out.println("==============2. Single user not found===========");
		baseURI = "https://reqres.in/api";
		
		// invalid userid provided to check user not found
		String invalidUserID = "23";
				
		given()
			.get("/users/" + invalidUserID)
		.then()
			.statusCode(404)
			.log().ifStatusCodeIsEqualTo(404);	
	}
	
//	3. Get list resource data	
	@Test(enabled = false)
	public void testValidateGetListResourceData() {
		
		System.out.println("==============3. Get list resource data===========");
		baseURI = "https://reqres.in/api";

		given()
			.get("/unknown")
		.then()
			.statusCode(200)
			.body("data[2].id", equalTo(3))
			.body("data.year", hasItems(2000,2001,2002,2003,2004,2005))
			//log response body
			.log().body()
			//log response headers
			.log().headers()
			//log response status
			.log().status();
			
	}
	
//	4. Get single resource data
	@Test(enabled = false)
	public void testValidateGetSingleResourceData() {
		
		System.out.println("==============4. Get single resource data===========");
		baseURI = "https://reqres.in/api";
		
		
		// valid userid provided to check user data
			String validUserID = "2";
				
		given()
			.get("/unknown/" +validUserID)
		.then()
			.statusCode(200)
			.body("data.id", equalTo(2))
			.body("data.name", equalTo("fuchsia rose"))
			//we are logging everything
			.log().everything();	
	}
	
//	5. Single resource not found
	
	@Test(enabled = false)
	public void testValidateGetSingleResourceNotFound() {
		System.out.println("==============5. Single resource not found===========");
		
		// invalid resource userid provided to check user not found
		String invalidResourceID = "23";
		
		baseURI = "https://reqres.in/api";

		given()
			.get("/unknown/" +invalidResourceID)
		.then()
			.statusCode(404)
			//validation id status code is equal to 404 then log details
			.log().ifStatusCodeIsEqualTo(404);	
	}
	
//	6.Post Register Successful
	@Test(enabled = false)
	public void testPostRegisterSuccessfull() {
		
		System.out.println("==============6.Post Register Successfull===========");
		
		baseURI = "https://reqres.in/api";
		JSONObject reqData = new JSONObject();
		reqData.put("email","eve.holt@reqres.in");
		reqData.put("password","pistol");

		System.out.println(reqData.toJSONString());

		given()
			.header("Content-Type","application/json")
			.header("Connection","keep-alive")
			.accept(ContentType.JSON)
			.body(reqData.toJSONString())
		.when()
			.post("/register")
		.then()
			.statusCode(200)
			.log().all();

//		response we should get
//		{
//		    "id": 4,
//		    "token": "QpwL5tke4Pnpja7X4"
//		}
	}
	
//	7.Post Register UnSuccessfull
	@Test(enabled = false)
	public void testPostRegisterUnSuccessfull() {
		
		System.out.println("==============7.Post Register UnSuccessfull===========");
		baseURI = "https://reqres.in/api";
		JSONObject reqData = new JSONObject();
		reqData.put("email","sydney@fife");

		System.out.println(reqData.toJSONString());

		given()
			.header("Connection","keep-alive")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(reqData.toJSONString())
		.when()
			.post("/register")
		.then()
		//verify status code 
			.statusCode(400)
		//validation for body 
			.body("error", equalTo("Missing password") )
			.log().all();
		
//		response we should get
//		{
//		    "error": "Missing password"
//		}
		
	}

//	8.Post Login Successful
	@Test(enabled = false)
	public void testPostLoginSuccessfull() {
		
		System.out.println("==============8.Post Login Successfull===========");
		
		baseURI = "https://reqres.in/api";
		JSONObject reqData = new JSONObject();
		reqData.put("email","eve.holt@reqres.in");
		reqData.put("password","pistol");

		System.out.println(reqData.toJSONString());

		given()
			.header("Content-Type","application/json")
			.header("Connection","keep-alive")
			.accept(ContentType.JSON)
			.body(reqData.toJSONString())
		.when()
			.post("/login")
		.then()
		//verify status code 
			.statusCode(200)
			.log().all();
	}
	
//	9.Post Login UnSuccessfull
	@Test(enabled = false)
	public void testPostLoginUnSuccessfull() {
		
		System.out.println("==============9.Post Login UnSuccessfull===========");
		
		baseURI = "https://reqres.in/api";
		JSONObject reqData = new JSONObject();
		reqData.put("email","peter@klaven");
	
		System.out.println(reqData.toJSONString());

		given()
		    .header("Content-Type","application/json")
		    .header("Connection","keep-alive")
		    .accept(ContentType.JSON)
			.body(reqData.toJSONString())
		.when()
			.post("/login")
		.then()
			.statusCode(400)
			//validation for body 
			.body("error", equalTo("Missing password") )
			.log().body()
			//if validation fails , status is displayed
			.log().ifValidationFails(LogDetail.STATUS);
	}
	
//	10.End to End API Test
	
	@Test
	public void endToEndAPITest() {
		System.out.println("==============10.End to End API Test===========");
		
		//Register user
		baseURI = "https://reqres.in/api";
		JSONObject reqData = new JSONObject();
		reqData.put("email","eve.holt@reqres.in");
		reqData.put("password","pistol");

		System.out.println(reqData.toJSONString());

		given()
			.header("Content-Type","application/json")
			.header("charset","utf-8")
			.header("Connection","keep-alive")
			.accept(ContentType.JSON)
			.body(reqData.toJSONString())
		.when()
			.post("/register")
		.then()
			.statusCode(200)
			.log().all();
		
		//extract Register token and print
		String Registertoken =  given()	
							.body(reqData.toJSONString())
							.contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("charset","utf-8")
						.when()
							.post("/register")
						.then()
							.extract().path("token");
		System.out.println("TOKEN : " + Registertoken);
		
		//extract userID and print
		int userID =  given()	
							.body(reqData.toJSONString())
							.contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("charset","utf-8")
						.when()
							.post("/register")
						.then()
							.extract().path("id");
		System.out.println("ID : " + userID);
		
		//User Login 
		given()
			.header("Content-Type","application/json")
			.header("Connection","keep-alive")
			.accept(ContentType.JSON)
			.body(reqData.toJSONString())
		.when()
				.post("/login")
		.then()
		//verify status code 
		.statusCode(200)
		.log().all();
		
		//Extract login token
		String Logintoken =  given()	
				.body(reqData.toJSONString())
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("charset","utf-8")
			.when()
				.post("/login")
			.then()
				.extract().path("token");
		System.out.println("TOKEN : " + Logintoken);
		
		// get single user to find the same user id >>4
		given()
			.get("/users/" + userID)
	   .then()
			.statusCode(200)
			.body("data.id", equalTo(4))
			.body("data.first_name", equalTo("Eve"))
			.body("data.last_name", equalTo("Holt"))
			.body("data.email", equalTo("eve.holt@reqres.in"))
			.log().body();
		
		// SINGLE <RESOURCE> use the same user id >> validate details
		given()
			.get("/unknown/" +userID)
	   .then()
			.statusCode(200)
			.body("data.id",equalTo(4) )
			.body("data.name", equalTo("aqua sky"))
			//we are logging everything
			.log().everything();
		
		// patch same user >> validate response >> search user >> validate
		
		reqData.put("name","John");
		reqData.put("job","Teacher");
		given()		
		 	.body(reqData.toJSONString())
	   .when()
			.patch("/users/" + userID)
	   .then()
		   .statusCode(200)
		   .log().body();
		System.out.println(userID + " : Updated Successfully");	
		
		try {
			//searching again if its updated or not
			given()
				.get("/users/" + userID).
			then()
				.statusCode(200)
				.body("data.id", equalTo(4))
				.log().body();
		} catch (Exception e) {
			
		}
		
	// delete same user >> validate code >> search user >> validate
	when()
		.delete("/users/" + userID)
	.then()
		.statusCode(204)
		.log().ifStatusCodeIsEqualTo(204);
	System.out.println(userID + " : Deleted Successfully");
	
	//searching again if its deleted or not
			given()
				.get("/users/" + userID)
		   .then()
				.statusCode(200)
				.body("data.id", equalTo(4))
				.log().body();
		
	}
}
