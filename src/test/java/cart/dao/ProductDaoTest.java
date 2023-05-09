package cart.dao;

import cart.domain.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        //given
        Product product = new Product(null, "치킨",
                "https://img.freepik.com/free-photo/crispy-fried-chicken-on-a-plate-with-salad-and-carrot_1150-20212.jpg", 19000L);

        //when
        productDao.save(product);

        //then

        List<Product> allProducts = productDao.findAllProducts();
        assertThat(allProducts.get(0))
                .usingRecursiveComparison()
                .ignoringFields("productId")
                .isEqualTo(product);
    }

    @Test
    void findAllProducts는_저장된_모든_상품을_조회한다() {
        List<Product> allProducts = productDao.findAllProducts();
        assertThat(allProducts).isNotNull();
    }

    @Test
    void updateProduct는_상품_정보를_수정한다() {
        long productId = productDao.save(new Product(null, "chicken", "imagelink", 1000L));

        Product product = new Product(productId, "chicken", "imagelink", 19000L);
        productDao.updateProduct(product);

        Product savedProduct = productDao.findProductById(productId);
        assertThat(savedProduct).usingRecursiveComparison().isEqualTo(product);
    }

    @Test
    void deleteProduct는_상품을_삭제한다() {
        long productId = productDao.save(new Product(null, "chicken", "imagelink", 1000L));
        assertThat(productDao.findAllProducts()).hasSize(1);

        productDao.deleteProduct(productId);
        assertThat(productDao.findAllProducts()).isEmpty();
    }
}
