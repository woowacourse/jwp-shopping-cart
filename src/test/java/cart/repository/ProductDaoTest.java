package cart.repository;

import cart.dto.ProductRequestDto;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
    @DisplayName("상품 생성 성공")
    void create_success() {
        //given
        final ProductEntity request = new ProductEntity(
                "홍실",
                "https://velog.velcdn.com/images/hong-sile/post/e794b8e7-ad0e-4373-9eae-8f9eb823f89e/image.png",
                100);

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
    @DisplayName("상품 전체 조회 성공")
    @Sql(scripts = "/dummy_data.sql")
    void findALl_success() {
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
    @DisplayName("상품 수정 성공")
    @Sql(scripts = "/dummy_data.sql")
    void update_success() {
        //given
        final ProductRequestDto requestDto = new ProductRequestDto("푸우", "pooh.png", 1_000_001);
        final Integer targetId = findPoohId();

        //when
        productDao.update(requestDto, targetId);
        final String sql = "select * from product where id = ?";
        final ProductEntity updatedProduct = jdbcTemplate.queryForObject(sql,
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
    @DisplayName("상품 수정 실패 - 존재하지 않는 상품 id")
    @Sql(scripts = "/dummy_data.sql")
    void update_fail_invalid_product_id() {
        //given
        final ProductRequestDto requestDto = new ProductRequestDto("푸우", "pooh.png", 1_000_001);
        final int invalidId = 0;

        //expect
        assertThatThrownBy(() -> productDao.update(requestDto, invalidId))
                .isInstanceOf(DataAccessException.class);
    }

    @Test
    @DisplayName("상품 삭제 성공")
    @Sql(scripts = "/dummy_data.sql")
    void delete_success() {
        //given
        final Integer poohId = findPoohId();

        //when
        productDao.delete(poohId);

        //then
        String sql = "select * from product where id = ?";
        assertThatThrownBy(() -> jdbcTemplate.queryForObject(sql, Integer.class, poohId))
                .isInstanceOf(DataAccessException.class);
    }

    @Test
    @DisplayName("상품 삭제 실패 - 존재하지 않는 상품 id")
    @Sql(scripts = "/dummy_data.sql")
    void delete_fail() {
        //given
        final int invalidId = 0;

        //expect
        assertThatThrownBy(() -> productDao.delete(invalidId))
                .isInstanceOf(DataAccessException.class);
    }

    private Integer findPoohId() {
        final String sql = "select id from product where name = 'pooh'";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
