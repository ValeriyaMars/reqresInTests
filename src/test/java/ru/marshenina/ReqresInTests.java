package ru.marshenina;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresInTests extends TestBase{

    @Test
    void checkListUsersAttributes() {

        Integer page = 1,
                perPage = 6,
                total = 12,
                totalPages = 2;

        given()
                .log().uri()
                .when()
                .get("/users")
                .then()
                .log().all()
                .statusCode(200)
                .body("page", is(page))
                .body("per_page", is(perPage))
                .body("total", is(total))
                .body("total_pages", is(totalPages));

    }

    @Test
    void checkListUsersDataAttributes() {

        Integer id = 1;
        String email = "george.bluth@reqres.in",
                firstName = "George",
                lastName = "Bluth",
                avatar = "https://reqres.in/img/faces/1-image.jpg";

        given()
                .log().uri()
                .when()
                .get("/users")
                .then()
                .log().all()
                .statusCode(200)
                .body("data[0].id", is(id))
                .body("data[0].email", is(email))
                .body("data[0].first_name", is(firstName))
                .body("data[0].last_name", is(lastName))
                .body("data[0].avatar", is(avatar));
    }

    @Test
    void checkSingleUserDataAttributes() {

        Integer id = 2;
        String email = "janet.weaver@reqres.in",
                firstName = "Janet",
                lastName = "Weaver",
                avatar = "https://reqres.in/img/faces/2-image.jpg";

        given()
                .log().uri()
                .when()
                .get("/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", is(id))
                .body("data.email", is(email))
                .body("data.first_name", is(firstName))
                .body("data.last_name", is(lastName))
                .body("data.avatar", is(avatar));
    }

    @Test
    void checkSingleUserSupportAttributes() {

        String supportUrl = "https://reqres.in/#support-heading",
                supportText = "To keep ReqRes free, contributions towards server costs are appreciated!";

        given()
                .log().uri()
                .when()
                .get("/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("support.url", is(supportUrl))
                .body("support.text", is(supportText));
    }

    @Test
    void checkSingleUserNotFound() {

        given()
                .log().uri()
                .when()
                .get("/users/100")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    void successfulCreateUserTest() {

        String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .log().uri()

                .body(body)
                .contentType(JSON)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void successfulUpdateJobUserTest() {

        String body = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .log().uri()

                .body(body)
                .contentType(JSON)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

}
