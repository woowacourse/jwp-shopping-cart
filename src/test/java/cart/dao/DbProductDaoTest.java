package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.entity.Product;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@DisplayName("product 를 ")
@JdbcTest
class DbProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new DbProductDao(jdbcTemplate);
    }

    @DisplayName("생성한다")
    @Test
    void saveTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));

        assertThat(productDao.findById(gitchan.getId())).isNotEmpty();
    }

    @DisplayName("id로 찾는다")
    @Test
    void findByIdTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));

        Optional<Product> foundProduct = productDao.findById(gitchan.getId());

        assertAll(
                () -> assertThat(foundProduct).isNotEmpty(),
                () -> assertThat(foundProduct.get()).isEqualTo(gitchan)
        );

    }

    @DisplayName("수정한다")
    @Test
    void updateTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));

        productDao.update(new Product(gitchan.getId(), "박스터", "boxster.img", 500));

        Product findProduct = productDao.findById(gitchan.getId()).get();
        assertAll(
                () -> assertThat(findProduct.getName()).isEqualTo("박스터"),
                () -> assertThat(findProduct.getImgUrl()).isEqualTo("boxster.img"),
                () -> assertThat(findProduct.getPrice()).isEqualTo(500)
        );
    }

    @DisplayName("삭제한다")
    @Test
    void deleteTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));

        productDao.deleteById(gitchan.getId());

        assertThat(productDao.findById(gitchan.getId())).isEmpty();
    }
}
