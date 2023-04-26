package cart.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        String productDropQuery = "DROP TABLE product IF EXISTS";
        String productCreateQuery = "CREATE TABLE product(id BIGINT NOT NULL AUTO_INCREMENT,name VARCHAR(20) NOT NULL,price INT NOT NULL,image_url TEXT NOT NULL, PRIMARY KEY(id))";
        String sampleQuery1 = "INSERT INTO product(name, price, image_url) VALUES ('피자', 13000, 'https://searchad-phinf.pstatic.net/MjAyMjEyMjdfMTE1/MDAxNjcyMTAxNTI0Nzg4.WfiSlsy9fTUQJ6q2FTGOaaOVU0QpSB0U1LvplKZQXzIg.H4UgI0VbKUszP7mzC3qhwpSMe15DluJnxjxVGDq_QUgg.PNG/451708-1fa87663-02e3-4303-b8a9-d7eea3676018.png?type=f160_160')";
        String sampleQuery2 = "INSERT INTO product(name, price, image_url) VALUES ('치킨', 27000, 'https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzAzMjdfMTI2%2FMDAxNjc5OTI1ODQ4NTgy.6RT9z-i5prsnwwc-6B9TaK6Q0Zcgsd3TeDGiUdqyDRIg.rW2kMtzBKNFhWWXyr_X2bZfR15AEPUOz-VJnqVaP0jEg.JPEG.koreasinju%2FIMG_3379.jpg&type=ff332_332')";
        String sampleQuery3 = "INSERT INTO product(name, price, image_url) VALUES ('샐러드', 9500, 'https://searchad-phinf.pstatic.net/MjAyMzA0MThfMjk0/MDAxNjgxODAwNDY4NjU4.FJcjmoGsxyCq0nZqlcmoAL2mwX8mM9ny9DdliQcqGZ0g.9cGk2IQHfPIm2-ABelEOY1cc-_8NBQgPMgPpjFZkGFEg.JPEG/2814800-bb4236af-96dd-42e7-8256-32ffaa73de52.jpg?type=f160_160')";

        jdbcTemplate.execute(productDropQuery);
        jdbcTemplate.execute(productCreateQuery);
        jdbcTemplate.update(sampleQuery1);
        jdbcTemplate.update(sampleQuery2);
        jdbcTemplate.update(sampleQuery3);
    }

    @Test
    @DisplayName("/products로 post 요청을 보내면 상태코드 200(ok)을 응답한다")
    void createProduct() {
        final String requestName = "소주";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest createRequest = new ProductRequest(requestName, requestPrice,
            requestUrl);

        RestAssured
            .given().contentType(ContentType.JSON).body(createRequest)
            .when().post("/products")
            .then().statusCode(200);
    }

    @Test
    @DisplayName("/products/{id}로 put 요청을 보내면 상태코드 200(ok)을 응답한다")
    void updateProduct() {
        final String requestName = "소주";
        final int requestPrice = 4_000;
        final String requestUrl = "none";
        final ProductRequest createRequest = new ProductRequest(requestName, requestPrice,
            requestUrl);

        RestAssured
            .given().contentType(ContentType.JSON).body(createRequest)
            .when().put("/products/1")
            .then().statusCode(200);
    }

    @Test
    @DisplayName("/products/{id}로 delete 요청을 보내면 상태코드 200(ok)을 응답한다")
    void deleteProduct() {
        RestAssured
            .given().contentType(ContentType.JSON)
            .when().delete("/products/1")
            .then().statusCode(200);
    }
}