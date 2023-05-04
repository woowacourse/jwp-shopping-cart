package cart.controller;

import static cart.fixture.ProductRequestFixture.PRODUCT_REQUEST_A;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.ProductDao;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

//TODO 페이지 테스트
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainControllerTest {

    private static final String ADMIN_PAGE_PRODUCT_LIST_HTML_TAG = "#product-list tr";
    private static final String INDEX_PAGE_PRODUCT_LIST_HTML_TAG = "ul.product-grid li.product";

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        jdbcTemplate.update("delete from product");
    }

    @DisplayName("admin 페이지로 접속시 모든 상품의 정보를 출력한다.")
    @Test
    void admin_page_print_all_products() {
        //given
        int countOfProduct = 3;
        createProductsByCount(countOfProduct);
        //when
        Response response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(PRODUCT_REQUEST_A)
                .when()
                .get("/admin");
        //then
        Document doc = Jsoup.parse(response.getBody().asString());
        int actual = doc.select(ADMIN_PAGE_PRODUCT_LIST_HTML_TAG).size();

        assertThat(actual).isEqualTo(countOfProduct);
    }

    @DisplayName("index 페이지로 접속시 모든 상품의 정보를 출력한다.")
    @Test
    void index_page_print_all_products() {
        //given
        int countOfProduct = 3;
        createProductsByCount(countOfProduct);
        //when
        Response response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(PRODUCT_REQUEST_A)
                .when()
                .get("/");
        //then
        Document doc = Jsoup.parse(response.getBody().asString());
        int actual = doc.select(INDEX_PAGE_PRODUCT_LIST_HTML_TAG).size();

        assertThat(actual).isEqualTo(countOfProduct);
    }

    private void createProductsByCount(int count) {
        for (int i = 0; i < count; i++) {
            productDao.save(PRODUCT_REQUEST_A.toEntity());
        }
    }
}
