package cart.dao;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("/schema.sql")
@Sql("/data.sql")
class ProductDaoTest {

    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("save() : 물품을 저장할 수 있다.")
    void test_save() throws Exception {
        //given
        final String name = "피자";
        final int price = 10000;
        final String imageUrl = "imageUrl";

        final ProductEntity productEntity = new ProductEntity(name, price, imageUrl);

        //when
        final int beforeSize = productDao.findAll().size();

        productDao.save(productEntity);

        final int afterSize = productDao.findAll().size();

        //then
        assertEquals(afterSize, beforeSize + 1);
    }

    @Test
    @DisplayName("findAll() : 저장된 물품을 모두 조회할 수 있다.")
    void test_findAll() throws Exception {
        //given
        final int resultSize = 2;

        //when & then
        assertEquals(resultSize, productDao.findAll().size());
    }

    @Test
    @DisplayName("modify() : 저장된 물품을 수정할 수 있다.")
    void test_modify() throws Exception {
        //given
        final Long id = 3L;
        final String name = "수정된 피자";
        final int price = 20000;
        final String imageUrl = "수정된 imageUrl";

        final ProductEntity modifiedProductEntity = new ProductEntity(id, name, price, imageUrl);

        //when
        productDao.modify(modifiedProductEntity);

        final ProductEntity savedProductEntity = productDao.findAll()
                                                           .stream()
                                                           .filter(it -> it.getId().equals(id))
                                                           .findFirst()
                                                           .orElseThrow();

        //then
        assertAll(
                () -> assertEquals(name, savedProductEntity.getName()),
                () -> assertEquals(price, savedProductEntity.getPrice()),
                () -> assertEquals(imageUrl, savedProductEntity.getImageUrl())
        );
    }

    @Test
    @DisplayName("deleteById() : 저장된 물품을 삭제할 수 있다.")
    void test_deleteById() throws Exception {
        //given
        final Long id = 3L;

        //when
        final int beforeSize = productDao.findAll().size();

        productDao.deleteById(id);

        final int afterSize = productDao.findAll().size();

        //then
        assertEquals(afterSize, beforeSize - 1);
    }
}
