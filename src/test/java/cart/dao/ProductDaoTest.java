package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@DisplayName("ProductDaoTest 테스트")
@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductDaoTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    @BeforeEach
    void setup() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    void save는_상품을_저장한다() {
        Product product = Product.of("치킨",
                "https://img.freepik.com/free-photo/crispy-fried-chicken-on-a-plate-with-salad-and-carrot_1150-20212.jpg",
                19000);

        productDao.save(product);

        List<Product> allProducts = productDao.findAllProducts();
        assertThat(allProducts.get(0)).usingRecursiveComparison().ignoringFields("productId").isEqualTo(product);
    }

    @Test
    void findAllProducts는_저장된_모든_상품을_조회한다() {
        List<Product> allProducts = productDao.findAllProducts();
        assertThat(allProducts).isNotNull();
    }
}
