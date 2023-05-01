package cart.repository;

import static cart.ProductFixture.PRODUCT_ENTITY1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.entity.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(JdbcProductRepository.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("모든 상품을 조회하는 기능 테스트")
    public void findAll() {
        final List<Product> all = productRepository.findAll();
        assertThat(all).hasSize(4);
    }

    @Test
    @DisplayName("ID로 상품을 조회하는 기능 테스트")
    public void findById() {
        final Optional<Product> product = productRepository.findById(PRODUCT_ENTITY1.getId());

        assertAll(
                () -> assertThat(product).isPresent(),
                () -> assertThat(product.get().getName()).isEqualTo(PRODUCT_ENTITY1.getName())
        );
    }

    @Test
    @DisplayName("상품을 저장하는 기능 테스트")
    public void createProduct() {
        final Product product = new Product("TestProduct", "a.png", 2000);
        final Product savedProduct = productRepository.save(product);

        assertAll(
                () -> assertThat(savedProduct.getId()).isNotNull(),
                () -> assertThat(savedProduct.getName()).isEqualTo("TestProduct"),
                () -> assertThat(savedProduct.getImageUrl()).isEqualTo("a.png"),
                () -> assertThat(savedProduct.getPrice()).isEqualTo(2000)
        );
    }

    @Test
    @DisplayName("상품을 업데이트하는 기능 테스트")
    public void updateProduct() {
        //given
        final Product product = productRepository.findById(PRODUCT_ENTITY1.getId())
                .orElseThrow(IllegalArgumentException::new);
        assertThat(product.getName()).isEqualTo(PRODUCT_ENTITY1.getName());

        //when
        final Product changedProduct = new Product(PRODUCT_ENTITY1.getId(), "CHANGED", product.getImageUrl(),
                product.getPrice());
        productRepository.update(changedProduct);

        //then
        final Product updatedProduct = productRepository.findById(PRODUCT_ENTITY1.getId())
                .orElseThrow(IllegalArgumentException::new);
        assertThat(updatedProduct.getName()).isEqualTo("CHANGED");
    }

    @Test
    @DisplayName("상품을 삭제하는 기능 테스트")
    public void deleteById() {
        assertThat(productRepository.findAll()).hasSize(4);

        productRepository.deleteById(PRODUCT_ENTITY1.getId());

        assertThat(productRepository.findAll()).hasSize(3);
    }
}
