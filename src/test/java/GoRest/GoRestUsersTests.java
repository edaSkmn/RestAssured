package GoRest;

import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.baseURI;

public class GoRestUsersTests {

    int userID;
    User newUser;


    @BeforeClass
    void Setup() {
        baseURI = "https://gorest.co.in/public/v2/users"; // PROD
        // baseURI = "https://test.gorest.co.in/public/v2/users";  // TEST
    }
    public String getRandomName(){
        return RandomStringUtils.randomAlphabetic(8);
    }
    public String getRandomEmail(){
        return RandomStringUtils.randomAlphabetic(8).toLowerCase()+"@gmail.com";
    }

    @Test
    public void createUserObject() {
        // Postmande neler yapiyorum?
        // başlangıç işlemleri
        // token aldım
        // users JSON ı hazırladım.

        // Map yontemi ile kisayolla baslangic islemlerinin yapimi
        Map<String, String> newUser=new HashMap<>();
        newUser.put("name", getRandomName());
        newUser.put("gender", "male");
        newUser.put("email", getRandomEmail());
        newUser.put("status", "active");


        int userID =
                given()
                        .header("Authorization", "Bearer 328e1109b94a601ba0cc92a47edd6e98dab6510f611fcebcb7f7b8fc736523a7")
                        .contentType(ContentType.JSON)
                        // ILK YONTEM (UZUN OLAN)
                        //.body("{\"name\":\""+getRandomName()+"\", \"gender\":\"male\", \"email\":\""+getRandomEmail()+"\", \"status\":\"active\"}")
                        // 2. YONTEM(KISA OLAN)
                        .body(newUser)
                        // üst taraf request özellikleridir : hazırlık işlemleri POSTMAN deki Authorization ve request BODY kısmı

                        .log().uri() // kontrol amacli
                        .log().body() // kontrol amacli

                        .when() // request in olduğu nokta    POSTMAN deki SEND butonu
                        .post("") // baseUri+parantez ici (http yoksa) response un oluştuğu nokta
                        // CREATE işlemi POST metodu ile çağırıyoruz POSTMAN deki gibi

                        // alt taraf response sonrası  POSTMAN deki test penceresi
                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id");

        System.out.println("userID = " + userID);
    }
    @Test
    public void createUserObjectWithObject() {
        newUser=new User();
        newUser.setName(getRandomName());
        newUser.setGender("male");
        newUser.setEmail(getRandomEmail());
        newUser.setStatus("active");

        userID =
                given()
                        .header("Authorization", "Bearer 328e1109b94a601ba0cc92a47edd6e98dab6510f611fcebcb7f7b8fc736523a7")
                        .contentType(ContentType.JSON)

                        .body(newUser)

                        .log().body() // kontrol amacli

                        .when()
                        .post("")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        //.extract().path("id")
                        .extract().jsonPath().getInt("id");

        System.out.println("userID = " + userID);

        // path : class veya tip dönüşümüne imkan veremeyen direkt veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.
    }
    class User{
        private String name;
        private String gender;
        private String email;
        private String status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
    @Test(dependsOnMethods = "createUserObjectWithObject")
    public void getUserByID() {

                given()
                        .header("Authorization", "Bearer 328e1109b94a601ba0cc92a47edd6e98dab6510f611fcebcb7f7b8fc736523a7")
                        .pathParam("userId",userID)
                        .log().uri()

                        .when()
                        .get("/{userId}")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("id", equalTo(userID))
        ;
    }
}