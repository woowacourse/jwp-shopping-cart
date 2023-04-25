package cart.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cart.domain.ProductRepository;
import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;

@JdbcTest
class DBProductRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private ProductRepository productRepository;
    private Long id1;
    private Long id2;

    @BeforeEach
    void setUp() {
        productRepository = new DBProductRepository(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE product IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE product ("
            + "    id      BIGINT            NOT NULL    AUTO_INCREMENT,"
            + "    name    VARCHAR(255)    NOT NULL,"
            + "    imgURL  VARCHAR(255)    NOT NULL,"
            + "    price   INT             NOT NULL,"
            + "    PRIMARY KEY (id))");

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("product")
            .usingGeneratedKeyColumns("id");

        ProductRequestDto product1 = new ProductRequestDto("name1", "url1", 1000);
        ProductRequestDto product2 = new ProductRequestDto("name2", "url2", 2000);

        Map<String, Object> map1 = Map.of(
            "name", product1.getName(),
            "imgurl", product1.getImgURL(),
            "price", product1.getPrice()
        );
        Map<String, Object> map2 = Map.of(
            "name", product2.getName(),
            "imgurl", product2.getImgURL(),
            "price", product2.getPrice()
        );

        this.id1 = simpleJdbcInsert.executeAndReturnKey(map1).longValue();
        this.id2 = simpleJdbcInsert.executeAndReturnKey(map2).longValue();
    }

    @Test
    void save() {
        ProductRequestDto product3 = new ProductRequestDto("name3", "url3", 3000);
        Long id3 = productRepository.save(product3);

        String sql = "SELECT * FROM product";
        assertThat(jdbcTemplate.query(sql, (rs, rn) -> rs).size()).isEqualTo(3);

    }

    @Test
    void findById() {
        ProductResponseDto response = productRepository.findById(id1);
        assertAll(
            () -> assertThat(response.getName()).isEqualTo("name1"),
            () -> assertThat(response.getImageUrl()).isEqualTo("url1"),
            () -> assertThat(response.getPrice()).isEqualTo(1000)
        );

    }

    @Test
    void findAll() {
        assertThat(productRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void updateById() {
        ProductRequestDto product = new ProductRequestDto("name4", "url4", 4000);
        productRepository.updateById(product, id2);

        String sql = "SELECT id, name, imgurl, price FROM product WHERE id = ?";
        ProductResponseDto productResponseDto = jdbcTemplate.queryForObject(sql,
            (rs, rn) -> new ProductResponseDto(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("imgurl"),
                rs.getInt("price")),
            id2);

        assertAll(
            () -> assertThat(productResponseDto.getName()).isEqualTo(product.getName()),
            () -> assertThat(productResponseDto.getImageUrl()).isEqualTo(product.getImgURL()),
            () -> assertThat(productResponseDto.getPrice()).isEqualTo(product.getPrice())
        );
    }

    @Test
    void deleteById() {
        productRepository.deleteById(id2);

        String sql = "SELECT * FROM product";
        assertThat(jdbcTemplate.query(sql, (rs, rn) -> rs).size()).isEqualTo(1);
    }
}
