package restassuredtestcases;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class FinalTest {
	
	@Test
	public void test_end_to_end_final() {
		
		System.out.println("==============End to End API Test===========");
		
		baseURI = "https://gorest.co.in/";
		JSONObject reqData = new JSONObject();
		reqData.put("name","Tenali Ramakrishna");
		reqData.put("gender", "male");
		reqData.put("email", "tenali.ramakrishna@15ce.com");
		reqData.put("status","active");

		System.out.println(reqData.toJSONString());
		//Register user
		given()
		//here i am sending this particular headers as request
			.header("Content-Type","application/json")
			.header("charset","utf-8")
			.accept(ContentType.JSON)
			.body(reqData.toJSONString())
		.when()
			.post("public/v1/users")
		.then()
		//this is response we get
			.statusCode(201)
			.log().all();
//			.log().body();

		
		
		//extract userID and print
		int userID =  given()	
							.body(reqData.toJSONString())
							.contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("charset","utf-8")
						.when()
							.post("public/v1/users")
						.then()
							.extract().path("id");
		System.out.println("ID : " + userID);
		
		
		
		// get single user to find the same user id >>
		given()
			.get("/users/" + userID)
	   .then()
			.statusCode(200)
			.body("data.name", equalTo("Tenali Ramakrishna"))
			.log().body();
		
		// patch same user >> validate response >> search user >> validate
		
		reqData.put("name","John");
		given()		
		 	.body(reqData.toJSONString())
	   .when()
			.patch("public/v1/users/" + userID)
	   .then()
		   .statusCode(200)
		   .log().body();
		System.out.println(userID + " : Updated Successfully");	
		
	//Create user post
		reqData.put("title","test");
		reqData.put("body", "testing");

		System.out.println(reqData.toJSONString());
		
		given()
		//here i am sending this particular headers as request
			.header("Content-Type","application/json")
			.header("charset","utf-8")
			.accept(ContentType.JSON)
			.body(reqData.toJSONString())
		.when()
			.post("public/v1/users" + userID + "/posts")
		.then()
		//this is response we get
			.statusCode(201)
			.log().all();
//			.log().body();
		
		//Create Post comment
		
		reqData.put("comment","test");
		reqData.put("gender", "male");
		reqData.put("email", "tenali.ramakrishna@15ce.com");
		reqData.put("status","active");

		System.out.println(reqData.toJSONString());
		
		given()
		//here i am sending this particular headers as request
			.header("Content-Type","application/json")
			.header("charset","utf-8")
			.accept(ContentType.JSON)
			.body(reqData.toJSONString())
		.when()
			.post("public/v1/users/100/posts")
		.then()
		//this is response we get
			.statusCode(201)
			.log().all();
//			.log().body();
		
		//Create a user TODO
		
		reqData.put("title","2022-02-14T00:00:00.000+05:30");
		reqData.put("dueon", "male");
		reqData.put("status","completed");

		System.out.println(reqData.toJSONString());
		
		given()
		//here i am sending this particular headers as request
			.header("Content-Type","application/json")
			.header("charset","utf-8")
			.accept(ContentType.JSON)
			.body(reqData.toJSONString())
		.when()
			.post("public/v1/users/" + userID +"/posts")
		.then()
		//this is response we get
			.statusCode(201)
			.log().all();
//			.log().body();

		
	// delete same user >> validate code >> search user >> validate
	when()
		.delete("/users/" + userID)
	.then()
		.statusCode(204)
		.log().ifStatusCodeIsEqualTo(204);
	System.out.println(userID + " : Deleted Successfully");
	
	//searching again if its deleted or not
			given()
				.get("public/v1/users/" + userID +"/posts")
		   .then()
				.statusCode(200)
				.log().body();
		
	}
	

}
