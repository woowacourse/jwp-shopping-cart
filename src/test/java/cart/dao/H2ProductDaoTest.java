package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.entity.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@DisplayName("product 를 ")
@JdbcTest
class H2ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new H2ProductDao(jdbcTemplate);
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

        productDao.update(new Product(gitchan.getId(), "박스터", "boxster.img", 1500));

        Product findProduct = productDao.findById(gitchan.getId()).get();
        assertAll(
                () -> assertThat(findProduct.getName()).isEqualTo("박스터"),
                () -> assertThat(findProduct.getImgUrl()).isEqualTo("boxster.img"),
                () -> assertThat(findProduct.getPrice()).isEqualTo(1500)
        );
    }

    @DisplayName("삭제한다")
    @Test
    void deleteTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));

        productDao.deleteById(gitchan.getId());

        Product savedProduct = productDao.findById(gitchan.getId()).get();
        assertThat(savedProduct.isDelete()).isTrue();
    }

    @DisplayName("여러개의 cart의 product ID로 조회할 수 있다")
    @Test
    void findByIdsTest() {
        Product gitchan = productDao.save(new Product("깃짱", "gitchan.img", 1000000000));
        Product boxster = productDao.save(new Product("박스터", "boxster.img", 1000000000));

        List<Product> products = productDao.findByIds(List.of(gitchan.getId(), boxster.getId(), gitchan.getId()));

        assertThat(products).usingRecursiveComparison()
                .isEqualTo(List.of(gitchan, boxster, gitchan));
    }
}
