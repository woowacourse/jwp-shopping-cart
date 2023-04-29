package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class DbProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new DbProductDao(jdbcTemplate);
    }

    @Test
    void saveTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));

        List<Product> products = productDao.findAll();

        assertThat(products).containsExactly(gitchan);
    }

    @Test
    void findByIdTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));

        Optional<Product> foundProduct = productDao.findById(gitchan.getId());

        assertThat(foundProduct).isNotEmpty();
        assertThat(foundProduct.get()).isEqualTo(gitchan);
    }

    @Test
    void updateTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));

        Product boxster = productDao.update(new Product(gitchan.getId(), "박스터", "boxster.img", 500));

        List<Product> products = productDao.findAll();
        assertThat(products)
                .map(Product::getName)
                .containsExactly(boxster.getName());
    }

    @Test
    void deleteTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));

        productDao.deleteById(gitchan.getId());

        List<Product> products = productDao.findAll();
        assertThat(products).doesNotContain(gitchan);
    }
}
