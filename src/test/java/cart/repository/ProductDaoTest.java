package cart.repository;

import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class ProductDaoTest {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    @DisplayName("정상적인 request 는 정상 생성한다.")
    void create() {
        //given
        final ProductEntity request = new ProductEntity("test", "dully.jpg", 100);

        //when
        final int id = productDao.create(request);
        final String sql = "select * from product";
        final ProductEntity result = jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ProductEntity(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getInt("price")
                ));


        //then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(id),
                () -> assertThat(result.getName()).isEqualTo(request.getName()),
                () -> assertThat(result.getImage()).isEqualTo(request.getImage()),
                () -> assertThat(result.getPrice()).isEqualTo(request.getPrice())
        );
    }

}
