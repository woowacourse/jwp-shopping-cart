package cart.dao;

import cart.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

@JdbcTest
class ProductDaoTest {

    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("상품을 저장할 수 있다")
    @Test
    void save() {
        //given
        ProductDto product = new ProductDto("연어", "", 1000);

        //when
        productDao.save(product);

        //then
        assertThat(countRowsInTableWhere(jdbcTemplate, "products", "name LIKE '연어'"))
                .isEqualTo(1);
    }

    @DisplayName("전체 상품을 조회할 수 있다")
    @Test
    void findAll() {
        //given
        List<ProductDto> products = List.of(new ProductDto("연어", "", 1000),
                new ProductDto("오션", "", 1000),
                new ProductDto("동해", "", 1000));

        products.forEach(productDao::save);

        //then
        assertThat(productDao.findAll()).hasSize(3);
    }

    @DisplayName("상품을 업데이트 할 수 있다")
    @Test
    void update() {
        //given
        ProductDto original = productDao.save(new ProductDto("오션", "", 1000));
        ProductDto newProduct = new ProductDto(original.getId(), "연어", "", 1000);

        //when
        productDao.update(newProduct);
        List<ProductDto> products = productDao.findAll();

        //then
        assertThat(products.get(0)).usingRecursiveComparison().isEqualTo(newProduct);
    }

    @DisplayName("상품을 삭제할 수 있다")
    @Test
    void delete() {
        //given
        ProductDto original = productDao.save(new ProductDto("오션", "", 1000));

        //when
        productDao.delete(original.getId());

        //then
        assertThat(countRowsInTableWhere(jdbcTemplate, "products", "name LIKE '오션'"))
                .isEqualTo(0);
    }
}
