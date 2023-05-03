package cart.dao;

import cart.persistance.dao.ProductDao;
import cart.persistance.entity.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Sql("/test.sql")
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
    @Test
    void shouldSaveProductWhenRequest() {
        final Product productToSave = Product.createWithoutId("changer", 10, "domain.com");
        final long productId = productDao.add(productToSave);
        final String sql = "SELECT id, name, price, image_url FROM product WHERE id = ?";

        final Product productFromDb = jdbcTemplate.queryForObject(sql,
                (resultSet, rowNumber) -> Product.create(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price"),
                        resultSet.getString("image_url"))
                , productId);

        assertAll(
                () -> assertThat(productFromDb.getName()).isEqualTo("changer"),
                () -> assertThat(productFromDb.getPrice()).isEqualTo(10),
                () -> assertThat(productFromDb.getImageUrl()).isEqualTo("domain.com")
        );
    }

    @DisplayName("상품 전체를 조회한다.")
    @Test
    void shouldReturnAllProductsWhenRequest() {

        final List<Product> products = productDao.findAll();

        assertThat(products).hasSize(2);
    }

    @DisplayName("상품을 수정한다.")
    @Test
    void shouldUpdateWhenRequest() {

        //given
        final Product productToUpdate = Product.create(
                1L,
                "당근",
                1000,
                "domain.kr"
        );

        //when
        productDao.update(productToUpdate);

        Product productAfterUpdate = jdbcTemplate.queryForObject(
                "SELECT id, name, price, image_url FROM product WHERE id = ?",
                (resultSet, rowNumber) -> Product.create(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price"),
                        resultSet.getString("image_url")
                ), 1);

        //then
        assertAll(
                () -> assertThat(productAfterUpdate.getId()).isEqualTo(productToUpdate.getId()),
                () -> assertThat(productAfterUpdate.getName()).isEqualTo(productToUpdate.getName()),
                () -> assertThat(productAfterUpdate.getPrice()).isEqualTo(productToUpdate.getPrice()),
                () -> assertThat(productAfterUpdate.getImageUrl()).isEqualTo(productToUpdate.getImageUrl())
        );
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void shouldDeleteWhenRequest() {

        productDao.deleteById(1);

        List<Product> products = jdbcTemplate.query(
                "SELECT id, name, price, image_url FROM product",
                (resultSet, rowNumber) -> Product.create(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price"),
                        resultSet.getString("image_url")
                ));

        assertThat(products).hasSize(1);
    }
}
