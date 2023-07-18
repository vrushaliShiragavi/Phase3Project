package phase3project;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class EndToEndTest {
	String employeeNameChange = "Tom";
	int totalNumberOfEmployees = 3;

	@Test
	public void TC01_GetAllEmployee() {
		Response response = Operations.GetAllEmployee();
		Assert.assertEquals(Operations.countEmployee(response), totalNumberOfEmployees);
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Test
	public void TC02_CreateEmployee() {
		Response response = Operations.CreateEmployee("John", "Roxy", "3000", "john@gmail.com");

		JsonPath json = response.jsonPath();
		Operations.employeeId = json.get("id").toString();

		Assert.assertEquals(response.statusCode(), 201);
		Assert.assertEquals(json.get("firstName").toString(), "John");

		response = Operations.GetAllEmployee();
		Assert.assertEquals(Operations.countEmployee(response), totalNumberOfEmployees + 1);
	}

	@Test
	public void TC03_UpdateEmployee() {

		Response empToUpdate = Operations.GetEmployee(Operations.employeeId);
		JsonPath jsonPath = empToUpdate.jsonPath();
		Response response = Operations.Updatemployee(employeeNameChange, jsonPath.get("lastName").toString(), jsonPath.get("salary").toString(), jsonPath.get("email").toString());
		String body = response.getBody().asString();

		Assert.assertEquals(response.jsonPath().get("firstName").toString(), employeeNameChange);
		Assert.assertEquals(response.statusCode(), 200);

		response = Operations.GetAllEmployee();

		List<String> names = response.jsonPath().get("firstName");
		if (names.contains("John")) {
			Assert.assertNull("Employee Still Present After Name Update ! " + names.toString());
		}
	}

	@Test
	public void TC04_GetEmployee() {
		Response response = Operations.GetEmployee(Operations.employeeId);
		Assert.assertEquals(response.statusCode(), 200);
		JsonPath json = response.jsonPath();
		Assert.assertEquals(json.get("firstName").toString(), employeeNameChange);
	}

	@Test
	public void TC05_DeleteEmployee() {
		Response response = Operations.DeleteEmployee();
		
		Assert.assertEquals(response.statusCode(), 200);

		response = Operations.GetAllEmployee();
		JsonPath json = response.jsonPath();
		List<String> names = json.get("firstName");
		if (names.contains(employeeNameChange)) {
			Assert.assertNull("Eployee Still Present After Deletion !" + names.toString());
		}

	}

	@Test
	public void TC06_GetEmployee() {
		Response response = Operations.GetEmployee(Operations.employeeId);
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void TC07_GetAllEmployee() {
		Response response = Operations.GetAllEmployee();

		Assert.assertEquals(Operations.countEmployee(response), totalNumberOfEmployees);
		Assert.assertEquals(response.statusCode(), 200);
	}
}