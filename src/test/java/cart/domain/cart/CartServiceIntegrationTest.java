package cart.domain.cart;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.domain.cart.dto.ProductResponse;
import cart.domain.member.Member;
import cart.domain.member.MemberDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@Transactional
public class CartServiceIntegrationTest {
    @Autowired
    CartService cartService;
    @Autowired
    MemberDao memberDao;

    @Autowired
    ProductDao productDao;

    @Test
    void 장바구니에_존재하는_유저의_상품을_조회한다() {
        String email = "cyh6099@gmail.com";
        Member member = memberDao.save(new Member(email, "Qwer1234"));
        Member memberNoHaveCartItem = memberDao.save(new Member("cyh6099@wooteco.com", "qwer1234"));

        List<Product> products = List.of(
                new Product("chicken", "image", 20000),
                new Product("pizza", "image", 20000)
        );


        Long chickenId = productDao.save(products.get(0));
        Long pizzaId = productDao.save(products.get(1));

        cartService.addProductToCart(email, chickenId);
        cartService.addProductToCart(email, pizzaId);

        List<ProductResponse> productResponses = cartService.findProductsByUserIdOnCart(member.getEmail());

        assertAll(
                () -> assertThat(cartService.findProductsByUserIdOnCart(member.getEmail())).hasSize(2),
                () -> assertThat(cartService.findProductsByUserIdOnCart(memberNoHaveCartItem.getEmail())).hasSize(0),
                () -> assertThat(productResponses.get(0).getName()).isEqualTo(products.get(0).getName())
        );
    }

    @Test
    void 장바구니_아이템을_삭제한다() {
        String email = "cyh6099@gmail.com";
        Member member = memberDao.save(new Member(email, "Qwer1234"));

        List<Product> products = List.of(
                new Product("chicken", "image", 20000),
                new Product("pizza", "image", 20000)
        );


        Long chickenId = productDao.save(products.get(0));
        Long pizzaId = productDao.save(products.get(1));

        Long chickenCartItemId = cartService.addProductToCart(email, chickenId);
        cartService.addProductToCart(email, pizzaId);

        Assertions.assertDoesNotThrow(() -> cartService.deleteCartItem(email, pizzaId));
    }
}
