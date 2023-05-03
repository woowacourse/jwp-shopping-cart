package cart.dao;

import cart.domain.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Transactional
class ProductDaoImplTest {

    ProductDao productDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        productDao = new ProductDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("데이터를 추가한다")
    void insert() {
        productDao.insert(new ProductEntity("pizza", "img", 10000));

        assertThat(productDao.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("모든 데이터를 찾는다")
    void findAll() {
        productDao.insert(new ProductEntity("pizza", "img", 10000));
        productDao.insert(new ProductEntity("chicken", "img", 20000));

        assertThat(productDao.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("특정 Id에 해당하는 제품을 찾는다")
    void findById() {
        String name = "pizza";
        String image = "img";
        int price = 10000;
        ProductEntity product = new ProductEntity(name, image, price);

        int id = (int) productDao.insert(product);

        ProductEntity insertProduct = productDao.findById(id).get();

        assertAll(
                () -> assertThat(insertProduct.getName()).isEqualTo(name),
                () -> assertThat(insertProduct.getImage()).isEqualTo(image),
                () -> assertThat(insertProduct.getPrice()).isEqualTo(price)
        );
    }

    @Test
    @DisplayName("특정 id에 해당하는 제품을 제거한다.")
    void delete() {
        int id = (int) productDao.insert(new ProductEntity("pizza", "img", 10000));

        productDao.delete(id);

        assertThat(productDao.findAll()).hasSize(0);
    }
}