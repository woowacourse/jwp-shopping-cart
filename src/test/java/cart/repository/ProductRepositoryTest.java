package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.entity.Product;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql({"/test-fixture.sql"})
class ProductRepositoryTest {

    private static final Product PRODUCT = new Product("테스트", "테스트URL", 4000);
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("모든 상품을 조회하는 기능 테스트")
    public void findAll() {
        final List<Product> all = productRepository.findAll();

        assertThat(all).hasSize(4);
    }

    @Test
    @DisplayName("상품을 저장하는 기능 테스트")
    public void createProduct() {
        final Product savedProduct = productRepository.save(PRODUCT);

        assertAll(
                () -> assertThat(savedProduct.getId()).isNotNull(),
                () -> assertThat(savedProduct.getName()).isEqualTo(PRODUCT.getName()),
                () -> assertThat(savedProduct.getImage()).isEqualTo(PRODUCT.getImage()),
                () -> assertThat(savedProduct.getPrice()).isEqualTo(PRODUCT.getPrice())
        );
    }

    @Test
    @DisplayName("상품을 업데이트하는 기능 테스트")
    public void updateProduct() {
        final Product product = productRepository.findById(1L)
                .orElseThrow(IllegalArgumentException::new);
        assertThat(product.getName()).isEqualTo("TEST1");

        final Product changedProduct = new Product(1L, "CHANGED", product.getImage(), product.getPrice());
        productRepository.save(changedProduct);
        assertThat(product.getName()).isEqualTo("CHANGED");
    }

    @Test
    @DisplayName("상품을 삭제하는 기능 테스트")
    public void deleteById() {
        assertThat(productRepository.findAll()).hasSize(4);

        productRepository.deleteById(1L);

        assertThat(productRepository.findAll()).hasSize(3);
    }
}
