package cart.controller.rest;

import cart.domain.cart.Item;
import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.persistence.CartDao;
import cart.persistence.MembersDao;
import cart.persistence.ProductsDao;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartsIntegrationTest {

    private static final Long MEMBER_ID = 1L;
    private static final String EMAIL = "munjin0201@naver.com";
    private static final String PASSWORD = "1234";

    @LocalServerPort
    int port;
    @Autowired
    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Sql("/data.sql")
    @DisplayName("/api/cart/items에 정상적인 post 요청이 들어오면 created 상태코드를 반환한다.")
    void createItemTest() {
        ProductsDao productsDao = applicationContext.getBean("h2ProductsDao", ProductsDao.class);
        Long createdId = productsDao.create(new Product("테스트", 1000, "http://testtest"));

        RestAssured
                .given()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("product-id", createdId)
                .when()
                .post("/api/cart/items")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @Sql("/data.sql")
    @DisplayName("/api/cart/items에 정상적인 get 요청이 들어오면 ok 상태코드를 반환한다.")
    void readItemsByMemberTest() {
        RestAssured
                .given()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/cart/items")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @Sql("/data.sql")
    @DisplayName("/api/cart/items에 정상적인 delete 요청이 들어오면 no_content 상태코드를 반환한다.")
    void deleteItemTest() {
        ProductsDao productsDao = applicationContext.getBean("h2ProductsDao", ProductsDao.class);
        Long createdProductId = productsDao.create(new Product("테스트", 1000, "http://testtest"));
        Product product = productsDao.findById(createdProductId);

        MembersDao membersDao = applicationContext.getBean("h2MembersDao", MembersDao.class);
        Member member = membersDao.findById(MEMBER_ID);

        Item item = new Item(member, product);

        CartDao cartDao = applicationContext.getBean("h2CartDao", CartDao.class);
        Long createdItemId = cartDao.saveItem(item);

        RestAssured.given()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/api/cart/items/" + createdItemId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
