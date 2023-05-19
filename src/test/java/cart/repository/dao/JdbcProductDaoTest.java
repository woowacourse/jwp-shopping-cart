package cart.repository.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.repository.entity.ProductEntity;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@JdbcTest
public class JdbcProductDaoTest {

    private static final int PIZZA_INDEX = 0;
    private static final int CHICKEN_INDEX = 1;
    private static final int JOKBAL_INDEX = 2;
    private static final int SUSHI_INDEX = 3;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new JdbcProductDao(jdbcTemplate);
    }

    @Test
    void 모든_상품을_조회한다() {
        final List<ProductEntity> products = productDao.findAll();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(products.size()).isEqualTo(4);
        softAssertions.assertThat(products.get(PIZZA_INDEX).getName()).isEqualTo("피자");
        softAssertions.assertThat(products.get(CHICKEN_INDEX).getName()).isEqualTo("치킨");
        softAssertions.assertThat(products.get(JOKBAL_INDEX).getName()).isEqualTo("족발");
        softAssertions.assertThat(products.get(SUSHI_INDEX).getName()).isEqualTo("초밥");
        softAssertions.assertAll();
    }

    @Test
    void 상품을_업데이트한다() {
        final ProductEntity updatedProductEntity = new ProductEntity(1L, "감자", "gamja.png", 2000);
        productDao.update(updatedProductEntity);

        final List<ProductEntity> products = productDao.findAll();
        assertThat(products.get(PIZZA_INDEX).getName()).isEqualTo(updatedProductEntity.getName());
    }

    @Test
    void 상품을_삭제한다() {
        productDao.delete(1L);
        productDao.delete(2L);
        productDao.delete(3L);
        productDao.delete(4L);

        assertThat(productDao.findAll()).isEmpty();
    }
}
