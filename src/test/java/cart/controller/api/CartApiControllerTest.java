package cart.controller.api;

import cart.config.AuthenticationPrincipal;
import cart.controller.dto.MemberRequest;
import cart.controller.dto.ProductRequest;
import cart.service.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static io.restassured.RestAssured.given;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("CartApiController 는")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CartService cartService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @PostMapping("{productId}")
    public ResponseEntity<Long> addCart(
            @PathVariable Long productId,
            @AuthenticationPrincipal MemberRequest memberRequest
    ) {
        Long id = cartService.addCart(productId, memberRequest);
        return ResponseEntity.ok(id);
    }

    @Test
    public void 상품을_등록한다() {
        final ProductRequest request = new ProductRequest("채채", "https://채채.com", 1000);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(toJson(request))
                .when()
                .post("/products")
                .then()
                .log().all()
                .statusCode(201);
    }

    private String toJson(final ProductRequest request) {
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
