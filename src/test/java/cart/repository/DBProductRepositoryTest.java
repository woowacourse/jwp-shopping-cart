package cart.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cart.domain.ProductRepository;
import cart.dto.ProductDto;
import cart.dto.ProductRequestDto;

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
            + "    imgURL  VARCHAR(8000)    NOT NULL,"
            + "    price   INT             NOT NULL,"
            + "    PRIMARY KEY (id))");

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("product")
            .usingGeneratedKeyColumns("id");

        ProductRequestDto product1 = new ProductRequestDto("name1", "url1", 1000);
        ProductRequestDto product2 = new ProductRequestDto("name2", "url2", 2000);

        Map<String, Object> map1 = Map.of(
            "name", product1.getName(),
            "imgurl", product1.getImgUrl(),
            "price", product1.getPrice()
        );
        Map<String, Object> map2 = Map.of(
            "name", product2.getName(),
            "imgurl", product2.getImgUrl(),
            "price", product2.getPrice()
        );

        this.id1 = simpleJdbcInsert.executeAndReturnKey(map1).longValue();
        this.id2 = simpleJdbcInsert.executeAndReturnKey(map2).longValue();
    }

    @Test
    @DisplayName("상품 정보를 DB에 저장한다.")
    void save() {
        ProductDto product3 = new ProductDto(null, "name3", "url3", 3000);
        Long id3 = productRepository.save(product3);

        String sql = "SELECT * FROM product";
        assertThat(jdbcTemplate.query(sql, (rs, rn) -> rs).size()).isEqualTo(3);

    }

    @Test
    @DisplayName("ID로 상품 정보를 조회한다.")
    void findById() {
        ProductDto response = productRepository.findById(id1);
        assertAll(
            () -> assertThat(response.getName()).isEqualTo("name1"),
            () -> assertThat(response.getImgUrl()).isEqualTo("url1"),
            () -> assertThat(response.getPrice()).isEqualTo(1000)
        );

    }

    @Test
    @DisplayName("모든 상품 정보를 조회한다.")
    void findAll() {
        assertThat(productRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("ID에 해당하는 상품 정보를 수정한다.")
    void updateById() {
        ProductDto product = new ProductDto(id2, "name4", "url4", 4000);
        productRepository.updateById(product, id2);

        String sql = "SELECT id, name, imgurl, price FROM product WHERE id = ?";
        ProductDto productDto = jdbcTemplate.queryForObject(sql,
            (rs, rn) -> new ProductDto(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("imgurl"),
                rs.getInt("price")),
            id2);

        assertAll(
            () -> assertThat(productDto.getName()).isEqualTo(product.getName()),
            () -> assertThat(productDto.getImgUrl()).isEqualTo(product.getImgUrl()),
            () -> assertThat(productDto.getPrice()).isEqualTo(product.getPrice())
        );
    }

    @Test
    @DisplayName("ID에 해당하는 상품 정보를 삭제한다.")
    void deleteById() {
        productRepository.deleteById(id2);

        String sql = "SELECT * FROM product";
        assertThat(jdbcTemplate.query(sql, (rs, rn) -> rs).size()).isEqualTo(1);
    }
}
