package ApiBookingTest;

import POJO.BookingData;
import POJO.BookingPatch;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class BookingApiTest {

    @BeforeClass
    public void setUp() {
        baseURI = ("https://restful-booker.herokuapp.com");
    }

    @Test(priority = 1)
    public void getBookingIds() {

        given()
                .when()
                .get("/booking")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(priority = 2)
    public void getBook() {

        given()
                .when()
                .get("/booking/" + 23)
                .then()
                .log().body()
                .statusCode(200);
    }

    String FirstName = getRandomString();
    String LastName = getRandomString();
    int RandomNumber = (int) (Math.random() * 1000 + 2000);

    @Test(priority = 3)
    public void createBooking() {

        Map<String, String> bookingdates = new HashMap<>();
        bookingdates.put("checkin", "2020-02-02");
        bookingdates.put("checkout", "2020-02-12");

        BookingData bookingData = new BookingData();
        bookingData.setFirstname(FirstName);
        bookingData.setLastname(LastName);
        bookingData.setTotalprice(RandomNumber);
        bookingData.setDepositpaid(true);
        bookingData.setBookingdates(bookingdates);
        bookingData.setAdditionalneeds("A la Carte");

                given()
                        .contentType(ContentType.JSON)
                        .body(bookingData)
                        .when()
                        .post("/booking")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().jsonPath().getInt("booking");
    }

    @Test(priority = 4)
    public void UpdateBooking() {

        CreateToken createToken = new CreateToken();
        String token = createToken.gettingToken();

        Map<String, String> bookingdates = new HashMap<>();
        bookingdates.put("checkin", "2020-02-12");
        bookingdates.put("checkout", "2020-02-22");

        BookingData bookingData = new BookingData();
        bookingData.setFirstname(FirstName);
        bookingData.setLastname(LastName);
        bookingData.setTotalprice(RandomNumber);
        bookingData.setDepositpaid(true);
        bookingData.setBookingdates(bookingdates);
        bookingData.setAdditionalneeds("A la Carte");

                given()
                        .cookie("token", token)
                        .contentType(ContentType.JSON)
                        .body(bookingData)
                        .when()
                        .put("/booking/" + 6765)
                        .then()
                        .log().body()
                        .statusCode(200);
    }

    @Test(priority = 5)
    public void PartUpdateBooking() {

        CreateToken createToken = new CreateToken();
        String token = createToken.gettingToken();

        BookingPatch bookingPatch = new BookingPatch();
        bookingPatch.setFirstname(FirstName);
        bookingPatch.setLastname(LastName);

        given()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .body(bookingPatch)
                .when()
                .patch("/booking/" + 6765)
                .then()
                .log().body()
                .statusCode(200);

    }

    @Test(priority = 6)
    public void DeleteBooking() {

        CreateToken createToken = new CreateToken();
        String token = createToken.gettingToken();

        given()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .when()
                .delete("/booking/" + 6765)
                .then()
                .statusCode(201);

    }



    public String getRandomString() {
        return RandomStringUtils.randomAlphabetic(4).toUpperCase();
    }
}
