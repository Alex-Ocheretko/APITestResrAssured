import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.*;

import java.util.*;

import static io.restassured.RestAssured.given;

public class Test1 {

    @Test
    public void get100RandomUser() {

        Response response = doGetRequest("https://randomuser.me/api/?inc=gender,nat,name&exc=nat&results=100");
        JsonPath path = response.jsonPath();
        List<HashMap<String, Map<String, Object>>> results = path.getList("results");

        for (HashMap<String, Map<String, Object>> singleObject : results) {
            Map<String, Object> name = singleObject.get("name");
            System.out.println(name.get("first") + " " + name.get("last"));
        }
    }

    public static Response doGetRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return
                given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                        when().get(endpoint).
                        then().contentType(ContentType.JSON).extract().response();
    }


}