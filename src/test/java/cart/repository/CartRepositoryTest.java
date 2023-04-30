package cart.repository;

import cart.entity.Cart;
import cart.entity.Member;
import cart.entity.Product;
import cart.repository.exception.CartPersistanceFailedException;
import cart.repository.exception.MemberPersistanceFailedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@ComponentScan(basePackages = "cart.repository")
class CartRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;

    @Test
    @DisplayName("주어진 Member 정보와 Product 정보로 Cart를 저장할 수 있다.")
    void save() throws MemberPersistanceFailedException, CartPersistanceFailedException {
        // given : 멤버와 상품을 저장한다.
        Member member = memberRepository.save(new Member("test@gmail.com", "password1234!"));
        Product product = productRepository.save(new Product("product", "product.png", new BigDecimal(4000)));

        // when
        Cart cart = cartRepository.save(new Cart(member.getEmail(), product.getId()));
        assertThat(cart.getMemberEmail()).isEqualTo(member.getEmail());
        assertThat(cart.getProductId()).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("존재하지 않는 멤버 이메일로 상품을 장바구니에 담을 수 없다.")
    void invalidEmail() throws CartPersistanceFailedException {
        Product product = productRepository.save(new Product("product", "product.png", new BigDecimal(4000)));

        assertThatThrownBy(() -> cartRepository.save(new Cart("invalid@gmail.com", product.getId())))
                .isInstanceOf(CartPersistanceFailedException.class)
                .hasMessage("존재하지 않는 멤버 또는 상품으로 장바구니에 담을 수 없습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 상품을 장바구니에 담을 수 없다.")
    void invalidProduct() throws MemberPersistanceFailedException {
        Member member = memberRepository.save(new Member("test@gmail.com", "password1234!"));

        assertThatThrownBy(() -> cartRepository.save(new Cart(member.getEmail(), 99L)))
                .isInstanceOf(CartPersistanceFailedException.class)
                .hasMessage("존재하지 않는 멤버 또는 상품으로 장바구니에 담을 수 없습니다.");
    }

    @Test
    @DisplayName("장바구니에 담은 상품이 삭제되면 장바구니 내역도 함께 사라진다.")
    void deleteProduct() throws MemberPersistanceFailedException, CartPersistanceFailedException {
        // given : 장바구니에 하나가 저장되어 있다.
        Member member = memberRepository.save(new Member("test@gmail.com", "password1234!"));
        Product product = productRepository.save(new Product("product", "product.png", new BigDecimal(4000)));
        cartRepository.save(new Cart(member.getEmail(), product.getId()));
        assertThat(cartRepository.findAllByMemberEmail(member.getEmail())).hasSize(1);

        // when : 상품을 삭제한다.
        productRepository.deleteById(product.getId());

        // then : 삭제된 상품에 대한 장바구니 내역도 사라져있어야 한다.
        assertThat(cartRepository.findAllByMemberEmail(member.getEmail())).hasSize(0);
    }

    @Test
    @DisplayName("같은 멤버가 같은 상품을 두 번 담을 수는 없다.")
    void duplicatingCart() throws CartPersistanceFailedException, MemberPersistanceFailedException {
        // given
        Member member = memberRepository.save(new Member("test@gmail.com", "password1234!"));
        Product product = productRepository.save(new Product("product", "product.png", new BigDecimal(4000)));
        cartRepository.save(new Cart(member.getEmail(), product.getId()));

        // when & then
        assertThatThrownBy(() -> cartRepository.save(new Cart(member.getEmail(), product.getId())))
                .isInstanceOf(CartPersistanceFailedException.class)
                .hasMessage("동일한 회원이 동일한 상품을 중복해서 장바구니에 담을 수 없습니다.");
    }

    @Test
    @DisplayName("주어진 이메일과 상품 아이디로 저장된 장바구니를 삭제할 수 있다.")
    void deleteByEmailAndId() throws MemberPersistanceFailedException, CartPersistanceFailedException {
        // given
        Member member = memberRepository.save(new Member("test@gmail.com", "password1234!"));
        Product product = productRepository.save(new Product("product", "product.png", new BigDecimal(4000)));
        cartRepository.save(new Cart(member.getEmail(), product.getId()));
        assertThat(cartRepository.findAllByMemberEmail(member.getEmail())).hasSize(1);

        // when
        cartRepository.deleteByMemberEmailAndProductId(member.getEmail(), product.getId());
        assertThat(cartRepository.findAllByMemberEmail(member.getEmail())).hasSize(0);
    }

    @Test
    @DisplayName("존재하지 않는 상품 아이디로는 장바구니를 삭제할 수 없다.")
    void invalidDeletingWithId() throws MemberPersistanceFailedException, CartPersistanceFailedException {
        // given
        Member member = memberRepository.save(new Member("test@gmail.com", "password1234!"));
        Product product = productRepository.save(new Product("product", "product.png", new BigDecimal(4000)));
        cartRepository.save(new Cart(member.getEmail(), product.getId()));
        assertThat(cartRepository.findAllByMemberEmail(member.getEmail())).hasSize(1);

        // when
        assertThatThrownBy(() -> cartRepository.deleteByMemberEmailAndProductId("invalid@gmail.com", product.getId()))
                .isInstanceOf(CartPersistanceFailedException.class)
                .hasMessage("삭제할 대상이 데이터베이스에 존재하지 않습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 이메일로는 장바구니를 삭제할 수 없다.")
    void invalidDeletingWithEmail() throws MemberPersistanceFailedException, CartPersistanceFailedException {
        // given
        Member member = memberRepository.save(new Member("test@gmail.com", "password1234!"));
        Product product = productRepository.save(new Product("product", "product.png", new BigDecimal(4000)));
        cartRepository.save(new Cart(member.getEmail(), product.getId()));
        assertThat(cartRepository.findAllByMemberEmail(member.getEmail())).hasSize(1);

        // when
        assertThatThrownBy(() -> cartRepository.deleteByMemberEmailAndProductId(member.getEmail(), 2L))
                .isInstanceOf(CartPersistanceFailedException.class)
                .hasMessage("삭제할 대상이 데이터베이스에 존재하지 않습니다.");
    }
}