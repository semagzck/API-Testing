package pojoExercise;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class pojoTest {
    public static void main(String[] args) {
        String url = "https://rahulshettyacademy.com/getCourse.php" +
                "?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M" +
                "&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";

        String partialcode = url.split("code=")[1];
        String code = partialcode.split("&scope")[0];

        String response = given().urlEncodingEnabled(false)
                .queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("grant_type", "authorization_code")
                .queryParams("state", "verifyfjdss")
                .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")

                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();
        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");
            
        GetCourse gc=given(). queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
        List<Api> apiCourses=gc.getCourses().getApi();
        for (int i=0;i< apiCourses.size();i++){
            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
                System.out.println(apiCourses.get(i).getPrice());
            }
        }
        String[] coursesTitles= { "Selenium Webdriver Java","Cypress","Protractor"};
        ArrayList<String> actualCoursesTitles=new ArrayList<>();

        List<WebAutomation> webAutomationsCourses=gc.getCourses().getWebAutomation();
        for (int i=0;i< webAutomationsCourses.size();i++){
                actualCoursesTitles.add(webAutomationsCourses.get(i).getCourseTitle());

        }
        List<String> expectedCoursesTitles= Arrays.asList(coursesTitles);
        Assert.assertEquals(expectedCoursesTitles,actualCoursesTitles);
    }
}

