package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
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
        //given
        ProductEntity product = new ProductEntity(null, "치킨",
                "https://img.freepik.com/free-photo/crispy-fried-chicken-on-a-plate-with-salad-and-carrot_1150-20212.jpg",
                BigDecimal.valueOf(19000));

        assertThat(productDao.insert(product)).isEqualTo(1L);
    }

    @Test
    void findAllProducts는_저장된_모든_상품을_조회한다() {
        List<ProductEntity> allProducts = productDao.findAll();

        assertThat(allProducts).isNotNull();
    }

    @Test
    void findById는_해당Id를_가진__상품을_조회한다() {
        assertThat(productDao.findById(1L)).isEmpty();
    }


    @Test
    void updateProduct는_상품_정보를_수정한다() {
        //given
        ProductEntity savedProduct = new ProductEntity(null, "chicken", "imagelink", BigDecimal.valueOf(100));
        Long productId = productDao.insert(savedProduct);

        //when
        ProductEntity productToUpdate = new ProductEntity(productId, "chicken", "imagelink",BigDecimal.valueOf(19000));
        productDao.updateProduct(productToUpdate);

        //then
        ProductEntity updatedroduct = productDao.findById(productId).get();
        assertThat(updatedroduct).usingRecursiveComparison().isEqualTo(productToUpdate);
    }

    @Test
    void deleteProduct는_상품을_삭제한다() {
        //given
        ProductEntity product = new ProductEntity(null, "chicken", "imagelink", BigDecimal.valueOf(100));
        Long productId = productDao.insert(product);
        assertThat(productDao.findAll()).hasSize(1);

        //when
        productDao.deleteProduct(productId);

        //then
        assertThat(productDao.findAll()).isEmpty();
    }
}
