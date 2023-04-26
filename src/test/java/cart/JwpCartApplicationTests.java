package cart;

import cart.controller.dto.ItemRequest;
import cart.dao.entity.Item;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class JwpCartApplicationTests {

    public static final List<Item> EXPECTED_PRODUCTS = List.of(
            new Item.Builder().id(1L)
                              .name("위키드")
                              .imageUrl("https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg")
                              .price(150000)
                              .build(),
            new Item.Builder().id(2L)
                              .name("마틸다")
                              .imageUrl("https://ticketimage.interpark" +
                                      ".com/Play/image/large/22/22009226_p.gif")
                              .price(100000)
                              .build(),
            new Item.Builder().id(3L)
                              .name("빌리 엘리어트")
                              .imageUrl("https://t1.daumcdn" +
                                      ".net/cfile/226F4D4C544F42CF34")
                              .price(200000)
                              .build()
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

        final String truncateSql = "TRUNCATE TABLE items ";
        jdbcTemplate.update(truncateSql);
        final String insertSql = "INSERT INTO items (name, image_url, price) values ('위키드', 'https://image.yes24.com/themusical/upFiles/Themusical/Play/post_2013wicked.jpg', 150000);\n " +
                "INSERT INTO items (name, image_url, price) values ('마틸다', 'https://ticketimage.interpark.com/Play/image/large/22/22009226_p.gif', 100000);\n" +
                "INSERT INTO items (name, image_url, price) values ('빌리 엘리어트', 'https://t1.daumcdn.net/cfile/226F4D4C544F42CF34', 200000);";
        jdbcTemplate.update(insertSql);
    }

    @DisplayName("GET / 요청 정상 응답")
    @Test
    void contextLoads() throws Exception {

        mockMvc.perform(get("/"))
               .andExpect(model().attribute("products", EXPECTED_PRODUCTS))
               .andExpect(view().name("index"))
               .andExpect(status().isOk());
    }

    @DisplayName("GET /admin 요청 정상 응답")
    @Test
    void getRequestAdmin() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(model().attribute("products", EXPECTED_PRODUCTS))
               .andExpect(view().name("admin"))
               .andExpect(status().isOk());
    }

    @DisplayName("POST /items 요청 정상 응답")
    @Test
    void postRequestItem() throws Exception {
        String content = objectMapper.writeValueAsString(new ItemRequest("레드북", 150000, "url"));
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
    void putRequestItem() throws JsonProcessingException {
        String content = objectMapper.writeValueAsString(new ItemRequest("레드북", 150000, "url"));
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
    void deleteRequestItem() {
        RestAssured.given()
                   .when()
                   .delete("/items/1")
                   .then()
                   .statusCode(HttpStatus.OK.value())
                   .header("Location", "/");
    }
}
