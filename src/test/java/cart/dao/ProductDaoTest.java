package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.product.Product;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@JdbcTest
class ProductDaoTest {

    private final ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("상품을 저장한다.")
    @Transactional
    @Test
    void shouldSaveProductWhenRequest() {
        final var productToSave = new Product("changer", 10, "urlisurl");
        long productId = productDao.save(productToSave);
        String sql = "SELECT id, name, price, image_url FROM product WHERE id = ?";

        Product productFromDb = jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price"),
                        resultSet.getString("image_url"))
                , productId);

        assertAll(
                () -> assertThat(productFromDb.getName()).isEqualTo("changer"),
                () -> assertThat(productFromDb.getPrice()).isEqualTo(10),
                () -> assertThat(productFromDb.getImageUrl()).isEqualTo("urlisurl")
        );
    }

    @DisplayName("상품 전체를 조회한다.")
    @Transactional
    @Test
    void shouldReturnAllProductsWhenRequest() {
        jdbcTemplate.update("INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)", "10", 100, "superUrl");
        jdbcTemplate.update("INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)", "100", 100, "superUrl");

        List<Product> products = productDao.findAll();

        assertAll(
                () -> assertThat(products).hasSize(2),
                () -> assertThat(products.get(0).getName()).isEqualTo("10"),
                () -> assertThat(products.get(0).getPrice()).isEqualTo(100),
                () -> assertThat(products.get(0).getImageUrl()).isEqualTo("superUrl"),
                () -> assertThat(products.get(1).getName()).isEqualTo("100")
        );
    }
}
