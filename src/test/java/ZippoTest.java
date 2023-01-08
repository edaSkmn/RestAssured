import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void test(){

        given()
                // hazırlık işlemlerini yapacağız (token,send body, parametreler)
                .when()
                // link i ve metodu veriyoruz
                .then()
                 //  assertion ve verileri ele alma extract
        ;

    }

    @Test
    public void statusCodeTest(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log().all() butun response u / bodyi gosterir
                .statusCode(200) // status kontrolu
        ;

    }
    @Test
    public void contentTypeTest(){

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
    public void checkCountryInResponseTest(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log().all() butun response u / bodyi gosterir
                .statusCode(200) // status kontrolu
                .body("country",equalTo("United States")) // body.country= United States ?
        ;

    }

    //pm                              RestAssured
    //body.country                    body("country",
    //body.'post code'                body("post code",
    //body.places[0].'place name'     body("places[0].'place name'")
    //body.places.'place name'        body("places.'place name'")   -> bütün place name leri verir
    //                                bir index verilmezse dizinin bütün elemanlarında arar
    @Test
    public void checkStateInResponseTest(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log().all() butun response u / bodyi gosterir
                .statusCode(200) // status kontrolu
                .body("places[0].state",equalTo("California")) // body.country= United States ?
        ;

    }
    @Test
    public void checkStateInResponseTest3(){

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
    public void bodyArrayHasSizeTest(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log().all() butun response u / bodyi gosterir
                .statusCode(200) // status kontrolu
                .body("places",hasSize(1)) // place in size i 1 e esit mi
        ;

    }
    @Test
    public void combiningTest(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // log().all() butun response u / bodyi gosterir
                .statusCode(200) // status kontrolu
                .body("places",hasSize(1)) // place in size i 1 e esit mi
                .body("places.state", hasItem("California")) // verilen path  deki liste bu item a sahip mi/iceriyor mu
                .body("places[0].'place name'", equalTo("Beverly Hills")) // verilen pathdeki deger buna esit mi
        ;

    }

}
