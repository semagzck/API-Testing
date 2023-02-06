package pojoExercise;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class specBuildersTest {
    public static void main(String[] args) {
        AddPlace bodyInformation=new AddPlace();
        bodyInformation.setAccuracy(50);
        bodyInformation.setAddress("İstanbul");
        bodyInformation.setLanguage("English");
        bodyInformation.setPhoneNumber("+90531 027 3625");
        bodyInformation.setWebsite("https://rahulshettyacademy.com");
        bodyInformation.setName("My Home");

        List<String> myList=new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        bodyInformation.setTypes(myList);

        Location location=new Location();
        location.setLat(-15.456378);
        location.setLng(156.7891);
        bodyInformation.setLocation(location);

        RequestSpecification requestSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123")
                .setContentType(ContentType.JSON).build();
        ResponseSpecification responseSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        RequestSpecification res= given().spec(requestSpec).body(bodyInformation);
        Response response=res.when().post("/maps/api/place/add/json")
                .then().spec(responseSpec).extract().response();

        String responseString=response.asString();
        System.out.println(responseString);

    }
}

