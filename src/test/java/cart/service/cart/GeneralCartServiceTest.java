package cart.service.cart;

import cart.config.ServiceTestConfig;
import cart.config.auth.AuthMember;
import cart.domain.cart.CartId;
import cart.domain.member.Member;
import cart.domain.member.MemberId;
import cart.domain.product.Product;
import cart.domain.product.ProductId;
import cart.repository.cart.CartJdbcRepository;
import cart.repository.member.MemberJdbcRepository;
import cart.repository.member.MemberRepository;
import cart.repository.product.ProductJdbcRepository;
import cart.repository.product.ProductRepository;
import cart.service.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralCartServiceTest extends ServiceTestConfig {
    private static final Member MEMBER = new Member("헤나", "test@test.com", "test");
    private static final Product CHICKEN = new Product("치킨", 10000, "image-url");

    GeneralCartService generalCartService;
    MemberRepository memberRepository;
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemberJdbcRepository(jdbcTemplate);
        productRepository = new ProductJdbcRepository(jdbcTemplate);
        generalCartService = new GeneralCartService(new CartJdbcRepository(jdbcTemplate), productRepository);
    }

    @DisplayName("장바구니에 상품을 추가한다.")
    @Test
    void addProduct() {
        // given
        final MemberId memberId = memberRepository.save(MEMBER);
        final ProductId productId = productRepository.save(CHICKEN);
        final AuthMember authMember = new AuthMember(memberId, "test@test.com");

        // when
        final CartId saveCartId = generalCartService.addProduct(authMember, productId);

        // then
        assertThat(saveCartId).isNotNull();
    }

    @DisplayName("장바구니에 상품을 삭제한다.")
    @Test
    void removeProduct() {
        // given
        final MemberId memberId = memberRepository.save(MEMBER);
        final ProductId productId = productRepository.save(CHICKEN);

        final AuthMember authMember = new AuthMember(memberId, "test@test.com");
        generalCartService.addProduct(authMember, productId);

        // when
        final int deleteCount = generalCartService.removeProduct(authMember, productId);

        // then
        assertThat(deleteCount).isOne();
    }

    @DisplayName("회원의 장바구니 상품 목록을 조회한다.")
    @Test
    void findAllByMember() {
        // given
        final MemberId memberId = memberRepository.save(MEMBER);
        final ProductId productId = productRepository.save(CHICKEN);

        final AuthMember authMember = new AuthMember(memberId, "test@test.com");
        generalCartService.addProduct(authMember, productId);

        // when
        final List<ProductResponse> findProducts = generalCartService.findAllByMember(authMember);

        // then
        assertThat(findProducts)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrder(
                        new ProductResponse(0L, "치킨", 10000, "image-url")
                );
    }
}
