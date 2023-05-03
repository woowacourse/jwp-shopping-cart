package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.cart.Item;
import cart.domain.cart.ItemEntity;
import cart.domain.product.ProductEntity;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:test.sql")
class CartServiceTest {

    @LocalServerPort
    private int port;
    @Autowired
    private CartService cartService;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void 상품_조회() {
        final int userId = 1;
        final List<ProductEntity> products = cartService.findAll(userId);

        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    void 상품_등록() {
        final int expectedId = 4;
        final int userId = 2;
        final int productId = 1;

        final Item item = new Item(userId, productId);
        final ItemEntity result = cartService.insert(item);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(expectedId),
                () -> assertThat(result.getUserId()).isEqualTo(userId),
                () -> assertThat(result.getProductId()).isEqualTo(productId)
        );
    }

    @Test
    void 상품_삭제() {
        final int userId = 1;
        final int itemId = 1;
        final int expectedSize = 2;

        cartService.delete(itemId);

        final List<ProductEntity> remainProducts = cartService.findAll(userId);
        assertThat(remainProducts.size()).isEqualTo(expectedSize);
    }
}
