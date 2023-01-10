import POJO.Location;
import POJO.ToDo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Tasks {
    /** Task 1
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * Converting Into POJO
     */
    @Test
    public void task1(){
            ToDo todo=
                given()

                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        .statusCode(200)
                        .log().body()
                        .extract().as(ToDo.class)
                ;
          System.out.println("todo = " + todo);
    }
    /**
     * Task 2
     * create a request to https://httpstat.us/203
     * expect status 203
     * expect content type TEXT
     */
    @Test
    public void task2(){

                given()

                        .when()
                        .get("https://httpstat.us/203")

                        .then()
                        .statusCode(203)
                        .log().body()
                        .contentType(ContentType.TEXT);
                ;
    }
    /**
     * Task 3
     * create a request to https://httpstat.us/203
     * expect status 203
     * expect content type TEXT
     * expect BODY to be equal to "203 Non-Authoritative Information"
     */
    @Test
    public void task3(){

        given()

                .when()
                .get("https://httpstat.us/203")

                .then()
                .statusCode(203)
                .log().body()
                .contentType(ContentType.TEXT)
                .body(equalTo("203 Non-Authoritative Information"))
                ;
        //2. yontem
        String bodyText=
        given()

                .when()
                .get("https://httpstat.us/203")

                .then()
                .statusCode(203)
                .log().body()
                .contentType(ContentType.TEXT)
                .extract().body().asString()
        ;
        Assert.assertTrue(bodyText.equalsIgnoreCase("203 Non-Authoritative Information"));
    }
    /**
     * Task 4
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect title in response body to be "quis ut nam facilis et officia qui"
     */
    @Test
    public void task4(){

        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .log().body()
                .contentType(ContentType.JSON)
                .body("title",equalTo("quis ut nam facilis et officia qui"))
        ;
    }
    /**
     * Task 5
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect response completed status to be false
     */
    @Test
    public void task5(){

        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .log().body()
                .contentType(ContentType.JSON)
                .body("completed",equalTo(false)) //hamcrest yontemi
        ;
        //2.Yontem
        Boolean completed=
                given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .log().body()
                .contentType(ContentType.JSON)
                .extract().path("completed") // extract ve testNG Assertion yontemi
        ;
        Assert.assertFalse(completed);

    }

}
