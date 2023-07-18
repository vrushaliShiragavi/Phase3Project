package phase3project;

import org.json.JSONObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Operations {

	public static String employeeId = "";
	private static String baseUri = "http://localhost:8088/employees";

	public static Response GetEmployee(String empId) {
		RestAssured.baseURI = baseUri + "/" + empId;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		return response;
	}

	public static Response GetAllEmployee() {
		RestAssured.baseURI = baseUri;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		return response;
	}

	public static Response CreateEmployee(String firstName,String lastName, String salary, String email) {

		JSONObject jobj = new JSONObject();
		jobj.put("firstName", firstName);
		jobj.put("lastName", lastName);
		jobj.put("salary", salary);
		jobj.put("email", email);

		RestAssured.baseURI = baseUri;
		RequestSpecification request = RestAssured.given();

		Response response = request
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(jobj.toString())
				.post();
		return response;
	}

	public static Response Updatemployee(String firstName,String lastName, String salary, String email) {
				
		JSONObject jobj = new JSONObject();
		jobj.put("firstName", firstName);
		jobj.put("lastName", lastName);
		jobj.put("salary", salary);
		jobj.put("email", email);
		
		RestAssured.baseURI = baseUri;
		RequestSpecification request = RestAssured.given();

		Response response = request
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(jobj.toString())
				.put("/" + employeeId);
		return response;
	}

	public static Response DeleteEmployee() {
		RestAssured.baseURI = baseUri;
		RequestSpecification request = RestAssured.given();
		Response response = request.delete("/" + employeeId);
		return response;
	}

	public static int countEmployee(Response response) {
		JsonPath json = response.jsonPath();
		int employeeCount = response.jsonPath().getList("employees").size();
		//System.out.println("The count is " + employeeCount);
		return employeeCount;

	}
}
