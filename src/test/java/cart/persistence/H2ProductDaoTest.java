package cart.persistence;

import cart.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Sql({"/data.sql", "/dummy-product.sql"})
class H2ProductDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    ProductDao dao;

    @BeforeEach
    void setUp() {
        dao = new H2ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("id가 1인 Product를 조회한다")
    void findByIdTest() {
        Product product1 = dao.findById(1L);

        assertThat(product1.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("삽입과 함께 keyHolder를 반환하고 findById로 조회하여 삽입을 검증한다")
    void insertAndGetKeyHolderTest() {
        Product dummy6 = new Product("dummy6", "test-url-dummy6", new BigDecimal("6000"));

        Long keyHolder = dao.insertAndGetKeyHolder(dummy6);
        Product inserted = new Product(keyHolder, dummy6.getName(), dummy6.getImageUrl(), dummy6.getPrice());

        Product found = dao.findById(keyHolder);

        assertThat(found).isEqualTo(inserted);
    }

    @Test
    @DisplayName("모든 레코드를 조회하면 5개이다.")
    void findAllTest() {
        assertThat(dao.findAll().size()).isEqualTo(5);
    }

    @Test
    @DisplayName("id가 3인 Product를 수정한다")
    void updateTest() {
        Long id = 3L;
        String name = "updated";
        String imageUrl = "updated-url";
        BigDecimal price = new BigDecimal("1000");

        Product updated = new Product(id, name, imageUrl, price);

        dao.update(updated);
        Product fined = dao.findById(id);

        assertThat(fined).isEqualTo(updated);
    }

    @Test
    @DisplayName("전체 레코드가 5개 일 때 id가 3인 Product를 삭제하면 전체 레코드의 개수는 4이다")
    void deleteTest() {
        dao.delete(3L);

        assertThat(dao.findAll().size()).isEqualTo(4);
    }
}
