package cart.repository;

import cart.dto.ProductRequestDto;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    @DisplayName("상품 생성 테스트")
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

    @Test
    @DisplayName("상품 전체 조회 테스트")
    @Sql(scripts = "/dummy_data.sql")
    void findALl() {
        //given,when
        final List<ProductEntity> allProducts = productDao.findAll();

        //then
        assertAll(
                () -> assertThat(allProducts).hasSize(3),
                () -> assertThat(allProducts.get(0).getName()).isEqualTo("pooh"),
                () -> assertThat(allProducts.get(0).getImage()).isEqualTo("pooh.jpg"),
                () -> assertThat(allProducts.get(0).getPrice()).isEqualTo(1_000_000),
                () -> assertThat(allProducts.get(2).getPrice()).isEqualTo(10)
        );
    }

    @Test
    @DisplayName("수정 테스트")
    @Sql(scripts = "/dummy_data.sql")
    void update() {
        //given
        final ProductRequestDto requestDto = new ProductRequestDto("푸우", "pooh.png", 1_000_001);
        final Integer targetId = findPoohId();

        //when
        productDao.update(requestDto, targetId);
        final String findUpdatedUserSql = "select * from product where id = ?";
        final ProductEntity updatedProduct = jdbcTemplate.queryForObject(findUpdatedUserSql,
                (rs, rowNum) -> new ProductEntity(
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getInt("price")
                ),
                targetId);

        //then
        assertAll(
                () -> assertThat(updatedProduct.getName()).isEqualTo("푸우"),
                () -> assertThat(updatedProduct.getImage()).isEqualTo("pooh.png"),
                () -> assertThat(updatedProduct.getPrice()).isEqualTo(1_000_001)
        );
    }

    @Test
    @DisplayName("유저의 정보를 삭제한다")
    @Sql(scripts = "/dummy_data.sql")
    void delete() {
        //given
        final Integer poohId = findPoohId();

        //when
        productDao.delete(poohId);

        //then
        String sql = "select * from product where id = ?";
        assertThatThrownBy(() -> jdbcTemplate.queryForObject(sql, Integer.class, poohId))
                .isInstanceOf(DataAccessException.class);
    }

    private Integer findPoohId() {
        final String sql = "select id from product where name = 'pooh'";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
