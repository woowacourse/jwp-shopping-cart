package cart.repository.cartproduct;

import cart.domain.cart.Cart;
import cart.domain.cart.CartId;
import cart.domain.cartproduct.CartProduct;
import cart.domain.cartproduct.CartProductId;
import cart.domain.member.Member;
import cart.domain.member.MemberId;
import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.repository.cart.CartJdbcRepository;
import cart.repository.cart.CartRepository;
import cart.repository.member.MemberJdbcRepository;
import cart.repository.member.MemberRepository;
import cart.repository.product.ProductJdbcRepository;
import cart.repository.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
class CartProductJdbcRepositoryTest {
    private static final Member MEMBER = new Member("헤나", "test@test.com", "test");
    private static final Product CHICKEN = new Product("치킨", 10000, "image-url");

    @Autowired
    JdbcTemplate jdbcTemplate;

    CartProductRepository cartProductRepository;
    CartRepository cartRepository;
    ProductRepository productRepository;
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        cartProductRepository = new CartProductJdbcRepository(jdbcTemplate);
        cartRepository = new CartJdbcRepository(jdbcTemplate);
        productRepository = new ProductJdbcRepository(jdbcTemplate);
        memberRepository = new MemberJdbcRepository(jdbcTemplate);
    }

    @DisplayName("장바구니에 상품을 저장한다.")
    @Test
    void save() {
        // given
        final MemberId memberId = memberRepository.save(MEMBER);
        final CartId cartId = cartRepository.save(new Cart(memberId));
        final ProductId productId = productRepository.save(CHICKEN);

        // when
        final CartProductId saveId = cartProductRepository.save(new CartProduct(cartId, productId));

        final Optional<CartProduct> maybeCartProduct = cartProductRepository.findByCartProductId(saveId);

        assertThat(maybeCartProduct).isPresent();
        final CartProduct cartProduct = maybeCartProduct.get();

        // then
        assertThat(cartProduct)
                .usingRecursiveComparison()
                .isEqualTo(new CartProduct(saveId, cartId, productId));
    }
}
