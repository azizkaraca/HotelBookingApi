package ApiBookingTest;

import POJO.BookingToken;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateToken {

    String token;

    @Test
    public String gettingToken(){

        BookingToken tokenPojo = new BookingToken();
        tokenPojo.setUsername("admin");
        tokenPojo.setPassword("password123");

        Response response =
                 given()
                .header("Content-Type", "application/json")
                .body(tokenPojo)
                .when()
                .post("/auth");

        response.then().statusCode(200);
        token = (response.path("token"));
        response.prettyPrint();
        return token;
    }


}
