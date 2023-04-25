package cart.dao;

import cart.entity.product.ProductEntity;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
class ProductDaoTest {

    @Autowired
    private DataSource dataSource;
    
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(dataSource);
    }

    @Test
    @DisplayName("상품을 DB에 저장한다.")
    void save() {
        //given
        final ProductEntity dazzle = new ProductEntity(
            null,
            "다즐",
            "스플릿.com",
            10000000,
            "다즐짱"
        );

        //when
        final Long dazzleId = productDao.save(dazzle);

        //then
        Assertions.assertThat(dazzleId).isNotNull();
    }
}
