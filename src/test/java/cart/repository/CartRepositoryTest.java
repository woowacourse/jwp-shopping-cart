package cart.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.domain.cart.Cart;
import cart.domain.member.Member;
import cart.domain.member.MemberId;
import cart.domain.product.Product;
import cart.domain.product.ProductId;

@SpringBootTest
class CartRepositoryTest {

	private static final Member MEMBER = new Member("kiara", "email@email.com", "pw");
	private static final Product CHICKEN = new Product("치킨", 10000, "치킨이미지");
	private static final Product PIZZA = new Product("피자", 20000, "피자이미지");

	CartRepository cartRepository;
	MemberRepository memberRepository;
	ProductRepository productRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		cartRepository = new CartRepository(jdbcTemplate);
		memberRepository = new MemberRepository(jdbcTemplate);
		productRepository = new ProductRepository(jdbcTemplate);
	}

	@DisplayName("장바구니 상품 저장 및 조회 테스트")
	@Test
	void insert() {
		// given
		final MemberId memberId = memberRepository.insert(MEMBER);
		final ProductId chickenId = productRepository.insert(CHICKEN);
		final ProductId pizzaId = productRepository.insert(PIZZA);

		// when
		cartRepository.insert(memberId, chickenId);
		cartRepository.insert(memberId, pizzaId);

		final List<Cart> carts = cartRepository.findAllByMemberId(memberId);

		// then
		Assertions.assertThat(carts.size()).isEqualTo(2);
	}
}
