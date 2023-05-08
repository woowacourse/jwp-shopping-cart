package cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.MemberDao;
import cart.dao.ProductCartDao;
import cart.dao.ProductDao;
import cart.dto.CartRequest;
import cart.dto.ProductRequest;
import cart.entity.Member;
import cart.entity.Product;
import cart.entity.ProductCart;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql("delete.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductDao productDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private ProductCartDao productCartDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("index 페이지를 조회한다")
    @Test
    public void indexTest() {
        RestAssured.given()
                .accept(MediaType.TEXT_HTML_VALUE)

                .when()
                .get("/")

                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML)
                .body(containsString("상품목록"));
    }

    @DisplayName("admin 페이지를 조회한다")
    @Test
    public void adminTest() {
        RestAssured.given()
                .accept(MediaType.TEXT_HTML_VALUE)

                .when()
                .get("/admin")

                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML)
                .body(containsString("관리자 페이지"));
    }

    @DisplayName("상품을 등록한다")
    @Test
    public void createProducts() {
        ProductRequest productRequest = new ProductRequest("박스터", "https://boxster.com", 10000);
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)

                .when()
                .post("/products")

                .then()

                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("박스터"))
                .body("imgUrl", equalTo("https://boxster.com"))
                .body("price", equalTo(10000));
        assertThat(productDao.findAll())
                .map(Product::getName)
                .containsExactly("박스터");
    }

    @DisplayName("상품을 수정한다")
    @Test
    public void updateProductTest() {
        Product gitchan = productDao.save(new Product("깃짱", "https://gitchan.com", 1000));
        ProductRequest productRequest = new ProductRequest("박스터", "https://boxster.com", 10000);
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)

                .when()
                .put("/products/{id}", gitchan.getId())

                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        Product findProduct = productDao.findById(gitchan.getId()).get();
        assertAll(
                () -> assertThat(findProduct.getName()).isEqualTo("박스터"),
                () -> assertThat(findProduct.getPrice()).isEqualTo(10000),
                () -> assertThat(findProduct.getImgUrl()).isEqualTo("https://boxster.com")
        );
    }

    @DisplayName("상품을 삭제한다")
    @Test
    public void deleteProductTest() {
        Product boxster = productDao.save(new Product("박스터", "1", 1000));
        RestAssured.given()

                .when()
                .delete("/products/{id}", boxster.getId())

                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        Product savedProduct = productDao.findById(boxster.getId()).get();
        assertThat(savedProduct.isDelete()).isTrue();
    }

    @DisplayName("로그인 된 회원으로 장바구니의 상품을 조회한다")
    @Test
    void findMyProductCartsTest() {
        Member member = memberDao.save(new Member("boxster@email.com", "boxster"));
        Product pizza = productDao.save(new Product("pizza", "https://pizza.com", 10000));
        Product burger = productDao.save(new Product("burger", "https://burger.com", 20000));
        productCartDao.save(new ProductCart(pizza.getId(), member.getId()));
        productCartDao.save(new ProductCart(burger.getId(), member.getId()));

        RestAssured.given()
                .auth().preemptive().basic("boxster@email.com", "boxster")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/carts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("cartResponses", hasSize(2))
                .body("cartResponses[0].productName", equalTo("pizza"))
                .body("cartResponses[0].productImgUrl", equalTo("https://pizza.com"))
                .body("cartResponses[0].productPrice", equalTo(10000));
    }

    @DisplayName("로그인 된 회원의 장바구니에 상품을 추가한다")
    @Test
    void addCartTest() {
        Member member = memberDao.save(new Member("boxster@email.com", "boxster"));
        Product pizza = productDao.save(new Product("pizza", "https://pizza.com", 10000));
        CartRequest cartRequest = new CartRequest(pizza.getId());

        RestAssured.given()
                .auth().preemptive().basic("boxster@email.com", "boxster")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequest)
                .when()
                .post("/carts")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", is(1));
    }

    @DisplayName("로그인 된 회원의 장바구니에서 상품을 삭제한다")
    @Test
    void deleteMyCart() {
        Member member = memberDao.save(new Member("boxster@email.com", "boxster"));
        Product pizza = productDao.save(new Product("pizza", "https://pizza.com", 10000));
        ProductCart cart = productCartDao.save(new ProductCart(pizza.getId(), member.getId()));

        RestAssured.given()
                .auth().preemptive().basic("boxster@email.com", "boxster")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/carts/{id}", cart.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        assertThat(productCartDao.findAllByMemberId(member.getId())).isEmpty();
    }

    @DisplayName("장바구니에 담긴 상품을 삭제하면 삭제된 상품이라고 보여준다")
    @Test
    void deleteProductInCart() {
        Member member = memberDao.save(new Member("boxster@email.com", "boxster"));
        Product pizza = productDao.save(new Product("pizza", "https://pizza.com", 10000));
        ProductCart cart = productCartDao.save(new ProductCart(pizza.getId(), member.getId()));

        productDao.deleteById(pizza.getId());

        RestAssured.given()
                .auth().preemptive().basic("boxster@email.com", "boxster")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/carts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("cartResponses", hasSize(1))
                .body("cartResponses[0].productName", equalTo("삭제된 상품"))
                .body("cartResponses[0].productImgUrl", equalTo(""))
                .body("cartResponses[0].productPrice", equalTo(0));
    }
}
