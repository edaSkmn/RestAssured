import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void test() {

        given()
                // hazırlık işlemlerini yapacağız (token,send body, parametreler)
                .when()
                // link i ve metodu veriyoruz
                .then()
        //  assertion ve verileri ele alma extract
        ;

    }

    @Test
    public void statusCodeTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log().all() butun response u / bodyi gosterir
                .statusCode(200) // status kontrolu
        ;

    }

    @Test
    public void contentTypeTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log().all() butun response u / bodyi gosterir
                .statusCode(200) // status kontrolu
                .contentType(ContentType.JSON) // donen sonuc JSON tipinde mi kontrolu yapilir
        ;

    }

    @Test
    public void checkCountryInResponseTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log().all() butun response u / bodyi gosterir
                .statusCode(200) // status kontrolu
                .body("country", equalTo("United States")) // body.country= United States ?
        ;

    }

    //pm                              RestAssured
    //body.country                    body("country",
    //body.'post code'                body("post code",
    //body.places[0].'place name'     body("places[0].'place name'")
    //body.places.'place name'        body("places.'place name'")   -> bütün place name leri verir
    //                                bir index verilmezse dizinin bütün elemanlarında arar
    @Test
    public void checkStateInResponseTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log().all() butun response u / bodyi gosterir
                .statusCode(200) // status kontrolu
                .body("places[0].state", equalTo("California")) // body.country= United States ?
        ;

    }

    @Test
    public void checkStateInResponseTest3() {

        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                // .log().body() // log().all() butun response u / bodyi gosterir
                .statusCode(200) // status kontrolu
                .body("places.'place name'", hasItem("Büyükdikili Köyü")) // verilen path  deki liste bu item a sahip mi/iceriyor mu
        ;

    }

    @Test
    public void bodyArrayHasSizeTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log().all() butun response u / bodyi gosterir
                .statusCode(200) // status kontrolu
                .body("places", hasSize(1)) // place in size i 1 e esit mi
        ;

    }

    @Test
    public void combiningTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log().all() butun response u / bodyi gosterir
                .statusCode(200) // status kontrolu
                .body("places", hasSize(1)) // place in size i 1 e esit mi
                .body("places.state", hasItem("California")) // verilen path  deki liste bu item a sahip mi/iceriyor mu
                .body("places[0].'place name'", equalTo("Beverly Hills")) // verilen pathdeki deger buna esit mi
        ;

    }

    @Test
    public void pathParamTest() {

        given()
                .pathParam("Country", "us")
                .pathParam("Zipkod", 90210)
                .log().uri() // request link Request URI:	http://api.zippopotam.us/us/90210

                .when()
                .get("http://api.zippopotam.us/{Country}/{Zipkod}")

                .then()
                .log().body()
                .statusCode(200)
        ;

    }

    @Test
    public void pathParamTest2() {
        // 90210 dan 90213 kadar test sonuçlarında places in size nın hepsinde 1 gediğini test ediniz.
        for (int i = 90210; i <= 90213; i++) {


            given()
                    .pathParam("Country", "us")
                    .pathParam("Zipkod", 90210)
                    .log().uri() // request link Request URI:	http://api.zippopotam.us/us/90210

                    .when()
                    .get("http://api.zippopotam.us/{Country}/{Zipkod}")

                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("places", hasSize(1))
            ;
        }
    }
    @Test
    public void queryParamTest() {
        //https://gorest.co.in/public/v1/users?page=3

        given()
                .param("page",1) // ?page=1  seklinde ekleniyor
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .statusCode(200)
                .body("meta.pagination.page", equalTo(1))
        ;

    }
    @Test
    public void queryParamTest2() {
        //https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

        for (int pageNo = 1; pageNo <=10 ; pageNo++) {

        given()
                .param("page",pageNo) // ?page=1  seklinde ekleniyor
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .statusCode(200)
                .body("meta.pagination.page", equalTo(pageNo))
        ;
        }
    }
}
