package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.pageObject.FBPage;
import test.pageObject.GoogleSearchPage;

import java.io.*;
import java.util.*;


import static io.restassured.RestAssured.given;

public class Test1 {

    final DriverWrapper driverWrapper = new DriverWrapper();
    GoogleSearchPage googleSearchPage = new GoogleSearchPage(driverWrapper.getDriver());
    FBPage fbPage = new FBPage(driverWrapper.getDriver());

    @AfterClass
    public void closeBrowser() {
        driverWrapper.close();
    }

    @Test
    public void get100RandomUsers() {

        int numberOfFemale = 0;
        int numberOfMale = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("first name");
        sb.append(",");
        sb.append("last name");
        sb.append("\n");

        Response response = doGetRequest("https://randomuser.me/api/?inc=gender,nat,name&exc=nat&results=100");
        JsonPath path = response.jsonPath();
        List<HashMap<String, Object>> results = path.getList("results");

        for (HashMap<String, Object> singleObject : results) {
            Map<String, Object> name = (Map<String, Object>) singleObject.get("name");
            if ("female".equals(singleObject.get("gender"))) {
                numberOfFemale += 1;
            }
            if ("male".equals(singleObject.get("gender"))) {
                numberOfMale += 1;
            }
            try (PrintWriter writer = new PrintWriter(new File(System.getProperty("user.dir") + "/src/main/resources/test.csv"))) {

                sb.append(name.get("first"));
                sb.append(",");
                sb.append(name.get("last"));
                sb.append("\n");

                writer.write(sb.toString());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Female: " + numberOfFemale + " Male: " + numberOfMale);

        Assert.assertEquals(numberOfFemale + numberOfMale, 100);
    }


    @Test(dataProvider = "Data")
    void searchUsersInFB(String firstName, String lastName) {
        driverWrapper.init();
        googleSearchPage.clickOnSearchLine();
        googleSearchPage.searchInGoogle(firstName+ " " + lastName +" | Facebook");
        googleSearchPage.clickOnFirstLink();
        if (fbPage.firstUserVisibility()) {
            fbPage.firstUserClick();
        }
        Assert.assertEquals(fbPage.getUserName(), firstName+ " " + lastName);
    }

    @DataProvider(name = "Data")
            public Iterator<Object []> dataReader() throws InterruptedException {
            List<Object []> dataLines = new ArrayList<>();
            boolean notDataHeader = false;
            String[] data;
            BufferedReader br;
            String line;
            String filePath = System.getProperty("user.dir") + "/src/main/resources/test.csv";

            try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
            data = line.split(",");
            if (notDataHeader) dataLines.add(data);
            notDataHeader = true;
            }
            } catch (Exception e) {
        e.printStackTrace();
    }
        return dataLines.iterator();
}


    public static Response doGetRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return
                given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                        when().get(endpoint).
                        then().contentType(ContentType.JSON).extract().response();
    }
}