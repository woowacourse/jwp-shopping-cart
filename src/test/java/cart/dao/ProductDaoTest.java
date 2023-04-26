package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.entity.ProductEntity;
import cart.dao.entity.ProductEntity.Builder;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;
    private ProductEntity productEntity = new ProductEntity.Builder()
            .name("cuteSeongHaDoll")
            .imgUrl("https://avatars.githubusercontent.com/u/95729738?v=4")
            .price(25000)
            .build();

    @BeforeEach
    void setUp() {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("전체 상품을 조회한다.")
    @Test
    void findAll() {
        //given
        productDao.insert(productEntity);
        //when
        List<ProductEntity> products = productDao.findAll();
        //then
        assertThat(products.size()).isEqualTo(1);
    }

    @DisplayName("상품을 저장한다.")
    @Test
    void save() {
        // when
        long savedId = productDao.insert(productEntity);

        // then
        assertThat(productDao.findAll().get(0).getId()).isEqualTo(savedId);
    }

    @DisplayName("상품 정보를 수정한다.")
    @Test
    void update() {
        // given
        long savedId = productDao.insert(productEntity);
        ProductEntity updateProductEntity = new Builder()
                .id(savedId)
                .name("cuteBaronDoll")
                .price(100000)
                .imgUrl("https://avatars.githubusercontent.com/u/70891072?v=4")
                .build();

        // when
        productDao.update(updateProductEntity);

        // then
        assertThat(productDao.findAll().get(0))
                .usingRecursiveComparison()
                .isEqualTo(updateProductEntity);
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void delete() {
        // given
        long savedId = productDao.insert(productEntity);

        // when
        productDao.delete(savedId);

        // then
        assertThat(productDao.findAll()).hasSize(0);
    }
}