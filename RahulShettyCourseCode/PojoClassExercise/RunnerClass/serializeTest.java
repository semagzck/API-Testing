package pojoExercise;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class serializeTest {
    public static void main(String[] args) {
        AddPlace body=new AddPlace();
        body.setAccuracy(50);
        body.setAddress("İstanbul");
        body.setLanguage("English");
        body.setPhoneNumber("+90531 027 3625");
        body.setWebsite("https://rahulshettyacademy.com");
        body.setName("My Home");

        List<String> myList=new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        body.setTypes(myList);

        Location location=new Location();
        location.setLat(-15.456378);
        location.setLng(156.7891);
        body.setLocation(location);

        RestAssured.baseURI="https://rahulshettyacademy.com";
        Response response= (Response) given().log().all().queryParam("key","qaclick123").body(body).
                when().post("/maps/api/place/add/json").
                then().assertThat().statusCode(200).extract().response();

        String responseString=response.asString();
        System.out.println(responseString);

    }
}

