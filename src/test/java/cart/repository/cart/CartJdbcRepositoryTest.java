package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartId;
import cart.domain.cartproduct.CartProduct;
import cart.domain.member.Member;
import cart.domain.member.MemberId;
import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.repository.cartproduct.CartProductJdbcRepository;
import cart.repository.cartproduct.CartProductRepository;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class CartJdbcRepositoryTest {
    private static final Member MEMBER = new Member("헤나", "test@test.com", "test");
    private static final Product CHICKEN = new Product("치킨", 10000, "image-url");
    private static final Product PIZZA = new Product("피자", 20000, "image2-url");

    @Autowired
    JdbcTemplate jdbcTemplate;

    CartRepository cartRepository;
    MemberRepository memberRepository;
    CartProductRepository cartProductRepository;
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        cartRepository = new CartJdbcRepository(jdbcTemplate);
        memberRepository = new MemberJdbcRepository(jdbcTemplate);
        cartProductRepository = new CartProductJdbcRepository(jdbcTemplate);
        productRepository = new ProductJdbcRepository(jdbcTemplate);
    }

    @DisplayName("장바구니를 저장한다.")
    @Test
    void save() {
        // given
        final MemberId memberId = memberRepository.save(MEMBER);

        // when
        final CartId saveId = cartRepository.save(new Cart(memberId));

        // then
        assertThat(saveId).isEqualTo(saveId);
    }

    @DisplayName("특정 회원의 장바구니 전체 상품 목록을 불러온다.")
    @Test
    void joinProductsByMemberId() {
        // given
        final MemberId memberId = memberRepository.save(MEMBER);
        final CartId cartId = cartRepository.save(new Cart(memberId));

        final ProductId productChickenId = productRepository.save(CHICKEN);
        final ProductId productPizzaId = productRepository.save(PIZZA);

        cartProductRepository.save(new CartProduct(cartId, productChickenId));
        cartProductRepository.save(new CartProduct(cartId, productPizzaId));

        // when
        final Optional<Cart> maybeCart = cartRepository.joinProductsByMemberId(memberId);
        assertThat(maybeCart).isPresent();

        final List<Product> findProducts = maybeCart.get().getProducts();

        // then
        assertThat(findProducts)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        new Product(productChickenId, "치킨", 10000, "image-url"),
                        new Product(productPizzaId, "피자", 20000, "image2-url")
                );
    }
}
