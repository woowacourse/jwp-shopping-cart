package cart.repository;

import static cart.ProductFixture.PRODUCT_ENTITY2;
import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.Product;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(JdbcShoppingCartRepository.class)
class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    @DisplayName("memberId와 productId로 장바구니에 product를 추가하는 기능 테스트")
    void addProduct() {
        shoppingCartRepository.addProduct(1L, 2L);

        final List<Product> allProduct = shoppingCartRepository.findAllProduct(1L);

        assertThat(allProduct)
                .contains(PRODUCT_ENTITY2);
    }

    @Test
    @DisplayName("cartid로 장바구니에 있는 product를 삭제하는 기능 테스트")
    void removeProduct() {
        final List<Product> allProduct = shoppingCartRepository.findAllProduct(1L);
        assertThat(allProduct).hasSize(1);

        shoppingCartRepository.removeProduct(1L);

        final List<Product> afterRemoveProduct = shoppingCartRepository.findAllProduct(1L);
        assertThat(afterRemoveProduct).hasSize(0);
    }

    @Test
    @DisplayName("memberId로 모든 프로덕트 정보를 찾는 기능 테스트")
    void findAllProduct() {
        final List<Product> allProduct = shoppingCartRepository.findAllProduct(1L);

        assertThat(allProduct).hasSize(1);
    }
}
