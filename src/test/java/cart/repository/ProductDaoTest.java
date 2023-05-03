package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.product.ImageUrl;
import cart.domain.product.Price;
import cart.domain.product.Product;
import cart.domain.product.ProductName;
import cart.entity.ProductEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class ProductDaoTest {
    private static final Product PRODUCT = new Product(new ProductName("글렌피딕"), new Price(230_000),
            ImageUrl.from("https://image.com/image.png"));

    @Autowired
    JdbcTemplate jdbcTemplate;

    ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("상품이 정상적으로 저장되어야 한다.")
    void save_success() {
        // given

        // when
        ProductEntity productEntity = productDao.save(PRODUCT);

        // then
        List<ProductEntity> allProducts = productDao.findAll();
        assertThat(allProducts)
                .hasSize(1);
        assertThat(allProducts.get(0))
                .isEqualTo(productEntity);
    }

    @Test
    @DisplayName("상품이 정상적으로 수정되어야 한다.")
    void update_success() {
        // given
        ProductEntity productEntity = productDao.save(PRODUCT);

        ProductEntity updateProduct = ProductEntity.Builder.builder()
                .id(productEntity.getId())
                .name("글렌리벳")
                .price(150_000)
                .imageUrl("yes")
                .build();

        // when
        productDao.update(updateProduct);

        // then
        ProductEntity foundProduct = productDao.findAll().get(0);
        assertThat(foundProduct)
                .isEqualTo(updateProduct);
    }

    @Test
    @DisplayName("상품이 정상적으로 삭제되어야 한다.")
    void delete_success() {
        // given
        ProductEntity productEntity = productDao.save(PRODUCT);

        // when
        productDao.deleteById(productEntity.getId());

        // then
        List<ProductEntity> allProducts = productDao.findAll();

        assertThat(allProducts)
                .isEmpty();
    }
}
