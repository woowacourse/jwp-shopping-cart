package cart;

import cart.controller.dto.ItemRequest;
import cart.controller.dto.ItemResponse;
import cart.domain.ImageUrl;
import cart.domain.Item;
import cart.domain.Name;
import cart.domain.Price;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class JwpCartApplicationTests {

    public static final List<ItemResponse> EXPECTED_PRODUCTS = List.of(
            ItemResponse.from(new Item.Builder().id(1L)
                                                .name(new Name("위키드"))
                                                .imageUrl(new ImageUrl("https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg"))
                                                .price(new Price(150000))
                                                .build()),
            ItemResponse.from(new Item.Builder().id(2L)
                                                .name(new Name("마틸다"))
                                                .imageUrl(new ImageUrl("https://ticketimage.interpark.com/Play/image/large/22/22009226_p.gif"))
                                                .price(new Price(100000))
                                                .build()),
            ItemResponse.from(new Item.Builder().id(3L)
                                                .name(new Name("빌리 엘리어트"))
                                                .imageUrl(new ImageUrl("https://t1.daumcdn.net/cfile/226F4D4C544F42CF34"))
                                                .price(new Price(200000))
                                                .build())
    );

    @LocalServerPort
    private int port;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @DisplayName("GET / 요청 정상 응답")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void contextLoads() throws Exception {

        mockMvc.perform(get("/"))
               .andExpect(model().attribute("products", EXPECTED_PRODUCTS))
               .andExpect(view().name("index"))
               .andExpect(status().isOk());
    }

    @DisplayName("GET /admin 요청 정상 응답")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void getRequestAdmin() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(model().attribute("products", EXPECTED_PRODUCTS))
               .andExpect(view().name("admin"))
               .andExpect(status().isOk());
    }

    @DisplayName("POST /items 요청 정상 응답")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void postRequestItem() throws Exception {
        String content = objectMapper.writeValueAsString(new ItemRequest("레드북", 150000, "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86764/86764_1000.jpg"));
        RestAssured.given()
                   .body(content)
                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                   .when()
                   .post("/items")
                   .then()
                   .statusCode(HttpStatus.CREATED.value())
                   .header("Location", "/");
    }

    @DisplayName("GET /items 요청 정상 응답")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void getRequestItem() {
        RestAssured.given()
                   .accept(MediaType.APPLICATION_JSON_VALUE)
                   .when()
                   .get("/items")
                   .then()
                   .statusCode(HttpStatus.OK.value())
                   .body("size()", is(3));
    }

    @DisplayName("PUT /items/{id} 요청 정상 응답")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void putRequestItem() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(new ItemRequest("레드북", 150000, "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86764/86764_1000.jpg"));
        RestAssured.given()
                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                   .body(content)
                   .when()
                   .put("/items/1")
                   .then()
                   .statusCode(HttpStatus.CREATED.value())
                   .header("Location", "/");
    }

    @DisplayName("DELETE /items/{id} 요청 정상 응답")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void deleteRequestItem() {
        RestAssured.given()
                   .when()
                   .delete("/items/1")
                   .then()
                   .statusCode(HttpStatus.OK.value())
                   .header("Location", "/");
    }

    @DisplayName("POST /items 요청 예외 응답, 이름 empty")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void postRequestItemExceptionWithEmptyName() throws Exception {
        String content = objectMapper.writeValueAsString(new ItemRequest("", 150000, "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86764/86764_1000.jpg"));
        RestAssured.given()
                .body(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/items")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(containsString("이름을 입력해주세요."));
    }

    @DisplayName("POST /items 요청 예외 응답")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void postRequestItemExceptionWithOver5000lengthURL() throws Exception {
        String url = "-".repeat(5001);
        String content = objectMapper.writeValueAsString(new ItemRequest("레드북", 150000, url));
        RestAssured.given()
                .body(content)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/items")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(containsString("URL은 5000자 이하로 입력해주세요."));
    }

    @DisplayName("PUT /items 요청 예외 응답")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void putRequestItemException() throws Exception {
        String content = objectMapper.writeValueAsString(new ItemRequest("", 150000, "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86764/86764_1000.jpg"));
        RestAssured.given()
                   .body(content)
                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                   .when()
                   .put("/items/1")
                   .then()
                   .statusCode(HttpStatus.BAD_REQUEST.value())
                   .body(containsString("이름을 입력해주세요."));
    }

    @DisplayName("PUT /items 요청 예외 응답")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void putRequestItemExceptionWithNotExist() throws Exception {
        String content = objectMapper.writeValueAsString(new ItemRequest("레드북", 150000, "https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86764/86764_1000.jpg"));
        RestAssured.given()
                   .body(content)
                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                   .when()
                   .put("/items/100")
                   .then()
                   .statusCode(HttpStatus.BAD_REQUEST.value())
                   .body(containsString("존재하지 않는 아이템 입니다."));
    }

    @DisplayName("DELETE /items 요청 예외 응답")
    @Test
    @Sql("classpath:initializeTestDb.sql")
    void deleteRequestItemException() throws Exception {
        RestAssured.when()
                   .delete("/items/100")
                   .then()
                   .statusCode(HttpStatus.BAD_REQUEST.value())
                   .body(containsString("존재하지 않는 아이템 입니다."));
    }
}
