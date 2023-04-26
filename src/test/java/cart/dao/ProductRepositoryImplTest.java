package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.product.ImageUrl;
import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.domain.product.ProductName;
import cart.domain.product.ProductPrice;
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

        List<Product> allProducts = productRepository.findAll();

        assertThat(allProducts).hasSize(2);
        assertThat(allProducts).extractingResultOf("getName")
                .contains("Chicken", "Pizza");
    }

    @DisplayName("상품 저장 테스트")
    @Test
    void insert() {
        Product pizza = new Product(
                ProductName.from("Pizza"),
                ProductPrice.from(18_000),
                ProductCategory.FOOD,
                ImageUrl.from("www.naver.com")
        );

        productRepository.save(pizza);

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(1);
        assertThat(allProducts).extractingResultOf("getName")
                .contains("Pizza");
    }
}
