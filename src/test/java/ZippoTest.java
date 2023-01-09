import POJO.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

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
//JSON serialize  Class yapısındaki bilgiyi JSON a dönüştürmek  Class -> JSON
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
    public void bodyJsonPathTest3() {

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

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    @BeforeClass
    void Setup(){

        baseURI="https://gorest.co.in/public/v1";

        requestSpec = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setContentType(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();
    }
    @Test
    public void requestResponseSpecification() {
        //https://gorest.co.in/public/v1/users?page=3

        given()
                .param("page",1)
                .spec(requestSpec)

                .when()
                .get("/users") // http olsaydi baseUri kullanmilmazdi

                .then()
                .body("meta.pagination.page", equalTo(1))
                .spec(responseSpec)
        ;
    }
    //Json exract
    @Test
    public void extractingJsonPath(){

        String placeName=
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .statusCode(200)
              //  .log().body()
                .extract().path("places[0].'place name'")
        // exract metodu ile given ile baslayan satir,
        // bir deger dondurur hale geldi, exract en sonda olmali
        ;

        System.out.println("placeName = " + placeName);
    }
    @Test
    public void extractingJsonPathInt(){
        // alinacak tipin degerine en uygun olan karsiliktaki tip yazilir
        int limit=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .statusCode(200)
                        //  .log().body()
                        .extract().path("meta.pagination.limit")
                ;
        System.out.println("limit = " + limit);
        Assert.assertEquals(limit,10,"test sonucu");
    }

    @Test
    public void extractingJsonPathList(){
        // alinacak tipin degerine en uygun olan karsiliktaki tip yazilir
        List<Integer> idler=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .statusCode(200)
                        //  .log().body()
                        .extract().path("data.id")
                ;
        System.out.println("idler = " + idler);
        Assert.assertTrue(idler.contains(4235));
    }

    @Test
    public void extractingJsonPathStringList(){

        List<String> names=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .statusCode(200)
                        //  .log().body()
                        .extract().path("data.name")
                ;
        System.out.println("names = " + names);
        Assert.assertTrue(names.contains("Gautam Ahluwalia"));
    }
    @Test
    public void extractingJsonPathStringResponseAll(){

     Response response=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .statusCode(200)
                        //  .log().body()
                        .extract().response() // butun body alindi
                ;
            List<Integer> idler=response.path("data.id");
            List<String> names=response.path("data.name");
            int limit= response.path("meta.pagination.limit");

        System.out.println("response= "+ response.prettyPrint()); // body yazdiriliyor bu sekilde
        System.out.println("idler = " + idler);
        System.out.println("names = " + names);
        System.out.println("limit = " + limit);

        Assert.assertTrue(names.contains("Gautam Ahluwalia"));
        Assert.assertTrue(idler.contains(4235));
        Assert.assertEquals(limit,10,"test sonucu");
    }
    @Test
    public void extractingJsonPOJO(){
//JSON DEserialize  JSON -> Class
        Location yer=
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                //.log().body()
                .extract().as(Location.class) // location class yapisina gore/ sablonuna gore
        ;
        System.out.println("yer.getPostCode() = " + yer.getPostCode());
        System.out.println("yer.getPlaces().get(0).getPlaceName() = " + yer.getPlaces().get(0).getPlaceName());

    }
    

}
