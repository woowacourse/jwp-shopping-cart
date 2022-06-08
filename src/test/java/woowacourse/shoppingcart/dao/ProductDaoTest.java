package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:testSchema.sql", "classpath:testData.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        productDao = new ProductDao(jdbcTemplate);
    }


    @Test
    void 상품의_id으로_조회할_경우_객체를_리턴하는지_확인() {

        // given
        Long id = 1L;

        // when
        final Product actual = productDao.findProductById(id);

        Product expected = new Product(1L, "맛있는 치킨", 100, "https://www.naver.com");

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void 상품의_id가_존재하지않는_경우() {
        // given
        Long id = 200L;

        // then
        assertThat(productDao.isValidId(id)).isFalse();
    }

    @Test
    void 전체_상품을_가져오는_경우() {
        //given
        List<Product> products = productDao.findProducts();

        assertThat(products.size()).isEqualTo(13);
    }

    @Test
    void 전체_상품의_갯수를_가져오는_경우() {
        Long total = productDao.countTotal();

        assertThat(total).isEqualTo(13L);
    }

    @Test
    void 전체_상품을_12개로_끊어서_가져오는_경우() {
        List<Product> pagedProducts1 = productDao.findProductsByPage(1, 12);
        List<Product> pagedProducts2 = productDao.findProductsByPage(2, 12);

        assertAll(() -> assertThat(pagedProducts1.size()).isEqualTo(12),
                () -> assertThat(pagedProducts2.size()).isEqualTo(1));
    }
}
