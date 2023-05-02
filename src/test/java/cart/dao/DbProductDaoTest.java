package cart.dao;

import cart.entity.Product;
import cart.entity.dao.DbProductDao;
import cart.entity.dao.ProductDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class DbProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new DbProductDao(jdbcTemplate, dataSource);
    }

    @Test
    void saveTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));

        List<Product> products = productDao.findAll();

        assertThat(products).contains(gitchan);
    }

    @Test
    void findByIdTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));

        Product foundProduct = productDao.findById(gitchan.getId());

        assertThat(foundProduct).isEqualTo(gitchan);
    }

    @Test
    void updateTest() {
//        productDao.save(new Product(1L, "깃짱", "gitchan.img", 1000));
//        Product boxster = productDao.update(new Product(1L, "박스터", "boxster.img", 5000));
//
//        Product foundProduct = productDao.findById(boxster.getId());
//
//        assertTrue(boxster.getName().equals(foundProduct.getName()));
    }

    @Test
    void deleteTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));

        productDao.deleteById(gitchan.getId());

        List<Product> products = productDao.findAll();
        assertThat(products).doesNotContain(gitchan);
    }
}
