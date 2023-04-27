package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;


@Import(ProductDao.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("상품 전체 조회 테스트")
    @Test
    void findAll() {
        jdbcTemplate.update("INSERT INTO product (name, price) VALUES ('Chicken', 18000), ('Pizza', 24000)");

        List<ProductEntity> all = productDao.findAll();

        assertThat(all).hasSize(2);
        assertThat(all).extracting("name")
                .contains("Chicken", "Pizza");
        assertThat(all).extracting("price")
                .contains(18000, 24000);
    }

    @DisplayName("상품 저장 테스트")
    @Test
    void saveProduct() {
        String productName = "ProductA";
        ProductEntity productEntity = new ProductEntity(
                null,
                productName,
                10_000,
                "ETC",
                "naver.com"
        );

        productDao.insert(productEntity);

        List<ProductEntity> allEntities = productDao.findAll();
        assertThat(allEntities).hasSize(1);
        assertThat(allEntities.get(0).getName()).isEqualTo(productName);
    }

    @DisplayName("단일 상품 삭제 테스트")
    @Test
    void deleteProduct() {
        String productName = "ProductA";
        ProductEntity productEntity = new ProductEntity(
                null,
                productName,
                10_000,
                "ETC",
                "naver.com"
        );
        Long savedId = productDao.insert(productEntity);
        assertThat(productDao.findAll()).hasSize(1);

        productDao.deleteById(savedId);

        List<ProductEntity> allEntities = productDao.findAll();
        assertThat(allEntities).hasSize(0);
    }

    @DisplayName("상품 수정 테스트")
    @Test
    void updateProduct() {
        ProductEntity productEntity = new ProductEntity(
                null,
                "Pizza",
                10_000,
                "FOOD",
                "pizza.com"
        );
        Long savedId = productDao.insert(productEntity);

        ProductEntity updatedProductEntity = new ProductEntity(
                savedId,
                "Chicken",
                20_000,
                "FOOD",
                "chicken.com"
        );
        productDao.update(updatedProductEntity);

        List<ProductEntity> allProducts = productDao.findAll();
        assertThat(allProducts).hasSize(1);
        ProductEntity savedProduct = allProducts.get(0);
        assertThat(savedProduct.getId()).isEqualTo(savedId);
        assertThat(savedProduct.getName()).isEqualTo("Chicken");
        assertThat(savedProduct.getPrice()).isEqualTo(20_000);
        assertThat(savedProduct.getCategory()).isEqualTo("FOOD");
        assertThat(savedProduct.getImageUrl()).isEqualTo("chicken.com");
    }
}
