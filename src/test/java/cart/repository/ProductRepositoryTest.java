package cart.repository;

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

    private static final Product PRODUCT_ENTITY = new Product("테스트", "테스트URL.png", 4000);
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("모든 상품을 조회하는 기능 테스트")
    public void findAll() {
        productRepository.save(PRODUCT_ENTITY);
        productRepository.save(PRODUCT_ENTITY);
        productRepository.save(PRODUCT_ENTITY);
        productRepository.save(PRODUCT_ENTITY);

        final List<Product> all = productRepository.findAll();
        assertThat(all).hasSize(4);
    }

    @Test
    @DisplayName("ID로 상품을 조회하는 기능 테스트")
    public void findById() {
        final Product savedProduct = productRepository.save(PRODUCT_ENTITY);

        final Optional<Product> product = productRepository.findById(savedProduct.getId());

        assertAll(
                () -> assertThat(product).isNotEmpty(),
                () -> assertThat(product.get().getName()).isEqualTo(PRODUCT_ENTITY.getName())
        );
    }

    @Test
    @DisplayName("상품을 저장하는 기능 테스트")
    public void createProduct() {
        final Product savedProduct = productRepository.save(PRODUCT_ENTITY);

        assertAll(
                () -> assertThat(savedProduct.getId()).isNotNull(),
                () -> assertThat(savedProduct.getName()).isEqualTo(PRODUCT_ENTITY.getName()),
                () -> assertThat(savedProduct.getImageUrl()).isEqualTo(PRODUCT_ENTITY.getImageUrl()),
                () -> assertThat(savedProduct.getPrice()).isEqualTo(PRODUCT_ENTITY.getPrice())
        );
    }

    @Test
    @DisplayName("상품을 업데이트하는 기능 테스트")
    public void updateProduct() {
        final Product savedProduct = productRepository.save(PRODUCT_ENTITY);
        //given
        final Product product = productRepository.findById(savedProduct.getId())
                .orElseThrow(IllegalArgumentException::new);
        assertThat(product.getName()).isEqualTo(PRODUCT_ENTITY.getName());

        //when
        final Product changedProduct = new Product(savedProduct.getId(), "CHANGED", product.getImageUrl(),
                product.getPrice());
        productRepository.update(changedProduct);

        //then
        final Product updatedProduct = productRepository.findById(savedProduct.getId())
                .orElseThrow(IllegalArgumentException::new);
        assertThat(updatedProduct.getName()).isEqualTo("CHANGED");
    }

    @Test
    @DisplayName("상품을 삭제하는 기능 테스트")
    public void deleteById() {
        final Product savedProduct = productRepository.save(PRODUCT_ENTITY);
        assertThat(productRepository.findAll()).hasSize(1);

        productRepository.deleteById(savedProduct.getId());

        assertThat(productRepository.findAll()).hasSize(0);
    }
}
