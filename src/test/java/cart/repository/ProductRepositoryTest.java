package cart.repository;

import cart.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Import(JdbcProductRepository.class)
class ProductRepositoryTest {

    private static final Product PRODUCT = new Product("테스트", "테스트URL.png", new BigDecimal(4000));
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("모든 상품을 조회하는 기능 테스트")
    public void findAll() {
        productRepository.save(PRODUCT);
        productRepository.save(PRODUCT);
        productRepository.save(PRODUCT);
        productRepository.save(PRODUCT);

        final List<Product> all = productRepository.findAll();
        assertThat(all).hasSize(4);
    }

    @Test
    @DisplayName("ID로 상품을 조회하는 기능 테스트")
    public void findById() {
        final Product savedProduct = productRepository.save(PRODUCT);

        final Product product = productRepository.findById(savedProduct.getId());

        assertThat(product.getName()).isEqualTo(PRODUCT.getName());
    }

    @Test
    @DisplayName("상품을 저장하는 기능 테스트")
    public void createProduct() {
        final Product savedProduct = productRepository.save(PRODUCT);

        assertAll(
                () -> assertThat(savedProduct.getId()).isNotNull(),
                () -> assertThat(savedProduct.getName()).isEqualTo(PRODUCT.getName()),
                () -> assertThat(savedProduct.getImageUrl()).isEqualTo(PRODUCT.getImageUrl()),
                () -> assertThat(savedProduct.getPrice()).isEqualTo(PRODUCT.getPrice())
        );
    }

    @Test
    @DisplayName("상품을 업데이트하는 기능 테스트")
    public void updateProduct() {
        final Product savedProduct = productRepository.save(PRODUCT);
        //given
        final Product product = productRepository.findById(savedProduct.getId());
        assertThat(product.getName()).isEqualTo(PRODUCT.getName());

        //when
        final Product changedProduct = new Product(savedProduct.getId(), "CHANGED", product.getImageUrl(),
                product.getPrice());
        productRepository.update(changedProduct);

        //then
        final Product updatedProduct = productRepository.findById(savedProduct.getId());
        assertThat(updatedProduct.getName()).isEqualTo("CHANGED");
    }

    @Test
    @DisplayName("상품을 삭제하는 기능 테스트")
    public void deleteById() {
        final Product savedProduct = productRepository.save(PRODUCT);
        assertThat(productRepository.findAll()).hasSize(1);

        productRepository.deleteById(savedProduct.getId());

        assertThat(productRepository.findAll()).hasSize(0);
    }
}
