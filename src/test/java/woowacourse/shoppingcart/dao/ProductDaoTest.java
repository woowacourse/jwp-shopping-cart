package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.entity.ProductEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings({"NonAsciiCharacters"})
class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    void 상품_저장() {
        // given

        // when
        Long id = productDao.save(new ProductEntity("초콜렛", 1_000, "www.test.com"));

        // then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    void 상품_ID로_상품_검색() {
        // given
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "www.test.com";
        Long id = productDao.save(new ProductEntity(name, price, imageUrl));

        // when
        Optional<ProductEntity> result = productDao.findById(id);

        // then
        assertThat(result).isPresent();

        ProductEntity product = result.get();
        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getImageUrl()).isEqualTo(imageUrl);
    }

    @Test
    void 상품_id_목록으로_상품_목록_조회() {
        // given
        Long id1 = productDao.save(new ProductEntity("아이스크림", 1_000, "https://yeonyeon.tistory.com"));
        Long id2 = productDao.save(new ProductEntity("마카롱", 2_000, "https://yeonyeon.tistory.com"));
        List<Long> ids = List.of(id1, id2);

        // when
        List<ProductEntity> products = productDao.findByIds(ids);

        // then
        assertThat(상품_아이디_목록(products)).containsOnly(id1, id2);
    }

    @Test
    void 빈_상품_id_목록으로_상품_목록_조회() {
        // given

        // when
        List<ProductEntity> products = productDao.findByIds(new ArrayList<>());

        // then
        assertThat(products).isEmpty();
    }

    @Test
    void 상품_목록_조회() {
        // given
        Long id1 = productDao.save(new ProductEntity("아이스크림", 1_000, "https://yeonyeon.tistory.com"));
        Long id2 = productDao.save(new ProductEntity("마카롱", 2_000, "https://yeonyeon.tistory.com"));

        // when
        List<ProductEntity> products = productDao.findAll();

        // then
        assertThat(상품_아이디_목록(products)).containsOnly(id1, id2);
    }

    private List<Long> 상품_아이디_목록(List<ProductEntity> products) {
        return products.stream()
                .map(ProductEntity::getId)
                .collect(Collectors.toUnmodifiableList());
    }

    @Test
    void 상품_삭제() {
        // given
        Long id = productDao.save(new ProductEntity("초콜렛", 1_000, "www.test.com"));

        // when
        productDao.delete(id);

        // then
        Optional<ProductEntity> product = productDao.findById(id);
        assertThat(product).isEmpty();
    }
}
