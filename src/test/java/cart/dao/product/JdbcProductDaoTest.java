package cart.dao.product;

import cart.domain.product.Product;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static cart.DummyData.DUMMY_PRODUCT_ONE;
import static cart.DummyData.INITIAL_PRODUCT_ONE;
import static cart.DummyData.INITIAL_PRODUCT_TWO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Import(JdbcProductDao.class)
@Sql("/reset-product-data.sql")
@JdbcTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class JdbcProductDaoTest {

    @Autowired
    JdbcProductDao productDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void 모든_상품_데이터를_반환하는지_확인한다() {
        final List<Product> productEntities = productDao.findAll();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(productEntities.size()).isEqualTo(2);
            softAssertions.assertThat(productEntities.get(0).getId()).isEqualTo(INITIAL_PRODUCT_ONE.getId().intValue());
            softAssertions.assertThat(productEntities.get(0).getName()).isEqualTo(INITIAL_PRODUCT_ONE.getName());
            softAssertions.assertThat(productEntities.get(0).getImageUrl()).isEqualTo(INITIAL_PRODUCT_ONE.getImageUrl());
            softAssertions.assertThat(productEntities.get(0).getPrice()).isEqualTo(INITIAL_PRODUCT_ONE.getPrice());
            softAssertions.assertThat(productEntities.get(1).getId()).isEqualTo(INITIAL_PRODUCT_TWO.getId().intValue());
            softAssertions.assertThat(productEntities.get(1).getName()).isEqualTo(INITIAL_PRODUCT_TWO.getName());
            softAssertions.assertThat(productEntities.get(1).getImageUrl()).isEqualTo(INITIAL_PRODUCT_TWO.getImageUrl());
            softAssertions.assertThat(productEntities.get(1).getPrice()).isEqualTo(INITIAL_PRODUCT_TWO.getPrice());
        });
    }

    @Test
    void 상품_등록이_되는지_확인한다() {
        assertDoesNotThrow(() -> productDao.insert(DUMMY_PRODUCT_ONE));
    }

    @Test
    void 상품_수정이_되는지_확인한다() {
        final Long id = 1L;

        assertDoesNotThrow(() -> productDao.updateById(id, DUMMY_PRODUCT_ONE));
    }

    @Test
    void 상품_삭제가_되는지_확인한다() {
        final Long id = 1L;

        assertDoesNotThrow(() -> productDao.deleteById(id));
    }
}
