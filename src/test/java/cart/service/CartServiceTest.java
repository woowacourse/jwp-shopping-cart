package cart.service;

import cart.dto.application.ItemEntityDto;
import cart.dto.application.MemberDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        final MemberDto member = new MemberDto("user1@email.com", "password1");

        final List<ItemEntityDto> products = cartService.findAll(member);

        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    void 상품_등록() {
        final int productId = 1;
        final MemberDto member = new MemberDto("user2@email.com", "password2");

        cartService.insert(member, productId);

        assertThat(cartService.findAll(member).size()).isEqualTo(1);
    }

    @Test
    void 상품_삭제() {
        final int itemId = 1;
        final MemberDto member = new MemberDto("user1@email.com", "password1");

        cartService.delete(itemId, member);

        assertThat(cartService.findAll(member).size()).isEqualTo(2);
    }
}
