package cart.service;

import static cart.MemberFixture.TEST_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;

import cart.ProductFixture;
import cart.entity.Product;
import cart.repository.JdbcMemberRepository;
import cart.repository.JdbcShoppingCartRepository;
import cart.service.dto.MemberInfo;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import({JdbcMemberRepository.class, JdbcShoppingCartRepository.class, ShoppingCartService.class})
class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService shoppingCartService;

    //TODO : service에서 테스트할 때 통합으로 할지 mock으로 할지 고민해보기
    @Test
    @DisplayName("email과 password로 user를 판별하고, 해당 user가 장바구니에 담은 product들 조회")
    public void findAllTest() {
        final MemberInfo memberinfo = new MemberInfo(TEST_MEMBER.getEmail(), TEST_MEMBER.getPassword());

        final List<Product> allProduct = shoppingCartService.findAllProduct(memberinfo);

        assertThat(allProduct)
                .containsExactly(ProductFixture.PRODUCT_ENTITY1);
    }
}
