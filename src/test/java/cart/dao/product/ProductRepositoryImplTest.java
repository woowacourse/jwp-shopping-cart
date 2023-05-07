package cart.dao.product;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.product.TestFixture;
import cart.product.domain.ImageUrl;
import cart.product.domain.Product;
import cart.product.domain.ProductCategory;
import cart.product.domain.ProductId;
import cart.product.domain.ProductName;
import cart.product.domain.ProductPrice;
import cart.product.persistence.ProductDao;
import cart.product.persistence.ProductRepositoryImpl;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@Import({ProductRepositoryImpl.class, ProductDao.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class ProductRepositoryImplTest {

    @Autowired
    private ProductRepositoryImpl productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("상품 전체 조회 테스트")
    @Test
    void findAll() {
        jdbcTemplate.update("INSERT INTO product (name, price) VALUES ('Chicken', 18000), ('Pizza', 24000)");

        final List<Product> allProducts = productRepository.findAll();

        assertThat(allProducts).hasSize(2);
        assertThat(allProducts).extractingResultOf("getName")
                .contains("Chicken", "Pizza");
    }

    @DisplayName("상품 저장 테스트")
    @Test
    void insert() {
        final Product pizza = TestFixture.PIZZA;

        productRepository.save(pizza);

        final List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(1);
        assertThat(allProducts).extractingResultOf("getName")
                .contains("Pizza");
    }

    @DisplayName("단일 상품 삭제 테스트")
    @Test
    void deleteById() {
        final Product pizza = TestFixture.PIZZA;
        final Long savedId = productRepository.save(pizza);
        assertThat(productRepository.findAll()).hasSize(1);

        productRepository.deleteById(savedId);

        final List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(0);
    }

    @DisplayName("상품 수정 테스트")
    @Test
    void update() {
        final Product pizza = TestFixture.PIZZA;
        final Long savedId = productRepository.save(pizza);

        final Product updatedProduct = new Product(
                ProductName.from("Chicken"),
                ProductPrice.from(20_000),
                ProductCategory.FOOD,
                ImageUrl.from("chicken.com"),
                ProductId.from(savedId)
        );

        productRepository.update(updatedProduct);

        final List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(1);
        final Product savedProduct = allProducts.get(0);
        assertThat(savedProduct.getProductId()).isEqualTo(savedId);
        assertThat(savedProduct.getName()).isEqualTo("Chicken");
        assertThat(savedProduct.getPrice()).isEqualTo(BigDecimal.valueOf(20_000));
        assertThat(savedProduct.getCategory()).isEqualTo(ProductCategory.FOOD);
        assertThat(savedProduct.getImageUrl()).isEqualTo("chicken.com");
    }
}
