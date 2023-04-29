package cart.controller;

import cart.dao.ItemDao;
import cart.dto.ItemRequest;
import cart.entity.CreateItem;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.core.IsEqual.equalTo;

@Sql({"classpath:test_init.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    private int port;

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(@LocalServerPort int port) {
        this.port = port;
        itemDao.save(new CreateItem("치킨", "a", 10000));
        itemDao.save(new CreateItem("피자", "b", 20000));
    }

    @Test
    @DisplayName("상품 추가 테스트")
    void addItemTest() {
        //given
        ItemRequest itemRequest = new ItemRequest("국밥", "c", 30000);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemRequest)
                .when().post("/admin/items/new")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .body(equalTo("ok"));
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateItemTest() {
        //given
        ItemRequest itemRequest = new ItemRequest("국밥", "c", 30000);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemRequest)
                .when().post("/admin/items/edit/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .body(equalTo("ok"));
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteItemTest() {
        //then
        RestAssured.given().log().all()
                .when().post("/admin/items/delete/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .body(equalTo("ok"));
    }

    @ParameterizedTest
    @DisplayName("상품명에 공백이 들어갔을 경우 테스트")
    @ValueSource(strings = {"", "    "})
    void newItemNameBlankTest(String name) {
        //given
        String message = "상품명은 공백일 수 없습니다.";
        ItemRequest itemRequest = new ItemRequest(name, "c", 10000);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemRequest)
                .when().post("/admin/items/new")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("name", equalTo(message));
    }

    @Test
    @DisplayName("상품명의 길이가 30을 넘는 경우 테스트")
    void newItemNameLengthOverSizeTest() {
        //given
        String name = "ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ";
        String message = "상품명의 길이는 30자 이하여야합니다.";
        ItemRequest itemRequest = new ItemRequest(name, "c", 10000);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemRequest)
                .when().post("/admin/items/new")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("name", equalTo(message));
    }

    @ParameterizedTest
    @DisplayName("상품 이미지 url에 공백이 들어간 경우 테스트")
    @ValueSource(strings = {"", "    "})
    void newItemImageUrlFailTest(String url) {
        //given
        String message = "이미지 url은 공백일 수 없습니다.";
        ItemRequest itemRequest = new ItemRequest("name", url, 10000);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemRequest)
                .when().post("/admin/items/new")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("imageUrl", equalTo(message));
    }

    @Test
    @DisplayName("상품 가격에 공백이 들어간 경우 테스트")
    void newItemPriceBlackFailTest() {
        //given
        Integer price = null;
        String message = "가격은 공백일 수 없습니다.";
        ItemRequest itemRequest = new ItemRequest("국밥", "c", price);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemRequest)
                .when().post("/admin/items/new")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("price", equalTo(message));
    }

    @Test
    @DisplayName("상품 가격이 0원 미만인 경우 테스트")
    void newItemPriceNegativeFailTest() {
        //given
        Integer price = -1;
        String message = "가격은 최소 0원 이상이어야합니다.";
        ItemRequest itemRequest = new ItemRequest("국밥", "c", price);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemRequest)
                .when().post("/admin/items/new")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("price", equalTo(message));
    }

    @Test
    @DisplayName("상품 가격이 100만원 이상인 경우 테스트")
    void newItemPriceOver1MFailTest() {
        //given
        Integer price = 1000001;
        String message = "가격은 최대 100만원 이하여야합니다.";
        ItemRequest itemRequest = new ItemRequest("국밥", "c", price);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemRequest)
                .when().post("/admin/items/new")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("price", equalTo(message));
    }
}
