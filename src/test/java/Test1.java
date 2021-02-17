import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.*;
import pageObject.GoogleSearchPage;

import java.util.*;

import static io.restassured.RestAssured.given;

public class Test1 {

    DriverWrapper driverWrapper;
    GoogleSearchPage googleSearchPage;

    @Test
    public void get100RandomUser() {

        int numberOfFemale = 0;
        int numberOfMale = 0;

        Response response = doGetRequest("https://randomuser.me/api/?inc=gender,nat,name&exc=nat&results=100");
        JsonPath path = response.jsonPath();
        List<HashMap<String, Object>> results = path.getList("results");

        for (HashMap<String, Object> singleObject : results) {
            Map<String, Object> name = (Map<String, Object>) singleObject.get("name");
            System.out.println(name.get("first") + " " + name.get("last"));
            if ("female".equals(singleObject.get("gender"))) {
                numberOfFemale += 1;
            }
            if ("male".equals(singleObject.get("gender"))) {
                numberOfMale += 1;
            }
        }
        System.out.println("Female: " + numberOfFemale + " Male: " + numberOfMale);
    }

    public static Response doGetRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return
                given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                        when().get(endpoint).
                        then().contentType(ContentType.JSON).extract().response();
    }
}