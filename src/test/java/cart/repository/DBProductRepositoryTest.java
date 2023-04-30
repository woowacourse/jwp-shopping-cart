package cart.repository;

import cart.domain.Product;
import cart.domain.ProductRepository;
import cart.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

        Product product1 = Product.createWithoutId("name1", "url1", 1000);
        Product product2 = Product.createWithoutId("name2", "url2", 2000);

        this.id1 = productRepository.save(new ProductDto(product1));
        this.id2 = productRepository.save(new ProductDto(product2));
    }

    @Test
    @DisplayName("상품 정보를 DB에 저장한다.")
    void save() {
        ProductDto product3 = new ProductDto(null, "name3", "url3", 3000);

        productRepository.save(product3);
        List<ProductDto> products = productRepository.findAll();
        assertThat(products).hasSize(3);
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
        List<ProductDto> products = productRepository.findAll();
        assertThat(products).hasSize(2);
    }

    @Test
    @DisplayName("ID에 해당하는 상품 정보를 수정한다.")
    void updateById() {
        ProductDto product = new ProductDto(id2, "name4", "url4", 4000);
        productRepository.updateById(product, id2);

        ProductDto findProduct = productRepository.findById(id2);

        assertAll(
            () -> assertThat(findProduct.getName()).isEqualTo(product.getName()),
            () -> assertThat(findProduct.getImgUrl()).isEqualTo(product.getImgUrl()),
            () -> assertThat(findProduct.getPrice()).isEqualTo(product.getPrice())
        );
    }

    @Test
    @DisplayName("ID에 해당하는 상품 정보를 삭제한다.")
    void deleteById() {
        productRepository.deleteById(id2);

        List<ProductDto> products = productRepository.findAll();
        assertThat(products).hasSize(1);
    }
}
